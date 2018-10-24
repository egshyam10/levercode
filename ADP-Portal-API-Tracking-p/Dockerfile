FROM openjdk:8-jre-slim

ENV APP_USER service-api
ENV APP_USER_ID 1001

RUN useradd -m -d /opt/service ${APP_USER} -u ${APP_USER_ID}

COPY target/adp-portal-api-tracking.jar /opt/service/adp-service.jar

# TODO: Should use COPY --chown instruction instead but not supported in build slave.
RUN chown -R ${APP_USER_ID}:${APP_USER_ID} /opt/service \
  && chmod 555 -R /opt/service

USER ${APP_USER_ID}

EXPOSE 8080/tcp

WORKDIR /opt/service

ENTRYPOINT [ "java" ]

# The Java security manager can be enabled for applications that have a security policy.
CMD [ "-jar", "adp-service.jar", "-Djava.security.egd=file:/dev/./urandom" ]
