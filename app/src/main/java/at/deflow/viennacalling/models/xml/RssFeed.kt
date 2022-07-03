package at.deflow.viennacalling.models.xml

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

// We go through the xml document hierarchically from top to bottom and save the elements we need.
@Root(name = "rss", strict = false)
data class RssFeed @JvmOverloads constructor(
    @param:Element(name = "channel", required = false)
    @field:Element(
        required = false,
        name = "channel"
    )
    var channel: Channel? = null,
)