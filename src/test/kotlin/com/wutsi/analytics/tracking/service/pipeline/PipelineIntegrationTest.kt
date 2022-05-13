package com.wutsi.analytics.tracking.service.pipeline

import com.wutsi.analytics.tracking.service.pipeline.step.BotStep
import com.wutsi.analytics.tracking.service.pipeline.step.DeviceTypeStep
import com.wutsi.analytics.tracking.service.pipeline.step.PersisterStep
import com.wutsi.analytics.tracking.service.pipeline.step.SourceStep
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class PipelineIntegrationTest {

    @Autowired
    private lateinit var pipeline: Pipeline

    @Test
    fun validate() {
        assertEquals(4, pipeline.steps.size)

        assertTrue(pipeline.steps[0] is BotStep)
        assertTrue(pipeline.steps[1] is DeviceTypeStep)
        assertTrue(pipeline.steps[2] is SourceStep)
        assertTrue(pipeline.steps[3] is PersisterStep)
    }
}
