package com.yo1000.keycloak.resource.server

/**
 *
 * @author yo1000
 */
interface AuthenticationUserRepository<out U, in ID> {
    fun findById(id: ID): U
}

data class IdOnly(
        val id: String
)

class IdOnlyAuthenticationUserRepository: AuthenticationUserRepository<IdOnly, String> {
    override fun findById(id: String): IdOnly {
        return IdOnly(id)
    }
}
