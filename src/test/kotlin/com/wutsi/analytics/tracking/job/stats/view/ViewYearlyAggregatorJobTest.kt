package com.wutsi.analytics.tracking.job.stats.view

import com.wutsi.analytics.tracking.job.AbstractAggregatorJobTest
import com.wutsi.analytics.tracking.job.step.view.ViewDailyAggregatorJob
import com.wutsi.analytics.tracking.job.step.view.ViewMonthlyAggregatorJob
import com.wutsi.analytics.tracking.job.step.view.ViewYearlyAggregatorJob
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.jdbc.Sql
import java.io.File
import kotlin.test.assertTrue

@Sql(value = ["/sql/clean.sql"])
internal class ViewYearlyAggregatorJobTest : AbstractAggregatorJobTest() {
    @Autowired
    private lateinit var daily: ViewDailyAggregatorJob

    @Autowired
    private lateinit var monthly: ViewMonthlyAggregatorJob

    @Autowired
    private lateinit var yearly: ViewYearlyAggregatorJob

    @Test
    fun run() {
        daily.run()
        monthly.run()
        yearly.run()

        assertTrue(File("$storageDirectory/aggregates/yearly/2020/view.csv").exists())
    }
}
