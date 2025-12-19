package com.mugo.glossarist.localization.manager

import com.mugo.glossarist.localization.extenstion.TranslationValidator
import com.mugo.glossarist.localization.extenstion.toLocaleCode
import com.mugo.glossarist.localization.locale.LocaleCode

/**
 * A container for a single localizable resource.
 *
 * This class holds all the translations for a specific resource key and provides the logic
 * for retrieving the correct translation based on the requested locale. It also enforces
 * that a translation for the default locale is always available as a fallback.
 *
 * @param T The type of the translation data class.
 * @param key The unique key identifying this resource.
 * @param translations The map of translations, where the key is a [LocaleCode] and the value is the translation object.
 * @param defaultLocale The [LocaleCode] to use as a fallback if a specific translation is not found.
 * @param validator The [TranslationValidator] used to validate the translations during initialization.
 */
class StringResourceRegistry<T>(
    private val key: String,
    private val translations: Map<LocaleCode, T>,
    private val defaultLocale: LocaleCode,
    private val validator: TranslationValidator,
) {
    init {
        require(translations.containsKey(defaultLocale)) {
            "Translations must include the default locale: ${defaultLocale.code}"
        }
    }

    /**
     * Retrieves the translation for the given locale.
     *
     * If a translation for the requested `locale` is not available, it falls back to the
     * `defaultLocale`. If no translation is found for either (which should be prevented by the init block),
     * it throws an error.
     *
     * @param locale The desired [LocaleCode].
     * @return The translation object of type [T].
     */
    fun getStrings(locale: LocaleCode): T =
        translations[locale]
            ?: translations[defaultLocale]
            ?: error("No translations found for resource '$key'")

    /**
     * A convenience function to retrieve the translation using a string locale code.
     *
     * @param localeCode The string representation of the locale (e.g., "en").
     * @return The translation object of type [T].
     */
    fun getStrings(localeCode: String): T = getStrings(localeCode.toLocaleCode())
}
