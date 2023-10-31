package com.example.royaal.mygamecollection.di

import com.example.royaal.commonui.FeatureEntry
import dagger.MapKey
import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.FIELD, AnnotationTarget.TYPE)
@MapKey
annotation class RouteKey(
    val key: KClass<out FeatureEntry>
)
