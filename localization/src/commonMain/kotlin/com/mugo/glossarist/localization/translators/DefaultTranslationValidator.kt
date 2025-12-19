package com.mugo.glossarist.localization.translators

import com.mugo.glossarist.localization.extenstion.TranslationValidator
import com.mugo.glossarist.localization.locale.LocaleCode
import io.github.aakira.napier.Napier

/**
 * The default implementation of [TranslationValidator].
 *
 * This validator performs the following checks:
 * - Ensures that translations are provided for all locales defined in `I18nConfig.supportedLocales`.
 * - Verifies that no translation is null.
 * - For simple `String` translations, it ensures they are not blank.
 *
 * **Limitation:**
 * Due to the current limitations of Kotlin's multiplatform reflection, this validator cannot
 * recursively check the properties of a data class. Developers are responsible for ensuring that
 * the string properties within their translation data classes are valid.
 */
class DefaultTranslationValidator : TranslationValidator {
    override fun <T> validate(
        translations: Map<LocaleCode, T>,
        requiredLocales: Set<LocaleCode>,
        resourceKey: String,
    ) {
        // Check all required locales are present
        val missingLocales = requiredLocales - translations.keys
        if (missingLocales.isNotEmpty()) {
            error(
                "Resource '$resourceKey' is missing translations for: " +
                    missingLocales.joinToString { it.code },
            )
        }

        // Validate each translation
        translations.forEach { (locale, translation) ->
            validateTranslation(translation, locale, resourceKey)
        }
    }

    private fun <T> validateTranslation(
        translation: T,
        locale: LocaleCode,
        resourceKey: String,
    ) {
        if (translation == null) {
            error("Translation for '$resourceKey' in locale '${locale.code}' is null")
        }

        // Only validate simple string translations
        if (translation is String && translation.isBlank()) {
            error(
                "Translation for '$resourceKey' in locale '${locale.code}' is blank. " +
                    "Received: \"$translation\"",
            )
        }

        // For data classes and complex objects, validation is skipped
        if (translation !is String) {
            Napier.d(
                "Skipping deep validation for data class '$resourceKey' in locale '${locale.code}'. " +
                    "Ensure all string properties have non-empty values.",
            )
        }
    }
}
