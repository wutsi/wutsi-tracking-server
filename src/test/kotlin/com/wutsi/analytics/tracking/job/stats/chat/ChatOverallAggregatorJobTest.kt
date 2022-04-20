package com.wutsi.analytics.tracking.job.stats.chat

import com.wutsi.analytics.tracking.job.AbstractAggregatorJobTest
import com.wutsi.analytics.tracking.job.step.chat.ChatDailyAggregatorJob
import com.wutsi.analytics.tracking.job.step.chat.ChatMonthlyAggregatorJob
import com.wutsi.analytics.tracking.job.step.chat.ChatOverallAggregatorJob
import com.wutsi.analytics.tracking.job.step.chat.ChatYearlyAggregatorJob
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.jdbc.Sql
import java.io.File
import kotlin.test.assertTrue

@Sql(value = ["/sql/clean.sql"])
internal class ChatOverallAggregatorJobTest : AbstractAggregatorJobTest() {
    @Autowired
    private lateinit var daily: ChatDailyAggregatorJob

    @Autowired
    private lateinit var monthly: ChatMonthlyAggregatorJob

    @Autowired
    private lateinit var yearly: ChatYearlyAggregatorJob

    @Autowired
    private lateinit var overall: ChatOverallAggregatorJob

    @Test
    fun run() {
        daily.run()
        monthly.run()
        yearly.run()
        overall.run()

        assertTrue(File("$storageDirectory/aggregates/overall/chat.csv").exists())
    }
}
