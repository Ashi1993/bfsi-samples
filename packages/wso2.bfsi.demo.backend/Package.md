# BFSI Accounts and Transaction Service

## Use case
This ballerina package contains simulated Open Banking resource endpoints. When the different Open Banking endpoints of the package is invoked, the package will respond with a corresponding JSON response.

## Using the Package

### Setup and run

1.  Import the Ballerina package to your project.

    ```ballerina
    import wso2bfsi/accounts_and_transaction_service as bfsi
    ```
2. Invoke the API.

    Example to retrieve an account by ID:

    ```
    http:Response accounts = check bfsi -> /accounts/[accountId]
    ```
    Example to retrieve transactions:

    ```
    http:Response transactions = check bfsi -> /transactions
    ```
3. Run the project.

    ```ballerina
    bal run
    ```

### Setup and run on Choreo

1. Perform steps 1 & 2 as mentioned above.

2. Push the project to a new Github repository.

3. Follow instructions to [connect the project repository to Choreo](https://wso2.com/choreo/docs/develop/manage-repository/connect-your-own-github-repository-to-choreo/)

4. Deploy API by following [instructions to deploy](https://wso2.com/choreo/docs/get-started/tutorials/create-your-first-rest-api/#step-2-deploy) and [test](https://wso2.com/choreo/docs/get-started/tutorials/create-your-first-rest-api/#step-3-test)

5. Invoke the API.

    Sample URL to retrieve a transactions by ID:

    `https://<domain>/<component>/<version>/transactions/T001`
