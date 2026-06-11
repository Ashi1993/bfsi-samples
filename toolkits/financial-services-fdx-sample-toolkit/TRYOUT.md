## Register DCR Application

Dynamic Client Registration (DCR) allows a Data Recipient application to register itself programmatically with the Authorization Server without manual intervention. This is the first step in the FDX onboarding flow and must be completed before any consent or data-sharing operations can take place.

To register a Data Recipient application, use the `POST /fdxrecipientapi/6.5.0/register` endpoint provided by the WSO2 Identity Server with the Open Banking Accelerator. The request body captures the application metadata including redirect URIs, scopes, consent duration settings, and optional intermediary details (e.g., a Data Access Platform sitting between the Data Recipient and the Authorization Server).

Upon successful registration, the server returns a `client_id` and `client_secret` that the application must use in all subsequent OAuth 2.0 flows. The `scope` field in the response confirms which FDX data scopes the application is permitted to request.

```
curl --location 'https://localhost:8243/fdxrecipientapi/6.5.0/register' \
--header 'accept: application/json' \
--header 'x-fapi-interaction-id: c770aef3-6784-41f7-8e0e-ff5f97bddb3a' \
--header 'FDX-API-Actor-Type: BATCH' \
--header 'Content-Type: application/json' \
--data-raw '{
   "client_name":"My Example Client",
   "description":"Recipient Application servicing financial use case requiring permissioned data sharing",
   "redirect_uris":[
      "https://partner.example/callback"
   ],
   "logo_uri":"https://client.example.org/logo.png",
   "client_uri":"https://example.net/",
   "jwks_uri":"https://keystore.openbankingtest.org.uk/0015800001HQQrZAAX/0015800001HQQrZAAX.jwks",
   "grant_types":[
      "authorization_code",
      "client_credentials"
   ],
   "contacts":[
      "support@example.net"
   ],
   "scope":"fdx:accountbasic:read fdx:accountdetail:read fdx:investments:read fdx:transfers:write",
   "duration_type":[
      "TIME_BOUND"
   ],
   "duration_period":365,
   "lookback_period":365,
   "registry_references":[
      {
         "registered_entity_name":"Official recipient name",
         "registered_entity_id":"4HCHXIURY78NNH6JH",
         "registry":"GLEIF"
      }
   ],
   "intermediaries":[
      {
         "name":"Data Access Platform Name",
         "description":"Data Access Platform specializing in servicing permissioned data sharing for Data Recipients",
         "uri":"https://partner.example/",
         "logo_uri":"https://partner.example/logo.png",
         "contacts":[
            "support@partner.com"
         ],
         "registry_references":[
            {
               "registered_entity_name":"Data Access Platform listed company Name",
               "registered_entity_id":"JJH7776512TGMEJSG",
               "registry":"FDX"
            }
         ]
      },
      {
         "name":"Digital Service Provider Name",
         "description":"Digital Service Provider to the Recipient",
         "uri":"https://sub-partner-one.example/",
         "logo_uri":"https://sub-partner-one.example/logo.png",
         "contacts":[
            "support@sub-partner-one.com"
         ],
         "registry_references":[
            {
               "registered_entity_name":"Service Provider listed company Name",
               "registered_entity_id":"9LUQNDG778LI9D1",
               "registry":"GLEIF"
            }
         ]
      }
   ]
}'
```

```
{
    "client_id": "VP0nqDPAWpefrhBEM3Z9_dzvp5ca",
    "client_secret": "HPBiz3tOguGCpfXSaNHbIhC8bMyMWNsbLFWnE3Xcvv8a",
    "client_secret_expires_at": 0,
    "redirect_uris": [
        "https://partner.example/callback"
    ],
    "grant_types": [
        "authorization_code",
        "client_credentials"
    ],
    "ext_application_version": "v3.0.0",
    "ext_application_owner": "is_admin@wso2.com@carbon.super",
    "ext_application_token_lifetime": 3600,
    "ext_user_token_lifetime": 3600,
    "ext_refresh_token_lifetime": 86400,
    "ext_id_token_lifetime": 3600,
    "ext_pkce_mandatory": false,
    "ext_pkce_support_plain": false,
    "ext_public_client": false,
    "ext_token_type": "true",
    "require_pushed_authorization_requests": false,
    "subject_type": "public",
    "ext_allowed_audience": "organization",
    "scope": "fdx:accountbasic:read fdx:accountdetail:read fdx:investments:read fdx:transfers:write"
}
```

## Register an Authorization Detail Type

FDX uses Rich Authorization Requests (RAR) — defined in RFC 9396 — to carry granular consent data alongside a standard OAuth 2.0 authorization request. Before a Data Recipient can include an `authorization_details` object in a PAR or authorization request, the corresponding authorization detail type must be registered in the Identity Server and linked to an API Resource.

This step creates the `fdx_v1.0` authorization detail type, which carries the FDX-specific consent structure (`durationType`, `durationPeriod`, `lookbackPeriod`, `resources`, and `dataClusters`). The JSON Schema embedded in the request defines and validates the shape of this object at the IS level, ensuring only well-formed consent requests can proceed.

The associated scopes (`fdx:accountbasic:read`, `fdx:accountdetail:read`, etc.) are also registered here and will be enforced during token issuance. Use the `POST /api/server/v1/api-resources` endpoint to perform this registration. Save the returned `id` — you will need it to authorize the application in the next step.

```
curl --location 'https://<IS_HOSTNAME>:<IS_PORT>/api/server/v1/api-resources/' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic <BASIC_AUTH_CREDENTIALS>' \
--data '{
    "name": "FDX Authorization Type",
    "identifier": "fdx_v1",
    "description": "FDX v1.0 authorization details type",
    "requiresAuthorization": true,
    "scopes": [
        {
            "description": "fdx:accountbasic:read",
            "displayName": "fdx:accountbasic:read",
            "name": "fdx:accountbasic:read"
        },
        {
            "description": "fdx:accountdetail:read",
            "displayName": "fdx:accountdetail:read",
            "name": "fdx:accountdetail:read"
        },
        {
            "description": "fdx:transactions:read",
            "displayName": "fdx:transactions:read",
            "name": "fdx:transactions:read"
        },
        {
            "description": "fdx:investments:read",
            "displayName": "fdx:investments:read",
            "name": "fdx:investments:read"
        },
        {
            "description": "fdx:transfers:write",
            "displayName": "fdx:transfers:write",
            "name": "fdx:transfers:write"
        }
    ],
    "authorizationDetailsTypes": [
        {
            "name": "FDX v1.0 Type",
            "type": "fdx_v1.0",
            "description": "Authorization type for FDX v1.0",
            "schema": {
                "type": "object",
                "required": [
                    "type",
                    "consentRequest"
                ],
                "properties": {
                    "type": {
                        "type": "string",
                        "enum": [
                            "fdx_v1.0"
                        ]
                    },
                    "consentRequest": {
                        "type": "object",
                        "required": [
                            "durationType",
                            "durationPeriod",
                            "lookbackPeriod",
                            "resources"
                        ],
                        "properties": {
                            "durationType": {
                                "type": "string",
                                "enum": [
                                    "ONE_TIME",
                                    "PERSISTENT",
                                    "TIME_BOUND"
                                ]
                            },
                            "durationPeriod": {
                                "type": "integer"
                            },
                            "lookbackPeriod": {
                                "type": "integer"
                            },
                            "resources": {
                                "type": "array",
                                "items": {
                                    "type": "object",
                                    "required": [
                                        "resourceType",
                                        "dataClusters"
                                    ],
                                    "properties": {
                                        "resourceType": {
                                            "type": "string",
                                            "enum": [
                                                "ACCOUNT", 
                                                "CUSTOMER",
                                                "DOCUMENT",
                                                "PAYMENT"
                                            ]
                                        },
                                        "dataClusters": {
                                            "type": "array",
                                            "items": {
                                                "type": "string",
                                                "enum": [
                                                    "ACCOUNT_BASIC",
                                                    "ACCOUNT_DETAILED",
                                                    "ACCOUNT_PAYMENTS",
                                                    "BILLS",
                                                    "CUSTOMER_CONTACT",
                                                    "CUSTOMER_PERSONAL",
                                                    "IMAGES",
                                                    "INVESTMENTS",
                                                    "NOTIFICATIONS",
                                                    "PAYMENT_SUPPORT",
                                                    "REWARDS",
                                                    "STATEMENTS",
                                                    "TAX",
                                                    "TRANSACTIONS",
                                                    "TRANSFERS"
                                                ]
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    ]
}'
```

```
{
    "id": "0e927a81-6e33-4584-a755-cfc78b644514",
    "name": "FDX Authorization Type",
    "description": "FDX v1.0 authorization details type",
    "identifier": "fdx_v1",
    "type": "BUSINESS",
    "requiresAuthorization": true,
    "scopes": [
        {
            "id": "a8c50fd6-cf99-481e-985f-23f8b41fda41",
            "displayName": "fdx:accountbasic:read",
            "name": "fdx:accountbasic:read",
            "description": "fdx:accountbasic:read"
        },
        {
            "id": "ec1f0026-4ad3-4849-8964-01f50d34f596",
            "displayName": "fdx:accountdetail:read",
            "name": "fdx:accountdetail:read",
            "description": "fdx:accountdetail:read"
        },
        {
            "id": "a608731c-2558-47c4-8b75-7ad1075dbbca",
            "displayName": "fdx:investments:read",
            "name": "fdx:investments:read",
            "description": "fdx:investments:read"
        },
        {
            "id": "15910150-5c55-4d5a-8fd9-cb14baefc977",
            "displayName": "fdx:transactions:read",
            "name": "fdx:transactions:read",
            "description": "fdx:transactions:read"
        },
        {
            "id": "0b5bcede-9c10-4742-b482-76b9cc53c6ac",
            "displayName": "fdx:transfers:write",
            "name": "fdx:transfers:write",
            "description": "fdx:transfers:write"
        }
    ],
    "authorizationDetailsTypes": [
        {
            "id": "22760a0a-d52a-404a-a450-252bf5962ad9",
            "type": "fdx_v1.0",
            "name": "FDX v1.0 Type",
            "description": "Authorization type for FDX v1.0",
            "schema": {
                "type": "object",
                "required": [
                    "type",
                    "consentRequest"
                ],
                "properties": {
                    "type": {
                        "type": "string",
                        "enum": [
                            "fdx_v1.0"
                        ]
                    },
                    "consentRequest": {
                        "type": "object",
                        "required": [
                            "durationType",
                            "durationPeriod",
                            "lookbackPeriod",
                            "resources"
                        ],
                        "properties": {
                            "durationType": {
                                "type": "string",
                                "enum": [
                                    "ONE_TIME",
                                    "PERSISTENT",
                                    "TIME_BOUND"
                                ]
                            },
                            "durationPeriod": {
                                "type": "integer"
                            },
                            "lookbackPeriod": {
                                "type": "integer"
                            },
                            "resources": {
                                "type": "array",
                                "items": {
                                    "type": "object",
                                    "required": [
                                        "resourceType",
                                        "dataClusters"
                                    ],
                                    "properties": {
                                        "resourceType": {
                                            "type": "string",
                                            "enum": [
                                                "ACCOUNT",
                                                "CUSTOMER",
                                                "DOCUMENT",
                                                "PAYMENT"
                                            ]
                                        },
                                        "dataClusters": {
                                            "type": "array",
                                            "items": {
                                                "type": "string",
                                                "enum": [
                                                    "ACCOUNT_BASIC",
                                                    "ACCOUNT_DETAILED",
                                                    "ACCOUNT_PAYMENTS",
                                                    "BILLS",
                                                    "CUSTOMER_CONTACT",
                                                    "CUSTOMER_PERSONAL",
                                                    "IMAGES",
                                                    "INVESTMENTS",
                                                    "NOTIFICATIONS",
                                                    "PAYMENT_SUPPORT",
                                                    "REWARDS",
                                                    "STATEMENTS",
                                                    "TAX",
                                                    "TRANSACTIONS",
                                                    "TRANSFERS"
                                                ]
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    ],
    "properties": []
}
```

## Retrieve application ID using the Client ID

After DCR, the Identity Server internally creates a Service Provider (SP) for the registered application. To authorize the API Resource registered in the previous step against this SP, you first need to look up the SP's internal `id` using the `client_id` returned by the DCR endpoint.

Use the `GET /api/server/v1/applications` endpoint with a filter on `clientId`. Replace `VP0nqDPAWpefrhBEM3Z9_dzvp5ca` with the `client_id` from your DCR response. The returned `id` field (e.g., `a3709414-...`) is the SP identifier used in the authorization step that follows.

```
curl --location 'https://localhost:9446/api/server/v1/applications?filter=clientId+eq+VP0nqDPAWpefrhBEM3Z9_dzvp5ca' \
--header 'Authorization: Basic aXNfYWRtaW5Ad3NvMi5jb206d3NvMjEyMw=='
```

```
{
    "totalResults": 1,
    "startIndex": 1,
    "count": 1,
    "applications": [
        {
            "id": "a3709414-1922-49fa-af83-37e54e1c8226",
            "name": "My_Example_Client",
            "description": "Service Provider for application My_Example_Client",
            "applicationVersion": "v3.0.0",
            "clientId": "VP0nqDPAWpefrhBEM3Z9_dzvp5ca",
            "realm": "",
            "access": "WRITE",
            "self": "/api/server/v1/applications/a3709414-1922-49fa-af83-37e54e1c8226"
        }
    ],
    "links": []
}
```

## Authorize the authorization details type to the created application

With both the SP `id` (from the previous step) and the API Resource `id` (from the registration step) in hand, you can now link the `fdx_v1.0` authorization detail type to the Data Recipient application. This grants the application permission to request that authorization detail type in a PAR or authorization request.

The `policyIdentifier` is set to `RBAC` and the `scopes` list should match the FDX scopes registered with the API Resource. The `authorizationDetailsTypes` array must include `fdx_v1.0`. Replace the application ID in the URL path with the SP `id` retrieved in the previous step.

A `200 OK` response confirms the authorization detail type is now enabled for the application.

```
curl --location 'https://localhost:9446/api/server/v1/applications/a3709414-1922-49fa-af83-37e54e1c8226/authorized-apis' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic aXNfYWRtaW5Ad3NvMi5jb206d3NvMjEyMw==' \
--data '{
    "id": "0e927a81-6e33-4584-a755-cfc78b644514",
    "policyIdentifier": "RBAC",
    "scopes": [
        "fdx:accountbasic:read",
        "fdx:accountdetail:read",
        "fdx:transactions:read",
        "fdx:investments:read",
        "fdx:transfers:write"
    ],
    "authorizationDetailsTypes": [
        "fdx_v1.0"
    ]
}'
```

```
200 OK
```

## Sample RAR Object for Payments

The Rich Authorization Request (RAR) object is the FDX-specific consent payload sent by a Data Recipient to describe exactly what data access it is requesting. For payment use cases, the `resourceType` is set to `PAYMENT` and the `dataClusters` array includes `TRANSFERS`.

The `paymentInfo` sub-object provides the specific payment intent details: the source account (`fromAccountId`), the payee (`toPayeeId`), the amount, the merchant account, and the due date. This information is displayed to the end user on the consent screen for explicit authorization.

The `durationType` of `PERSISTENT` means the consent does not expire automatically; `lookbackPeriod` of 90 days governs how far back historical data may be accessed.

```json
[
   {
      "type":"fdx_v1.0",
      "consentRequest":{
         "durationType":"PERSISTENT",
         "durationPeriod":365,
         "lookbackPeriod":90,
         "resources":[
            {
               "resourceType":"PAYMENT",
               "dataClusters":[
                  "TRANSFERS"
               ],
               "paymentInfo":{
                  "fromAccountId":"ACCOUNT-123",
                  "toPayeeId":"PAYEE-ABC",
                  "amount":10.99,
                  "merchantAccountId":"MERCHANT-ACCOUNT-ID-0001",
                  "dueDate":"2021-08-17"
               }
            }
         ]
      }
   }
]
```

## Initiate PAR

Pushed Authorization Requests (PAR) — defined in RFC 9126 — improve security by sending the full authorization request to the server before redirecting the user, rather than encoding it in the browser URL. The server returns a short-lived `request_uri` that is used in the subsequent browser redirect.

For FDX, the PAR request must include:
- A signed `request` JWT (the Request Object) containing the authorization parameters including the `authorization_details` RAR object.
- A `client_assertion` JWT for client authentication (private key JWT method).
- The `authorization_details` as a URL-encoded JSON array matching the consent structure registered earlier.

The `request_uri` returned in the response is valid for 60 seconds (as indicated by `expires_in`). You must initiate the browser-based authorization redirect within this window.

**Note:** Replace `{{request_object}}` and `{{client_assertion}}` with the actual signed JWTs generated using the Data Recipient's private key. Sample values are provided below the curl command for reference.

```
curl --location 'https://localhost:9446/oauth2/par' \
--header 'Accept: */*' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--header 'Cookie: sessionNonceCookie-873050e9-9901-43a9-8a39-e9ce6d5b68fe=dc9dd41e-888c-4270-b26d-47e520e88328; sessionNonceCookie-da36e3fe-089d-4dfc-8e2d-7d875dc8cfa0=bec06259-8c6b-4996-91a8-8d81416f4c35' \
--data-urlencode 'request={{request_object}}' \
--data-urlencode 'client_assertion_type=urn:ietf:params:oauth:client-assertion-type:jwt-bearer' \
--data-urlencode 'client_assertion={{client_assertion}}' \
--data-urlencode 'client_id=VP0nqDPAWpefrhBEM3Z9_dzvp5ca' \
--data-urlencode 'authorization_details=[
   {
      "type":"fdx_v1.0",
      "consentRequest":{
         "durationType":"PERSISTENT",
         "durationPeriod":365,
         "lookbackPeriod":90,
         "resources":[
            {
               "resourceType":"PAYMENT",
               "dataClusters":[
                  "TRANSFERS"
               ],
               "paymentInfo":{
                  "fromAccountId":"ACCOUNT-123",
                  "toPayeeId":"PAYEE-ABC",
                  "amount":10.99,
                  "merchantAccountId":"MERCHANT-ACCOUNT-ID-0001",
                  "dueDate":"2021-08-17"
               }
            }
         ]
      }
   }
]'
```

Sample Request Object

eyJ0eXAiOiJKV1QiLCJraWQiOiJzQ2VrTmdTV0lhdVEzNGtsUmhER3Fmd3BqYzQiLCJhbGciOiJQUzI1NiJ9.eyJpYXQiOjE3ODAzOTM1MjcsIm5iZiI6MTc4MDM5MzUzMSwiZXhwIjoxNzgwMzk3MTMxLCJqdGkiOiJpVWZlVkotZVZQZ3E2Q2M0cTFuRzQiLCJhdWQiOiJodHRwczovL2xvY2FsaG9zdDo5NDQ2L29hdXRoMi90b2tlbiIsImlzcyI6IlZQMG5xRFBBV3BlZnJoQkVNM1o5X2R6dnA1Y2EiLCJzY29wZSI6Im9wZW5pZCBmZHg6YWNjb3VudGJhc2ljOnJlYWQiLCJhdXRob3JpemF0aW9uX2RldGFpbHMiOlt7InR5cGUiOiJmZHhfdjEuMCIsImNvbnNlbnRSZXF1ZXN0Ijp7ImR1cmF0aW9uVHlwZSI6IlBFUlNJU1RFTlQiLCJkdXJhdGlvblBlcmlvZCI6MzY1LCJsb29rYmFja1BlcmlvZCI6OTAsInJlc291cmNlcyI6W3sicmVzb3VyY2VUeXBlIjoiUEFZTUVOVCIsImRhdGFDbHVzdGVycyI6WyJUUkFOU0ZFUlMiXSwicGF5bWVudEluZm8iOnsiZnJvbUFjY291bnRJZCI6IkFDQ09VTlQtMTIzIiwidG9QYXllZUlkIjoiUEFZRUUtQUJDIiwiYW1vdW50IjoxMC45OSwibWVyY2hhbnRBY2NvdW50SWQiOiJNRVJDSEFOVC1BQ0NPVU5ULUlELTAwMDEiLCJkdWVEYXRlIjoiMjAyMS0wOC0xNyJ9fV19fV0sImNsYWltcyI6eyJpZF90b2tlbiI6eyJhY3IiOnsidmFsdWVzIjpbInVybjpjZHMuYXU6Y2RyOjMiXSwiZXNzZW50aWFsIjp0cnVlfX0sInVzZXJpbmZvIjp7fX0sInJlc3BvbnNlX3R5cGUiOiJjb2RlIGlkX3Rva2VuIiwicmVkaXJlY3RfdXJpIjoiaHR0cHM6Ly9wYXJ0bmVyLmV4YW1wbGUvY2FsbGJhY2siLCJzdGF0ZSI6InN1aXRlIiwibm9uY2UiOiI4ZmM0Y2JiNC0yODdiLTQyYWEtYTFkMC02N2RjZTZmYzc0NzkiLCJjbGllbnRfaWQiOiJWUDBucURQQVdwZWZyaEJFTTNaOV9kenZwNWNhIiwiY29kZV9jaGFsbGVuZ2UiOiI4V2Rid3ZYblJZbHk0Q0otR3JZQjJhNl9MNEwtMFpXWE1tam5EaXR0YVV3IiwiY29kZV9jaGFsbGVuZ2VfbWV0aG9kIjoiUzI1NiJ9.m5n5V-qL5rfacyuMlHSa0JGi14rxEmmsGdXSHBEndamwzG9pnpnFYU7QDQBAbI__dRCT-z2u7r1ODaPAoLaEhDrblM7dXWaBQN4sYEQb-4Ar_rozBzJH8SW4ichiDuRy-MvbNTMvwuOMtGdxw6ZXaHGcREswEU6TA5Z7ZMl_bKXJ4rmFwEn9_omDK9O35ifsqSktKlayFU7-n4_LxLPvoyqVgJn_EW83iU8IR0IgF9yBXjo8u5jeu8CnFtXX_Z927m4zbLZ7JLXBivP6w8K2BHLDYzA_QfEafqoIl5Ede29CLjZGGdjFxMljv7rJXqHOo87MNHj1pwoKidz5CiB9Hw

Sample Client Assertion

eyJ0eXAiOiJKV1QiLCJraWQiOiJzQ2VrTmdTV0lhdVEzNGtsUmhER3Fmd3BqYzQiLCJhbGciOiJQUzI1NiJ9.eyJpYXQiOjE3ODAzOTM1MzEsIm5iZiI6MTc4MDM5MzUyNywiZXhwIjoxNzgwMzk3MTMxLCJqdGkiOiIxNzgwMzkzNTMxMTQyIiwic3ViIjoiVlAwbnFEUEFXcGVmcmhCRU0zWjlfZHp2cDVjYSIsImF1ZCI6Imh0dHBzOi8vbG9jYWxob3N0Ojk0NDYvb2F1dGgyL3Rva2VuIiwiaXNzIjoiVlAwbnFEUEFXcGVmcmhCRU0zWjlfZHp2cDVjYSJ9.l32RwhnoNWrXxbCO0S2gcL3BZEWguZzPAesEz-raOQ1oeZ0l-clNjheqoTOP5BJtS8EHa-pEwIuOLk7nWDGI-8akEb8S5tqMUQcch2m6C1Ef0Hb54jucv3B4opFAa13yOBenYBoNqswVrB42a2nEaoGoaV357yrIOJT0MJaPO-cbECAqKVhD1H_U65VQgMrJTfkPmmJ2IAs10CdWTJw_3HuBAnoz94vmpKP6bObK4Z9qiyyKq67DtwuJPbE5kOYwy9mPthouVhr3s6EiDIHMq35NxGiRJkfhTtjeFAfsvg40FneyMofqF-SK7dfmYQFhkKU4St745N-0l1vuh5KbSg

Sample Response
```
{
    "expires_in": 60,
    "request_uri": "urn:ietf:params:oauth:par:request_uri:824d4563-3315-4619-89c7-9887d7b7fcc5"
}
```

## Authorize Request

With the `request_uri` obtained from the PAR step, redirect the end user's browser to the Authorization Server's `/oauth2/authorize` endpoint. The Identity Server will load the pre-registered authorization request, display the FDX consent screen showing the requested data clusters and payment details, and prompt the user to approve or deny the consent.

Replace `request_uri` with the value returned by your PAR call. The `client_id` must match the one from your DCR registration. Upon user approval, the server redirects to the `redirect_uri` with an authorization `code` and `id_token` (for `code id_token` response type). This code is exchanged for an access token in the standard OAuth 2.0 token endpoint flow.

```
https://localhost:9446/oauth2/authorize?client_id=VP0nqDPAWpefrhBEM3Z9_dzvp5ca&request_uri=urn:ietf:params:oauth:par:request_uri:824d4563-3315-4619-89c7-9887d7b7fcc5
```