package com.wutsi.analytics.tracking.job.stats.share

import com.wutsi.analytics.tracking.job.AbstractAggregatorJobTest
import com.wutsi.analytics.tracking.job.step.share.ShareDailyAggregatorJob
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.io.File
import kotlin.test.assertTrue

internal class ShareDailyAggregatorJobTest : AbstractAggregatorJobTest() {
    @Autowired
    private lateinit var job: ShareDailyAggregatorJob

    @Test
    fun run() {
        job.run()

        assertTrue(File("$storageDirectory/aggregates/daily/2020/04/14/share.csv").exists())
    }
}
