package io.vertx.ext.consul;

import io.vertx.ext.consul.suite.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author <a href="mailto:ruslan.sennov@gmail.com">Ruslan Sennov</a>
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        AclTokens.class,
        Checks.class,
        Events.class,
        KVStore.class,
        Services.class
})
public class ConsulTestSuite {

    @BeforeClass
    public static void startConsul() throws Exception {
        ConsulProcessHolder.consul();
    }

    @AfterClass
    public static void stopConsul() {
        ConsulProcessHolder.consul().close();
    }

}