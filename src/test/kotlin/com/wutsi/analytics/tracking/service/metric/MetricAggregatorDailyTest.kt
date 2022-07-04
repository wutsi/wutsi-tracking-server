package com.wutsi.analytics.tracking.service.metric

import com.wutsi.analytics.tracking.entity.MetricType
import com.wutsi.analytics.tracking.service.AbstractAggregatorTestBase
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
import java.io.File
import java.time.LocalDate
import kotlin.test.assertTrue

internal class MetricAggregatorDailyTest : AbstractAggregatorTestBase() {
    override fun getAggregator() = MetricAggregatorDaily(MetricType.VIEW, LocalDate.of(2020, 4, 14))

    @Test
    fun aggregate() {
        // GIVEN
        storage.store(
            "/tracks/2020/04/14/2020-04-14-000.csv",
            javaClass.getResourceAsStream("/aggregator/daily/view/2020-04-14-000.csv")
        )
        storage.store(
            "/tracks/2020/04/14/2020-04-14-001.csv",
            javaClass.getResourceAsStream("/aggregator/daily/view/2020-04-14-001.csv")
        )
        storage.store(
            "/tracks/2020/04/14/2020-04-15-000.csv",
            javaClass.getResourceAsStream("/aggregator/daily/view/2020-04-15-000.csv")
        )

        // WHEN
        getAggregator().aggregate(storage)

        // THEN
        val file = File("$storageDirectory/aggregates/daily/2020/04/14/view.csv")
        assertTrue(file.exists())

        assertFileMatches(
            javaClass.getResourceAsStream("/aggregator/daily/view/output.csv"),
            ByteArrayInputStream(file.readBytes())
        )
    }
}
