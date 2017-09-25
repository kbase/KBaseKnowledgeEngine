package kbaseknowledgeengine;

import java.util.List;

import us.kbase.auth.AuthToken;
import us.kbase.common.service.RpcContext;

public interface IKBaseKnowledgeEngine {
	
	public List<ConnectorStatus> getConnectorsStatus(AuthToken authPart, RpcContext jsonRpcContext);

	public List<AppStatus> getAppsStatus(AuthToken authPart, RpcContext jsonRpcContext);

	public RunAppOutput runApp(RunAppParams params, AuthToken authPart, RpcContext jsonRpcContext);

	public String getConnectorState(GetConnectorStateParams params, AuthToken authPart);
	
	public void cleanAppData(CleanAppDataParams params, AuthToken authPart);
	
    public void cleanConnectorErrors(AuthToken authPart);

}
