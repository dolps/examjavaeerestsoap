package no.exam.dolplads.gameCommands.commands;

import com.google.gson.Gson;
import com.netflix.hystrix.Hystrix;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import no.exam.dolplads.quizApi.dto.QuizDto;


import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

/**
 * Created by dolplads on 13/12/2016.
 */


public class GetQuizCommand extends HystrixCommand<Response> {
    private String fetchUrl;
    private Client client;


    public GetQuizCommand(Client client, String fetchUrl) {
        super(HystrixCommandGroupKey.Factory.asKey("Interactions with quizAPI"));
        this.fetchUrl = fetchUrl;
        this.client = client;

    }
    // little buggy thisone(use it for soap)
    public GetQuizCommand(String fetchUrl) {
        super(HystrixCommandGroupKey.Factory.asKey("Interactions with quizAPI"));

        this.fetchUrl = fetchUrl;
        this.client = ClientBuilder.newClient();
        // seems like this work as a warm up for the next request..
        try {
            QuizDto dto = client.target(fetchUrl).request(MediaType.APPLICATION_JSON).get(QuizDto.class);
        } catch (Exception e) {
            System.out.println("error fetching quiz was caught");
        }
    }

    @Override
    protected Response run() throws Exception {
        QuizDto quizDto = client.target(fetchUrl).request(MediaType.APPLICATION_JSON).get(QuizDto.class);

        return Response.ok(new Gson().toJson(quizDto), MediaType.APPLICATION_JSON).build();
    }

    @Override
    protected Response getFallback() {
        return Response.serverError().build();
    }
}