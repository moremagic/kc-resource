package com.yo1000.keycloak.resource.client

import org.keycloak.adapters.springboot.KeycloakSpringBootProperties
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@Controller
@RequestMapping("/kc/resource/client")
class KcResourceClientController(
        val template: KeycloakRestTemplate,
        val props: KeycloakSpringBootProperties
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
            hoge
            This page is Resource Client side.
            Resource Server Response: [$resp]
            """.trimIndent()
    }

    @GetMapping("/users/count")
    @ResponseBody
    fun getCount(): String {
        val resp = template.getForObject("${props.authServerUrl}/admin/realms/kc-resource/users/count", String::class.java)

        return """
            usercount endpoint
            ${resp}
            """.trimIndent()
    }

    @GetMapping("/users")
    @ResponseBody
    fun getUsers(): String {
        val resp = template.getForObject("${props.authServerUrl}/admin/realms/kc-resource/users", String::class.java)

        return """
            usercount endpoint
            ${resp}
            """.trimIndent()
    }

    @GetMapping("/users/add")
    @ResponseBody
    fun getAdd(@RequestParam("name") name: String): String {
        val user = UserRepresentation()
        user.username = name
        user.isEnabled = true
        user.email = "${name}@keycloal.local"
        user.isEmailVerified = true


        var ccc = CredentialRepresentation()
        ccc.isTemporary = true
        user.credentials = listOf(ccc)


        val resp = template.postForObject("${props.authServerUrl}/admin/realms/kc-resource/users", user, String::class.java)

        return """
            adduser endpoint
            ${user.username} create.
            """.trimIndent()
    }

    @GetMapping("/users/update/{id}")
    @ResponseBody
    fun getUpdate(@RequestParam("name") name: String, @PathVariable("id") id: String): String {
        val user = UserRepresentation()
        user.id = id
        user.username = name
        user.firstName = name

        val resp = template.put("${props.authServerUrl}/admin/realms/kc-resource/users/${id}", user, String::class.java)

        return """
            update user endpoint
            ${user.username} update.
            """.trimIndent()
    }

    @GetMapping("/users/delete/{id}")
    @ResponseBody
    fun getDelete(@PathVariable("id") id: String): String {
        val resp = template.delete("${props.authServerUrl}/admin/realms/kc-resource/users/{id}", mapOf("id" to id))

        return """
            deleteuser endpoint
            ${id} delete.
            """.trimIndent()
    }

    @GetMapping("/users/verify/{id}")
    @ResponseBody
    fun getVerify(@PathVariable("id") id: String): String {
        val resp = template.put("${props.authServerUrl}/admin/realms/kc-resource/users/${id}/send-verify-email", String::class.java)

        return """
            verify endpoint
            ${id}
            """.trimIndent()
    }

    @GetMapping("/test")
    @ResponseBody
    fun getTest(token: KeycloakAuthenticationToken): String {
        return """
                test endpoint
                ${token.name}
                ${token.account.roles}
                """.trimIndent()
    }

    @GetMapping("/logout")
    @ResponseBody
    fun getLogout(httpServletRequest: HttpServletRequest): String {
        httpServletRequest.logout()
        return """
            logout endpoint
            """.trimIndent()
    }
}

