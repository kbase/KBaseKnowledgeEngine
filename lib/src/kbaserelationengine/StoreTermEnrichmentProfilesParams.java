
package kbaserelationengine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * <p>Original spec-file type: StoreTermEnrichmentProfilesParams</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "profiles"
})
public class StoreTermEnrichmentProfilesParams {

    @JsonProperty("profiles")
    private List<TermEnrichmentProfile> profiles;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("profiles")
    public List<TermEnrichmentProfile> getProfiles() {
        return profiles;
    }

    @JsonProperty("profiles")
    public void setProfiles(List<TermEnrichmentProfile> profiles) {
        this.profiles = profiles;
    }

    public StoreTermEnrichmentProfilesParams withProfiles(List<TermEnrichmentProfile> profiles) {
        this.profiles = profiles;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperties(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return ((((("StoreTermEnrichmentProfilesParams"+" [profiles=")+ profiles)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
