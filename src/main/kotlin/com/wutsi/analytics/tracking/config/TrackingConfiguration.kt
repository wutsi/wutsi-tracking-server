package com.wutsi.analytics.tracking.config

import com.wutsi.analytics.tracking.service.TrackPersister
import com.wutsi.analytics.tracking.service.pipeline.Pipeline
import com.wutsi.analytics.tracking.service.pipeline.step.BotStep
import com.wutsi.analytics.tracking.service.pipeline.step.DeviceTypeStep
import com.wutsi.analytics.tracking.service.pipeline.step.PersisterStep
import com.wutsi.analytics.tracking.service.pipeline.step.SourceStep
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TrackingConfiguration(
    private val persister: TrackPersister,
) {
    @Bean
    fun pipeline() = Pipeline(
        arrayListOf(
            botStep(),
            deviceTypeStep(),
            sourceStep(),

            /* IMPORTANT: This MUST always be the last step */
            persisterStep()
        )
    )

    @Bean
    fun botStep() = BotStep()

    @Bean
    fun deviceTypeStep() = DeviceTypeStep()

    @Bean
    fun persisterStep() = PersisterStep(persister, 1000)

    @Bean
    fun sourceStep() = SourceStep()
}
