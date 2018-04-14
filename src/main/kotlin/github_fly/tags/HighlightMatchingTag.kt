package github_fly.tags

import github_fly.RiotTag
import github_fly.buildScript
import github_fly.onUpdate
import github_fly.riot
import github_fly.utils.Segment
import github_fly.utils.matchedSegments

interface HighlightMatchingOpts {
    var content: String
    var keyword: String
}

interface HighlightMatchingTag : HighlightMatchingOpts, RiotTag {
    var segments: Array<Segment>
}


private val TEMPLATE = """
<span><span each="{segments}" class="{ highlight: this.matched }">{this.content}</span></span>
"""

private const val CSS = """
.highlight {
  color: red
}
"""

private val script = buildScript<HighlightMatchingTag, HighlightMatchingOpts> { opts ->
    fun update() {
        this.content = opts.content
        this.keyword = opts.keyword
        this.segments = matchedSegments(content, keyword).toTypedArray()
    }
    update()
    this.onUpdate(::update)
}

@Suppress("unused")
val highlightMatchingTag = riot.tag(
        "highlight-matching",
        template = TEMPLATE,
        css = CSS,
        script = script
)