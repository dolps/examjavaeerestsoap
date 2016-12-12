package com.woact.dolplads.exam2016.soap.dto;

import com.woact.dolplads.exam2016.soap.model.NewsEntity;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class NewsConverter {

    private NewsConverter() {
    }

    public static NewsDto transform(NewsEntity entity) {
        Objects.requireNonNull(entity);

        NewsDto dto = new NewsDto();
        dto.newsId = entity.getId();

        dto.authorId = entity.getAuthorId();
        dto.text = entity.getText();
        dto.country = entity.getCountry();

        return dto;
    }

    public static List<NewsDto> transform(List<NewsEntity> entities) {
        Objects.requireNonNull(entities);

        return entities.stream()
                .map(NewsConverter::transform)
                .collect(Collectors.toList());
    }

}