package com.wutsi.analytics.tracking.job.stats.view

import com.wutsi.analytics.tracking.job.AbstractAggregatorJobTest
import com.wutsi.analytics.tracking.job.step.view.ViewDailyAggregatorJob
import com.wutsi.analytics.tracking.job.step.view.ViewMonthlyAggregatorJob
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.jdbc.Sql
import java.io.File
import kotlin.test.assertTrue

@Sql(value = ["/sql/clean.sql"])
internal class ViewMonthlyAggregatorJobTest : AbstractAggregatorJobTest() {
    @Autowired
    private lateinit var daily: ViewDailyAggregatorJob

    @Autowired
    private lateinit var monthly: ViewMonthlyAggregatorJob

    @Test
    fun run() {
        daily.run()
        monthly.run()

        assertTrue(File("$storageDirectory/aggregates/monthly/2020/04/view.csv").exists())
    }
}
