package com.wutsi.analytics.tracking.job

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.io.File
import kotlin.test.assertTrue

internal class VisitDailyAggregatorJobTest : AbstractAggregatorJobTest() {
    @Autowired
    private lateinit var job: VisitDailyAggregatorJob

    @Test
    fun run() {
        job.run()

        assertTrue(File("$storageDirectory/aggregates/daily/2020/04/14/view.csv").exists())
    }
}
