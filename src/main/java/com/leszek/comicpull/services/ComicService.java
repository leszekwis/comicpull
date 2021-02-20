package com.leszek.comicpull.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.leszek.comicpull.classes.Comic;

import java.util.List;

public interface ComicService {
    public List<Comic> getComics();
    public Comic getComicFromJson(JsonNode pJson);
}
