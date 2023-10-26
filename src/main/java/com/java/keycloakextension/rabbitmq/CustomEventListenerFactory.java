package com.java.keycloakextension.rabbitmq;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.keycloakextension.rabbitmq.config.RabbitMqConfig;
import org.keycloak.Config;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.events.EventType;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.net.URI;
import java.util.EnumMap;

public class CustomEventListenerFactory implements EventListenerProviderFactory {

  private static final Logger logger = LoggerFactory.getLogger(CustomEventListenerFactory.class);

  public static final String PROVIDER_ID = "rabbit-event-listener";
  protected EnumMap<EventType, String> eventMapping;
  private ObjectMapper objectMapper;
  protected CachingConnectionFactory connectionFactory;
  private RabbitTemplate rabbitTemplate;

  protected void configureRoutingMapping() {
    eventMapping = new EnumMap<>(EventType.class);
    eventMapping.put(EventType.LOGIN, "routing-key");
  }

  @Override
  public EventListenerProvider create(KeycloakSession session) {
    return new CustomEventListener(rabbitTemplate, eventMapping, objectMapper, session);
  }

  @Override
  public void init(Config.Scope config) {
    logger.debug("called init");
    RabbitMqConfig rabbitMqConfig = RabbitMqConfig.createFromScope(config);

    connectionFactory = new CachingConnectionFactory(getConnectionUri(rabbitMqConfig));
    connectionFactory.setUsername(rabbitMqConfig.getUsername());
    connectionFactory.setPassword(rabbitMqConfig.getPassword());
    connectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CONNECTION);

    rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setExchange(rabbitMqConfig.getExchange());

    configureRoutingMapping();

    objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  protected URI getConnectionUri(RabbitMqConfig rabbitMqConfig) {
    String protocol;
    if (rabbitMqConfig.getUseTls()) {
      protocol = "amqps";
    } else {
      protocol = "amqp";
    }
    return URI.create(protocol + "://" + rabbitMqConfig.getUrl() + ":" + rabbitMqConfig.getPort());
  }

  @Override
  public void postInit(KeycloakSessionFactory factory) {

  }

  @Override
  public void close() {

  }

  @Override
  public String getId() {
    return PROVIDER_ID;
  }
}
