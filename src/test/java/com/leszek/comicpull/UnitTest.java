package com.leszek.comicpull;

import com.google.common.collect.Comparators;
import com.google.common.collect.Lists;
import com.leszek.comicpull.classes.Comic;
import com.leszek.comicpull.services.impl.PDLComicServiceImpl;
import com.leszek.comicpull.services.impl.XKCDComicServiceImpl;
import io.jooby.MockRouter;
import io.jooby.StatusCode;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UnitTest {
  @Test
  public void welcome() {
    MockRouter router = new MockRouter(new App());
    router.get("/", rsp -> {
      assertEquals("Welcome to ComicPull!", rsp.value());
      assertEquals(StatusCode.OK, rsp.getStatusCode());
    });
  }

  @Test
  public void sortTest(){
    PDLComicServiceImpl pdlService = mock(PDLComicServiceImpl.class);
    XKCDComicServiceImpl xkcdService = mock(XKCDComicServiceImpl.class);
    when(pdlService.getComics()).thenReturn(Lists.newArrayList(
            new Comic("pdlTitle1","pdlImgUrl1","pdlWebUrl1", new Date(ThreadLocalRandom.current().nextInt() * 1000L) ),
            new Comic("pdlTitle1","pdlImgUrl2","pdlWebUrl2", new Date(ThreadLocalRandom.current().nextInt() * 1000L) ),
            new Comic("pdlTitle1","pdlImgUrl3","pdlWebUrl3", new Date(ThreadLocalRandom.current().nextInt() * 1000L) )
            )
    );
    when(xkcdService.getComics()).thenReturn(Lists.newArrayList(
            new Comic("xkcdTitle1","xkcdImgUrl1","xkcdWebUrl1", new Date(ThreadLocalRandom.current().nextInt() * 1000L) ),
            new Comic("xkcdTitle1","xkcdImgUrl2","xkcdWebUrl2", new Date(ThreadLocalRandom.current().nextInt() * 1000L) ),
            new Comic("xkcdTitle1","xkcdImgUrl3","xkcdWebUrl3", new Date(ThreadLocalRandom.current().nextInt() * 1000L) )
            )
    );

    List<Comic> allComics = new ArrayList<>();
    allComics.addAll(pdlService.getComics());
    allComics.addAll(xkcdService.getComics());

    allComics.sort((o1, o2) -> o2.getPublicationDate().compareTo(o1.getPublicationDate()));
    boolean isInDescendingOrder = Comparators.isInOrder(allComics, (o1, o2) -> o2.getPublicationDate().compareTo(o1.getPublicationDate()));

    assertTrue(isInDescendingOrder, "Comics are not in descending order");

  }
}
