package io.github.lazarusmugo.glossarist.localization.locale

/**
 * Interface for providing platform-specific locale information.
 *
 * This abstraction allows the internationalization system to work across different platforms by
 * providing a unified way to access the system's current locale setting.
 *
 * Example implementations:
 * - Android: Uses `Locale.getDefault().language`
 * - iOS: Uses `NSLocale.currentLocale().languageCode`
 * - Desktop: Uses `Locale.getDefault().language`
 */
interface LocaleProvider {
    /**
     * Get the current system locale code.
     *
     * @return The locale code as a string (e.g., "en", "sw")
     */
    fun getCurrentLocale(): String
}

expect class PlatformLocaleProvider() : LocaleProvider {
    override fun getCurrentLocale(): String
}
