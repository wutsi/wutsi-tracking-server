package com.wutsi.analytics.tracking.job

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import com.wutsi.analytics.tracking.endpoint.AbstractEndpointTest
import com.wutsi.platform.core.storage.StorageService
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.mock.mockito.MockBean
import java.net.URL
import java.time.Clock
import java.time.Instant
import java.time.ZoneId

abstract class AbstractAggregatorJobTest : AbstractEndpointTest() {
    @Autowired
    protected lateinit var storage: StorageService

    @MockBean
    private lateinit var clock: Clock

    @Value("\${wutsi.platform.storage.local.directory:\${user.home}/wutsi/storage}")
    protected lateinit var storageDirectory: String

    private lateinit var urls: List<URL>

    @BeforeEach
    override fun setUp() {
        super.setUp()

        val now = Instant.ofEpochMilli(1586952657000)
        doReturn(now).whenever(clock).instant()
        doReturn(ZoneId.of("UTC")).whenever(clock).zone

        urls = listOf(
            store("/tracks/2020/04/14/2020-04-14-000.csv"),
            store("/tracks/2020/04/14/2020-04-14-001.csv"),
            store("/tracks/2020/04/15/2020-04-15-000.csv")
        )
    }

    protected fun store(path: String): URL {
        val content = javaClass.getResourceAsStream(path)
        return storage.store(path, content)
    }
}
