package kbaseknowledgeengine;

import java.util.ArrayList;
import java.util.List;

import us.kbase.auth.AuthToken;
import us.kbase.common.service.RpcContext;

public class FakeKBaseKnowledgeEngine implements IKBaseKnowledgeEngine {
	
	static int jobId = 12346720;
	static int STAT_CHANGE_TIME_DELAY = 5000;
	static final Long cms = System.currentTimeMillis();
	
	static final String STATE_NONE = "none";
	static final String STATE_ACCEPTED = "accepted";
	static final String STATE_QUEUED = "queued";
	static final String STATE_STARTED = "started";
	static final String STATE_FINISHED = "finished";
	static final String STATE_ERROR = "error";
	
	
	static final String[][] _apps = new String[][]{
    	{"kbadmin", "A1", "Orthology GO profiles",       		"12346715", STATE_FINISHED, "<output>", "500000", "0", "500000",   "" + (cms + 1000), "" + (cms + 50000), "" + (cms + (3600 + 2389)*1000), "" + (cms + (3600 * 5000)*1000)},
    	{"kbadmin", "A2", "Expression: biclusters (HCL)", 		"12346716", STATE_FINISHED, "<output>", "2000",   "0", "200000",   "" + (cms + 1000), "" + (cms + 53000), "" + (cms + (3600 + 1237)*1000), "" + (cms + (3600 * 5000)*1000)},
    	{"kbadmin", "A3", "Expression: GO enrichemt",     		"12346717", STATE_FINISHED, "<output>", "2000",   "0", "4500",     "" + (cms + 1000), "" + (cms + 53000), "" + (cms + (3600 + 1237)*1000), "" + (cms + (3600 * 5000)*1000)},
    	{"kbadmin", "A4", "Expression: orthology GO profiles",	"12346718", STATE_FINISHED, "<output>", "2000",   "0", "5800",     "" + (cms + 1000), "" + (cms + 53000), "" + (cms + (3600 + 1237)*1000), "" + (cms + (3600 * 5000)*1000)},
    	{"kbadmin", "A5", "Fitness: biclusters (HCL)",	 		"12346719", STATE_FINISHED, "<output>", "4590",   "0", "4590548",  "" + (cms + 1000), "" + (cms + 53000), "" + (cms + (3600 + 1237)*1000), "" + (cms + (3600 * 5000)*1000)},
    	{"kbadmin", "A6", "Fitness: GO enrichemt",     			"12346720", STATE_FINISHED, "<output>", "4590",   "0", "49048",    "" + (cms + 1000), "" + (cms + 53000), "" + (cms + (3600 + 1237)*1000), "" + (cms + (3600 * 5000)*1000)},
    	{"kbadmin", "A7", "Fitness: orthology GO profiles",		"0",        STATE_NONE, 	"<output>", "0",      "0", "0",        "0", "0", "0", "0" }
    };  
	String[][] _connectors = new String[][]{
		{"psnovichkov", "<obj_ref>", "<obj_type>", "GenomeHomologyConnector", "Genome homology connector", "12346712", STATE_FINISHED, "<output>", "4858", "0", "4858", "" + (cms + 1000), "" + (cms + 50000), "" + (cms + (3600 + 2389)*1000)},
		{"rsutormin",   "<obj_ref>", "<obj_type>", "GenomeHomologyConnector", "Genome homology connector", "12346713", STATE_QUEUED,   "<output>", "4457", "0", "4457", "" + (cms + 1000), "" + (cms + 53000), "" + (cms + (3600 + 1237)*1000)},
	};
	
    
    List<AppStatus> fakeApps;
    List<ConnectorStatus> fakeConnectors;

    class RunAppThread extends Thread {
    	AppStatus appStatus;
    	public RunAppThread(AppStatus appStatus){
    		this.appStatus = appStatus;
    	}
    	
    	@Override
		public void run() {
			try {
				// State None
				appStatus
					.withJobId("0")
					.withState(STATE_ACCEPTED)
					.withNewReNodes(0L)
					.withUpdatedReNodes(0L)
					.withNewReLinks(0L)
					.withQueuedEpochMs(0L)
					.withStartedEpochMs(0L)
					.withFinishedEpochMs(0L);
					
				Thread.sleep(STAT_CHANGE_TIME_DELAY);
				
				// State queued
				appStatus
					.withJobId("" + (jobId++))
					.withState(STATE_QUEUED)
					.withQueuedEpochMs(System.currentTimeMillis());
				
				Thread.sleep(STAT_CHANGE_TIME_DELAY);
				
				// State started
				appStatus
					.withState(STATE_STARTED)
					.withStartedEpochMs(System.currentTimeMillis());
				
				Thread.sleep(STAT_CHANGE_TIME_DELAY);
				
				// State started
				appStatus
					.withState(STATE_FINISHED)
	        		.withNewReNodes(Long.parseLong("12767346"))
	        		.withUpdatedReNodes(Long.parseLong("165246"))
	        		.withNewReLinks(Long.parseLong("23748234"))					
					.withFinishedEpochMs(System.currentTimeMillis());
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}    	
    }
	
    public FakeKBaseKnowledgeEngine() {
    	buildApps();
    	buildConnectors();
    }

    private void buildConnectors() {
    	fakeConnectors = new ArrayList<ConnectorStatus>();		

        for(String[] _connector: _connectors){
            fakeConnectors.add(
            	new ConnectorStatus()
         			.withUser(_connector[0])
         			.withObjRef(_connector[1])
         			.withObjType(_connector[2])
         			.withConnectorApp(_connector[3])
                    .withConnectorTitle(_connector[4])
         			.withJobId(_connector[5])
         			.withState(_connector[6])
         			.withOutput(_connector[7])
         			.withNewReNodes(Long.parseLong(_connector[8]))
         			.withUpdatedReNodes(Long.parseLong(_connector[9]))
         			.withNewReLinks(Long.parseLong(_connector[10]))
         			.withQueuedEpochMs(Long.parseLong(_connector[11]))
         			.withStartedEpochMs(Long.parseLong(_connector[12]))
         			.withFinishedEpochMs(Long.parseLong(_connector[13])));      	
        }
    	
	}

	private void buildApps() {
		fakeApps = new ArrayList<AppStatus>();            
        
        for(String[] appVals: _apps){
        	AppStatus appStatus = new AppStatus();
        	initAppStatus(appStatus, appVals);
            fakeApps.add( appStatus);   	
        }        		
	}

	private void initAppStatus(AppStatus appStatus, String[] vals){
    	appStatus
			.withUser(vals[0])
			.withApp(vals[1])
			.withAppTitle(vals[2])
			.withJobId(vals[3])
			.withState(vals[4])
			.withOutput(vals[5])
			.withNewReNodes(Long.parseLong(vals[6]))
			.withUpdatedReNodes(Long.parseLong(vals[7]))
			.withNewReLinks(Long.parseLong(vals[8]))
			.withQueuedEpochMs(Long.parseLong(vals[9]))
			.withStartedEpochMs(Long.parseLong(vals[10]))
			.withFinishedEpochMs(Long.parseLong(vals[11]))
			.withScheduledEpochMs(Long.parseLong(vals[12]));        
    }
    
    @Override
	public List<ConnectorStatus> getConnectorsStatus(AuthToken authPart, RpcContext jsonRpcContext) {
		return fakeConnectors;
	}


    @Override
	public List<AppStatus> getAppsStatus(AuthToken authPart, RpcContext jsonRpcContext) {
		return fakeApps;
	}


    @Override
	public RunAppOutput runApp(RunAppParams params, AuthToken authPart, RpcContext jsonRpcContext) {
    	Long cms = System.currentTimeMillis();
		
        for(AppStatus appStatus: fakeApps){
        	if(appStatus.getApp().equals(params.getApp())){
        		new RunAppThread(appStatus).start();
//        		appStatus
//        		.withJobId("12317624")
//        		.withState("finished")
//        		.withNewReNodes(Long.parseLong("12767346"))
//        		.withUpdatedReNodes(Long.parseLong("165246"))
//        		.withNewReLinks(Long.parseLong("23748234"))
//        		.withQueuedEpochMs(Long.parseLong("" + cms))
//        		.withStartedEpochMs(Long.parseLong("" + (cms + 10023)))
//        		.withFinishedEpochMs(Long.parseLong("" + (cms + 32346*1000)))    
//        		.withScheduledEpochMs(Long.parseLong("" + (cms + (3600 * 5000)*1000)));        		
        	}
        }		
        return new RunAppOutput().withJobId("" + jobId);
	}

    @Override
	public void testInit(AuthToken authPart, RpcContext jsonRpcContext) {
		buildApps();
	}
	
	public static void main(String[] args) {
		FakeKBaseKnowledgeEngine f =  new FakeKBaseKnowledgeEngine();
		System.out.println("Connectors: " + f.getConnectorsStatus(null, null).size());
		System.out.println("Apps: " + f.getAppsStatus(null, null).size());
		
		
		System.out.println("Before:" + f.getAppsStatus(null, null).get(6));
		f.runApp(new RunAppParams().withApp("A7"), null, null);
		System.out.println("After:" + f.getAppsStatus(null, null).get(6));
	}
	
	
}
