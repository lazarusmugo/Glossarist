package com.mugo.glossarist

import android.app.Application
import com.mugo.glossarist.localization.locale.LocaleCode
import com.mugo.glossarist.localization.manager.Strings
import com.mugo.glossarist.localization.models.I18nConfig
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

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
