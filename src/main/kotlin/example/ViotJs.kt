package example

@JsName("riot")
external object riot {
    fun tag(tagName: String, template: String? = definedExternally, css: String? = definedExternally, attrs: String? = definedExternally, script: (dynamic) -> Unit)
    fun mount(tag: String)
}

// https://stackoverflow.com/questions/49773452/how-to-generate-a-javascript-function-with-this-field-value-in-body-in-kotlin/49780099#49780099
inline fun <T> buildScript(crossinline block: T.(dynamic) -> Unit): (dynamic) -> Unit = { options: dynamic ->
    block(js("this") as T, options)
}
