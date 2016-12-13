package com.woact.dolplads.exam2016.gameApi.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.io.BufferedInputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.Assert.assertTrue;

/**
 * Created by dolplads on 13/12/2016.
 */
public class GameResourceTestIT extends GameApplicationTestBase {
    private static WireMockServer wiremockServer;
    private static Process process;

    @Override
    public void testGetRandomFail() throws Exception {
        wiremockServer.stop();
        super.testGetRandomFail();
    }

    @BeforeClass
    public static void startJar() throws Exception {
        String jar = "game.jar";
        String jarLocation = "target" + File.separator + jar;

        if (!Files.exists(Paths.get(jarLocation))) {
            throw new AssertionError("Jar file was not created at: " + jarLocation);
        }

        String[] command = new String[]{"java", "-jar", jarLocation, "server", "game.yml"};

        process = new ProcessBuilder().command(command).start();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                stopProcess();
            }
        });

        assertTrue(process.isAlive());

        Scanner in = new Scanner(new BufferedInputStream(process.getInputStream()));
        while (in.hasNext()) {
            String line = in.nextLine();
            System.out.println(line);
            if (line.contains("Server: Started")) {
                break;
            }
        }
        wiremockServer = new WireMockServer(
                wireMockConfig().port(8080).notifier(new ConsoleNotifier(true)));
        wiremockServer.start();
    }

    @AfterClass
    public static void stopJar() {
        wiremockServer.stop();
        stopProcess();
    }

    private static void stopProcess() {
        if (process != null && process.isAlive()) {
            process.destroy();
            process = null;
        }
    }
}
