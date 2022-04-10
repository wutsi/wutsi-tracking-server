package com.wutsi.analytics.tracking.job

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.io.File
import kotlin.test.assertTrue

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class VisitDailyAggregatorJobTest : AbstractDailyAggregatorJobTest() {
    @Autowired
    private lateinit var job: VisitDailyAggregatorJob

    @Test
    fun run() {
        job.run()

        assertTrue(File("$storageDirectory/aggregates/2020/04/14/visit.csv").exists())
    }
}
