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
    private final Set<WSEvent> loadedEvents = Collections.synchronizedSet(
            new HashSet<WSEvent>());
    private Thread watcher;

    private static final String NEW_OBJECT_VER = "NEW_VERSION";
    private static final String NEW_OBJECT = "NEW_ALL_VERSIONS";

    public WSEventProcessor(MongoStorage store, WSEventListener listener) throws IOException {
        this.store = store;
        supportedStorageTypes = ExecConfigLoader.loadConnectorConfigs().stream()
                .map(item -> item.getWorkspaceType()).collect(Collectors.toSet());
        watcher = new Thread(new Runnable() {
            
            @Override
            public void run() {
                while (true) {
                    List<WSEvent> newEvents = loadNewEvents(loadedEvents);
                    for (WSEvent evt : newEvents) {
                        listener.objectVersionCreated(evt);
                        if (Thread.currentThread().isInterrupted()) {
                            watcher = null;
                            return;
                        }
                    }
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
                watcher = null;
            }
        });
        watcher.start();
    }
    
    public void stopWatcher() {
        if (watcher == null) {
            return;
        }
        watcher.interrupt();
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {}
            if (watcher == null) {
                return;
            }
        }
    }
    
    private List<WSEvent> loadNewEvents(Set<WSEvent> alreadyLoaded) {
        List<WSEvent> ret = new ArrayList<>();
        for (WSEvent evt : store.loadUnprocessedEvents()) {
            if (evt.eventType != null && supportedEventTypes.contains(evt.eventType) &&
                    evt.storageObjectType != null && 
                    supportedStorageTypes.contains(evt.storageObjectType)) {
                if (alreadyLoaded.contains(evt)) {
                    continue;
                }
                alreadyLoaded.add(evt);
                ret.add(evt);
            }
        }
        return ret;
    }
    
    public static interface WSEventListener {
        public void objectVersionCreated(WSEvent evt);
    }
}
