package com.leszek.comicpull.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.leszek.comicpull.classes.Comic;
import com.leszek.comicpull.services.ComicService;
import com.leszek.comicpull.utils.RequestUtils;
import com.typesafe.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class XKCDComicServiceImpl implements ComicService {
    private static final Logger log = LoggerFactory.getLogger(XKCDComicServiceImpl.class);

    @Inject
    private RequestUtils requestUtils;

    @Inject
    private Config conf;

    /**
     * Returns the 10 latest comics from xkcd
     * @return      List of comics xkcd
     * @see         Comic
     */
    @Override
    public List<Comic> getComics() {
        List<Comic> comics = new ArrayList<>();
        String specificComicUrl = conf.getString("xkcdNumUrl");
        JsonNode latestComic = getLatestComic();
        if (latestComic != null) {
            // get latest comic number
            comics.add(getComicFromJson(latestComic));
            int latestComicNumber = latestComic.get("num").asInt();
            for (int i = 1; i < 10; i++) {
                JsonNode comic = requestUtils.getJson(String.format(specificComicUrl, latestComicNumber - i));
                if (comic != null) {
                    comics.add(getComicFromJson(comic));
                }
            }

        }
        return comics;
    }

    /**
     * Extracts a Comic object from Json
     * @param pJson Json of the comic
     * @return      Comic object
     * @see         Comic
     */
    @Override
    public Comic getComicFromJson(JsonNode pJson) {
        Calendar cal = Calendar.getInstance();
        // xkcd comics dont have a publishing time only date so set it to start of the day;
        cal.set(pJson.get("year").asInt(), pJson.get("month").asInt()-1, pJson.get("day").asInt(),0, 0, 0);
        Date pubDate = cal.getTime();
        return new Comic(
                pJson.get("title").asText(),
                pJson.get("img").asText(),
                String.format(conf.getString("xkcdWebUrl"),
                        pJson.get("num").asInt()),
                pubDate
        );
    }

    /**
     * Returns the latest comic from xkcd
     * @return      Latest comic from xkcs
     */
    private JsonNode getLatestComic() {
        // grab the latest comic so we have the full one and the latest number to grab the rest.
        return requestUtils.getJson(conf.getString("xkcdLatestUrl"));
    }
}
