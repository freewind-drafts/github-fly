package github_fly.tags

import github_fly.buildScript
import github_fly.riot
import github_fly.utils.isArrowDown
import github_fly.utils.isArrowUp
import github_fly.utils.isEnter
import github_fly.utils.matched
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.KeyboardEvent
import kotlin.browser.window

data class SearchItem(val title: String, val url: String, var selected: Boolean = false)

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
            <li each="{matchedItems}" class="{selected: this.selected}">
                <highlight-matching content="{this.title}" keyword="{parent.keyword}"></highlight-matching>
            </li>
        </ul>
    </div>
</div>
"""

private const val CSS = """
.selected {
    background-color: #EEE;
}
"""

private fun findMatchedItems(items: Array<SearchItem>, keyword: String): Array<SearchItem> {
    return items.filter { matched(it.title, keyword) }.toTypedArray()
}

private fun getSelectedItem(items: Array<SearchItem>): SearchItem? {
    return items.find { it.selected }
}

private fun selectFirst(items: Array<SearchItem>) {
    items.forEach { it.selected = false }
    items.firstOrNull()?.apply {
        this.selected = true
    }
}

private val SCRIPT = buildScript<MainPageTag, dynamic> {
    fun init(keyword: String, items: Array<SearchItem>) {
        println("keyword != this.keyword: ${keyword != this.keyword}")
        if (keyword != this.keyword) {
            this.keyword = keyword
            this.matchedItems = items
            selectFirst(this.matchedItems)
        }
    }

    init("", initData)
    this.search = fun(event: KeyboardEvent) {
        when {
            event.isEnter() -> getSelectedItem(this.matchedItems)?.run {
                window.open(url = this.url, target = "_blank")
            }
            event.isArrowUp() -> selectPreviousItem(this.matchedItems)
            event.isArrowDown() -> selectNextItem(this.matchedItems)
            else -> {
                val keyword = (event.target as? HTMLInputElement)?.value?.trim() ?: ""
                val matchedItems = if (keyword.isEmpty()) initData else findMatchedItems(initData, keyword)
                init(keyword, matchedItems)
            }
        }
    }
}

fun selectNextItem(items: Array<SearchItem>) {
    selectItem(items, 1)
}

private fun selectPreviousItem(items: Array<SearchItem>) {
    selectItem(items, -1)
}

private fun selectItem(items: Array<SearchItem>, step: Int) {
    val index = items.indexOfFirst { it.selected }
    println("index: $index")
    if (index != -1) {
        items[index].selected = false
        val newIndex = (index + step).let {
            if (it < 0) {
                items.size - 1
            } else if (it >= items.size) {
                0
            } else {
                it
            }
        }
        println("newIndex: $newIndex")
        items[newIndex].selected = true
    }
}

@Suppress("unused")
val mainPageTag = riot.tag("main-page",
        template = TEMPLATE,
        script = SCRIPT,
        css = CSS
)

