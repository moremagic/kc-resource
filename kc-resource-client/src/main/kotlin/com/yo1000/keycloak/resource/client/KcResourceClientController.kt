package com.yo1000.keycloak.resource.client

import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/kc/resource/client")
class KcResourceClientController(
        val template: KeycloakRestTemplate
) {
    companion object {
        const val ENDPOINT = "http://localhost:18080/kc/resource/server/{role}"
    }

    @GetMapping("/admin")
    @ResponseBody
    fun getAdmin(): String {
        val resp = template.getForObject(ENDPOINT, String::class.java, mapOf("role" to "admin"))
        return """
            This page is Resource Client side.
            Resource Server Response: [$resp]
            """.trimIndent()
    }

    @GetMapping("/user")
    @ResponseBody
    fun getUser(): String {
        val resp = template.getForObject(ENDPOINT, String::class.java, mapOf("role" to "user"))
        return """
            This page is Resource Client side.
            Resource Server Response: [$resp]
            """.trimIndent()
    }
}

