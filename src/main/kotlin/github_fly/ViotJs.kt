package github_fly

@JsName("riot")
external object riot {
    fun tag(tagName: String, template: String? = definedExternally, css: String? = definedExternally, attrs: String? = definedExternally, script: (dynamic) -> Unit)
    fun mount(tag: String)
}

external interface RiotTag {
    fun on(event: String, func: () -> Unit)
}

fun RiotTag.onUpdate(func: () -> Unit) {
    this.on("update", func)
}

// https://stackoverflow.com/questions/49773452/how-to-generate-a-javascript-function-with-this-field-value-in-body-in-kotlin/49780099#49780099
inline fun <T, OPTS> buildScript(crossinline block: T.(OPTS) -> Unit): (OPTS) -> Unit = { options: OPTS ->
    console.log("### buildScript")
    console.log(options)
    block(js("this") as T, options)
}
