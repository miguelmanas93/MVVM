package com.miguel.ags.mvvmtodos.utils

open class Avisos<out T>(private val content: T) {

    var manipulado = false
        private set // Permitir lectura externa pero no escritura

    /**
     * Devuelve el contenido e impide su uso de nuevo.
     */
    fun getContentSiNoManipulado(): T? {
        return if (manipulado) {
            null
        } else {
            manipulado = true
            content
        }
    }

    /**
     * Devuelve el contenido, incluso si ha sido manipulado.
     */
    fun peekContenido(): T = content
}