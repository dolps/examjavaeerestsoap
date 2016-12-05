package com.woact.dolplads.exam2016.gameApi;


import com.woact.dolplads.exam2016.gameApi.client.GameResource;
import com.woact.dolplads.exam2016.gameApi.db.GameDAO;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.ScanningHibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;

import javax.ws.rs.client.Client;

public class GameApplication extends Application<GameConfiguration> {

    public static void main(final String[] args) throws Exception {
        new GameApplication().run(args);
    }

    private final HibernateBundle<GameConfiguration> hibernate =
            new ScanningHibernateBundle<GameConfiguration>("com.woact.dolplads.exam2016.gameApi.core") {
                @Override
                public DataSourceFactory getDataSourceFactory(GameConfiguration configuration) {
                    return configuration.getDataSourceFactory();
                }
            };

    @Override
    public String getName() {
        return "game";
    }

    @Override
    public void initialize(final Bootstrap<GameConfiguration> bootstrap) {
        bootstrap.addBundle(hibernate);

        bootstrap.addBundle(new AssetsBundle("/assets", "/", "index.html", "static"));
        bootstrap.addBundle(new AssetsBundle("/assets/css", "/css", null, "b"));
        bootstrap.addBundle(new AssetsBundle("/assets/fonts", "/fonts", null, "c"));
        bootstrap.addBundle(new AssetsBundle("/assets/images", "/images", null, "d"));
        bootstrap.addBundle(new AssetsBundle("/assets/lang", "/lang", null, "e"));
        bootstrap.addBundle(new AssetsBundle("/assets/lib", "/lib", null, "f"));
        bootstrap.addBundle(new AssetsBundle("/assets", "/swagger-ui.js", "swagger-ui.js", "static2"));
    }

    @Override
    public void run(final GameConfiguration configuration,
                    final Environment environment) throws Exception {

        final Client client = new JerseyClientBuilder(environment)
                .using(configuration.getJerseyClientConfiguration())
                .build(getName());

        final GameDAO gameDAO = new GameDAO(hibernate.getSessionFactory());
        environment.jersey().register(new GameResource(client, gameDAO));


        setupSwagger(environment);
    }

    private void setupSwagger(Environment environment) {
        environment.jersey().register(new ApiListingResource());
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("0.0.1");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("localhost:9000");
        beanConfig.setBasePath("/api");
        beanConfig.setResourcePackage("com.javaee2.dolplads.client");
        beanConfig.setScan(true);
        environment.jersey().register(new ApiListingResource());
        environment.jersey().register(new io.swagger.jaxrs.listing.SwaggerSerializers());
    }

}
