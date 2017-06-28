package me.michalik.keycloak.events.rest;

import org.apache.http.client.HttpClient;
import org.keycloak.Config;
import org.keycloak.connections.httpclient.HttpClientProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.OperationType;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

import java.util.HashSet;
import java.util.Set;

public class RestEventListenerProviderFactory implements EventListenerProviderFactory {

    private Set<EventType> excludedEvents;
    private String eventsUrl;
    private Set<OperationType> excludedAdminOperations;
    private String operationsUrl;

    @Override
    public RestEventListenerProvider create(KeycloakSession session) {
        HttpClient httpClient = session.getProvider(HttpClientProvider.class).getHttpClient();
        return new RestEventListenerProvider(excludedEvents, eventsUrl, excludedAdminOperations, operationsUrl, httpClient);
    }

    @Override
    public void init(Config.Scope config) {
        String[] excludes = config.getArray("excludes");
        if (excludes != null) {
            excludedEvents = new HashSet<EventType>();
            for (String e : excludes) {
                excludedEvents.add(EventType.valueOf(e));
            }
        }

        String[] excludesOperations = config.getArray("excludesOperations");
        if (excludesOperations != null) {
            excludedAdminOperations = new HashSet<OperationType>();
            for (String e : excludesOperations) {
                excludedAdminOperations.add(OperationType.valueOf(e));
            }
        }

        this.eventsUrl = config.get("eventsUrl");
        this.operationsUrl = config.get("operationsUrl");
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {

    }

    @Override
    public void close() {
    }

    @Override
    public String getId() {
        return "events-rest";
    }

}