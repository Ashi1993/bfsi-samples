package org.wso2.openbanking.fdx.extensions.model;

import com.fasterxml.jackson.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.HashMap;
import java.util.Map;
import javax.validation.constraints.*;
import javax.validation.Valid;

import io.swagger.annotations.*;
import java.util.Objects;

/**
 * A user account or resource representation
 **/
@ApiModel(description = "A user account or resource representation")
@JsonTypeName("Account")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2026-05-05T10:53:22.833811+05:30[Asia/Colombo]", comments = "Generator version: 7.14.0")
public class Account  {
  private String displayName;
  private String title;
  private String description;
  private Map<String, Object> additionalProperties = new HashMap<>();

  public Account() {
  }

  @JsonCreator
  public Account(
    @JsonProperty(required = true, value = "displayName") String displayName
  ) {
    super(
    );
    this.displayName = displayName;
  }

  /**
   * Account display name
   **/
  public Account displayName(String displayName) {
    this.displayName = displayName;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Account display name")
  @JsonProperty(required = true, value = "displayName")
  @NotNull public String getDisplayName() {
    return displayName;
  }

  @JsonProperty(required = true, value = "displayName")
  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  /**
   * Account title
   **/
  public Account title(String title) {
    this.title = title;
    return this;
  }

  
  @ApiModelProperty(value = "Account title")
  @JsonProperty("title")
  public String getTitle() {
    return title;
  }

  @JsonProperty("title")
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Account description
   **/
  public Account description(String description) {
    this.description = description;
    return this;
  }

  
  @ApiModelProperty(value = "Account description")
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  @JsonProperty("description")
  public void setDescription(String description) {
    this.description = description;
  }


  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
  }

  @JsonAnySetter
  public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Account account = (Account) o;
    return Objects.equals(this.displayName, account.displayName) &&
        Objects.equals(this.title, account.title) &&
        Objects.equals(this.description, account.description) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(displayName, title, description, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Account {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    displayName: ").append(toIndentedString(displayName)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }


}

