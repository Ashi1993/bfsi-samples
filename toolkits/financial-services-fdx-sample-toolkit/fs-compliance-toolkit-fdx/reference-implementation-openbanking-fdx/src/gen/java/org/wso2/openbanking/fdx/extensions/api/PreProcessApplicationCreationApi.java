package org.wso2.openbanking.fdx.extensions.api;

import org.wso2.openbanking.fdx.extensions.model.AppCreateProcessRequestBody;
import org.wso2.openbanking.fdx.extensions.model.ErrorResponse;
import org.wso2.openbanking.fdx.extensions.model.Response200ForApplicationCreation;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import javax.validation.constraints.*;
import javax.validation.Valid;

/**
* Represents a collection of functions to interact with the API endpoints.
*/
@Path("/pre-process-application-creation")
@Api(description = "the pre-process-application-creation API")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2026-05-05T10:53:22.833811+05:30[Asia/Colombo]", comments = "Generator version: 7.14.0")
public class PreProcessApplicationCreationApi {

    @POST
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "handle pre validations & changes to the manual application creation", notes = "", response = Response200ForApplicationCreation.class, authorizations = {
        @Authorization(value = "OAuth2", scopes = {
             }),
        
        @Authorization(value = "BasicAuth")
         }, tags={ "Manual Client Registration" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Ok", response = Response200ForApplicationCreation.class),
        @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
        @ApiResponse(code = 500, message = "Server Error", response = ErrorResponse.class)
    })
    public Response preProcessApplicationCreationPost(@Valid @NotNull AppCreateProcessRequestBody appCreateProcessRequestBody) {
        return Response.ok().entity("magic!").build();
    }
}
