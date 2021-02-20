package com.leszek.comicpull.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.leszek.comicpull.classes.Comic;
import com.leszek.comicpull.services.ComicService;
import com.leszek.comicpull.utils.RequestUtils;
import com.typesafe.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PDLComicServiceImpl implements ComicService {
    private static final Logger log = LoggerFactory.getLogger(PDLComicServiceImpl.class);

    @Inject
    private RequestUtils requestUtils;

    @Inject
    private Config conf;



    /**
     * Returns the 10 latest comics from Poorly Drawn Lines
     * @return      List of comics from Poorly Drawn Lines
     * @see         Comic
     */
    @Override
    public List<Comic> getComics() {
        log.info("getting pdl comics");
        String url = conf.getString("pdlUrl");
        List<Comic> comicList = new ArrayList<>();

        // get the converted rss feed to json;
        JsonNode pdlFeed = requestUtils.getJson(url);
        if(pdlFeed !=null){
            if(pdlFeed.has("channel")){
                JsonNode channel = pdlFeed.get("channel");
                if(channel.has("item")){
                    ArrayNode items = (ArrayNode) channel.get("item");
                    //loop through each <item> from the rss feed
                    items.forEach(feedItem ->{
                        comicList.add(getComicFromJson(feedItem));
                    });
                }
            }
        }
        log.info("got {} pdl comics", comicList.size());
        return comicList;
    }

    /**
     * Extracts a Comic object from Json
     * @param pJson Json of the comic
     * @return      Comic object
     * @see         Comic
     */
    @Override
    public Comic getComicFromJson(JsonNode pJson) {
        log.info("getting comic from json: {}", pJson);
        Date pubDate = null;
        // RSS dates are in RFC-822 date format
        DateFormat dateFormatterRssPubDate = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
        try {
            pubDate = dateFormatterRssPubDate.parse(pJson.get("pubDate").asText());
        } catch (ParseException e) {
            log.error("error getting date  from pubDate: {}",pJson.get("pubDate").asText());
        }
        String title = pJson.get("title").asText();
        String webUrl = pJson.get("origLink").asText();
        String encodedHtml = pJson.get("encoded").asText();
        // grab img url from encoded as it links directly to the image;
        int start = encodedHtml.indexOf("img loading=\"lazy\" src=\"") + "img loading=\"lazy\" src=\"".length();
        int end = encodedHtml.indexOf("\"", start);
        String imgUrl = encodedHtml.substring(start, end);
        return new Comic(title, imgUrl, webUrl, pubDate);
    }
}
