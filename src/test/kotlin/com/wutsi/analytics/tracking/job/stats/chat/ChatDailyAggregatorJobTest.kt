package com.wutsi.analytics.tracking.job.stats.chat

import com.wutsi.analytics.tracking.job.AbstractAggregatorJobTest
import com.wutsi.analytics.tracking.job.step.chat.ChatDailyAggregatorJob
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.io.File
import kotlin.test.assertTrue

internal class ChatDailyAggregatorJobTest : AbstractAggregatorJobTest() {
    @Autowired
    private lateinit var job: ChatDailyAggregatorJob

    @Test
    fun run() {
        job.run()

        assertTrue(File("$storageDirectory/aggregates/daily/2020/04/14/chat.csv").exists())
    }
}
