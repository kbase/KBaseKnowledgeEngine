package kbaseknowledgeengine;

import java.util.ArrayList;
import java.util.List;

import us.kbase.auth.AuthToken;
import us.kbase.common.service.RpcContext;

public class FakeKBaseKnowledgeEngine {
    List<ConnectorStatus> fakeConnectors = new ArrayList<ConnectorStatus>();
    List<AppStatus> fakeApps = new ArrayList<AppStatus>();

	
    public FakeKBaseKnowledgeEngine() {
    	Long cms = System.currentTimeMillis();

    	// Build fake connectors
        String[][] _connectors = new String[][]{
        	{"psnovichkov", "<obj_ref>", "<obj_type>", "HomologyConnector", "12346712", "finished", "<output>", "4858", "0", "4858", "" + (cms + 1000), "" + (cms + 50000), "" + (cms + (3600 + 2389)*1000)},
        	{"rsutormin",   "<obj_ref>", "<obj_type>", "HomologyConnector", "12346713", "queued",   "<output>", "4457", "0", "4457", "" + (cms + 1000), "" + (cms + 53000), "" + (cms + (3600 + 1237)*1000)},
        };
        
        for(String[] _connector: _connectors){
            fakeConnectors.add(
            	new ConnectorStatus()
         			.withUser(_connector[0])
         			.withObjRef(_connector[1])
         			.withObjType(_connector[2])
         			.withConnectorApp(_connector[3])
         			.withJobId(_connector[4])
         			.withState(_connector[5])
         			.withOutput(_connector[6])
         			.withNewReNodes(Long.parseLong(_connector[7]))
         			.withUpdatedReNodes(Long.parseLong(_connector[8]))
         			.withNewReLinks(Long.parseLong(_connector[9]))
         			.withQueuedEpochMs(Long.parseLong(_connector[10]))
         			.withStartedEpochMs(Long.parseLong(_connector[11]))
         			.withFinishedEpochMs(Long.parseLong(_connector[12])));      	
        }

        // Build fake apps
        String[][] _apps = new String[][]{
        	{"kbadmin", "Orthology GO profiles",       			"12346715", "finished", "<output>", "500000", "0", "500000",   "" + (cms + 1000), "" + (cms + 50000), "" + (cms + (3600 + 2389)*1000)},
        	{"kbadmin", "Expression: biclusters (HCL)", 		"12346716", "finished", "<output>", "2000",   "0", "200000",   "" + (cms + 1000), "" + (cms + 53000), "" + (cms + (3600 + 1237)*1000)},
        	{"kbadmin", "Expression: GO enrichemt",     		"12346717", "finished", "<output>", "2000",   "0", "4500",     "" + (cms + 1000), "" + (cms + 53000), "" + (cms + (3600 + 1237)*1000)},
        	{"kbadmin", "Expression: orthology GO profiles",	"12346718", "finished", "<output>", "2000",   "0", "5800",     "" + (cms + 1000), "" + (cms + 53000), "" + (cms + (3600 + 1237)*1000)},
        	{"kbadmin", "Fitness: biclusters (HCL)",	 		"12346719", "finished", "<output>", "4590",   "0", "4590548",  "" + (cms + 1000), "" + (cms + 53000), "" + (cms + (3600 + 1237)*1000)},
        	{"kbadmin", "Fitness: GO enrichemt",     			"12346720", "finished", "<output>", "4590",   "0", "49048",    "" + (cms + 1000), "" + (cms + 53000), "" + (cms + (3600 + 1237)*1000)},
        	{"kbadmin", "Fitness: orthology GO profiles",		"0",        "none", 	"<output>", "0",      "0", "0",        "0", "0", "0" }
        };              
        
        for(String[] _app: _apps){
            fakeApps.add(
            	new AppStatus()
            		.withUser(_app[0])
            		.withApp(_app[1])
            		.withJobId(_app[2])
            		.withState(_app[3])
            		.withOutput(_app[4])
            		.withNewReNodes(Long.parseLong(_app[5]))
            		.withUpdatedReNodes(Long.parseLong(_app[6]))
            		.withNewReLinks(Long.parseLong(_app[7]))
            		.withQueuedEpochMs(Long.parseLong(_app[8]))
            		.withStartedEpochMs(Long.parseLong(_app[9]))
            		.withFinishedEpochMs(Long.parseLong(_app[9])));        	
        }        
    }


	public List<ConnectorStatus> getConnectorsStatus(AuthToken authPart, RpcContext jsonRpcContext) {
		return fakeConnectors;
	}


	public List<AppStatus> getAppsStatus(AuthToken authPart, RpcContext jsonRpcContext) {
		return fakeApps;
	}


	public RunAppOutput runApp(RunAppParams params, AuthToken authPart, RpcContext jsonRpcContext) {
    	Long cms = System.currentTimeMillis();
		
        for(AppStatus appStatus: fakeApps){
        	if(appStatus.getApp().equals(params.getApp())){
        		appStatus
        		.withJobId("12317624")
        		.withState("finished")
        		.withNewReNodes(Long.parseLong("12767346"))
        		.withUpdatedReNodes(Long.parseLong("165246"))
        		.withNewReLinks(Long.parseLong("23748234"))
        		.withQueuedEpochMs(Long.parseLong("" + cms))
        		.withStartedEpochMs(Long.parseLong("" + (cms + 10023)))
        		.withFinishedEpochMs(Long.parseLong("" + (cms + 32346*1000)));    
        	}
        }		
        return new RunAppOutput().withJobId("12317624");
	}

	public static void main(String[] args) {
		FakeKBaseKnowledgeEngine f =  new FakeKBaseKnowledgeEngine();
		System.out.println("Connectors: " + f.getConnectorsStatus(null, null).size());
		System.out.println("Apps: " + f.getAppsStatus(null, null).size());
		
		
		System.out.println("Before:" + f.getAppsStatus(null, null).get(6));
		f.runApp(new RunAppParams().withApp("Fitness: orthology GO profiles"), null, null);
		System.out.println("After:" + f.getAppsStatus(null, null).get(6));
	}
	
	
}
