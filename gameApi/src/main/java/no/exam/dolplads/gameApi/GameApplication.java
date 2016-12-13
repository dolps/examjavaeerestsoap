package no.exam.dolplads.gameApi;


import com.netflix.config.ConfigurationManager;
import no.exam.dolplads.gameApi.resource.GameResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
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

    @Override
    public String getName() {
        return "game";
    }

    @Override
    public void initialize(final Bootstrap<GameConfiguration> bootstrap) {
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

        String externalHost = "http://localhost:" + configuration.getTestUrl();
        environment.jersey().register(new GameResource(client, externalHost));


        setupHystrix();
        setupSwagger(environment);
    }

    private void setupHystrix() {
        AbstractConfiguration conf = ConfigurationManager.getConfigInstance();
        conf.setProperty("hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds", 500);
        conf.setProperty("hystrix.command.default.circuitBreaker.requestVolumeThreshold", 2);
        conf.setProperty("hystrix.command.default.circuitBreaker.errorThresholdPercentage", 50);
        conf.setProperty("hystrix.command.default.circuitBreaker.sleepWindowInMilliseconds", 5000);
    }

    private void setupSwagger(Environment environment) {
        environment.jersey().register(new ApiListingResource());
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("0.0.1");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("localhost:9000");
        beanConfig.setBasePath("/game/api");
        beanConfig.setResourcePackage("no.exam.dolplads.gameApi");
        beanConfig.setScan(true);
        environment.jersey().register(new ApiListingResource());
        environment.jersey().register(new io.swagger.jaxrs.listing.SwaggerSerializers());
    }

}
