package com.wutsi.analytics.tracking

import com.wutsi.platform.core.WutsiApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling

@WutsiApplication
@SpringBootApplication
@EnableAsync
@EnableScheduling
class Application

fun main(vararg args: String) {
    org.springframework.boot.runApplication<Application>(*args)
}
