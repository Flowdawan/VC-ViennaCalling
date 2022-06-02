package com.example.viennacalling.models.xml

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)
data class RssFeed @JvmOverloads constructor(
    @param:Element(name = "channel", required = false)
    @field:Element(
        required = false,
        name = "channel")
    var channel: Channel? = null,
)