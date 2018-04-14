package github_fly.utils


data class Segment(val content: String, val matched: Boolean)

private fun getMatchedPositions(text: String, keyword: String): List<Int> {
    val matchedPositions = mutableListOf<Int>()
    var currentPosition = 0
    for (char in keyword) {
        var found = false
        while (currentPosition < text.length) {
            if (text[currentPosition] == char) {
                matchedPositions.add(currentPosition)
                currentPosition++
                found = true
                break
            } else {
                currentPosition++
            }
        }
        if (!found) {
            matchedPositions.clear()
            break
        }
    }
    return matchedPositions
}

fun matchedSegments(text: String, keyword: String): List<Segment> {
    console.log("matchedSegments(text: $text, keyword: $keyword)")
    val matchedPositions = getMatchedPositions(text, keyword)
    if (matchedPositions.isEmpty()) {
        return listOf(Segment(text, false))
    }

    val segments = mutableListOf<Segment>()

    for ((index, char) in text.withIndex()) {
        segments.add(Segment(char.toString(), matchedPositions.contains(index)));
    }
    return segments
}

fun matched(text: String, keyword: String): Boolean {
    return getMatchedPositions(text, keyword).isNotEmpty()
}