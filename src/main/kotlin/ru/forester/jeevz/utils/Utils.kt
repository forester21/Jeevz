package ru.forester.jeevz.utils

fun tryToDo(action: () -> Unit){
    try {
        action.invoke()
    } catch (e: Exception){
        //TODO change to logger
        println(e)
    }
}