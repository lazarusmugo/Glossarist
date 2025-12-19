package io.github.lazarusmugo.glossarist.localization.locale

import kotlinx.browser.window

actual class PlatformLocaleProvider actual constructor() : LocaleProvider {
    actual override fun getCurrentLocale(): String = window.navigator.language.split("-").first()
}
