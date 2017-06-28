# Keycloak REST events
 
Send keycloak events to configure URL.

## Installation

Build jar:

```bash
mvn clean install
```

Add module to Keycloak:

```bash
KEYCLOAK_HOME/bin/jboss-cli.sh --command="module add --name=me.michalik.keycloak.events.rest --resources=target/keycloak-events-rest.jar --dependencies=org.keycloak.keycloak-core,org.keycloak.keycloak-server-spi,org.keycloak.keycloak-server-spi-private,org.apache.httpcomponents,com.fasterxml.jackson.core.jackson-databind"
```

In configuration file (```standalone/configuration/standalone.xml```) add:

```xml
<providers>
    ...
    <provider>module:me.michalik.keycloak.events.rest</provider>
</providers>
```

Configuration module:

```xml
<subsystem xmlns="urn:jboss:domain:keycloak-server:1.1">
    ...
    <spi name="eventsListener">
        <provider name="events-rest" enabled="true">
            <properties>
                <property name="excludes" value="[]"/>
                <property name="operationsUrl" value="http://example.com/events"/>
                <property name="excludesOperations" value="[]"/>
                <property name="operationsUrl" value="http://example.com/operations"/>
            </properties>
        </provider>
    </spi>
</subsystem>
```

In Admin Console (Events menu) add Event Listener:  "events-rest"