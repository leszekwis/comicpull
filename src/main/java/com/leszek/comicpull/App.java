package com.leszek.comicpull;

import com.leszek.comicpull.classes.Comic;
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
      log.info("getting comics");
      List<Comic> comicList = new ArrayList<>();
      //todo add logic
      return comicList;
    });
  }

  public static void main(final String[] args) {
    runApp(args, App::new);
  }

}
