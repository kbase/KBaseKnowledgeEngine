/*
A KBase module: KBaseKnowledgeEngine
*/

module KBaseKnowledgeEngine {
	/* A boolean. 0 = false, other = true. */
	typedef int boolean;

	/*
	  state - one of queued, started, finished, error.
	  output - either empty for queued/started states or error message for error state or output message for finished.
	*/
	typedef structure {
		string user;
		string obj_ref;
		string obj_type;
		string connector_app;
		string connector_title;
		string job_id;
		string state;
		string output;
		int new_re_nodes;
		int updated_re_nodes;
		int new_re_links;
		int queued_epoch_ms;
		int started_epoch_ms;
		int finished_epoch_ms;
	} ConnectorStatus;

	funcdef getConnectorsStatus() returns (list<ConnectorStatus>) authentication required; 

	/*
	  state - one of none, queued, started, finished, error.
	  output - either empty for queued/started states or error message for error state or output message for finished.
	*/
	typedef structure {
		string user;
		string app;
		string app_title;
		string job_id;
		string state;
		string output;
		int new_re_nodes;
		int updated_re_nodes;
		int new_re_links;
		int queued_epoch_ms;
		int started_epoch_ms;
		int finished_epoch_ms;
		int scheduled_epoch_ms;
	} AppStatus;

	funcdef getAppsStatus() returns (list<AppStatus>) authentication required; 

	/*
	  app - name of registered KB-SDK module configured to be compatible with KE.
	  ref_mode - flag for public reference data processing (accessible only for admins).
	*/
	typedef structure {
		string app;
		boolean ref_mode;
	} RunAppParams;

	typedef structure {
		string job_id;
	} RunAppOutput;

	/*
		Execute KE-App.  
	*/
	funcdef runApp(RunAppParams params) returns (RunAppOutput) authentication required;
	
	
	/*
		Restores the initial state (for testing)
	*/
	funcdef testInit() returns () authentication required;
};
