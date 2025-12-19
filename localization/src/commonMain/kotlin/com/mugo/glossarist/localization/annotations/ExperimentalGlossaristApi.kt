package com.mugo.glossarist.localization.annotations

/**
 * Marks a declaration as part of Glossarist's experimental API.
 *
 * Components marked with this annotation are subject to change and may be modified or removed
 * in future releases without any prior notice.
 * It is recommended to use these APIs with caution and be prepared for migrations.
 */
@RequiresOptIn(
    message = "This is an experimental API for Glossarist and is subject to change.",
    level = RequiresOptIn.Level.WARNING,
)
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.TYPEALIAS, AnnotationTarget.PROPERTY)
annotation class ExperimentalGlossaristApi
