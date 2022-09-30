package ru.forester.jeevz.utils

import mu.KLogging

fun tryToDo(action: () -> Unit){
    try {
        action.invoke()
    } catch (e: Exception){
        LOG.logger.error("Error", e)
    }

}
object LOG : KLogging()
