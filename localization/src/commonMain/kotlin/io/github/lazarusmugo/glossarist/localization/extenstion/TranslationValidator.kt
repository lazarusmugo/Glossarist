package io.github.lazarusmugo.glossarist.localization.extenstion

import io.github.lazarusmugo.glossarist.localization.locale.LocaleCode

/**
 * Defines the contract for validating a set of translations.
 *
 * This interface allows for custom validation logic to be plugged into the string registration process.
 * A default implementation is provided, but developers can create their own to enforce different rules.
 */
interface TranslationValidator {
    /**
     * Validates that a given map of translations meets the required criteria.
     *
     * The validator should check if translations are provided for all `requiredLocales`.
     * It can also perform other checks, such as checking for nulls or empty strings.
     *
     * @param T The type of the translation data class.
     * @param translations The map of translations to validate.
     * @param requiredLocales The set of locales that must be present in the translations map.
     * @param resourceKey The unique key of the resource being validated, used for clear error reporting.
     * @throws IllegalStateException if validation fails.
     */
    fun <T> validate(
        translations: Map<LocaleCode, T>,
        requiredLocales: Set<LocaleCode>,
        resourceKey: String,
    )
}
