package com.wutsi.analytics.tracking.service.pipeline.step

import com.wutsi.analytics.tracking.dto.Track
import com.wutsi.analytics.tracking.service.TrackPersister
import com.wutsi.analytics.tracking.service.pipeline.Step
import java.util.Collections
import javax.annotation.PreDestroy

open class PersisterStep(
    private val persister: TrackPersister,
    private val bufferSize: Int = 1000,
) : Step {
    private val buffer = Collections.synchronizedList(mutableListOf<Track>())

    @PreDestroy
    fun destroy() {
        flush()
    }

    fun flush() {
        val copy = mutableListOf<Track>()
        copy.addAll(buffer)

        persister.persist(copy)
        buffer.removeAll(copy)
    }

    override fun process(track: Track): Track {
        buffer.add(track)
        if (shouldFlush()) {
            flush()
        }
        return track
    }

    private fun shouldFlush(): Boolean {
        return buffer.size >= bufferSize
    }
}
