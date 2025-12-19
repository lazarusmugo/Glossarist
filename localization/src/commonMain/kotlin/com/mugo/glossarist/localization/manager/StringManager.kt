package com.mugo.glossarist.localization.manager

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import com.mugo.glossarist.localization.extenstion.toLocaleCode
import com.mugo.glossarist.localization.locale.LocaleCode
import com.mugo.glossarist.localization.models.I18nConfig

/**
 * The internal core of the internationalization system.
 *
 * This class is responsible for managing the registration of all string resources,
 * handling the currently active locale, and retrieving the correct translations.
 * It is not intended for direct use by developers; instead, interaction should happen
 * through the `Strings` singleton.
 *
 * @param config The [I18nConfig] that defines the behavior of this manager.
 */
class StringsManager(
    private val config: I18nConfig,
) {
    private val registries = mutableMapOf<String, StringResourceRegistry<*>>()
    private var currentLocale: MutableState<LocaleCode> =
        mutableStateOf(config.localeProvider.getCurrentLocale().toLocaleCode())

    /**
     * Registers a new string resource.
     *
     * This method validates the provided translations (if enforcement is enabled in the config),
     * checks for duplicate resource keys, and then creates and stores a [StringResourceRegistry]
     * for the given resource.
     *
     * @param T The type of the translation data class.
     * @param key A unique string key for the resource.
     * @param translations The map of translations for each locale.
     * @throws IllegalStateException if a resource with the same key is already registered.
     */
    fun <T> registerStringResource(
        key: String,
        translations: Map<LocaleCode, T>,
    ) {
        // Validate only if enforcement is enabled
        if (config.enforceTranslations) {
            config.validator.validate(translations, config.supportedLocales, key)
        }

        // Check for duplicate keys
        if (registries.containsKey(key)) {
            error("Resource with key '$key' is already registered")
        }

        val registry =
            StringResourceRegistry(
                key = key,
                translations = translations,
                defaultLocale = config.defaultLocale,
                validator = config.validator,
            )

        registries[key] = registry
    }

    /**
     * Retrieves the appropriate translation for the given key in a Composable context.
     *
     * This function observes the `currentLocale` state, ensuring that any change to the locale
     * will trigger a recomposition in the calling Composable, thus displaying the new language.
     *
     * @param T The expected type of the translation data class.
     * @param key The unique key of the resource to retrieve.
     * @return The translation data class for the currently active locale.
     */
    @Composable
    fun <T> getStrings(key: String): T {
        val locale by currentLocale
        return getStringsForLocale(key, locale)
    }

    /**
     * Retrieves the translation for a specific locale, without being reactive.
     *
     * This is used for non-composable contexts or for displaying a string in a language
     * other than the currently active one.
     *
     * @param T The expected type of the translation data class.
     * @param key The unique key of the resource to retrieve.
     * @param locale The [LocaleCode] for which to get the translation.
     * @return The translation data class for the specified locale.
     * @throws IllegalStateException if no resource with the given key is found.
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> getStringsForLocale(
        key: String,
        locale: LocaleCode,
    ): T {
        val registry =
            registries[key] ?: error("Resource '$key' not registered")

        return (registry as StringResourceRegistry<T>).getStrings(locale)
    }

    /**
     * Sets the application's current locale.
     *
     * Changing this value will cause any Composable using `getStrings` to recompose.
     * A warning is printed if the locale is not in the list of supported locales.
     *
     * @param locale The [LocaleCode] to set as active.
     */
    fun setLocale(locale: LocaleCode) {
        if (!config.supportedLocales.contains(locale)) {
            println("Warning: Locale '${locale.code}' is not in supported locales")
        }
        currentLocale.value = locale
    }

    /**
     * A convenience function to set the locale using a string code.
     *
     * @param localeCode The string code (e.g., "en") to set as the active locale.
     */
    fun setLocale(localeCode: String) {
        setLocale(localeCode.toLocaleCode())
    }

    /**
     * Resets the current locale to the one provided by the system's [LocaleProvider].
     */
    fun resetToSystemLocale() {
        currentLocale.value = config.localeProvider.getCurrentLocale().toLocaleCode()
    }

    /**
     * Returns the currently active locale.
     */
    fun getCurrentLocale(): LocaleCode = currentLocale.value
}
