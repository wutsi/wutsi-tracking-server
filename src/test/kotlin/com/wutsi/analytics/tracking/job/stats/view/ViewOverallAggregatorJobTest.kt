package com.wutsi.analytics.tracking.job.stats.view

import com.wutsi.analytics.tracking.job.AbstractAggregatorJobTest
import com.wutsi.analytics.tracking.job.step.view.ViewDailyAggregatorJob
import com.wutsi.analytics.tracking.job.step.view.ViewMonthlyAggregatorJob
import com.wutsi.analytics.tracking.job.step.view.ViewOverallAggregatorJob
import com.wutsi.analytics.tracking.job.step.view.ViewYearlyAggregatorJob
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.jdbc.Sql
import java.io.File
import kotlin.test.assertTrue

@Sql(value = ["/sql/clean.sql"])
internal class ViewOverallAggregatorJobTest : AbstractAggregatorJobTest() {
    @Autowired
    private lateinit var daily: ViewDailyAggregatorJob

    @Autowired
    private lateinit var monthly: ViewMonthlyAggregatorJob

    @Autowired
    private lateinit var yearly: ViewYearlyAggregatorJob

    @Autowired
    private lateinit var overall: ViewOverallAggregatorJob

    @Test
    fun run() {
        daily.run()
        monthly.run()
        yearly.run()
        overall.run()

        assertTrue(File("$storageDirectory/aggregates/overall/view.csv").exists())
    }
}
