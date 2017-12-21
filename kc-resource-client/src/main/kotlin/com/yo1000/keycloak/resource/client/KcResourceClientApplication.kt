package com.yo1000.keycloak.resource.client

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class KcResourceClientApplication

fun main(args: Array<String>) {
    SpringApplication.run(KcResourceClientApplication::class.java, *args)
}
