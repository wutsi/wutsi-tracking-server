package com.wutsi.analytics.tracking.job.stats.view

import com.wutsi.analytics.tracking.job.AbstractAggregatorJobTest
import com.wutsi.analytics.tracking.job.step.view.ViewDailyAggregatorJob
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.io.File
import kotlin.test.assertTrue

internal class ViewDailyAggregatorJobTest : AbstractAggregatorJobTest() {
    @Autowired
    private lateinit var job: ViewDailyAggregatorJob

    @Test
    fun run() {
        job.run()

        assertTrue(File("$storageDirectory/aggregates/daily/2020/04/14/view.csv").exists())
    }
}
