package com.leszek.comicpull.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.leszek.comicpull.classes.Comic;
import com.leszek.comicpull.services.ComicService;

import java.util.List;

public class XKCDComicServiceImpl implements ComicService {

    /**
     * Returns the 10 latest comics from xkcd
     * @return      List of comics xkcd
     * @see         Comic
     */
    @Override
    public List<Comic> getComics() {
        return null;
    }

    /**
     * Extracts a Comic object from Json
     * @param pJson Json of the comic
     * @return      Comic object
     * @see         Comic
     */
    @Override
    public Comic getComicFromJson(JsonNode pJson) {
        return null;
    }
}
