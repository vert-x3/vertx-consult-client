package io.vertx.ext.consul;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.test.core.VertxTestBase;

import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * @author <a href="mailto:ruslan.sennov@gmail.com">Ruslan Sennov</a>
 */
public class ConsulTestBase extends VertxTestBase {

    static BiFunction<Vertx, JsonObject, ConsulClient> clientCreator;
    static Consumer<ConsulClient> clientCloser;

    protected ConsulClient masterClient;
    protected ConsulClient writeClient;
    protected ConsulClient readClient;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        masterClient = clientCreator.apply(vertx, config(ConsulProcessHolder.masterToken()));
        writeClient = clientCreator.apply(vertx, config(ConsulProcessHolder.writeToken()));
        readClient = clientCreator.apply(vertx, config(ConsulProcessHolder.readToken()));
    }

    @Override
    public void tearDown() throws Exception {
        clientCloser.accept(masterClient);
        clientCloser.accept(writeClient);
        clientCloser.accept(readClient);
        super.tearDown();
    }

    private JsonObject config(String token) {
        return new JsonObject()
                .put("acl_token", token)
                .put("dc", ConsulProcessHolder.dc())
                .put("host", "localhost")
                .put("port", ConsulProcessHolder.consul().getHttpPort());
    }

}
