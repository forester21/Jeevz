package ru.forester.jeevz.utils

import mu.KLogging

inline fun <T> T.runIf(clause: Boolean, block: T.() -> T): T =
    run {
        if (clause) {
            block()
        } else {
            this
        }
    }

fun tryToDo(action: () -> Unit) {
    try {
        action.invoke()
    } catch (e: Exception) {
        LOG.logger.error("Error", e)
    }

}

object LOG : KLogging()
