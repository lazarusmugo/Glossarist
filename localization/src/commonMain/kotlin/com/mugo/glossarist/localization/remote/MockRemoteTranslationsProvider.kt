package com.mugo.glossarist.localization.remote

import com.mugo.glossarist.localization.locale.LocaleCode
import io.github.aakira.napier.Napier
import kotlinx.coroutines.delay

/**
 * A mock implementation of [RemoteTranslationsProvider] for testing and development.
 * It simulates a network request by returning a hardcoded JSON string after a short delay.
 */
class MockRemoteTranslationsProvider : RemoteTranslationsProvider {
    override suspend fun fetch(locale: LocaleCode): String? {
        Napier.i(tag = "MockRemoteProvider", message = "Fetching translations for ${locale.code}...")
        // Simulate network latency
        delay(1000)

        // For this example, we only return a mock response for French.
        if (locale.code.startsWith("fr")) {
            Napier.i(tag = "MockRemoteProvider", message = "Returning mock French JSON bundle.")
            return fr_bundle_json
        } else {
            Napier.w(tag = "MockRemoteProvider", message = "No mock bundle for locale: ${locale.code}")
            return null
        }
    }
}

// This simulates the bundled JSON that would be downloaded from your server for the French locale.
private const val fr_bundle_json = """
{
  "advanced.string.example": {
    "hello": "Bonjour",
    "welcome": "Bienvenue à ",
    "subtitle": "Vérifions votre identité",
    "plural": {
        "0": "Vous n'avez aucun nouveau message",
        "1": "Vous avez 1 nouveau message",
        "other": "Vous avez {count} nouveaux messages"
    }
  }
}
"""
