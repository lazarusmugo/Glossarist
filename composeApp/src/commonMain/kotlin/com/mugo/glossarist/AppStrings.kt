package com.mugo.glossarist

import com.mugo.glossarist.localization.extenstion.stringResource
import com.mugo.glossarist.localization.locale.LocaleCode

data class AppStrings(
    val welcome: String,
    val test: String,
)

val appStringsEn = AppStrings(welcome = "Welcome", test = "Try")

val appStringsSw = AppStrings(welcome = "Karibu", test = "Jaribu")

val appStrings =
    stringResource(
        key = "app.strings",
        translations =
            mapOf(LocaleCode.Standard.EN to appStringsEn, LocaleCode.Standard.SW to appStringsSw),
    )
