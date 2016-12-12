package com.woact.dolplads.exam2016.soap.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;

@Entity
public class NewsEntity {

    @Id
    @GeneratedValue
    private Long id;

    //@NotBlank //Note: non-standard constraints, ie in Hibernate but not in JPA
    @Size(max = 32)
    private String authorId;

    //@NotBlank
    @Size(max = 1024)
    private String text;

    @NotNull //again, non-standard, ie need to use Hibernate, as Java 8 dates are not supported in JPA 2.1
    private ZonedDateTime creationTime;

    private String country;


    public NewsEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ZonedDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(ZonedDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
