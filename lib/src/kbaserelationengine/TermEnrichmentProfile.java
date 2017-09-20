
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
 * <p>Original spec-file type: TermEnrichmentProfile</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "guid",
    "keapp_guid",
    "source_gene_set_guid",
    "source_gene_set_type",
    "term_namespace",
    "terms"
})
public class TermEnrichmentProfile {

    @JsonProperty("guid")
    private String guid;
    @JsonProperty("keapp_guid")
    private String keappGuid;
    @JsonProperty("source_gene_set_guid")
    private String sourceGeneSetGuid;
    @JsonProperty("source_gene_set_type")
    private String sourceGeneSetType;
    @JsonProperty("term_namespace")
    private String termNamespace;
    @JsonProperty("terms")
    private List<TermEnrichment> terms;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("guid")
    public String getGuid() {
        return guid;
    }

    @JsonProperty("guid")
    public void setGuid(String guid) {
        this.guid = guid;
    }

    public TermEnrichmentProfile withGuid(String guid) {
        this.guid = guid;
        return this;
    }

    @JsonProperty("keapp_guid")
    public String getKeappGuid() {
        return keappGuid;
    }

    @JsonProperty("keapp_guid")
    public void setKeappGuid(String keappGuid) {
        this.keappGuid = keappGuid;
    }

    public TermEnrichmentProfile withKeappGuid(String keappGuid) {
        this.keappGuid = keappGuid;
        return this;
    }

    @JsonProperty("source_gene_set_guid")
    public String getSourceGeneSetGuid() {
        return sourceGeneSetGuid;
    }

    @JsonProperty("source_gene_set_guid")
    public void setSourceGeneSetGuid(String sourceGeneSetGuid) {
        this.sourceGeneSetGuid = sourceGeneSetGuid;
    }

    public TermEnrichmentProfile withSourceGeneSetGuid(String sourceGeneSetGuid) {
        this.sourceGeneSetGuid = sourceGeneSetGuid;
        return this;
    }

    @JsonProperty("source_gene_set_type")
    public String getSourceGeneSetType() {
        return sourceGeneSetType;
    }

    @JsonProperty("source_gene_set_type")
    public void setSourceGeneSetType(String sourceGeneSetType) {
        this.sourceGeneSetType = sourceGeneSetType;
    }

    public TermEnrichmentProfile withSourceGeneSetType(String sourceGeneSetType) {
        this.sourceGeneSetType = sourceGeneSetType;
        return this;
    }

    @JsonProperty("term_namespace")
    public String getTermNamespace() {
        return termNamespace;
    }

    @JsonProperty("term_namespace")
    public void setTermNamespace(String termNamespace) {
        this.termNamespace = termNamespace;
    }

    public TermEnrichmentProfile withTermNamespace(String termNamespace) {
        this.termNamespace = termNamespace;
        return this;
    }

    @JsonProperty("terms")
    public List<TermEnrichment> getTerms() {
        return terms;
    }

    @JsonProperty("terms")
    public void setTerms(List<TermEnrichment> terms) {
        this.terms = terms;
    }

    public TermEnrichmentProfile withTerms(List<TermEnrichment> terms) {
        this.terms = terms;
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
        return ((((((((((((((("TermEnrichmentProfile"+" [guid=")+ guid)+", keappGuid=")+ keappGuid)+", sourceGeneSetGuid=")+ sourceGeneSetGuid)+", sourceGeneSetType=")+ sourceGeneSetType)+", termNamespace=")+ termNamespace)+", terms=")+ terms)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
