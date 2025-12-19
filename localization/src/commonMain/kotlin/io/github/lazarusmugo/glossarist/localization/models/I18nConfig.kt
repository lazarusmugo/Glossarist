package io.github.lazarusmugo.glossarist.localization.models

import io.github.lazarusmugo.glossarist.localization.annotations.ExperimentalGlossaristApi
import io.github.lazarusmugo.glossarist.localization.extenstion.TranslationValidator
import io.github.lazarusmugo.glossarist.localization.locale.LocaleCode
import io.github.lazarusmugo.glossarist.localization.locale.LocaleProvider
import io.github.lazarusmugo.glossarist.localization.locale.PlatformLocaleProvider
import io.github.lazarusmugo.glossarist.localization.translators.DefaultTranslationValidator

/**
 * Represents the complete configuration for the Glossarist internationalization system.
 *
 * An instance of this class is required to initialize the `Strings` manager.
 * It allows you to define supported languages, fallback behavior, and validation rules.
 *
 * @property localeProvider The provider used to fetch the device's current system locale. Defaults to [PlatformLocaleProvider].
 * @property defaultLocale The default locale to use as a fallback when a translation for the current locale is not available. Defaults to English (`en`).
 * @property supportedLocales The complete set of locales that the application officially supports. This is a mandatory field.
 * @property validator The validation logic to apply to each string resource upon registration. Defaults to [DefaultTranslationValidator].
 * @property enforceTranslations A flag to determine whether to enforce translation validation. If `true` (the default), the app will crash if a translation is missing.
 */
@ExperimentalGlossaristApi
data class I18nConfig(
    val localeProvider: LocaleProvider = PlatformLocaleProvider(),
    val defaultLocale: LocaleCode = LocaleCode.Standard.EN,
    val supportedLocales: Set<LocaleCode>,
    val validator: TranslationValidator = DefaultTranslationValidator(),
    val enforceTranslations: Boolean = true,
)
