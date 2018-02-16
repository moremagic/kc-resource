package com.yo1000.keycloak.resource.server

import org.keycloak.adapters.spi.KeycloakAccount
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken
import org.springframework.security.core.GrantedAuthority

/**
 *
 * @author yo1000
 */
class KeycloakAuthenticationUserToken<out U>(
        account: KeycloakAccount?,
        interactive: Boolean,
        authorities: MutableCollection<out GrantedAuthority>? = null,
        val user: U)
    : KeycloakAuthenticationToken(account, interactive, authorities) {
    override fun toString(): String {
        return "${super.toString()}; user: $user"
    }
}
