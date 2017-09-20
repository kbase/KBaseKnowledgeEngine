package kbaserelationengine;

import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import us.kbase.auth.AuthToken;
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
public class KBaseRelationEngineServiceClient {
    private JsonClientCaller caller;
    private String serviceVersion = "release";
    private static URL DEFAULT_URL = null;
    static {
        try {
            DEFAULT_URL = new URL("https://kbase.us/services/service_wizard");
        } catch (MalformedURLException mue) {
            throw new RuntimeException("Compile error in client - bad url compiled");
        }
    }

    /** Constructs a client with the default url and no user credentials.*/
    public KBaseRelationEngineServiceClient() {
       caller = new JsonClientCaller(DEFAULT_URL);
        caller.setDynamic(true);
    }


    /** Constructs a client with a custom URL and no user credentials.
     * @param url the URL of the service.
     */
    public KBaseRelationEngineServiceClient(URL url) {
        caller = new JsonClientCaller(url);
        caller.setDynamic(true);
    }
    /** Constructs a client with a custom URL.
     * @param url the URL of the service.
     * @param token the user's authorization token.
     * @throws UnauthorizedException if the token is not valid.
     * @throws IOException if an IOException occurs when checking the token's
     * validity.
     */
    public KBaseRelationEngineServiceClient(URL url, AuthToken token) throws UnauthorizedException, IOException {
        caller = new JsonClientCaller(url, token);
        caller.setDynamic(true);
    }

    /** Constructs a client with a custom URL.
     * @param url the URL of the service.
     * @param user the user name.
     * @param password the password for the user name.
     * @throws UnauthorizedException if the credentials are not valid.
     * @throws IOException if an IOException occurs when checking the user's
     * credentials.
     */
    public KBaseRelationEngineServiceClient(URL url, String user, String password) throws UnauthorizedException, IOException {
        caller = new JsonClientCaller(url, user, password);
        caller.setDynamic(true);
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
    public KBaseRelationEngineServiceClient(URL url, String user, String password, URL auth) throws UnauthorizedException, IOException {
        caller = new JsonClientCaller(url, user, password, auth);
        caller.setDynamic(true);
    }

    /** Constructs a client with the default URL.
     * @param token the user's authorization token.
     * @throws UnauthorizedException if the token is not valid.
     * @throws IOException if an IOException occurs when checking the token's
     * validity.
     */
    public KBaseRelationEngineServiceClient(AuthToken token) throws UnauthorizedException, IOException {
        caller = new JsonClientCaller(DEFAULT_URL, token);
        caller.setDynamic(true);
    }

    /** Constructs a client with the default URL.
     * @param user the user name.
     * @param password the password for the user name.
     * @throws UnauthorizedException if the credentials are not valid.
     * @throws IOException if an IOException occurs when checking the user's
     * credentials.
     */
    public KBaseRelationEngineServiceClient(String user, String password) throws UnauthorizedException, IOException {
        caller = new JsonClientCaller(DEFAULT_URL, user, password);
        caller.setDynamic(true);
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

    public String getServiceVersion() {
        return this.serviceVersion;
    }

    public void setServiceVersion(String newValue) {
        this.serviceVersion = newValue;
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
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<List<FeatureSequence>>> retType = new TypeReference<List<List<FeatureSequence>>>() {};
        List<List<FeatureSequence>> res = caller.jsonrpcCall("KBaseRelationEngine.getFeatureSequences", args, retType, true, true, jsonRpcContext, this.serviceVersion);
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
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<List<CompendiumDescriptor>>> retType = new TypeReference<List<List<CompendiumDescriptor>>>() {};
        List<List<CompendiumDescriptor>> res = caller.jsonrpcCall("KBaseRelationEngine.getCompendiumDescriptors", args, retType, true, true, jsonRpcContext, this.serviceVersion);
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
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<GraphUpdateStat>> retType = new TypeReference<List<GraphUpdateStat>>() {};
        List<GraphUpdateStat> res = caller.jsonrpcCall("KBaseRelationEngine.storeKEAppDescriptor", args, retType, true, true, jsonRpcContext, this.serviceVersion);
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
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<Object> retType = new TypeReference<Object>() {};
        caller.jsonrpcCall("KBaseRelationEngine.cleanKEAppResults", args, retType, false, true, jsonRpcContext, this.serviceVersion);
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
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<KEAppDescriptor>> retType = new TypeReference<List<KEAppDescriptor>>() {};
        List<KEAppDescriptor> res = caller.jsonrpcCall("KBaseRelationEngine.getKEAppDescriptor", args, retType, true, true, jsonRpcContext, this.serviceVersion);
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
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<GraphUpdateStat>> retType = new TypeReference<List<GraphUpdateStat>>() {};
        List<GraphUpdateStat> res = caller.jsonrpcCall("KBaseRelationEngine.storeBiclusters", args, retType, true, true, jsonRpcContext, this.serviceVersion);
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
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<List<Bicluster>>> retType = new TypeReference<List<List<Bicluster>>>() {};
        List<List<Bicluster>> res = caller.jsonrpcCall("KBaseRelationEngine.getBiclusters", args, retType, true, true, jsonRpcContext, this.serviceVersion);
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
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<GraphUpdateStat>> retType = new TypeReference<List<GraphUpdateStat>>() {};
        List<GraphUpdateStat> res = caller.jsonrpcCall("KBaseRelationEngine.storeTermEnrichmentProfiles", args, retType, true, true, jsonRpcContext, this.serviceVersion);
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
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<List<FeatureTerms>>> retType = new TypeReference<List<List<FeatureTerms>>>() {};
        List<List<FeatureTerms>> res = caller.jsonrpcCall("KBaseRelationEngine.getFeatureTerms", args, retType, true, true, jsonRpcContext, this.serviceVersion);
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
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<GraphUpdateStat>> retType = new TypeReference<List<GraphUpdateStat>>() {};
        List<GraphUpdateStat> res = caller.jsonrpcCall("KBaseRelationEngine.storeWSGenome", args, retType, true, true, jsonRpcContext, this.serviceVersion);
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
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<GraphUpdateStat>> retType = new TypeReference<List<GraphUpdateStat>>() {};
        List<GraphUpdateStat> res = caller.jsonrpcCall("KBaseRelationEngine.connectWSFeatures2RefOrthologs", args, retType, true, true, jsonRpcContext, this.serviceVersion);
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
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<GraphUpdateStat>> retType = new TypeReference<List<GraphUpdateStat>>() {};
        List<GraphUpdateStat> res = caller.jsonrpcCall("KBaseRelationEngine.connectWSFeatures2RefOTerms", args, retType, true, true, jsonRpcContext, this.serviceVersion);
        return res.get(0);
    }

    public Map<String, Object> status(RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        TypeReference<List<Map<String, Object>>> retType = new TypeReference<List<Map<String, Object>>>() {};
        List<Map<String, Object>> res = caller.jsonrpcCall("KBaseRelationEngine.status", args, retType, true, false, jsonRpcContext, this.serviceVersion);
        return res.get(0);
    }
}
