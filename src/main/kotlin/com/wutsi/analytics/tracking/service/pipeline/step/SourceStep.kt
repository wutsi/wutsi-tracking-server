package com.wutsi.analytics.tracking.service.pipeline.step

import com.wutsi.analytics.tracking.dto.Track
import com.wutsi.analytics.tracking.entity.Source
import com.wutsi.analytics.tracking.service.pipeline.Step
import java.util.Locale

class SourceStep : Step {
    companion object {
        private val SEO_DOMAINS = arrayListOf(
            "google",
            "bing",
            "yahoo"
        )
        private val EMAIL_DOMAINS = arrayListOf(
            "mail.google.com",
            "mail.yahoo.com",
            "outlook.live.com",
            "pixel.mail.wutsi.com"
        )
    }

    override fun process(track: Track): Track {
        val domain = extractDomainName(track.referer?.lowercase())
        val source = toSourceType(domain)
        return source?.let { track.copy(source = it.name) } ?: track
    }

    private fun toSourceType(domain: String): Source? {
        if (isDirect(domain)) {
            return Source.DIRECT
        } else if (isFacebook(domain)) {
            return Source.FACEBOOK
        } else if (isWhatsapp(domain)) {
            return Source.WHATSAPP
        } else if (isEmail(domain)) {
            return Source.EMAIL
        } else if (isInstagram(domain)) {
            return Source.INSTAGRAM
        } else if (isSEO(domain)) {
            return Source.SEO
        }
        return null
    }

    private fun isDirect(domain: String) = domain.isEmpty()

    private fun isSEO(domain: String) = SEO_DOMAINS.find { domain.startsWith(it) } != null

    private fun isFacebook(domain: String) = domain.contains("facebook.com")

    private fun isWhatsapp(domain: String) = domain.contains("whatsapp.com")

    private fun isInstagram(domain: String) = domain.contains("instagram.com")

    private fun isEmail(domain: String) = EMAIL_DOMAINS.contains(domain)

    private fun extractDomainName(url: String?): String {
        if (url == null) {
            return ""
        }

        var domainName = url

        var index = domainName.indexOf("://")

        if (index != -1) {
            // keep everything after the "://"
            domainName = domainName.substring(index + 3)
        }

        index = domainName.indexOf('/')

        if (index != -1) {
            // keep everything before the '/'
            domainName = domainName.substring(0, index)
        }

        // check for and remove a preceding 'www'
        // followed by any sequence of characters (non-greedy)
        // followed by a '.'
        // from the beginning of the string
        domainName = domainName.replaceFirst("^www.*?\\.".toRegex(), "")

        return domainName.lowercase(Locale.getDefault())
    }
}
