package com.java.keycloakextension.rabbitmq.config;

import org.keycloak.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

  private static final Logger logger = LoggerFactory.getLogger(RabbitMqConfig.class);
  protected static final String ENV_VAR_PREFIX = "RMQ_";

  private String url;
  private Integer port;
  private String username;
  private String password;
  private String vhost;
  private boolean useTls;

  private String exchange;

  public static RabbitMqConfig createFromScope(Config.Scope config) {
    RabbitMqConfig cfg = new RabbitMqConfig();

    cfg.url = resolveConfigVar(config, "url", "amqp-broker");
    cfg.port = Integer.valueOf(resolveConfigVar(config, "port", "5672"));
    cfg.username = resolveConfigVar(config, "username", "guest");
    cfg.password = resolveConfigVar(config, "password", "guest");
    cfg.vhost = resolveConfigVar(config, "vhost", "");
    cfg.useTls = Boolean.parseBoolean(resolveConfigVar(config, "use_tls", "false"));

    cfg.exchange = resolveConfigVar(config, "exchange", "access.sessions.notify");
    return cfg;
  }

  protected static String resolveConfigVar(Config.Scope config, String variableName, String defaultValue) {

    String value = defaultValue;
    if (config != null && config.get(variableName) != null) {
      value = config.get(variableName);
    } else {
      //try from env variables eg: RMQ_URL:
      String envVariableName = ENV_VAR_PREFIX + variableName.toUpperCase();
      if (System.getenv(envVariableName) != null) {
        value = System.getenv(envVariableName);
      }
    }
    logger.info("rabbitmq configuration: {}={}", variableName, value);
    return value;

  }

  public String getUrl() {
    return url;
  }

  public Integer getPort() {
    return port;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getVhost() {
    return vhost;
  }

  public boolean getUseTls() {
    return useTls;
  }

  public String getExchange() {
    return exchange;
  }
}