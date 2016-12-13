package no.exam.dolplads.gameApi.resource;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import no.exam.dolplads.gameApi.GameApplication;
import no.exam.dolplads.gameApi.GameApplicationTestBase;
import no.exam.dolplads.gameApi.GameConfiguration;
import org.junit.*;

import static org.hamcrest.core.Is.is;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by dolplads on 01/12/2016.
 */
public class GameResourceTest extends GameApplicationTestBase {
    @ClassRule
    public static final DropwizardAppRule<GameConfiguration> RULE =
            new DropwizardAppRule<>(GameApplication.class, ResourceHelpers.resourceFilePath("game.yml"));
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(Integer.parseInt(RULE.getConfiguration().getTestUrl()));
}