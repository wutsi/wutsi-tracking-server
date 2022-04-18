package com.wutsi.analytics.tracking.config

import com.wutsi.analytics.tracking.service.TrackPersister
import com.wutsi.analytics.tracking.service.pipeline.Pipeline
import com.wutsi.analytics.tracking.service.pipeline.step.BotStep
import com.wutsi.analytics.tracking.service.pipeline.step.DeviceTypeStep
import com.wutsi.analytics.tracking.service.pipeline.step.PersisterStep
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TrackingConfiguration(
    private val persister: TrackPersister,
) {
    @Bean
    fun pipeline() = Pipeline(
        arrayListOf(
            BotStep(),
            DeviceTypeStep(),

            /* IMPORTANT: This MUST always be the last step */
            PersisterStep(persister, 1000)
        )
    )
}
