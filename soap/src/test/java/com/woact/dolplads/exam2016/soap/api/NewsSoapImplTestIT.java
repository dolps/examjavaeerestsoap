package com.woact.dolplads.exam2016.soap.api;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pg6100.soap.client.NewsDto;
import org.pg6100.soap.client.NewsSoap;
import org.pg6100.soap.client.NewsSoapImplService;

import javax.xml.ws.BindingProvider;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by dolplads on 09/12/2016.
 */
public class NewsSoapImplTest {
    static NewsSoap ws;

    @BeforeClass
    public static void setUp() throws Exception {
        NewsSoapImplService service = new NewsSoapImplService();
        ws = service.getNewsSoapImplPort();

        String url = "http://localhost:8080/newssoap/NewsSoapImpl";

        ((BindingProvider) ws).getRequestContext().put(
                BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);
    }

    @Before
    @After
    public void cleanData() {
        List<NewsDto> list = ws.get(null, null);

        list.forEach(dto -> ws.deleteNews(dto.getNewsId()));
    }

    @Test
    public void testCleanDB() {

        List<NewsDto> list = ws.get(null, null);

        assertEquals(0, list.size());
    }
}