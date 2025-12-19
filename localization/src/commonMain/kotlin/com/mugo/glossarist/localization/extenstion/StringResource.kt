package com.mugo.glossarist.localization.extenstion

import com.mugo.glossarist.localization.annotations.ExperimentalGlossaristApi
import com.mugo.glossarist.localization.locale.LocaleCode

/**
 * Creates and registers a localizable string resource.
 *
 * This is the primary factory function for defining all localizable strings in the application.
 * It creates a [StringsProperty] delegate that automatically handles registration and retrieval
 * of the correct translation based on the current locale.
 *
 * **Example Usage:**
 * ```
 * val authStrings = stringResource(
 *     key = "auth_strings",
 *     translations = mapOf(
 *         LocaleCode.Standard.EN to AuthStrings(welcome = "Welcome"),
 *         LocaleCode.Standard.FR to AuthStrings(welcome = "Bienvenue")
 *     )
 * )
 * ```
 *
 * @param T The type of the data class holding the translations for this resource.
 * @param key A unique string identifier for this resource. This is used to retrieve the resource later.
 *            It's crucial that this key is unique across the entire application to avoid conflicts.
 * @param translations A map where keys are [LocaleCode]s and values are instances of the translation data class [T].
 * @return A [StringsProperty] instance that can be used as a property delegate.
 */
@ExperimentalGlossaristApi
fun <T> stringResource(
    key: String,
    translations: Map<LocaleCode, T>,
): StringsProperty<T> = StringsProperty(key, translations)
