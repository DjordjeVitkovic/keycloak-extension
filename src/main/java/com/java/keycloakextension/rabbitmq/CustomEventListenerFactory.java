package com.java.keycloakextension.rabbitmq;

import org.keycloak.Config;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.events.EventType;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

import java.util.EnumMap;

public class CustomEventListenerFactory implements EventListenerProviderFactory {

  public static final String PROVIDER_ID = "rabbit-event-listener";
  protected EnumMap<EventType, String> eventMapping;

  protected void configureRoutingMapping() {
    eventMapping = new EnumMap<>(EventType.class);
    eventMapping.put(EventType.LOGIN, "routing-key");
  }

  @Override
  public EventListenerProvider create(KeycloakSession session) {
    return new CustomEventListener();
  }

  @Override
  public void init(Config.Scope config) {

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
