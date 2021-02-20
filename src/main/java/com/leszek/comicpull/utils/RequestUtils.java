package com.leszek.comicpull.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestUtils {
    private final static Logger log = LoggerFactory.getLogger(RequestUtils.class);

    public JsonNode getJson(String url) {
        try {
            HttpResponse response = HttpRequest.get(url).acceptJson().send();
            if (response.statusCode() == HttpStatus.HTTP_OK) {
                String contentType = response.contentType();
                if(contentType.contains("json")){
                    //return json
                    return new ObjectMapper().readTree(response.bodyBytes());
                }else if (contentType.contains("xml")){
                    // convert xml to json using jackson for easier processing later;
                    return new XmlMapper().readTree(response.bodyBytes());
                }else{
                    // unknown response type
                    return null;
                }
            }
        } catch (Exception e) {
            log.error("failed to get Json from url: {}", url);
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
