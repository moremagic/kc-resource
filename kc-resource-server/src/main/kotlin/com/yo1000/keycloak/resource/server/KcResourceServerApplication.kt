package com.yo1000.keycloak.resource.server

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class KcResourceServerApplication

fun main(args: Array<String>) {
    SpringApplication.run(KcResourceServerApplication::class.java, *args)
}
