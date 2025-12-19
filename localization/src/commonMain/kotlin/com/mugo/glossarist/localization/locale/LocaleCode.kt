package com.mugo.glossarist.localization.locale

import com.mugo.glossarist.localization.annotations.ExperimentalGlossaristApi

/**
 * Sealed interface representing a locale code. Supports both standard ISO
 * 639-1(https://www.loc.gov/standards/iso639-2/php/code_list.php) codes and custom locale codes.
 */
@ExperimentalGlossaristApi
sealed interface LocaleCode {
    val code: String

    /**
     * Standard ISO 639-1 two-letter language codes. Based on the international standard for language
     * representation.
     */
    enum class Standard(
        override val code: String,
    ) : LocaleCode {
        EN("en"), // English
        SW("sw"), // Swahili
        FR("fr"), // French
    }

    /**
     * Custom locale codes for non-standard languages, dialects, or slang. Use this for app-specific
     * language variants that don't have ISO codes.
     *
     * Example: Custom("en-slang") for English slang variant
     */
    data class Custom(
        override val code: String,
    ) : LocaleCode {
        init {
            require(code.isNotBlank()) { "Custom locale code cannot be blank" }
            require(code.length in 2..10) { "Custom locale code must be 2-10 characters" }
        }
    }
}
