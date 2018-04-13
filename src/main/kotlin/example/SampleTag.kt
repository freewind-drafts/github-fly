package example

private interface SampleTag {
    var title: String
    var message: String
    var click: () -> Unit
}

private val script = buildScript<SampleTag> {
    this.title = "Sample"
    this.message = "welcome"
    this.click = { message = "Clicked!" }
}

private const val TEMPLATE = """
<div>
    <h1>{title}</h1>
    <p>Dear user, { message }</p>
    <button click={click}>Click Me!</button>
</div>
"""

private const val CSS = """
:scope { font-size: 20px }
h1 { color: red }
p { color: blue }
"""

fun buildSampleTag() {
    riot.tag("sample", template = TEMPLATE, css = CSS, script = script)
}

