
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
 * <p>Original spec-file type: TermEnrichment</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "term_guid",
    "sample_count",
    "total_count",
    "expected_count",
    "p_value"
})
public class TermEnrichment {

    @JsonProperty("term_guid")
    private String termGuid;
    @JsonProperty("sample_count")
    private Long sampleCount;
    @JsonProperty("total_count")
    private Long totalCount;
    @JsonProperty("expected_count")
    private Long expectedCount;
    @JsonProperty("p_value")
    private Double pValue;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("term_guid")
    public String getTermGuid() {
        return termGuid;
    }

    @JsonProperty("term_guid")
    public void setTermGuid(String termGuid) {
        this.termGuid = termGuid;
    }

    public TermEnrichment withTermGuid(String termGuid) {
        this.termGuid = termGuid;
        return this;
    }

    @JsonProperty("sample_count")
    public Long getSampleCount() {
        return sampleCount;
    }

    @JsonProperty("sample_count")
    public void setSampleCount(Long sampleCount) {
        this.sampleCount = sampleCount;
    }

    public TermEnrichment withSampleCount(Long sampleCount) {
        this.sampleCount = sampleCount;
        return this;
    }

    @JsonProperty("total_count")
    public Long getTotalCount() {
        return totalCount;
    }

    @JsonProperty("total_count")
    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public TermEnrichment withTotalCount(Long totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    @JsonProperty("expected_count")
    public Long getExpectedCount() {
        return expectedCount;
    }

    @JsonProperty("expected_count")
    public void setExpectedCount(Long expectedCount) {
        this.expectedCount = expectedCount;
    }

    public TermEnrichment withExpectedCount(Long expectedCount) {
        this.expectedCount = expectedCount;
        return this;
    }

    @JsonProperty("p_value")
    public Double getPValue() {
        return pValue;
    }

    @JsonProperty("p_value")
    public void setPValue(Double pValue) {
        this.pValue = pValue;
    }

    public TermEnrichment withPValue(Double pValue) {
        this.pValue = pValue;
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
        return ((((((((((((("TermEnrichment"+" [termGuid=")+ termGuid)+", sampleCount=")+ sampleCount)+", totalCount=")+ totalCount)+", expectedCount=")+ expectedCount)+", pValue=")+ pValue)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
