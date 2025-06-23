package org.example.momsitter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MomsitterApplication

fun main(args: Array<String>) {
	runApplication<MomsitterApplication>(*args)
}
