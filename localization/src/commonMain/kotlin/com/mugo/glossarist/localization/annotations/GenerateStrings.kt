package com.mugo.glossarist.localization.annotations

/**
 * Marks a file as containing string resources that should be processed by the Glossarist KSP processor.
 *
 * This annotation is a signal to the `StringResourceProcessor` to scan the annotated file for
 * properties of type `StringsProperty`. The processor will then collect these properties and
 * include them in the generated `GeneratedStrings.kt` file, enabling them to be part of the
 * automatic registration process.
 *
 * You should apply this annotation to any file where you define your `stringResource` delegates.
 *
 * **Usage:**
 * ```
 * @file:GenerateStrings
 *
 * package com.example.i18n
 *
 * val authStrings = stringResource(...)
 * val homeStrings = stringResource(...)
 * ```
 */
@Target(AnnotationTarget.FILE)
@Retention(AnnotationRetention.SOURCE)
annotation class GenerateStrings
