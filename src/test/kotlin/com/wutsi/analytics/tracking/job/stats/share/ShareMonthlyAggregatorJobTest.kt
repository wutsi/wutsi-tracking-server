package com.wutsi.analytics.tracking.job.stats.share

import com.wutsi.analytics.tracking.job.AbstractAggregatorJobTest
import com.wutsi.analytics.tracking.job.step.share.ShareDailyAggregatorJob
import com.wutsi.analytics.tracking.job.step.share.ShareMonthlyAggregatorJob
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.jdbc.Sql
import java.io.File
import kotlin.test.assertTrue

@Sql(value = ["/sql/clean.sql"])
internal class ShareMonthlyAggregatorJobTest : AbstractAggregatorJobTest() {
    @Autowired
    private lateinit var daily: ShareDailyAggregatorJob

    @Autowired
    private lateinit var monthly: ShareMonthlyAggregatorJob

    @Test
    fun run() {
        daily.run()
        monthly.run()

        assertTrue(File("$storageDirectory/aggregates/monthly/2020/04/share.csv").exists())
    }
}
