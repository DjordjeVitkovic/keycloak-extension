package com.java.keycloakextension.rabbitmq;

import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.admin.AdminEvent;

public class CustomEventListener implements EventListenerProvider {
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
