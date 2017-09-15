package kbaseknowledgeengine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import kbaseknowledgeengine.cfg.ExecConfigLoader;
import kbaseknowledgeengine.db.MongoStorage;
import kbaseknowledgeengine.db.WSEvent;

public class WSEventProcessor {
    private final MongoStorage store;
    private final Set<String> supportedStorageTypes;
    private final Set<String> supportedEventTypes = Collections.unmodifiableSet(
            new HashSet<String>(Arrays.asList(NEW_OBJECT_VER, NEW_OBJECT)));

    private static final String NEW_OBJECT_VER = "NEW_VERSION";
    private static final String NEW_OBJECT = "NEW_ALL_VERSIONS";

    public WSEventProcessor(MongoStorage store) throws IOException {
        this.store = store;
        supportedStorageTypes = ExecConfigLoader.loadConnectorConfigs().stream()
                .map(item -> item.getWorkspaceType()).collect(Collectors.toSet());
    }
    
    public List<WSEvent> loadEvents() {
        List<WSEvent> ret = new ArrayList<>();
        for (WSEvent evt : store.loadUnprocessedEvents()) {
            if (evt.eventType != null && supportedEventTypes.contains(evt.eventType) &&
                    evt.storageObjectType != null && 
                    supportedStorageTypes.contains(evt.storageObjectType)) {
                ret.add(evt);
            }
        }
        return ret;
    }
}
