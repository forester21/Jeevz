package ru.forester.jeevz

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients
@SpringBootApplication
class JeevzApplication

fun main(args: Array<String>) {
	runApplication<JeevzApplication>(*args)
}
