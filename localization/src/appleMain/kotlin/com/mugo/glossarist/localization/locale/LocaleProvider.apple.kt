package com.mugo.glossarist.localization.locale

import platform.Foundation.NSLocale
import platform.Foundation.currentLocale
import platform.Foundation.languageCode

actual class PlatformLocaleProvider actual constructor() : LocaleProvider {
    actual override fun getCurrentLocale(): String = NSLocale.currentLocale.languageCode
}
