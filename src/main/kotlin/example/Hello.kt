package example

@JsName("riot")
external object riot {
    fun tag(tagName: String, template: String, css: String, attrs: String, tag: dynamic)
    fun mount(tag: String)
}

interface SampleTag {
    var name: String
    var message: String
    var click: () -> Unit
}

// https://stackoverflow.com/questions/49773452/how-to-generate-a-javascript-function-with-this-field-value-in-body-in-kotlin/49780099#49780099
inline fun <T> builder(block: T.() -> Unit) = block(js("this"))

fun main(args: Array<String>) {

    val sampleTag = { opts: dynamic ->
        builder<SampleTag> {
            this.name = "sample"
            this.message = "init-message"
            this.click = { message = "Clicked!" }
        }
    }

    riot.tag("sample", "<div><h1>Sample</h1><p onclick=\"{ click }\">Dear user, { message }</p></div>", "", "", sampleTag)
    riot.mount("*")

}
