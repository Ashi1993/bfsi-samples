package org.wso2.openbanking.fdx.extensions.model;

import com.fasterxml.jackson.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.*;
import javax.validation.Valid;

import io.swagger.annotations.*;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Consumer related data fetched from the banking backend.
 **/
@ApiModel(description = "Consumer related data fetched from the banking backend.")
@JsonTypeName("SuccessResponsePopulateConsentAuthorizeScreenData_consumerData")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2026-05-05T10:53:22.833811+05:30[Asia/Colombo]", comments = "Generator version: 7.14.0")
public class SuccessResponsePopulateConsentAuthorizeScreenDataConsumerData {
  private @Valid List<@Valid SuccessResponsePopulateConsentAuthorizeScreenDataConsumerDataAccountsInner> accounts = new ArrayList<>();
  private Map<String, Object> additionalProperties = new HashMap<>();
  public SuccessResponsePopulateConsentAuthorizeScreenDataConsumerData() {
  }

  /**
   * List of all user accounts/resources selectable in the UI
   **/
  public SuccessResponsePopulateConsentAuthorizeScreenDataConsumerData accounts(List<@Valid SuccessResponsePopulateConsentAuthorizeScreenDataConsumerDataAccountsInner> accounts) {
    this.accounts = accounts;
    return this;
  }

  
  @ApiModelProperty(value = "List of all user accounts/resources selectable in the UI")
  @JsonProperty("accounts")
  @Valid public List<@Valid SuccessResponsePopulateConsentAuthorizeScreenDataConsumerDataAccountsInner> getAccounts() {
    return accounts;
  }

  @JsonProperty("accounts")
  public void setAccounts(List<@Valid SuccessResponsePopulateConsentAuthorizeScreenDataConsumerDataAccountsInner> accounts) {
    this.accounts = accounts;
  }

  public SuccessResponsePopulateConsentAuthorizeScreenDataConsumerData addAccountsItem(SuccessResponsePopulateConsentAuthorizeScreenDataConsumerDataAccountsInner accountsItem) {
    if (this.accounts == null) {
      this.accounts = new ArrayList<>();
    }

    this.accounts.add(accountsItem);
    return this;
  }

  public SuccessResponsePopulateConsentAuthorizeScreenDataConsumerData removeAccountsItem(SuccessResponsePopulateConsentAuthorizeScreenDataConsumerDataAccountsInner accountsItem) {
    if (accountsItem != null && this.accounts != null) {
      this.accounts.remove(accountsItem);
    }

    return this;
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
    SuccessResponsePopulateConsentAuthorizeScreenDataConsumerData successResponsePopulateConsentAuthorizeScreenDataConsumerData = (SuccessResponsePopulateConsentAuthorizeScreenDataConsumerData) o;
    return Objects.equals(this.accounts, successResponsePopulateConsentAuthorizeScreenDataConsumerData.accounts) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accounts, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SuccessResponsePopulateConsentAuthorizeScreenDataConsumerData {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    accounts: ").append(toIndentedString(accounts)).append("\n");
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

