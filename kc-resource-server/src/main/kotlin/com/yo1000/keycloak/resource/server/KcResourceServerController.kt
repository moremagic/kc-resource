package com.yo1000.keycloak.resource.server

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/kc/resource/server")
class KcResourceServerController {
    @GetMapping("/admin")
    fun getAdminResource(): String {
        return "ADMIN Resource!!"
    }

    @GetMapping("/user")
    fun getUserResource(): String {
        return "USER Resource."
    }
}

