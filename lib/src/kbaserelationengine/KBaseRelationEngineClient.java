package kbaserelationengine;

import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import us.kbase.auth.AuthToken;
import us.kbase.common.service.JobState;
import us.kbase.common.service.JsonClientCaller;
import us.kbase.common.service.JsonClientException;
import us.kbase.common.service.RpcContext;
import us.kbase.common.service.UnauthorizedException;

/**
 * <p>Original spec-file module name: KBaseRelationEngine</p>
 * <pre>
 * A KBase module: KBaseRelationEngine
 * </pre>
 */
public class KBaseRelationEngineClient {
    private JsonClientCaller caller;
    private long asyncJobCheckTimeMs = 100;
    private int asyncJobCheckTimeScalePercent = 150;
    private long asyncJobCheckMaxTimeMs = 300000;  // 5 minutes
    private String serviceVersion = "release";


    /** Constructs a client with a custom URL and no user credentials.
     * @param url the URL of the service.
     */
    public KBaseRelationEngineClient(URL url) {
        caller = new JsonClientCaller(url);
    }
    /** Constructs a client with a custom URL.
     * @param url the URL of the service.
     * @param token the user's authorization token.
     * @throws UnauthorizedException if the token is not valid.
     * @throws IOException if an IOException occurs when checking the token's
     * validity.
     */
    public KBaseRelationEngineClient(URL url, AuthToken token) throws UnauthorizedException, IOException {
        caller = new JsonClientCaller(url, token);
    }

    /** Constructs a client with a custom URL.
     * @param url the URL of the service.
     * @param user the user name.
     * @param password the password for the user name.
     * @throws UnauthorizedException if the credentials are not valid.
     * @throws IOException if an IOException occurs when checking the user's
     * credentials.
     */
    public KBaseRelationEngineClient(URL url, String user, String password) throws UnauthorizedException, IOException {
        caller = new JsonClientCaller(url, user, password);
    }

    /** Constructs a client with a custom URL
     * and a custom authorization service URL.
     * @param url the URL of the service.
     * @param user the user name.
     * @param password the password for the user name.
     * @param auth the URL of the authorization server.
     * @throws UnauthorizedException if the credentials are not valid.
     * @throws IOException if an IOException occurs when checking the user's
     * credentials.
     */
    public KBaseRelationEngineClient(URL url, String user, String password, URL auth) throws UnauthorizedException, IOException {
        caller = new JsonClientCaller(url, user, password, auth);
    }

    /** Get the token this client uses to communicate with the server.
     * @return the authorization token.
     */
    public AuthToken getToken() {
        return caller.getToken();
    }

    /** Get the URL of the service with which this client communicates.
     * @return the service URL.
     */
    public URL getURL() {
        return caller.getURL();
    }

    /** Set the timeout between establishing a connection to a server and
     * receiving a response. A value of zero or null implies no timeout.
     * @param milliseconds the milliseconds to wait before timing out when
     * attempting to read from a server.
     */
    public void setConnectionReadTimeOut(Integer milliseconds) {
        this.caller.setConnectionReadTimeOut(milliseconds);
    }

    /** Check if this client allows insecure http (vs https) connections.
     * @return true if insecure connections are allowed.
     */
    public boolean isInsecureHttpConnectionAllowed() {
        return caller.isInsecureHttpConnectionAllowed();
    }

    /** Deprecated. Use isInsecureHttpConnectionAllowed().
     * @deprecated
     */
    public boolean isAuthAllowedForHttp() {
        return caller.isAuthAllowedForHttp();
    }

    /** Set whether insecure http (vs https) connections should be allowed by
     * this client.
     * @param allowed true to allow insecure connections. Default false
     */
    public void setIsInsecureHttpConnectionAllowed(boolean allowed) {
        caller.setInsecureHttpConnectionAllowed(allowed);
    }

    /** Deprecated. Use setIsInsecureHttpConnectionAllowed().
     * @deprecated
     */
    public void setAuthAllowedForHttp(boolean isAuthAllowedForHttp) {
        caller.setAuthAllowedForHttp(isAuthAllowedForHttp);
    }

    /** Set whether all SSL certificates, including self-signed certificates,
     * should be trusted.
     * @param trustAll true to trust all certificates. Default false.
     */
    public void setAllSSLCertificatesTrusted(final boolean trustAll) {
        caller.setAllSSLCertificatesTrusted(trustAll);
    }
    
    /** Check if this client trusts all SSL certificates, including
     * self-signed certificates.
     * @return true if all certificates are trusted.
     */
    public boolean isAllSSLCertificatesTrusted() {
        return caller.isAllSSLCertificatesTrusted();
    }
    /** Sets streaming mode on. In this case, the data will be streamed to
     * the server in chunks as it is read from disk rather than buffered in
     * memory. Many servers are not compatible with this feature.
     * @param streamRequest true to set streaming mode on, false otherwise.
     */
    public void setStreamingModeOn(boolean streamRequest) {
        caller.setStreamingModeOn(streamRequest);
    }

    /** Returns true if streaming mode is on.
     * @return true if streaming mode is on.
     */
    public boolean isStreamingModeOn() {
        return caller.isStreamingModeOn();
    }

    public void _setFileForNextRpcResponse(File f) {
        caller.setFileForNextRpcResponse(f);
    }

    public long getAsyncJobCheckTimeMs() {
        return this.asyncJobCheckTimeMs;
    }

    public void setAsyncJobCheckTimeMs(long newValue) {
        this.asyncJobCheckTimeMs = newValue;
    }

    public int getAsyncJobCheckTimeScalePercent() {
        return this.asyncJobCheckTimeScalePercent;
    }

    public void setAsyncJobCheckTimeScalePercent(int newValue) {
        this.asyncJobCheckTimeScalePercent = newValue;
    }

    public long getAsyncJobCheckMaxTimeMs() {
        return this.asyncJobCheckMaxTimeMs;
    }

    public void setAsyncJobCheckMaxTimeMs(long newValue) {
        this.asyncJobCheckMaxTimeMs = newValue;
    }

    public String getServiceVersion() {
        return this.serviceVersion;
    }

    public void setServiceVersion(String newValue) {
        this.serviceVersion = newValue;
    }

    protected <T> JobState<T> _checkJob(String jobId, TypeReference<List<JobState<T>>> retType) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(jobId);
        List<JobState<T>> res = caller.jsonrpcCall("KBaseRelationEngine._check_job", args, retType, true, true);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: getFeatureSequences</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link kbaserelationengine.GetFeatureSequencesParams GetFeatureSequencesParams}
     * @return   instance of list of type {@link kbaserelationengine.FeatureSequence FeatureSequence}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    protected String _getFeatureSequencesSubmit(GetFeatureSequencesParams params, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        if (this.serviceVersion != null) {
            if (jsonRpcContext == null || jsonRpcContext.length == 0 || jsonRpcContext[0] == null)
                jsonRpcContext = new RpcContext[] {new RpcContext()};
            jsonRpcContext[0].getAdditionalProperties().put("service_ver", this.serviceVersion);
        }
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<String>> retType = new TypeReference<List<String>>() {};
        List<String> res = caller.jsonrpcCall("KBaseRelationEngine._getFeatureSequences_submit", args, retType, true, true, jsonRpcContext);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: getFeatureSequences</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link kbaserelationengine.GetFeatureSequencesParams GetFeatureSequencesParams}
     * @return   instance of list of type {@link kbaserelationengine.FeatureSequence FeatureSequence}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public List<FeatureSequence> getFeatureSequences(GetFeatureSequencesParams params, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        String jobId = _getFeatureSequencesSubmit(params, jsonRpcContext);
        TypeReference<List<JobState<List<List<FeatureSequence>>>>> retType = new TypeReference<List<JobState<List<List<FeatureSequence>>>>>() {};
        long asyncJobCheckTimeMs = this.asyncJobCheckTimeMs;
        while (true) {
            if (Thread.currentThread().isInterrupted())
                throw new JsonClientException("Thread was interrupted");
            try { 
                Thread.sleep(asyncJobCheckTimeMs);
            } catch(Exception ex) {
                throw new JsonClientException("Thread was interrupted", ex);
            }
            asyncJobCheckTimeMs = Math.min(asyncJobCheckTimeMs * this.asyncJobCheckTimeScalePercent / 100, this.asyncJobCheckMaxTimeMs);
            JobState<List<List<FeatureSequence>>> res = _checkJob(jobId, retType);
            if (res.getFinished() != 0L)
                return res.getResult().get(0);
        }
    }

    /**
     * <p>Original spec-file function name: getCompendiumDescriptors</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link kbaserelationengine.GetCompendiumDescriptorsParams GetCompendiumDescriptorsParams}
     * @return   instance of list of type {@link kbaserelationengine.CompendiumDescriptor CompendiumDescriptor}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    protected String _getCompendiumDescriptorsSubmit(GetCompendiumDescriptorsParams params, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        if (this.serviceVersion != null) {
            if (jsonRpcContext == null || jsonRpcContext.length == 0 || jsonRpcContext[0] == null)
                jsonRpcContext = new RpcContext[] {new RpcContext()};
            jsonRpcContext[0].getAdditionalProperties().put("service_ver", this.serviceVersion);
        }
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<String>> retType = new TypeReference<List<String>>() {};
        List<String> res = caller.jsonrpcCall("KBaseRelationEngine._getCompendiumDescriptors_submit", args, retType, true, true, jsonRpcContext);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: getCompendiumDescriptors</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link kbaserelationengine.GetCompendiumDescriptorsParams GetCompendiumDescriptorsParams}
     * @return   instance of list of type {@link kbaserelationengine.CompendiumDescriptor CompendiumDescriptor}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public List<CompendiumDescriptor> getCompendiumDescriptors(GetCompendiumDescriptorsParams params, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        String jobId = _getCompendiumDescriptorsSubmit(params, jsonRpcContext);
        TypeReference<List<JobState<List<List<CompendiumDescriptor>>>>> retType = new TypeReference<List<JobState<List<List<CompendiumDescriptor>>>>>() {};
        long asyncJobCheckTimeMs = this.asyncJobCheckTimeMs;
        while (true) {
            if (Thread.currentThread().isInterrupted())
                throw new JsonClientException("Thread was interrupted");
            try { 
                Thread.sleep(asyncJobCheckTimeMs);
            } catch(Exception ex) {
                throw new JsonClientException("Thread was interrupted", ex);
            }
            asyncJobCheckTimeMs = Math.min(asyncJobCheckTimeMs * this.asyncJobCheckTimeScalePercent / 100, this.asyncJobCheckMaxTimeMs);
            JobState<List<List<CompendiumDescriptor>>> res = _checkJob(jobId, retType);
            if (res.getFinished() != 0L)
                return res.getResult().get(0);
        }
    }

    /**
     * <p>Original spec-file function name: storeKEAppDescriptor</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link kbaserelationengine.StoreKEAppDescriptorParams StoreKEAppDescriptorParams}
     * @return   instance of type {@link kbaserelationengine.GraphUpdateStat GraphUpdateStat}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    protected String _storeKEAppDescriptorSubmit(StoreKEAppDescriptorParams params, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        if (this.serviceVersion != null) {
            if (jsonRpcContext == null || jsonRpcContext.length == 0 || jsonRpcContext[0] == null)
                jsonRpcContext = new RpcContext[] {new RpcContext()};
            jsonRpcContext[0].getAdditionalProperties().put("service_ver", this.serviceVersion);
        }
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<String>> retType = new TypeReference<List<String>>() {};
        List<String> res = caller.jsonrpcCall("KBaseRelationEngine._storeKEAppDescriptor_submit", args, retType, true, true, jsonRpcContext);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: storeKEAppDescriptor</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link kbaserelationengine.StoreKEAppDescriptorParams StoreKEAppDescriptorParams}
     * @return   instance of type {@link kbaserelationengine.GraphUpdateStat GraphUpdateStat}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public GraphUpdateStat storeKEAppDescriptor(StoreKEAppDescriptorParams params, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        String jobId = _storeKEAppDescriptorSubmit(params, jsonRpcContext);
        TypeReference<List<JobState<List<GraphUpdateStat>>>> retType = new TypeReference<List<JobState<List<GraphUpdateStat>>>>() {};
        long asyncJobCheckTimeMs = this.asyncJobCheckTimeMs;
        while (true) {
            if (Thread.currentThread().isInterrupted())
                throw new JsonClientException("Thread was interrupted");
            try { 
                Thread.sleep(asyncJobCheckTimeMs);
            } catch(Exception ex) {
                throw new JsonClientException("Thread was interrupted", ex);
            }
            asyncJobCheckTimeMs = Math.min(asyncJobCheckTimeMs * this.asyncJobCheckTimeScalePercent / 100, this.asyncJobCheckMaxTimeMs);
            JobState<List<GraphUpdateStat>> res = _checkJob(jobId, retType);
            if (res.getFinished() != 0L)
                return res.getResult().get(0);
        }
    }

    /**
     * <p>Original spec-file function name: cleanKEAppResults</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link kbaserelationengine.CleanKEAppResultsParams CleanKEAppResultsParams}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    protected String _cleanKEAppResultsSubmit(CleanKEAppResultsParams params, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        if (this.serviceVersion != null) {
            if (jsonRpcContext == null || jsonRpcContext.length == 0 || jsonRpcContext[0] == null)
                jsonRpcContext = new RpcContext[] {new RpcContext()};
            jsonRpcContext[0].getAdditionalProperties().put("service_ver", this.serviceVersion);
        }
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<String>> retType = new TypeReference<List<String>>() {};
        List<String> res = caller.jsonrpcCall("KBaseRelationEngine._cleanKEAppResults_submit", args, retType, true, true, jsonRpcContext);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: cleanKEAppResults</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link kbaserelationengine.CleanKEAppResultsParams CleanKEAppResultsParams}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public void cleanKEAppResults(CleanKEAppResultsParams params, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        String jobId = _cleanKEAppResultsSubmit(params, jsonRpcContext);
        TypeReference<List<JobState<Object>>> retType = new TypeReference<List<JobState<Object>>>() {};
        long asyncJobCheckTimeMs = this.asyncJobCheckTimeMs;
        while (true) {
            if (Thread.currentThread().isInterrupted())
                throw new JsonClientException("Thread was interrupted");
            try { 
                Thread.sleep(asyncJobCheckTimeMs);
            } catch(Exception ex) {
                throw new JsonClientException("Thread was interrupted", ex);
            }
            asyncJobCheckTimeMs = Math.min(asyncJobCheckTimeMs * this.asyncJobCheckTimeScalePercent / 100, this.asyncJobCheckMaxTimeMs);
            JobState<Object> res = _checkJob(jobId, retType);
            if (res.getFinished() != 0L)
                return;
        }
    }

    /**
     * <p>Original spec-file function name: getKEAppDescriptor</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link kbaserelationengine.GetKEAppDescriptorParams GetKEAppDescriptorParams}
     * @return   instance of type {@link kbaserelationengine.KEAppDescriptor KEAppDescriptor}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    protected String _getKEAppDescriptorSubmit(GetKEAppDescriptorParams params, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        if (this.serviceVersion != null) {
            if (jsonRpcContext == null || jsonRpcContext.length == 0 || jsonRpcContext[0] == null)
                jsonRpcContext = new RpcContext[] {new RpcContext()};
            jsonRpcContext[0].getAdditionalProperties().put("service_ver", this.serviceVersion);
        }
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<String>> retType = new TypeReference<List<String>>() {};
        List<String> res = caller.jsonrpcCall("KBaseRelationEngine._getKEAppDescriptor_submit", args, retType, true, true, jsonRpcContext);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: getKEAppDescriptor</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link kbaserelationengine.GetKEAppDescriptorParams GetKEAppDescriptorParams}
     * @return   instance of type {@link kbaserelationengine.KEAppDescriptor KEAppDescriptor}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public KEAppDescriptor getKEAppDescriptor(GetKEAppDescriptorParams params, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        String jobId = _getKEAppDescriptorSubmit(params, jsonRpcContext);
        TypeReference<List<JobState<List<KEAppDescriptor>>>> retType = new TypeReference<List<JobState<List<KEAppDescriptor>>>>() {};
        long asyncJobCheckTimeMs = this.asyncJobCheckTimeMs;
        while (true) {
            if (Thread.currentThread().isInterrupted())
                throw new JsonClientException("Thread was interrupted");
            try { 
                Thread.sleep(asyncJobCheckTimeMs);
            } catch(Exception ex) {
                throw new JsonClientException("Thread was interrupted", ex);
            }
            asyncJobCheckTimeMs = Math.min(asyncJobCheckTimeMs * this.asyncJobCheckTimeScalePercent / 100, this.asyncJobCheckMaxTimeMs);
            JobState<List<KEAppDescriptor>> res = _checkJob(jobId, retType);
            if (res.getFinished() != 0L)
                return res.getResult().get(0);
        }
    }

    /**
     * <p>Original spec-file function name: storeBiclusters</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link kbaserelationengine.StoreBiclustersParams StoreBiclustersParams}
     * @return   instance of type {@link kbaserelationengine.GraphUpdateStat GraphUpdateStat}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    protected String _storeBiclustersSubmit(StoreBiclustersParams params, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        if (this.serviceVersion != null) {
            if (jsonRpcContext == null || jsonRpcContext.length == 0 || jsonRpcContext[0] == null)
                jsonRpcContext = new RpcContext[] {new RpcContext()};
            jsonRpcContext[0].getAdditionalProperties().put("service_ver", this.serviceVersion);
        }
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<String>> retType = new TypeReference<List<String>>() {};
        List<String> res = caller.jsonrpcCall("KBaseRelationEngine._storeBiclusters_submit", args, retType, true, true, jsonRpcContext);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: storeBiclusters</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link kbaserelationengine.StoreBiclustersParams StoreBiclustersParams}
     * @return   instance of type {@link kbaserelationengine.GraphUpdateStat GraphUpdateStat}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public GraphUpdateStat storeBiclusters(StoreBiclustersParams params, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        String jobId = _storeBiclustersSubmit(params, jsonRpcContext);
        TypeReference<List<JobState<List<GraphUpdateStat>>>> retType = new TypeReference<List<JobState<List<GraphUpdateStat>>>>() {};
        long asyncJobCheckTimeMs = this.asyncJobCheckTimeMs;
        while (true) {
            if (Thread.currentThread().isInterrupted())
                throw new JsonClientException("Thread was interrupted");
            try { 
                Thread.sleep(asyncJobCheckTimeMs);
            } catch(Exception ex) {
                throw new JsonClientException("Thread was interrupted", ex);
            }
            asyncJobCheckTimeMs = Math.min(asyncJobCheckTimeMs * this.asyncJobCheckTimeScalePercent / 100, this.asyncJobCheckMaxTimeMs);
            JobState<List<GraphUpdateStat>> res = _checkJob(jobId, retType);
            if (res.getFinished() != 0L)
                return res.getResult().get(0);
        }
    }

    /**
     * <p>Original spec-file function name: getBiclusters</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link kbaserelationengine.GetBiclustersParams GetBiclustersParams}
     * @return   instance of list of type {@link kbaserelationengine.Bicluster Bicluster}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    protected String _getBiclustersSubmit(GetBiclustersParams params, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        if (this.serviceVersion != null) {
            if (jsonRpcContext == null || jsonRpcContext.length == 0 || jsonRpcContext[0] == null)
                jsonRpcContext = new RpcContext[] {new RpcContext()};
            jsonRpcContext[0].getAdditionalProperties().put("service_ver", this.serviceVersion);
        }
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<String>> retType = new TypeReference<List<String>>() {};
        List<String> res = caller.jsonrpcCall("KBaseRelationEngine._getBiclusters_submit", args, retType, true, true, jsonRpcContext);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: getBiclusters</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link kbaserelationengine.GetBiclustersParams GetBiclustersParams}
     * @return   instance of list of type {@link kbaserelationengine.Bicluster Bicluster}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public List<Bicluster> getBiclusters(GetBiclustersParams params, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        String jobId = _getBiclustersSubmit(params, jsonRpcContext);
        TypeReference<List<JobState<List<List<Bicluster>>>>> retType = new TypeReference<List<JobState<List<List<Bicluster>>>>>() {};
        long asyncJobCheckTimeMs = this.asyncJobCheckTimeMs;
        while (true) {
            if (Thread.currentThread().isInterrupted())
                throw new JsonClientException("Thread was interrupted");
            try { 
                Thread.sleep(asyncJobCheckTimeMs);
            } catch(Exception ex) {
                throw new JsonClientException("Thread was interrupted", ex);
            }
            asyncJobCheckTimeMs = Math.min(asyncJobCheckTimeMs * this.asyncJobCheckTimeScalePercent / 100, this.asyncJobCheckMaxTimeMs);
            JobState<List<List<Bicluster>>> res = _checkJob(jobId, retType);
            if (res.getFinished() != 0L)
                return res.getResult().get(0);
        }
    }

    /**
     * <p>Original spec-file function name: storeTermEnrichmentProfiles</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link kbaserelationengine.StoreTermEnrichmentProfilesParams StoreTermEnrichmentProfilesParams}
     * @return   instance of type {@link kbaserelationengine.GraphUpdateStat GraphUpdateStat}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    protected String _storeTermEnrichmentProfilesSubmit(StoreTermEnrichmentProfilesParams params, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        if (this.serviceVersion != null) {
            if (jsonRpcContext == null || jsonRpcContext.length == 0 || jsonRpcContext[0] == null)
                jsonRpcContext = new RpcContext[] {new RpcContext()};
            jsonRpcContext[0].getAdditionalProperties().put("service_ver", this.serviceVersion);
        }
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<String>> retType = new TypeReference<List<String>>() {};
        List<String> res = caller.jsonrpcCall("KBaseRelationEngine._storeTermEnrichmentProfiles_submit", args, retType, true, true, jsonRpcContext);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: storeTermEnrichmentProfiles</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link kbaserelationengine.StoreTermEnrichmentProfilesParams StoreTermEnrichmentProfilesParams}
     * @return   instance of type {@link kbaserelationengine.GraphUpdateStat GraphUpdateStat}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public GraphUpdateStat storeTermEnrichmentProfiles(StoreTermEnrichmentProfilesParams params, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        String jobId = _storeTermEnrichmentProfilesSubmit(params, jsonRpcContext);
        TypeReference<List<JobState<List<GraphUpdateStat>>>> retType = new TypeReference<List<JobState<List<GraphUpdateStat>>>>() {};
        long asyncJobCheckTimeMs = this.asyncJobCheckTimeMs;
        while (true) {
            if (Thread.currentThread().isInterrupted())
                throw new JsonClientException("Thread was interrupted");
            try { 
                Thread.sleep(asyncJobCheckTimeMs);
            } catch(Exception ex) {
                throw new JsonClientException("Thread was interrupted", ex);
            }
            asyncJobCheckTimeMs = Math.min(asyncJobCheckTimeMs * this.asyncJobCheckTimeScalePercent / 100, this.asyncJobCheckMaxTimeMs);
            JobState<List<GraphUpdateStat>> res = _checkJob(jobId, retType);
            if (res.getFinished() != 0L)
                return res.getResult().get(0);
        }
    }

    /**
     * <p>Original spec-file function name: getFeatureTerms</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link kbaserelationengine.GetFeatureTermsParams GetFeatureTermsParams}
     * @return   instance of list of type {@link kbaserelationengine.FeatureTerms FeatureTerms}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    protected String _getFeatureTermsSubmit(GetFeatureTermsParams params, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        if (this.serviceVersion != null) {
            if (jsonRpcContext == null || jsonRpcContext.length == 0 || jsonRpcContext[0] == null)
                jsonRpcContext = new RpcContext[] {new RpcContext()};
            jsonRpcContext[0].getAdditionalProperties().put("service_ver", this.serviceVersion);
        }
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<String>> retType = new TypeReference<List<String>>() {};
        List<String> res = caller.jsonrpcCall("KBaseRelationEngine._getFeatureTerms_submit", args, retType, true, true, jsonRpcContext);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: getFeatureTerms</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link kbaserelationengine.GetFeatureTermsParams GetFeatureTermsParams}
     * @return   instance of list of type {@link kbaserelationengine.FeatureTerms FeatureTerms}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public List<FeatureTerms> getFeatureTerms(GetFeatureTermsParams params, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        String jobId = _getFeatureTermsSubmit(params, jsonRpcContext);
        TypeReference<List<JobState<List<List<FeatureTerms>>>>> retType = new TypeReference<List<JobState<List<List<FeatureTerms>>>>>() {};
        long asyncJobCheckTimeMs = this.asyncJobCheckTimeMs;
        while (true) {
            if (Thread.currentThread().isInterrupted())
                throw new JsonClientException("Thread was interrupted");
            try { 
                Thread.sleep(asyncJobCheckTimeMs);
            } catch(Exception ex) {
                throw new JsonClientException("Thread was interrupted", ex);
            }
            asyncJobCheckTimeMs = Math.min(asyncJobCheckTimeMs * this.asyncJobCheckTimeScalePercent / 100, this.asyncJobCheckMaxTimeMs);
            JobState<List<List<FeatureTerms>>> res = _checkJob(jobId, retType);
            if (res.getFinished() != 0L)
                return res.getResult().get(0);
        }
    }

    /**
     * <p>Original spec-file function name: storeWSGenome</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link kbaserelationengine.StoreWSGenomeParams StoreWSGenomeParams}
     * @return   instance of type {@link kbaserelationengine.GraphUpdateStat GraphUpdateStat}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    protected String _storeWSGenomeSubmit(StoreWSGenomeParams params, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        if (this.serviceVersion != null) {
            if (jsonRpcContext == null || jsonRpcContext.length == 0 || jsonRpcContext[0] == null)
                jsonRpcContext = new RpcContext[] {new RpcContext()};
            jsonRpcContext[0].getAdditionalProperties().put("service_ver", this.serviceVersion);
        }
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<String>> retType = new TypeReference<List<String>>() {};
        List<String> res = caller.jsonrpcCall("KBaseRelationEngine._storeWSGenome_submit", args, retType, true, true, jsonRpcContext);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: storeWSGenome</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link kbaserelationengine.StoreWSGenomeParams StoreWSGenomeParams}
     * @return   instance of type {@link kbaserelationengine.GraphUpdateStat GraphUpdateStat}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public GraphUpdateStat storeWSGenome(StoreWSGenomeParams params, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        String jobId = _storeWSGenomeSubmit(params, jsonRpcContext);
        TypeReference<List<JobState<List<GraphUpdateStat>>>> retType = new TypeReference<List<JobState<List<GraphUpdateStat>>>>() {};
        long asyncJobCheckTimeMs = this.asyncJobCheckTimeMs;
        while (true) {
            if (Thread.currentThread().isInterrupted())
                throw new JsonClientException("Thread was interrupted");
            try { 
                Thread.sleep(asyncJobCheckTimeMs);
            } catch(Exception ex) {
                throw new JsonClientException("Thread was interrupted", ex);
            }
            asyncJobCheckTimeMs = Math.min(asyncJobCheckTimeMs * this.asyncJobCheckTimeScalePercent / 100, this.asyncJobCheckMaxTimeMs);
            JobState<List<GraphUpdateStat>> res = _checkJob(jobId, retType);
            if (res.getFinished() != 0L)
                return res.getResult().get(0);
        }
    }

    /**
     * <p>Original spec-file function name: connectWSFeatures2RefOrthologs</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link kbaserelationengine.ConnectWSFeatures2RefOrthologsParams ConnectWSFeatures2RefOrthologsParams}
     * @return   instance of type {@link kbaserelationengine.GraphUpdateStat GraphUpdateStat}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    protected String _connectWSFeatures2RefOrthologsSubmit(ConnectWSFeatures2RefOrthologsParams params, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        if (this.serviceVersion != null) {
            if (jsonRpcContext == null || jsonRpcContext.length == 0 || jsonRpcContext[0] == null)
                jsonRpcContext = new RpcContext[] {new RpcContext()};
            jsonRpcContext[0].getAdditionalProperties().put("service_ver", this.serviceVersion);
        }
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<String>> retType = new TypeReference<List<String>>() {};
        List<String> res = caller.jsonrpcCall("KBaseRelationEngine._connectWSFeatures2RefOrthologs_submit", args, retType, true, true, jsonRpcContext);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: connectWSFeatures2RefOrthologs</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link kbaserelationengine.ConnectWSFeatures2RefOrthologsParams ConnectWSFeatures2RefOrthologsParams}
     * @return   instance of type {@link kbaserelationengine.GraphUpdateStat GraphUpdateStat}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public GraphUpdateStat connectWSFeatures2RefOrthologs(ConnectWSFeatures2RefOrthologsParams params, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        String jobId = _connectWSFeatures2RefOrthologsSubmit(params, jsonRpcContext);
        TypeReference<List<JobState<List<GraphUpdateStat>>>> retType = new TypeReference<List<JobState<List<GraphUpdateStat>>>>() {};
        long asyncJobCheckTimeMs = this.asyncJobCheckTimeMs;
        while (true) {
            if (Thread.currentThread().isInterrupted())
                throw new JsonClientException("Thread was interrupted");
            try { 
                Thread.sleep(asyncJobCheckTimeMs);
            } catch(Exception ex) {
                throw new JsonClientException("Thread was interrupted", ex);
            }
            asyncJobCheckTimeMs = Math.min(asyncJobCheckTimeMs * this.asyncJobCheckTimeScalePercent / 100, this.asyncJobCheckMaxTimeMs);
            JobState<List<GraphUpdateStat>> res = _checkJob(jobId, retType);
            if (res.getFinished() != 0L)
                return res.getResult().get(0);
        }
    }

    /**
     * <p>Original spec-file function name: connectWSFeatures2RefOTerms</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link kbaserelationengine.ConnectWSFeatures2RefOTermsParams ConnectWSFeatures2RefOTermsParams}
     * @return   instance of type {@link kbaserelationengine.GraphUpdateStat GraphUpdateStat}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    protected String _connectWSFeatures2RefOTermsSubmit(ConnectWSFeatures2RefOTermsParams params, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        if (this.serviceVersion != null) {
            if (jsonRpcContext == null || jsonRpcContext.length == 0 || jsonRpcContext[0] == null)
                jsonRpcContext = new RpcContext[] {new RpcContext()};
            jsonRpcContext[0].getAdditionalProperties().put("service_ver", this.serviceVersion);
        }
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<String>> retType = new TypeReference<List<String>>() {};
        List<String> res = caller.jsonrpcCall("KBaseRelationEngine._connectWSFeatures2RefOTerms_submit", args, retType, true, true, jsonRpcContext);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: connectWSFeatures2RefOTerms</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link kbaserelationengine.ConnectWSFeatures2RefOTermsParams ConnectWSFeatures2RefOTermsParams}
     * @return   instance of type {@link kbaserelationengine.GraphUpdateStat GraphUpdateStat}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public GraphUpdateStat connectWSFeatures2RefOTerms(ConnectWSFeatures2RefOTermsParams params, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        String jobId = _connectWSFeatures2RefOTermsSubmit(params, jsonRpcContext);
        TypeReference<List<JobState<List<GraphUpdateStat>>>> retType = new TypeReference<List<JobState<List<GraphUpdateStat>>>>() {};
        long asyncJobCheckTimeMs = this.asyncJobCheckTimeMs;
        while (true) {
            if (Thread.currentThread().isInterrupted())
                throw new JsonClientException("Thread was interrupted");
            try { 
                Thread.sleep(asyncJobCheckTimeMs);
            } catch(Exception ex) {
                throw new JsonClientException("Thread was interrupted", ex);
            }
            asyncJobCheckTimeMs = Math.min(asyncJobCheckTimeMs * this.asyncJobCheckTimeScalePercent / 100, this.asyncJobCheckMaxTimeMs);
            JobState<List<GraphUpdateStat>> res = _checkJob(jobId, retType);
            if (res.getFinished() != 0L)
                return res.getResult().get(0);
        }
    }

    public Map<String, Object> status(RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        if (this.serviceVersion != null) {
            if (jsonRpcContext == null || jsonRpcContext.length == 0 || jsonRpcContext[0] == null)
                jsonRpcContext = new RpcContext[] {new RpcContext()};
            jsonRpcContext[0].getAdditionalProperties().put("service_ver", this.serviceVersion);
        }
        List<Object> args = new ArrayList<Object>();
        TypeReference<List<String>> retType1 = new TypeReference<List<String>>() {};
        List<String> res1 = caller.jsonrpcCall("KBaseRelationEngine._status_submit", args, retType1, true, true, jsonRpcContext);
        String jobId = res1.get(0);
        TypeReference<List<JobState<List<Map<String, Object>>>>> retType2 = new TypeReference<List<JobState<List<Map<String, Object>>>>>() {};
        long asyncJobCheckTimeMs = this.asyncJobCheckTimeMs;
        while (true) {
            if (Thread.currentThread().isInterrupted())
                throw new JsonClientException("Thread was interrupted");
            try { 
                Thread.sleep(asyncJobCheckTimeMs);
            } catch(Exception ex) {
                throw new JsonClientException("Thread was interrupted", ex);
            }
            asyncJobCheckTimeMs = Math.min(asyncJobCheckTimeMs * this.asyncJobCheckTimeScalePercent / 100, this.asyncJobCheckMaxTimeMs);
            JobState<List<Map<String, Object>>> res2 = _checkJob(jobId, retType2);
            if (res2.getFinished() != 0L)
                return res2.getResult().get(0);
        }
    }
}
