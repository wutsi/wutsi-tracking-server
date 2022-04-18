package com.wutsi.analytics.tracking.job

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.io.File
import kotlin.test.assertTrue

internal class VisitMonthlyAggregatorJobTest : AbstractAggregatorJobTest() {
    @Autowired
    private lateinit var daily: VisitDailyAggregatorJob

    @Autowired
    private lateinit var monthly: VisitMonthlyAggregatorJob

    @Test
    fun run() {
        daily.run()
        monthly.run()

        assertTrue(File("$storageDirectory/aggregates/monthly/2020/04/view.csv").exists())
    }
}
