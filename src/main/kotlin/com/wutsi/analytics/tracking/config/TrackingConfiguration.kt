package com.wutsi.analytics.tracking.config

import com.wutsi.analytics.tracking.service.TrackPersister
import com.wutsi.analytics.tracking.service.pipeline.Pipeline
import com.wutsi.analytics.tracking.service.pipeline.step.StepPersist
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TrackingConfiguration(
    private val persister: TrackPersister
) {
    @Bean
    fun pipeline() = Pipeline(
        arrayListOf(
            stepPersist() /* IMPORTANT: This MUST always be the last step */
        )
    )

    @Bean
    fun stepPersist() = StepPersist(persister, 1000)
}
