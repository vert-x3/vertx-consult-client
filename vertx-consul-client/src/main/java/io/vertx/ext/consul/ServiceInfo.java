package io.vertx.ext.consul;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:ruslan.sennov@gmail.com">Ruslan Sennov</a>
 */
@DataObject
public class ServiceInfo {

    private static final String NODE = "Node";
    private static final String ADDRESS = "Address";
    private static final String SERVICE_ID = "ServiceID";
    private static final String SERVICE_NAME = "ServiceName";
    private static final String SERVICE_TAGS = "ServiceTags";
    private static final String SERVICE_ADDRESS = "ServiceAddress";
    private static final String SERVICE_PORT = "ServicePort";

    private static final String AGENT_SERVICE_ID = "ID";
    private static final String AGENT_SERVICE_SERVICE = "Service";
    private static final String AGENT_SERVICE_TAGS = "Tags";
    private static final String AGENT_SERVICE_ADDRESS = "Address";
    private static final String AGENT_SERVICE_PORT = "Port";

    private String node;
    private String nodeAddress;
    private String id;
    private String name;
    private List<String> tags;
    private String address;
    private int port;

    public static ServiceInfo parseAgentInfo(JsonObject jsonObject) {
        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.id = jsonObject.getString(AGENT_SERVICE_ID);
        serviceInfo.name = jsonObject.getString(AGENT_SERVICE_SERVICE);
        JsonArray tagsArr = jsonObject.getJsonArray(AGENT_SERVICE_TAGS);
        serviceInfo.tags = tagsArr == null ? null : tagsArr.getList();
        serviceInfo.address = jsonObject.getString(AGENT_SERVICE_ADDRESS);
        serviceInfo.port = jsonObject.getInteger(AGENT_SERVICE_PORT);
        return serviceInfo;
    }

    private ServiceInfo() {}

    public ServiceInfo(JsonObject jsonObject) {
        this.node = jsonObject.getString(NODE);
        this.nodeAddress = jsonObject.getString(ADDRESS);
        this.id = jsonObject.getString(SERVICE_ID);
        this.name = jsonObject.getString(SERVICE_NAME);
        JsonArray tagsArr = jsonObject.getJsonArray(SERVICE_TAGS);
        this.tags = tagsArr == null ? null : tagsArr.stream().map(obj -> (String) obj).collect(Collectors.toList());
        this.address = jsonObject.getString(SERVICE_ADDRESS);
        this.port = jsonObject.getInteger(SERVICE_PORT);
    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        if (node != null) {
            jsonObject.put(NODE, node);
        }
        if (nodeAddress != null) {
            jsonObject.put(ADDRESS, nodeAddress);
        }
        if (id != null) {
            jsonObject.put(SERVICE_ID, id);
        }
        if (name != null) {
            jsonObject.put(SERVICE_NAME, name);
        }
        if (tags != null) {
            jsonObject.put(SERVICE_TAGS, new JsonArray(tags));
        }
        if (address != null) {
            jsonObject.put(SERVICE_ADDRESS, address);
        }
        if (port != 0) {
            jsonObject.put(SERVICE_PORT, port);
        }
        return jsonObject;
    }

    public String getNode() {
        return node;
    }

    public String getNodeAddress() {
        return nodeAddress;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }
}