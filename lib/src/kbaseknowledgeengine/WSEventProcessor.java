package kbaseknowledgeengine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import kbaseknowledgeengine.cfg.IExecConfigLoader;
import kbaseknowledgeengine.db.MongoStorage;
import kbaseknowledgeengine.db.WSEvent;
import us.kbase.common.service.UObject;

public class WSEventProcessor {
    private final MongoStorage store;
    private final Set<String> supportedStorageTypes;
    private final Set<String> supportedEventTypes = Collections.unmodifiableSet(
            new HashSet<String>(Arrays.asList(EVENT_TYPE_NEW_OBJECT_VER, EVENT_TYPE_NEW_OBJECT)));
    private final Set<WSEvent> loadedEvents = Collections.synchronizedSet(
            new HashSet<WSEvent>());
    private final WSEventListener listener;
    private volatile Thread watcher;

    public static final String EVENT_TYPE_NEW_OBJECT_VER = "NEW_VERSION";
    public static final String EVENT_TYPE_NEW_OBJECT = "NEW_ALL_VERSIONS";

    public WSEventProcessor(MongoStorage store, WSEventListener listener,
            IExecConfigLoader ecl) throws IOException {
        this.store = store;
        this.listener = listener;
        supportedStorageTypes = ecl.loadConnectorConfigs().stream()
                .map(item -> item.getWorkspaceType()).collect(Collectors.toSet());
        watcher = new Thread(new Runnable() {
            
            @Override
            public void run() {
                while (true) {
                    try {
                        updateEvents();
                        if (Thread.currentThread().isInterrupted()) {
                            break;
                        }
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
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {}
            if (watcher == null) {
                return;
            }
        }
        if (watcher != null) {
            throw new IllegalStateException("Couldn't stop watcher thread");
        }
    }
    
    private void updateEvents() throws InterruptedException {
        List<WSEvent> newEvents = new ArrayList<>();
        for (WSEvent evt : store.loadUnprocessedEvents()) {
            if (evt.eventType != null && supportedEventTypes.contains(evt.eventType) &&
                    evt.storageObjectType != null && 
                    supportedStorageTypes.contains(evt.storageObjectType)) {
                if (loadedEvents.contains(evt)) {
                    continue;
                }
                loadedEvents.add(evt);
                newEvents.add(evt);
            }
        }
        for (WSEvent evt : newEvents) {
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException();
            }
            WSEvent copy = UObject.transformStringToObject(
                    UObject.transformObjectToString(evt), WSEvent.class);
            listener.objectVersionCreated(copy);
        }
    }
    
    public static interface WSEventListener {
        public void objectVersionCreated(WSEvent evt);
    }
}
