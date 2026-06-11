/**
 * Copyright (c) 2025, WSO2 LLC. (https://www.wso2.com).
 * <p>
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 *     http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.financial.services.fdx.identity.authorize.impl;

public class FDXAuthorizationDetailProcessorImplTests {

    // TODO: FIx the tests using latest dependency versions
//    private FDXAuthorizationDetailProcessorImpl processor;
//    private AuthorizedAPIManagementServiceImpl authorizedAPIManagementService;
//    private final String tenantDomain = "carbon.super";
//    private final String clientId = "sample-client-id";
//    ApplicationManagementService applicationManagementService;
//    ServiceProvider serviceProvider;
//    OAuthAppDO mockOAuthAppDO;
//
//    @BeforeMethod
//    public void setUp() {
//        authorizedAPIManagementService = mock(AuthorizedAPIManagementServiceImpl.class);
//        processor = new FDXAuthorizationDetailProcessorImpl();
//        applicationManagementService = mock(ApplicationManagementService.class);
//        OAuth2ServiceComponentHolder.setApplicationMgtService(applicationManagementService);
//        serviceProvider = mock(ServiceProvider.class);
//        mockOAuthAppDO = mock(OAuthAppDO.class);
//        when(mockOAuthAppDO.getOauthConsumerKey()).thenReturn(clientId);
//    }
//
//    private void setCache(MockedStatic<AppInfoCache> appInfoCache, OAuthAppDO appDO) {
//
//        appDO.setOauthConsumerKey("sample-client-id");
//        AuthenticatedUser user = new AuthenticatedUser();
//        user.setTenantDomain(tenantDomain);
//        appDO.setUser(user);
//
//        AppInfoCache mockAppInfoCache = mock(AppInfoCache.class);
//        lenient().when(mockAppInfoCache.getValueFromCache(clientId, tenantDomain)).thenReturn(appDO);
//        appInfoCache.when(AppInfoCache::getInstance).thenReturn(mockAppInfoCache);
//    }
//
//    private AuthorizationDetail mockAuthorizationDetail(Map<String, Object> dataClusters) {
//        AuthorizationDetail authorizationDetail = mock(AuthorizationDetail.class);
//        authorizationDetail.setType("fdx_v1.0");
//        Map<String, Object> details = new HashMap<>();
//        Map<String, Object> consentRequest = new HashMap<>();
//        ArrayList<Object> resources = new ArrayList<>();
//        resources.add(dataClusters);
//        consentRequest.put("durationType", "DURATION_TYPE");
//        consentRequest.put("durationPeriod", 1);
//        consentRequest.put("lookbackPeriod", 1);
//        consentRequest.put("resources", resources);
//        details.put("consentRequest", consentRequest);
//        when(authorizationDetail.getDetails()).thenReturn(details);
//        return authorizationDetail;
//    }
//
//    @Test
//    public void testValidateSuccessfulValidation() throws Exception {
//        // Mock AuthorizationDetail
//        Map<String, Object> dataClusters = new HashMap<>();
//        dataClusters.put("dataClusters", Arrays.asList("TRANSACTIONS", "INVESTMENTS"));
//        AuthorizationDetail authorizationDetail = mockAuthorizationDetail(dataClusters);
//
//        List<AuthorizedScopes> mockScopes = Arrays.asList(new AuthorizedScopes("policy-1",
//                Arrays.asList("fdx:transactions:read", "fdx:investments:read")));
//
//        // Mock AuthorizationDetailsContext
//        AuthorizationDetailsContext context = mock(AuthorizationDetailsContext.class);
//        when(context.getAuthorizationDetail()).thenReturn(authorizationDetail);
//        when(context.getOAuthAppDO()).thenReturn(mockOAuthAppDO);
//
//        try (MockedStatic<IdentityTenantUtil> mockedStatic = Mockito.mockStatic(IdentityTenantUtil.class)) {
//            mockedStatic.when(IdentityTenantUtil::getTenantDomainFromContext).thenReturn(tenantDomain);
//
//            try (MockedStatic<OAuthServerConfiguration> oAuthServerConfiguration = mockStatic(
//                    OAuthServerConfiguration.class)) {
//                OAuthServerConfiguration mockOAuthServerConfiguration = mock(OAuthServerConfiguration.class);
//                oAuthServerConfiguration.when(
//                        OAuthServerConfiguration::getInstance).thenReturn(mockOAuthServerConfiguration);
//
//                try (MockedStatic<AppInfoCache> appInfoCache = mockStatic(AppInfoCache.class)) {
//                    setCache(appInfoCache, mockOAuthAppDO);
//                    doReturn(serviceProvider).when(applicationManagementService)
//                            .getServiceProviderByClientId(anyString(), anyString(), anyString());
//                    try (MockedStatic<IdentityDataHolder> holderMockedStatic = Mockito
//                            .mockStatic(
//                                    IdentityDataHolder.class)) {
//                        IdentityDataHolder mockIdentityDataHolder = mock(IdentityDataHolder.class);
//                        holderMockedStatic.when(IdentityDataHolder::getInstance)
//                                .thenReturn(mockIdentityDataHolder);
//                        when(mockIdentityDataHolder.getAuthorizedAPIManagementService())
//                                .thenReturn(authorizedAPIManagementService);
//
//                        try (MockedStatic<AuthorizationDetailProcessorUtils> utilsMockedStatic = Mockito.mockStatic(
//                                AuthorizationDetailProcessorUtils.class)) {
//                            utilsMockedStatic.when(
//                                            () -> AuthorizationDetailProcessorUtils
//                                            .getApplicationResourceIdByClientId(
//                                                    clientId,
//                                                    tenantDomain))
//                                    .thenReturn("app123");
//                            utilsMockedStatic.when(
//                                            () -> AuthorizationDetailProcessorUtils.getAuthorizedScopesByAppId(
//                                                    "app123",
//                                                    tenantDomain))
//                                    .thenReturn(mockScopes);
//
//                            // Run validation
//                            ValidationResult result = processor.validate(context);
//
//                            // Assert
//                            Assert.assertTrue(result.isValid());
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    @Test
//    public void testValidateInvalidDataCluster() throws Exception {
//        // Mock AuthorizationDetail
//        Map<String, Object> dataClusters = new HashMap<>();
//        dataClusters.put("dataClusters", Arrays.asList("INVALID_CLUSTER1", "INVALID_CLUSTER2"));
//        AuthorizationDetail authorizationDetail = mockAuthorizationDetail(dataClusters);
//        when(authorizationDetail.getType()).thenReturn("fdx_v1.0");
//
//        List<AuthorizedScopes> mockScopes = Arrays.asList(new AuthorizedScopes("policy-1",
//                Arrays.asList("fdx:transactions:read", "fdx:investments:read")));
//
//        // Mock AuthorizationDetailsContext
//        AuthorizationDetailsContext context = mock(AuthorizationDetailsContext.class);
//        when(context.getAuthorizationDetail()).thenReturn(authorizationDetail);
//        when(context.getOAuthAppDO()).thenReturn(mockOAuthAppDO);
//
//        try (MockedStatic<IdentityTenantUtil> mockedStatic = Mockito.mockStatic(IdentityTenantUtil.class)) {
//            mockedStatic.when(IdentityTenantUtil::getTenantDomainFromContext).thenReturn(tenantDomain);
//
//            try (MockedStatic<OAuthServerConfiguration> oAuthServerConfiguration = mockStatic(
//                    OAuthServerConfiguration.class)) {
//                OAuthServerConfiguration mockOAuthServerConfiguration = mock(OAuthServerConfiguration.class);
//                oAuthServerConfiguration.when(
//                        OAuthServerConfiguration::getInstance).thenReturn(mockOAuthServerConfiguration);
//
//                try (MockedStatic<AppInfoCache> appInfoCache = mockStatic(AppInfoCache.class)) {
//                    setCache(appInfoCache, mockOAuthAppDO);
//                    doReturn(serviceProvider).when(applicationManagementService)
//                            .getServiceProviderByClientId(anyString(), anyString(), anyString());
//                    try (MockedStatic<IdentityDataHolder> holderMockedStatic = Mockito
//                            .mockStatic(
//                                    IdentityDataHolder.class)) {
//                        IdentityDataHolder mockIdentityDataHolder = mock(IdentityDataHolder.class);
//                        holderMockedStatic.when(IdentityDataHolder::getInstance)
//                                .thenReturn(mockIdentityDataHolder);
//                        when(mockIdentityDataHolder.getAuthorizedAPIManagementService())
//                                .thenReturn(authorizedAPIManagementService);
//
//                        try (MockedStatic<AuthorizationDetailProcessorUtils> utilsMockedStatic = Mockito.mockStatic(
//                                AuthorizationDetailProcessorUtils.class)) {
//                            utilsMockedStatic.when(
//                                          () -> AuthorizationDetailProcessorUtils.getApplicationResourceIdByClientId(
//                                                    clientId,
//                                                    tenantDomain))
//                                    .thenReturn("app123");
//                            utilsMockedStatic.when(
//                                            () -> AuthorizationDetailProcessorUtils.getAuthorizedScopesByAppId(
//                                                    "app123",
//                                                    tenantDomain))
//                                    .thenReturn(mockScopes);
//
//                            // Execute and Assert
//                            Assert.assertThrows(AuthorizationDetailsProcessingException.class,
//                                    () -> processor.validate(context));
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//
//    @Test
//    public void testValidateInvalidScope() throws Exception {
//        // Mock AuthorizationDetail
//        Map<String, Object> dataClusters = new HashMap<>();
//        dataClusters.put("dataClusters", Arrays.asList("TRANSACTIONS", "INVESTMENTS"));
//        AuthorizationDetail authorizationDetail = mockAuthorizationDetail(dataClusters);
//        when(authorizationDetail.getType()).thenReturn("fdx_v1.0");
//
//        List<AuthorizedScopes> mockScopes = Arrays.asList(new AuthorizedScopes("policy-1",
//                Arrays.asList("fdx:transactions:read", "fdx:images:read")));
//
//        // Mock AuthorizationDetailsContext
//        AuthorizationDetailsContext context = mock(AuthorizationDetailsContext.class);
//        when(context.getAuthorizationDetail()).thenReturn(authorizationDetail);
//        when(context.getOAuthAppDO()).thenReturn(mockOAuthAppDO);
//
//        try (MockedStatic<IdentityTenantUtil> mockedStatic = Mockito.mockStatic(IdentityTenantUtil.class)) {
//            mockedStatic.when(IdentityTenantUtil::getTenantDomainFromContext).thenReturn(tenantDomain);
//
//            try (MockedStatic<OAuthServerConfiguration> oAuthServerConfiguration = mockStatic(
//                    OAuthServerConfiguration.class)) {
//                OAuthServerConfiguration mockOAuthServerConfiguration = mock(OAuthServerConfiguration.class);
//                oAuthServerConfiguration.when(
//                        OAuthServerConfiguration::getInstance).thenReturn(mockOAuthServerConfiguration);
//
//                try (MockedStatic<AppInfoCache> appInfoCache = mockStatic(AppInfoCache.class)) {
//                    setCache(appInfoCache, mockOAuthAppDO);
//                    doReturn(serviceProvider).when(applicationManagementService)
//                            .getServiceProviderByClientId(anyString(), anyString(), anyString());
//
//                    try (MockedStatic<IdentityDataHolder> holderMockedStatic = Mockito
//                            .mockStatic(
//                                    IdentityDataHolder.class)) {
//                        IdentityDataHolder mockIdentityDataHolder = mock(IdentityDataHolder.class);
//                        holderMockedStatic.when(IdentityDataHolder::getInstance)
//                                .thenReturn(mockIdentityDataHolder);
//                        when(mockIdentityDataHolder.getAuthorizedAPIManagementService())
//                                .thenReturn(authorizedAPIManagementService);
//
//                        try (MockedStatic<AuthorizationDetailProcessorUtils> utilsMockedStatic = Mockito.mockStatic(
//                                AuthorizationDetailProcessorUtils.class)) {
//                            utilsMockedStatic.when(
//                                         () -> AuthorizationDetailProcessorUtils.getApplicationResourceIdByClientId(
//                                                    clientId,
//                                                    tenantDomain))
//                                    .thenReturn("app123");
//                            utilsMockedStatic.when(
//                                            () -> AuthorizationDetailProcessorUtils.getAuthorizedScopesByAppId(
//                                                    "app123",
//                                                    tenantDomain))
//                                    .thenReturn(mockScopes);
//
//                            // Execute and Assert
//                            Assert.assertThrows(AuthorizationDetailsProcessingException.class,
//                                    () -> processor.validate(context));
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    @Test
//    public void testValidateWithoutClientId() throws Exception {
//        // Mock AuthorizationDetail
//        Map<String, Object> dataClusters = new HashMap<>();
//        dataClusters.put("dataClusters", Arrays.asList("TRANSACTIONS", "INVESTMENTS"));
//        AuthorizationDetail authorizationDetail = mockAuthorizationDetail(dataClusters);
//
//        // Mock AuthorizationDetailsContext
//        AuthorizationDetailsContext context = mock(AuthorizationDetailsContext.class);
//        when(context.getAuthorizationDetail()).thenReturn(authorizationDetail);
//        when(context.getOAuthAppDO()).thenReturn(mockOAuthAppDO);
//        when(mockOAuthAppDO.getOauthConsumerKey()).thenReturn(null);
//
//        // Run validation
//        ValidationResult result = processor.validate(context);
//
//        // Assert
//        Assert.assertTrue(result.isInvalid());
//    }
//
//    @Test
//    public void testGetDataClustersWithValidInput() {
//        // Mock AuthorizationDetail
//        AuthorizationDetail authorizationDetail = mock(AuthorizationDetail.class);
//        Map<String, Object> details = new HashMap<>();
//        Map<String, Object> consentRequest = new HashMap<>();
//        ArrayList<Object> resources = new ArrayList<>();
//        Map<String, Object> dataClusters = new HashMap<>();
//        dataClusters.put("dataClusters", Arrays.asList("NOTIFICATIONS"));
//        resources.add(dataClusters);
//        consentRequest.put("durationType", "DURATION_TYPE");
//        consentRequest.put("durationPeriod", 1);
//        consentRequest.put("lookbackPeriod", 1);
//        consentRequest.put("resources", resources);
//        details.put("consentRequest", consentRequest);
//        when(authorizationDetail.getDetails()).thenReturn(details);
//        when(authorizationDetail.getType()).thenReturn("fdx_v1.0");
//
//        // Execute
//        List<List<String>> actualDataClusters = processor.getDataClusters(authorizationDetail);
//
//        // Assert
//        Assert.assertEquals(actualDataClusters.size(), 1);
//    }
//
//    @Test
//    public void testGetDataClustersWithEmptyInput() {
//        // Mock AuthorizationDetail
//        AuthorizationDetail authorizationDetail = mock(AuthorizationDetail.class);
//        when(authorizationDetail.getDetails()).thenReturn(Collections.emptyMap());
//
//        // Execute
//        List<List<String>> dataClusters = processor.getDataClusters(authorizationDetail);
//
//        // Assert
//        Assert.assertTrue(dataClusters.isEmpty());
//    }
}



