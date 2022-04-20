package com.wutsi.analytics.tracking.job.stats.chat

import com.wutsi.analytics.tracking.job.AbstractAggregatorJobTest
import com.wutsi.analytics.tracking.job.step.chat.ChatDailyAggregatorJob
import com.wutsi.analytics.tracking.job.step.chat.ChatMonthlyAggregatorJob
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.jdbc.Sql
import java.io.File
import kotlin.test.assertTrue

@Sql(value = ["/sql/clean.sql"])
internal class ChatMonthlyAggregatorJobTest : AbstractAggregatorJobTest() {
    @Autowired
    private lateinit var daily: ChatDailyAggregatorJob

    @Autowired
    private lateinit var monthly: ChatMonthlyAggregatorJob

    @Test
    fun run() {
        daily.run()
        monthly.run()

        assertTrue(File("$storageDirectory/aggregates/monthly/2020/04/chat.csv").exists())
    }
}
