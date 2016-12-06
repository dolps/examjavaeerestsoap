package com.woact.dolplads.exam2016.gameApi;


import com.netflix.config.ConfigurationManager;
import com.woact.dolplads.exam2016.gameApi.client.GameResource;
import com.woact.dolplads.exam2016.gameApi.db.GameDAO;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.ScanningHibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import org.apache.commons.configuration.AbstractConfiguration;
import org.glassfish.jersey.client.JerseyClientBuilder;

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

        final Client client = new JerseyClientBuilder().build();
        final GameDAO gameDAO = new GameDAO(hibernate.getSessionFactory());

        environment.jersey().register(new GameResource(client, gameDAO));


        setupHystrix();
        setupSwagger(environment);
    }

    private void setupHystrix() {
        //Hystrix configuration
        AbstractConfiguration conf = ConfigurationManager.getConfigInstance();
        // how long to wait before giving up a request?
        conf.setProperty("hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds", 500); //default is 1000
        // how many failures before activating the CB?
        conf.setProperty("hystrix.command.default.circuitBreaker.requestVolumeThreshold", 2); //default 20
        conf.setProperty("hystrix.command.default.circuitBreaker.errorThresholdPercentage", 50);
        //for how long should the CB stop requests? after this, 1 single request will try to check if remote server is ok
        conf.setProperty("hystrix.command.default.circuitBreaker.sleepWindowInMilliseconds", 5000);
    }

    private void setupSwagger(Environment environment) {
        environment.jersey().register(new ApiListingResource());
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("0.0.1");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("localhost:9000");
        beanConfig.setBasePath("/app/api");
        beanConfig.setResourcePackage("com.woact.dolplads.exam2016.gameApi");
        beanConfig.setScan(true);
        environment.jersey().register(new ApiListingResource());
        environment.jersey().register(new io.swagger.jaxrs.listing.SwaggerSerializers());
    }

}
