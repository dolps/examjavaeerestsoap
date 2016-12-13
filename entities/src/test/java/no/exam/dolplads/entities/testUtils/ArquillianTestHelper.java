package no.exam.dolplads.entities.testUtils;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.runner.RunWith;

/**
 * Created by dolplads on 25/09/16.
 * <p>
 * Adds needed packages
 */
@RunWith(Arquillian.class)
public abstract class ArquillianTestHelper {
    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive war = ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "no.exam.dolplads.entities")
                .addAsResource("META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

        System.out.println(war.toString(true));
        return war;
    }
}
