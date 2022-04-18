package com.wutsi.analytics.tracking.service.pipeline.step

import com.wutsi.analytics.tracking.dto.Track
import com.wutsi.analytics.tracking.service.pipeline.Step
import ua_parser.Parser

open class BotStep : Step {
    private val uaParser = Parser()

    override fun process(track: Track): Track {
        if (track.ua == null)
            return track

        val client = uaParser.parse(track.ua)
        val bot = client.device?.family.equals("spider", true)
        return track.copy(bot = bot)
    }
}
