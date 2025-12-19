package io.github.lazarusmugo.glossarist.localization.locale

import java.util.Locale

actual class PlatformLocaleProvider actual constructor() : LocaleProvider {
    actual override fun getCurrentLocale(): String = Locale.getDefault().language
}
