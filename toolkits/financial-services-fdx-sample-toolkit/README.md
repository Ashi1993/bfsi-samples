# WSO2 Open Banking FDX Compliance Reference Implementation

A reference implementation for the [FDX (Financial Data Exchange)](https://financialdataexchange.org/) specification, built on top of the WSO2 Financial Services Accelerator 4.0.0.

## Overview

This toolkit provides extension point implementations and a demo backend to help developers build FDX-compliant Open Banking solutions using WSO2 Identity Server (IS) 7.2.0 and WSO2 API Manager (APIM) 4.6.0. It covers the full consent lifecycle — creation, authorization, retrieval, validation, and revocation — along with token management, DCR, RAR-based authorization detail processing, and event notification flows.

## Repository Structure

```
financial-services-fdx-sample-toolkit/
├── fs-compliance-toolkit-fdx/
│   ├── reference-implementation-openbanking-fdx/   # WSO2 accelerator extension implementations
│   │   └── src/
│   │       ├── gen/java/org/wso2/openbanking/fdx/extensions/
│   │       │   ├── api/                            # Auto-generated JAX-RS API interfaces (OpenAPI Generator)
│   │       │   └── model/                          # Auto-generated request/response model classes
│   │       └── main/
│   │           ├── java/org/wso2/openbanking/fdx/extensions/
│   │           │   ├── impl/consent/               # Consent flow extension implementations
│   │           │   ├── impl/dcr/                   # DCR pre/post processing implementations
│   │           │   ├── utils/                      # FDX utilities and constants
│   │           │   └── exceptions/                 # Custom FDX exception types
│   │           ├── openapi/                        # OpenAPI spec for accelerator extension points
│   │           └── webapp/WEB-INF/                 # Servlet and app configuration
│   ├── org.wso2.financial.services.fdx.identity/   # OSGi bundle for IS-level authorization detail processing
│   │   └── src/main/java/org/wso2/financial/services/fdx/identity/
│   │       ├── authorize/impl/                     # FDX authorization detail processor implementation
│   │       ├── authorize/model/                    # FDX authorization details model
│   │       └── authorize/utils/                    # Authorization detail utilities
│   └── demo-backend/                               # Sample banking services for demos and testing
│       └── src/main/java/com/wso2/openbanking/fdx/demo/backend/
│           ├── services/                           # Account, bank, payment, and funds confirmation endpoints
│           ├── configurations/                     # Application configuration classes
│           └── util/                               # Common utilities and error constants
└── sample-configs/                                 # Sample deployment configuration files
    ├── wso2is-7.2.0-deployment.toml
    └── wso2am-4.6.0-deployment.toml
```

> **Note:** Code under `src/gen/` is auto-generated from the OpenAPI spec in `src/main/openapi/` using the [OpenAPI Generator CLI](https://openapi-generator.tech/). Do not edit files in `src/gen/` directly — regenerate them by running `mvn generate-sources`.

### Modules

| Module | Artifact | Purpose |
|--------|----------|---------|
| `reference-implementation-openbanking-fdx` | `api#reference-implementation#ob#fdx.war` | Implements WSO2 accelerator extension APIs for the FDX consent flow |
| `org.wso2.financial.services.fdx.identity` | `org.wso2.financial.services.fdx.identity-1.0.0.jar` | OSGi bundle deployed into WSO2 IS to process FDX RAR authorization details |
| `demo-backend` | `api#openbanking#fdx#backend.war` | Provides mock banking endpoints (accounts, payments, funds confirmation) for demos and testing |

## Prerequisites

- **Java** 11
- **Maven** 3.x
- **WSO2 Identity Server** 7.2.0 with the Financial Services Accelerator
- **WSO2 API Manager** 4.6.0 (optional, for full API gateway deployment)

## Build

Build all modules from the project root:

```bash
mvn clean install
```

The built artifacts are placed in each module's `target/` directory.

## Deployment

### Reference Implementation

Deploy `api#reference-implementation#ob#fdx.war` to the WSO2 Identity Server's servlet container (TomEE/Tomcat). This registers the FDX extension endpoints that the accelerator invokes during consent flows.

```
https://<IS_HOST>:<PORT>/api/reference-implementation/ob/fdx/
```

### Identity Module

Deploy `org.wso2.financial.services.fdx.identity-1.0.0.jar` as an OSGi bundle into WSO2 Identity Server's `repository/components/dropins/` directory. This registers the `FDXAuthorizationDetailProcessorImpl` component that processes FDX RAR (`authorization_details`) objects during OAuth 2.0 authorization flows.

### Demo Backend

Deploy `api#openbanking#fdx#backend.war` to any servlet container accessible by the API Manager. It exposes mock banking service endpoints:

| Path | Service |
|------|---------|
| `/services/accounts/*` | Account information |
| `/services/bankaccounts/*` | Bank account operations |
| `/services/banks/*` | Bank details |
| `/services/fundsConfirmation/*` | Funds availability checks |
| `/services/payments/*` | Payment operations |

### Sample Configurations

Reference deployment configuration files for both products are provided under `sample-configs/`:

| File | Product |
|------|---------|
| `wso2is-7.2.0-deployment.toml` | WSO2 Identity Server 7.2.0 |
| `wso2am-4.6.0-deployment.toml` | WSO2 API Manager 4.6.0 |

## Extension Points

The reference implementation covers the following WSO2 accelerator extension hooks:

- **DCR** — pre/post client registration and update processing
- **Consent lifecycle** — creation, retrieval, revocation, authorization screen population, persistence, and access validation
- **Authorization details (RAR)** — `fdx_v1.0` authorization detail type processing and scope-to-data-cluster mapping (via the identity OSGi bundle)
- **Token & authorize flows** — token refresh handling, authorization validation
- **Event notifications** — event creation, subscription management, polling
- **Error handling** — custom error response mapping
- **File operations** — file upload and retrieval

Extension point contracts are defined in `fs-compliance-toolkit-fdx/reference-implementation-openbanking-fdx/src/main/openapi/accelerator-extensions-v1.0.4.yaml`.

## Try Out

See [TRYOUT.md](TRYOUT.md) for a step-by-step guide covering DCR, authorization detail type registration, PAR, and the full consent authorization flow.

## License

Copyright (c) WSO2 LLC. All rights reserved.
