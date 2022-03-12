package com.wutsi.analytics.tracking.service

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.anyOrNull
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.wutsi.analytics.tracking.dto.Track
import com.wutsi.platform.core.storage.StorageService
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.io.InputStream
import kotlin.test.assertTrue

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class TrackPersisterTest {
    @MockBean
    lateinit var storage: StorageService

    @Test
    fun `store track`() {
        val track1 = createTrack("111")
        val track2 = createTrack("222")

        TrackPersister(TrackWriter(), storage).persist(listOf(track1, track2))

        val path = argumentCaptor<String>()
        val input = argumentCaptor<InputStream>()
        verify(storage).store(path.capture(), input.capture(), eq("text/csv"), eq(Int.MAX_VALUE), anyOrNull())

        assertTrue(path.firstValue.endsWith(".csv"))
    }

    @Test
    fun `donot store empty track`() {
        val storage: StorageService = mock()
        val writer: TrackWriter = mock()

        TrackPersister(writer, storage).persist(emptyList())

        verify(storage, never()).store(any(), any(), any(), any(), anyOrNull())
    }

    private fun createTrack(correlationId: String) = Track(
        correlationId = correlationId,
    )
}
