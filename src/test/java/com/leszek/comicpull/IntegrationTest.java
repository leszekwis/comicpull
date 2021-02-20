package com.leszek.comicpull;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.common.collect.Comparators;
import com.leszek.comicpull.classes.Comic;
import io.jooby.JoobyTest;
import io.jooby.StatusCode;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JoobyTest(App.class)
public class IntegrationTest {

  static OkHttpClient client = new OkHttpClient();

  @Test
  public void shouldSayHi(int serverPort) throws IOException {
    Request req = new Request.Builder()
        .url("http://localhost:" + serverPort)
        .build();

    try (Response rsp = client.newCall(req).execute()) {
      assertEquals("Welcome to ComicPull!", rsp.body().string());
      assertEquals(StatusCode.OK.value(), rsp.code());
    }
  }

  @Test
  public void getComics(int serverPort) throws IOException {
    Request req = new Request.Builder()
            .url("http://localhost:" + serverPort+"/comics")
            .build();

    try (Response rsp = client.newCall(req).execute()) {

      //check that json returns
      assertEquals("application/json;charset=UTF-8", rsp.header("Content-Type"), "Response not Json");
      assertEquals(StatusCode.OK.value(), rsp.code());

      ObjectMapper mapper = new ObjectMapper();
      mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
      ArrayNode comics = (ArrayNode) mapper.readTree(rsp.body().string());
      List<Comic> comicsList = mapper.convertValue(comics, new TypeReference<List<Comic>>() {});


      //verify 20 comics returned
      assertEquals(20, comicsList.size(),"20 comics not in the response");

      boolean isInDescendingOrder = Comparators.isInOrder(comicsList, (o1, o2) -> o2.getPublicationDate().compareTo(o1.getPublicationDate()));

      assertTrue(isInDescendingOrder, "Comics are not in descending order");
    }
  }
}
