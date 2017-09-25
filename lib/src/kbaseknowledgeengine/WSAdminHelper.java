package kbaseknowledgeengine;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import us.kbase.auth.AuthToken;
import us.kbase.common.service.JsonClientException;
import us.kbase.common.service.Tuple11;
import us.kbase.common.service.UObject;
import us.kbase.common.service.UnauthorizedException;
import workspace.GetObjectInfo3Params;
import workspace.GetObjectInfo3Results;
import workspace.ObjectSpecification;
import workspace.WorkspaceClient;

public class WSAdminHelper {
    private final WorkspaceClient wsCl;
    
    public WSAdminHelper(URL wsUrl, AuthToken keAdminToken) 
            throws UnauthorizedException, IOException {
        this.wsCl = new WorkspaceClient(wsUrl, keAdminToken);
        wsCl.setIsInsecureHttpConnectionAllowed(true);
    }
    
    public ObjectInfo getObjectInfo(int accessGroupId, String objId, Integer version) 
            throws IOException, JsonClientException {
        String objRef = accessGroupId + "/" + objId +
                (version == null ? "" : ("/" + version));
        return getObjectInfo(objRef);
    }
    
    public ObjectInfo getObjectInfo(String objRef) throws IOException, JsonClientException {
        final Map<String, Object> command = new HashMap<>();
        command.put("command", "getObjectInfo");
        command.put("params", new GetObjectInfo3Params().withIncludeMetadata(1L).withObjects(
                Arrays.asList(new ObjectSpecification().withRef(objRef))));
        return new ObjectInfo(wsCl.administer(new UObject(command))
                .asClassInstance(GetObjectInfo3Results.class).getInfos().get(0));
    }
    
    public static class ObjectInfo {
        private String resolvedRef;
        private String owner;
        private Integer featureCount;
        
        public ObjectInfo(Tuple11 <Long, String, String, String, Long, String, 
                Long, String, String, Long, Map<String, String>> wsInfo) {
            resolvedRef = wsInfo.getE7() + "/" + wsInfo.getE1() + "/" + wsInfo.getE5();
            owner = wsInfo.getE6();
            Map<String, String> meta = wsInfo.getE11();
            if (meta != null) {
                String nf = meta.get("Number features");
                if (nf != null) {
                    try {
                        featureCount = Integer.parseInt(nf);
                    } catch (Exception ex) {}
                }
            }
        }
        
        public String getResolvedRef() {
            return resolvedRef;
        }
        
        public String getOwner() {
            return owner;
        }
        
        public Integer getFeatureCount() {
            return featureCount;
        }
    }
}
