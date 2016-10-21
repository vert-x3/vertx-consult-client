package io.vertx.ext.consul;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Options used to create session.
 *
 * @author <a href="mailto:ruslan.sennov@gmail.com">Ruslan Sennov</a>
 */
@DataObject
public class SessionOptions {

  private static final String LOCK_KEY = "LockDelay";
  private static final String NAME_KEY = "Name";
  private static final String NODE_KEY = "Node";
  private static final String CHECKS_KEY = "Checks";
  private static final String BEHAVIOR_KEY = "Behavior";
  private static final String TTL_KEY = "TTL";

  private long lockDelay;
  private String name;
  private String node;
  private List<String> checks;
  private SessionBehavior behavior;
  private long ttl;

  /**
   * Default constructor
   */
  public SessionOptions() {
  }

  /**
   * Copy constructor
   *
   * @param options the one to copy
   */
  public SessionOptions(SessionOptions options) {
    this.lockDelay = options.lockDelay;
    this.name = options.name;
    this.node = options.node;
    this.checks = new ArrayList<>(options.checks);
    this.behavior = options.behavior;
    this.ttl = options.ttl;
  }

  /**
   * Constructor from JSON
   *
   * @param options the JSON
   */
  public SessionOptions(JsonObject options) {
    this.lockDelay = cutSeconds(options.getString(LOCK_KEY));
    this.name = options.getString(NAME_KEY);
    this.node = options.getString(NODE_KEY);
    JsonArray arr = options.getJsonArray(CHECKS_KEY);
    this.checks = arr == null ? null : arr.getList();
    this.behavior = SessionBehavior.of(options.getString(BEHAVIOR_KEY));
    this.ttl = cutSeconds(options.getString(TTL_KEY));
  }

  /**
   * Convert to JSON
   *
   * @return the JSON
   */
  public JsonObject toJson() {
    JsonObject jsonObject = new JsonObject();
    if (lockDelay != 0) {
      jsonObject.put(LOCK_KEY, lockDelay + "s");
    }
    if (name != null) {
      jsonObject.put(NAME_KEY, name);
    }
    if (node != null) {
      jsonObject.put(NODE_KEY, node);
    }
    if (checks != null) {
      jsonObject.put(CHECKS_KEY, checks);
    }
    if (behavior != null) {
      jsonObject.put(BEHAVIOR_KEY, behavior.key);
    }
    if (ttl != 0) {
      jsonObject.put(TTL_KEY, ttl + "s");
    }
    return jsonObject;
  }

  /**
   * Get the lock-delay period.
   *
   * @return the lock-delay period
   * @see Session#getLockDelay()
   */
  public long getLockDelay() {
    return lockDelay;
  }

  /**
   * Set the lock-delay period.
   *
   * @param lockDelay the lock-delay period in seconds
   * @return reference to this, for fluency
   * @see Session#getLockDelay()
   */
  public SessionOptions setLockDelay(long lockDelay) {
    this.lockDelay = lockDelay;
    return this;
  }

  /**
   * Get the human-readable name for the Session
   *
   * @return the name of session
   */
  public String getName() {
    return name;
  }

  /**
   * Set the human-readable name for the Session
   *
   * @param name the name of session
   * @return reference to this, for fluency
   */
  public SessionOptions setName(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get the node to which the session will be assigned
   *
   * @return the ID of node
   */
  public String getNode() {
    return node;
  }

  /**
   * Set the node to which the session will be assigned
   *
   * @param node the ID of node
   * @return reference to this, for fluency
   */
  public SessionOptions setNode(String node) {
    this.node = node;
    return this;
  }

  /**
   * Get a list of associated health checks.
   *
   * @return list of associated health checks
   * @see #setChecks(List)
   */
  public List<String> getChecks() {
    return checks;
  }

  /**
   * Set a list of associated health checks. It is highly recommended that,
   * if you override this list, you include the default "serfHealth"
   *
   * @param checks list of associated health checks
   * @return reference to this, for fluency
   */
  public SessionOptions setChecks(List<String> checks) {
    this.checks = checks;
    return this;
  }

  /**
   * Get the behavior when a session is invalidated.
   *
   * @return the session behavior
   * @see #setBehavior(SessionBehavior)
   */
  public SessionBehavior getBehavior() {
    return behavior;
  }

  /**
   * Set the behavior when a session is invalidated. The release behavior is the default if none is specified.
   *
   * @param behavior the session behavior
   * @return reference to this, for fluency
   */
  public SessionOptions setBehavior(SessionBehavior behavior) {
    this.behavior = behavior;
    return this;
  }

  /**
   * Get the TTL interval.
   *
   * @return the TTL interval in seconds
   * @see #setTtl(long)
   */
  public long getTtl() {
    return ttl;
  }

  /**
   * Set the TTL interval. When TTL interval expires without being renewed, the session has expired
   * and an invalidation is triggered. If specified, it must be between 10s and 86400s currently.
   *
   * @param ttl the TTL interval in seconds
   * @return reference to this, for fluency
   */
  public SessionOptions setTtl(long ttl) {
    this.ttl = ttl;
    return this;
  }

  private static long cutSeconds(String value) {
    if (value != null && value.endsWith("s")) {
      return Integer.parseInt(value.substring(0, value.length() - 1));
    } else {
      return 0L;
    }
  }
}