package com.mugo.glossarist.localization.locale

import androidx.compose.ui.text.intl.Locale

actual class PlatformLocaleProvider actual constructor() : LocaleProvider {
    actual override fun getCurrentLocale(): String = Locale.current.language
}
