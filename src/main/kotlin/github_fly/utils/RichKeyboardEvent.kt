package github_fly.utils

import org.w3c.dom.events.KeyboardEvent

fun KeyboardEvent.isEnter(): Boolean {
    return this.keyCode == 13
}

fun KeyboardEvent.isArrowUp(): Boolean {
    return this.keyCode == 38
}

fun KeyboardEvent.isArrowDown(): Boolean {
    return this.keyCode == 40
}