
package kbaserelationengine;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * <p>Original spec-file type: StoreKEAppDescriptorParams</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "app"
})
public class StoreKEAppDescriptorParams {

    /**
     * <p>Original spec-file type: KEAppDescriptor</p>
     * 
     * 
     */
    @JsonProperty("app")
    private KEAppDescriptor app;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * <p>Original spec-file type: KEAppDescriptor</p>
     * 
     * 
     */
    @JsonProperty("app")
    public KEAppDescriptor getApp() {
        return app;
    }

    /**
     * <p>Original spec-file type: KEAppDescriptor</p>
     * 
     * 
     */
    @JsonProperty("app")
    public void setApp(KEAppDescriptor app) {
        this.app = app;
    }

    public StoreKEAppDescriptorParams withApp(KEAppDescriptor app) {
        this.app = app;
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
        return ((((("StoreKEAppDescriptorParams"+" [app=")+ app)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
