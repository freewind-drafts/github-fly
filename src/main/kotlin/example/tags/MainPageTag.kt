package example.tags

import example.buildScript
import example.riot
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.KeyboardEvent

data class SearchItem(val title: String, val url: String)

interface MainPageTag {
    var matchedItems: Array<SearchItem>
    var search: (KeyboardEvent) -> Unit
}

private val initData = arrayOf(
        SearchItem("kotlin-demos / kotlin-hello-world-demo", "http://github.com/kotlin-demos/kotlin-hello-world-demo"),
        SearchItem("java-demos / java-hello-world-demo", "http://github.com/java-demos/java-hello-world-demo")
)

private const val TEMPLATE = """
<div>
    <div><input type='text' onkeyup={search} autofocus /></div>
    <div>
        <ul>
            <li each={matchedItems}>
                {title}
            </li>
        </ul>
    </div>
</div>
"""

private fun findMatchedItems(items: Array<SearchItem>, keyword: String): Array<SearchItem> {
    return items.filter { it.title.contains(keyword) }.toTypedArray()
}

private val script = buildScript<MainPageTag> {
    this.matchedItems = initData
    this.search = fun(event: KeyboardEvent) {
        val keyword: String? = (event.target as? HTMLInputElement)?.value?.trim()
        this.matchedItems = if (keyword == null) initData else findMatchedItems(initData, keyword)
    }
}

@Suppress("unused")
val mainPageTag = riot.tag("main-page",
        template = TEMPLATE,
        script = script
)

//<highlight-matching-component :content="item.title" :keyword="keyword" />