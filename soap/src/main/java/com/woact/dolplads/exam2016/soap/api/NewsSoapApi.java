package com.woact.dolplads.exam2016.soap.api;

import com.woact.dolplads.exam2016.soap.dto.NewsDto;

import javax.jws.WebService;
import java.util.List;

@WebService(name = "NewsSoap")
public interface NewsSoapApi {
    List<NewsDto> get(String country, String authorId);

    Long createNews(NewsDto dto);

    NewsDto getNews(Long id);

    void updateNews(NewsDto dto);

    boolean deleteNews(long id);
}