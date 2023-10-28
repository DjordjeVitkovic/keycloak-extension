FROM quay.io/keycloak/keycloak:22.0.3 AS builder

WORKDIR /opt/keycloak
COPY /build/libs/keycloak-extension-0.0.1-SNAPSHOT.jar /opt/keycloak/providers
COPY /build/libs/spring-*.jar /opt/keycloak/providers
RUN /opt/keycloak/bin/kc.sh build "--http-relative-path=/auth"

FROM quay.io/keycloak/keycloak:22.0.3
COPY --from=builder /opt/keycloak/ /opt/keycloak/

ENV JAVA_OPTS_APPEND="-Dkeycloak.profile.feature.admin_fine_grained_authz=enabled -Dkeycloak.profile.feature.token_exchange=enabled -Dkeycloak.migration.strategy=OVERWRITE_EXISTING"
#-Dkeycloak.migration.action=import -Dkeycloak.migration.provider=dir -Dkeycloak.migration.dir=/opt/keycloak/data/import
ENV KC_DB=postgres
ENV KC_DB_URL=jdbc:postgresql://postgres:5432/keycloak
ENV KC_DB_USERNAME=keycloak
ENV KC_DB_PASSWORD=keycloak
ENV KC_LOG_LEVEL=DEBUG
ENV KC_HOSTNAME_PATH=/auth
ENV KC_HTTP_ENABLED=false
ENV KC_HTTP_RELATIVE_PATH=/auth
ENV KEYCLOAK_ADMIN=admin
ENV KEYCLOAK_ADMIN_PASSWORD=admin

ENTRYPOINT ["/opt/keycloak/bin/kc.sh"]

CMD ["start-dev --import-realm"]