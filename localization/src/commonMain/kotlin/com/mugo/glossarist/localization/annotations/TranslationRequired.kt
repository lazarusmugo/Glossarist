package com.mugo.glossarist.localization.annotations

/**
 * Annotation to mark data classes that require translations for all locales. Use this on your
 * string data classes to enable compile-time validation.
 *
 * Usage:
 * ```
 * @TranslationRequired
 * data class IdentityVerificationStrings(val hello: String)
 * ```
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class TranslationRequired
