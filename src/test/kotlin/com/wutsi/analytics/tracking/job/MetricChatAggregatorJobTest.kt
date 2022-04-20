package com.wutsi.analytics.tracking.job

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.io.File

internal class MetricChatAggregatorJobTest : AbstractMetricAggregatorJobTest() {
    @Autowired
    private lateinit var job: MetricChatAggregatorJob

    @Test
    fun run() {
        job.run()

        assertTrue(File("$storageDirectory/aggregates/daily/2020/04/14/chat.csv").exists())
        assertTrue(File("$storageDirectory/aggregates/monthly/2020/04/chat.csv").exists())
        assertTrue(File("$storageDirectory/aggregates/yearly/2020/chat.csv").exists())
        assertTrue(File("$storageDirectory/aggregates/overall/chat.csv").exists())
    }
}
