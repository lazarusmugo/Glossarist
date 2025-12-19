package com.mugo.glossarist.localization.manager

import androidx.compose.runtime.Composable
import com.mugo.glossarist.localization.annotations.ExperimentalGlossaristApi
import com.mugo.glossarist.localization.locale.LocaleCode
import com.mugo.glossarist.localization.models.I18nConfig

/**
 * The primary public API for the Glossarist internationalization library.
 *
 * This global singleton provides a centralized point for initializing, managing, and accessing
 * localized string resources throughout your application.
 *
 * **Usage Pattern:**
 * 1. **Initialization:** Call `Strings.initialize()` once at application startup with your desired configuration.
 * 2. **Registration:** String resources are typically registered automatically via the `stringResource` delegate.
 * 3. **Access:** Use `Strings.get()` within your Composables or `Strings.getNonCompose()` in other contexts like ViewModels.
 * 4. **Locale Control:** Use `setLocale()` to dynamically change the language.
 */
@ExperimentalGlossaristApi
object Strings {
    private lateinit var manager: StringsManager
    private lateinit var config: I18nConfig

    /**
     * Initializes the internationalization manager. This must be called once at application startup
     * before any other methods in this object are used.
     *
     * @param config The [I18nConfig] containing the setup for default locale, supported locales,
     * and validation rules.
     */
    fun initialize(config: I18nConfig) {
        this.config = config
        this.manager = StringsManager(config)
    }

    /** Ensures that `initialize()` has been called before proceeding. */
    private fun ensureInitialized() {
        check(::manager.isInitialized) { "Strings not initialized. Call Strings.initialize() first." }
    }

    /**
     * Registers a string resource with a unique key and its associated translations.
     * This method is typically called by the `stringResource` delegate, not directly by developers.
     *
     * @param T The type of the translation data class.
     * @param key A unique string key to identify the resource.
     * @param translations A map where keys are [LocaleCode]s and values are the translation instances.
     */
    fun <T> register(
        key: String,
        translations: Map<LocaleCode, T>,
    ) {
        ensureInitialized()
        manager.registerStringResource(key, translations)
    }

    /**
     * Retrieves the appropriate translation for the given key in a Composable context.
     *
     * This function observes the current locale and automatically triggers recomposition when the
     * language changes, ensuring the UI always displays the correct translation.
     *
     * @param T The expected type of the translation data class.
     * @param key The unique key of the string resource.
     * @return The translation data class for the currently active locale.
     */
    @Composable
    fun <T> get(key: String): T {
        ensureInitialized()
        return manager.getStrings(key)
    }

    /**
     * Retrieves the appropriate translation for a given key outside of a Composable context (e.g., in a ViewModel).
     *
     * This function provides a snapshot of the translation for the currently active locale. It does *not*
     * automatically update if the locale changes later.
     *
     * @param T The expected type of the translation data class.
     * @param key The unique key of the string resource.
     * @return The translation data class for the currently active locale.
     */
    fun <T> getNonCompose(key: String): T {
        ensureInitialized()
        return manager.getStringsForLocale(key, manager.getCurrentLocale())
    }

    /**
     * Sets the active locale for the application.
     *
     * All Composables using `Strings.get()` will automatically recompose to reflect the new language.
     *
     * @param locale The [LocaleCode] to set as the current language.
     */
    fun setLocale(locale: LocaleCode) {
        ensureInitialized()
        manager.setLocale(locale)
    }

    /**
     * Sets the active locale for the application using a string code.
     *
     * @param localeCode The string representation of the locale (e.g., "en", "fr").
     */
    fun setLocale(localeCode: String) {
        ensureInitialized()
        manager.setLocale(localeCode)
    }

    /**
     * Resets the active locale to the system's default, as determined by the [LocaleProvider].
     */
    fun resetToSystemLocale() {
        ensureInitialized()
        manager.resetToSystemLocale()
    }

    /**
     * @return The currently active [LocaleCode].
     */
    fun getCurrentLocale(): LocaleCode {
        ensureInitialized()
        return manager.getCurrentLocale()
    }

    /**
     * @return The set of all locales supported by the application, as defined in [I18nConfig].
     */
    fun getSupportedLocales(): Set<LocaleCode> {
        ensureInitialized()
        return config.supportedLocales
    }
}
