package com.yo1000.keycloak.resource.server

import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken
import org.keycloak.authorization.client.AuthorizationDeniedException
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper

/**
 *
 * @author yo1000
 */
class KeycloakAuthenticationUserProvider<U>(
        @JvmField var grantedAuthoritiesMapper: GrantedAuthoritiesMapper? = null,
        private val authenticationUserRepository: AuthenticationUserRepository<U, String>
) : KeycloakAuthenticationProvider() {
    override fun setGrantedAuthoritiesMapper(grantedAuthoritiesMapper: GrantedAuthoritiesMapper) {
        this.grantedAuthoritiesMapper = grantedAuthoritiesMapper
    }

    override fun authenticate(authentication: Authentication): Authentication {
        val authToken = super.authenticate(authentication) as KeycloakAuthenticationToken
        val mapper = grantedAuthoritiesMapper

        return KeycloakAuthenticationUserToken<U>(
                authToken.account,
                authToken.isInteractive,
                if (mapper != null) {
                    mapper.mapAuthorities(authToken.authorities)
                } else {
                    authToken.authorities
                },
                authenticationUserRepository.findById(authToken.name))
    }
}
