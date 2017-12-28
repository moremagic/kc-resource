package com.yo1000.keycloak.resource.server

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.keycloak.KeycloakPrincipal
import org.keycloak.adapters.RefreshableKeycloakSecurityContext
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class KcResourceServerControllerTests {
    @Autowired
    lateinit var context: WebApplicationContext
    lateinit var mockMvc: MockMvc

    @Before
    fun beforeTestEach() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply<DefaultMockMvcBuilder>(SecurityMockMvcConfigurers.springSecurity())
                .build()
    }

    /**
     * When the user has Admin and User roles,
     * then can access endpoints that require Admin role or User role.
     */
    @Test
    fun when_the_user_has_Admin_and_User_roles_then_can_access_endpoints_that_require_Admin_role_or_User_role() {
        val token = KeycloakAuthenticationToken(
                SimpleKeycloakAccount(
                        Mockito.mock(KeycloakPrincipal::class.java),
                        setOf("admin", "user"),
                        Mockito.mock(RefreshableKeycloakSecurityContext::class.java)),
                false)

        mockMvc.perform(MockMvcRequestBuilders
                .get("/kc/resource/server/admin")
                .with(SecurityMockMvcRequestPostProcessors
                        .authentication(token)))
                .andDo(MockMvcResultHandlers
                        .print())
                .andExpect(MockMvcResultMatchers
                        .status().isOk)

        mockMvc.perform(MockMvcRequestBuilders
                .get("/kc/resource/server/user")
                .with(SecurityMockMvcRequestPostProcessors
                        .authentication(token)))
                .andDo(MockMvcResultHandlers
                        .print())
                .andExpect(MockMvcResultMatchers
                        .status().isOk)
    }

    /**
     * When the user has only User role,
     * then can access endpoints that require User role,
     * but can't access endpoints that require Admin role.
     */
    @Test
    fun when_the_user_has_only_User_role_then_can_access_endpoints_that_require_User_role_but_cant_access_endpoints_that_require_Admin_role() {
        val token = KeycloakAuthenticationToken(
                SimpleKeycloakAccount(
                        Mockito.mock(KeycloakPrincipal::class.java),
                        setOf("user"),
                        Mockito.mock(RefreshableKeycloakSecurityContext::class.java)),
                false)

        mockMvc.perform(MockMvcRequestBuilders
                .get("/kc/resource/server/admin")
                .with(SecurityMockMvcRequestPostProcessors
                        .authentication(token)))
                .andDo(MockMvcResultHandlers
                        .print())
                .andExpect(MockMvcResultMatchers
                        .status().isForbidden)

        mockMvc.perform(MockMvcRequestBuilders
                .get("/kc/resource/server/user")
                .with(SecurityMockMvcRequestPostProcessors
                        .authentication(token)))
                .andDo(MockMvcResultHandlers
                        .print())
                .andExpect(MockMvcResultMatchers
                        .status().isOk)
    }
}

