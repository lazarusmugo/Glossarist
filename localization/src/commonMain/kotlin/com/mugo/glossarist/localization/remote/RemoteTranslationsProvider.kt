package com.mugo.glossarist.localization.remote

import com.mugo.glossarist.localization.locale.LocaleCode

/**
 * A provider that fetches translations from a remote source.
 * This allows for dynamic updates of translations without requiring an app update.
 */
interface RemoteTranslationsProvider {
    /**
     * Fetches the translations for a given locale.
     * This should return a bundled JSON string containing all translations for the given locale.
     *
     * @param locale The locale to fetch translations for.
     * @return A JSON string, or null if fetching fails.
     */
    suspend fun fetch(locale: LocaleCode): String?
}
