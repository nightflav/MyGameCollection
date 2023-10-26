package com.example.royaal.core.common.model

interface EntityMapper <F: Any, T: Any> {

    fun F.map(): T

}