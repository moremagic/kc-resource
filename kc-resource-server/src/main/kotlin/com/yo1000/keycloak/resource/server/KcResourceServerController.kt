package com.yo1000.keycloak.resource.server

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/kc/resource/server")
class KcResourceServerController {
    @GetMapping("/admin")
    fun getAdminResource(userToken: KeycloakAuthenticationUserToken<String>): String {
        return """
            ADMIN Resource!!
            ${userToken.toString().replace(";", "\n")}
            """.trimIndent()
    }

    @GetMapping("/user")
    fun getUserResource(userToken: KeycloakAuthenticationUserToken<String>): String {
        return """
            USER Resource.
            ${userToken.toString().replace(";", "\n")}
            """.trimIndent()
    }
}

