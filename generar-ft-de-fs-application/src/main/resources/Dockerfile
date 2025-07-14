FROM registry.tier1.es/tier1-devops/producto/base/jdk:1.8.202

ENV COMERZZIA_HOME="${INSTALL_DIR}"

ENV APP_JAR=generar-ft-de-fs-application-0.0.1-SNAPSHOT.jar \
    JAVA_OPTS="-Doracle.jdbc.timezoneAsRegion=false -Dfile.encoding=UTF-8"

COPY ["/target/generar-ft-de-fs-application-0.0.1-SNAPSHOT.jar", "${INSTALL_DIR}/"]

CMD "${INSTALL_DIR}/java/bin/java" ${JAVA_OPTS} -Xmx${JAVA_OPTS_MX}m -DCOMERZZIA_HOME=${INSTALL_DIR} -jar ${APP_JAR}
