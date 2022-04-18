package com.wutsi.analytics.tracking.service.pipeline.step

import com.wutsi.analytics.tracking.dto.Track
import com.wutsi.analytics.tracking.entity.DeviceType
import com.wutsi.analytics.tracking.service.pipeline.Step
import ua_parser.Parser

open class DeviceTypeStep : Step {
    private val uaParser = Parser()

    override fun process(track: Track): Track {
        if (track.ua == null)
            return track
        else if (track.ua.contains("(dart:io)", true))
            return track.copy(deviceType = DeviceType.APP.name)

        val client = uaParser.parse(track.ua)
        val deviceType = if (client.device?.family.equals("spider", true)) // Bot
            null
        else if (client.userAgent.family?.contains("mobile", true) == true)
            DeviceType.MOBILE
        else
            DeviceType.DESKTOP

        return track.copy(deviceType = deviceType?.name)
    }
}
