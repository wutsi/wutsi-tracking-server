package com.wutsi.analytics.tracking.job

import com.nhaarman.mockitokotlin2.verify
import com.wutsi.analytics.tracking.endpoint.AbstractEndpointTest
import com.wutsi.analytics.tracking.service.pipeline.step.PersisterStep
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean

internal class PersisterStepJobTest : AbstractEndpointTest() {
    @MockBean
    private lateinit var step: PersisterStep

    @Autowired
    private lateinit var job: PersisterStepJob

    @Test
    fun run() {
        job.run()

        verify(step).flush()
    }
}
