
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
 * <p>Original spec-file type: StoreBiclustersParams</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "biclusters"
})
public class StoreBiclustersParams {

    @JsonProperty("biclusters")
    private List<Bicluster> biclusters;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("biclusters")
    public List<Bicluster> getBiclusters() {
        return biclusters;
    }

    @JsonProperty("biclusters")
    public void setBiclusters(List<Bicluster> biclusters) {
        this.biclusters = biclusters;
    }

    public StoreBiclustersParams withBiclusters(List<Bicluster> biclusters) {
        this.biclusters = biclusters;
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
        return ((((("StoreBiclustersParams"+" [biclusters=")+ biclusters)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
