package github_fly.tags

import github_fly.buildScript
import github_fly.riot
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.KeyboardEvent

data class SearchItem(val title: String, val url: String)

interface MainPageTag {
    var keyword: String
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
            <li each="{matchedItems}">
                <highlight-matching content="{this.title}" keyword="{parent.keyword}"></highlight-matching>
            </li>
        </ul>
    </div>
</div>
"""

private fun findMatchedItems(items: Array<SearchItem>, keyword: String): Array<SearchItem> {
    return items.filter { it.title.contains(keyword) }.toTypedArray()
}

private val script = buildScript<MainPageTag, dynamic> {
    this.keyword = ""
    this.matchedItems = initData
    this.search = fun(event: KeyboardEvent) {
        this.keyword = (event.target as? HTMLInputElement)?.value?.trim() ?: ""
        this.matchedItems = if (keyword.isEmpty()) initData else findMatchedItems(initData, keyword)
    }
}

@Suppress("unused")
val mainPageTag = riot.tag("main-page",
        template = TEMPLATE,
        script = script
)

