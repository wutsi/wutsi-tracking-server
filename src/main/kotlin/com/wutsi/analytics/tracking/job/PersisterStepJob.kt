package com.wutsi.analytics.tracking.job

import com.wutsi.analytics.tracking.service.pipeline.step.PersisterStep
import com.wutsi.platform.core.logging.DefaultKVLogger
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class PersisterStepJob(private val step: PersisterStep) {
    @Scheduled(cron = "\${wutsi.application.jobs.persister.cron}")
    fun run() {
        val logger = DefaultKVLogger()
        try {
            val count = step.flush()

            logger.add("job", javaClass.simpleName)
            logger.add("count", count)
        } catch (ex: Exception) {
            logger.setException(ex)
        } finally {
            logger.log()
        }
    }
}
