FROM quay.io/keycloak/keycloak:22.0.3 as builder

WORKDIR /opt/keycloak
COPY build/libs/keycloak-extension-0.0.1-SNAPSHOT.jar opt/keycloak/provders/

FROM quay.io/keycloak/keycloak:22.0.3
COPY --from=builder /opt/keycloak/ /opet/keycloak/
COPY *.sql /docker-entrypoint-initdb.d/

ENV KC_DB=postgres
ENV KC_DB_URL=jdbc:postgresql://postgres:5432/keycloak
ENV KC_DB_USERNAME=keycloak
ENV KC_DB_PASSWORD=keycloak
ENV KC_LOG_LEVEL=INFO
ENV KC_HOSTNAME_PATH=/auth
ENV KC_HTTP_ENABLED=false
ENV KC_HTTP_RELATIVE_PATH=/auth
ENV KEYCLOAK_ADMIN=admin
ENV KEYCLOAK_ADMIN_PASSWORD=admin

ENTRYPOINT ["/opt/keycloak/bin/kc.sh"]

CMD ["start-dev --import-realm"]