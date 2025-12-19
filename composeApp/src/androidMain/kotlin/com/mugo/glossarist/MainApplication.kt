package com.mugo.glossarist

import android.app.Application
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.github.lazarusmugo.glossarist.localization.locale.LocaleCode
import io.github.lazarusmugo.glossarist.localization.manager.Strings
import io.github.lazarusmugo.glossarist.localization.models.I18nConfig

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Napier.base(DebugAntilog())

        Strings.initialize(
            I18nConfig(
                supportedLocales =
                    setOf(
                        LocaleCode.Standard.EN,
                        LocaleCode.Standard.SW,
                    ),
                enforceTranslations = true,
            ),
        )
    }
}
