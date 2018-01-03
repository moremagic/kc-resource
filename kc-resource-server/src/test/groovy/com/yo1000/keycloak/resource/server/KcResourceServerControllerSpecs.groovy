package com.yo1000.keycloak.resource.server

import org.junit.Before
import org.keycloak.KeycloakPrincipal
import org.keycloak.adapters.RefreshableKeycloakSecurityContext
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class KcResourceServerControllerSpecs extends Specification {
    @Autowired
    WebApplicationContext context
    @Shared
    MockMvc mockMvc

    @Before
    def beforeTestEach() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build()
    }

    /**
     * When the user has Admin and User roles,
     * then can access endpoints that require Admin role or User role.
     */
    def when_the_user_has_Admin_and_User_roles_then_can_access_endpoints_that_require_Admin_role_or_User_role() {
        given:
        def token = new KeycloakAuthenticationToken(
                new SimpleKeycloakAccount(
                        Mockito.mock(KeycloakPrincipal.class),
                        ["admin", "user"].toSet(),
                        Mockito.mock(RefreshableKeycloakSecurityContext.class)),
                false)

        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/kc/resource/server/admin")
                .with(SecurityMockMvcRequestPostProcessors.authentication(token)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())

        mockMvc.perform(MockMvcRequestBuilders.get("/kc/resource/server/user")
                .with(SecurityMockMvcRequestPostProcessors.authentication(token)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
    }

    /**
     * When the user has only User role,
     * then can access endpoints that require User role,
     * but can't access endpoints that require Admin role.
     */
    def when_the_user_has_only_User_role_then_can_access_endpoints_that_require_User_role_but_cant_access_endpoints_that_require_Admin_role() {
        given:
        def token = new KeycloakAuthenticationToken(
                new SimpleKeycloakAccount(
                        Mockito.mock(KeycloakPrincipal.class),
                        ["user"].toSet(),
                        Mockito.mock(RefreshableKeycloakSecurityContext.class)),
                false)

        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/kc/resource/server/admin")
                .with(SecurityMockMvcRequestPostProcessors.authentication(token)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden())

        mockMvc.perform(MockMvcRequestBuilders.get("/kc/resource/server/user")
                .with(SecurityMockMvcRequestPostProcessors.authentication(token)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
    }
}