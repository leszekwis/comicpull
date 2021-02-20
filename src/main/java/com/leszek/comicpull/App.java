package com.leszek.comicpull;

import com.leszek.comicpull.classes.Comic;
import com.leszek.comicpull.services.impl.PDLComicServiceImpl;
import com.leszek.comicpull.services.impl.XKCDComicServiceImpl;
import io.jooby.Jooby;
import io.jooby.di.GuiceModule;
import io.jooby.json.JacksonModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class App extends Jooby {
private static final Logger log = LoggerFactory.getLogger(App.class);
  {
    // add dependency Injection
    install(new GuiceModule());
    // add Jackson
    install(new JacksonModule());

    get("/", ctx -> "Welcome to Jooby!");

    get("/comics", ctx -> {
      log.info("getting all comics");
      PDLComicServiceImpl pdlComicService = require(PDLComicServiceImpl.class);
      XKCDComicServiceImpl xkcdComicService = require(XKCDComicServiceImpl.class);

      List<Comic> comicList = new ArrayList<>();

      //add all the comics to the list
      comicList.addAll(pdlComicService.getComics());
      comicList.addAll(xkcdComicService.getComics());
      log.info("got {} comics total", comicList.size());
      // sort comics in descending order;
      comicList.sort((comic1, comic2)-> comic2.getPublicationDate().compareTo(comic1.getPublicationDate()));

      return comicList;
    });
  }

  public static void main(final String[] args) {
    runApp(args, App::new);
  }

}
