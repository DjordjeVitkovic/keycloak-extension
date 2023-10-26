package com.java.keycloakextension.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.KeycloakSession;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.EnumMap;
import java.util.Map;

public class CustomEventListener implements EventListenerProvider {

  private final Map<EventType, String> eventMapping;
  private final RabbitTemplate rabbitTemplate;
  private final ObjectMapper objectMapper;
  private final KeycloakSession session;

  public CustomEventListener(RabbitTemplate rabbitTemplate, EnumMap<EventType, String> eventMapping, ObjectMapper objectMapper, KeycloakSession session) {
    this.rabbitTemplate = rabbitTemplate;
    this.eventMapping = eventMapping;
    this.objectMapper = objectMapper;
    this.session = session;
  }

  @Override
  public void onEvent(Event event) {
    // TODO: 10/23/2023 What should be done on publish
  }

  @Override
  public void onEvent(AdminEvent event, boolean includeRepresentation) {

  }

  @Override
  public void close() {

  }
}
