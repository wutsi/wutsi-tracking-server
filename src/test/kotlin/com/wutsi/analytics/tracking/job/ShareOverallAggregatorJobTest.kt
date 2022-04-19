package com.wutsi.analytics.tracking.job

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.jdbc.Sql
import java.io.File
import kotlin.test.assertTrue

@Sql(value = ["/sql/clean.sql"])
internal class ShareOverallAggregatorJobTest : AbstractAggregatorJobTest() {
    @Autowired
    private lateinit var daily: ShareDailyAggregatorJob

    @Autowired
    private lateinit var monthly: ShareMonthlyAggregatorJob

    @Autowired
    private lateinit var yearly: ShareYearlyAggregatorJob

    @Autowired
    private lateinit var overall: ShareOverallAggregatorJob

    @Test
    fun run() {
        daily.run()
        monthly.run()
        yearly.run()
        overall.run()

        assertTrue(File("$storageDirectory/aggregates/overall/share.csv").exists())
    }
}
