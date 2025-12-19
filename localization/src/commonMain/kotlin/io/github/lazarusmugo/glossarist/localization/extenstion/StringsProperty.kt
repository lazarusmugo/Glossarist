package io.github.lazarusmugo.glossarist.localization.extenstion

import androidx.compose.runtime.Composable
import io.github.lazarusmugo.glossarist.localization.locale.LocaleCode
import io.github.lazarusmugo.glossarist.localization.manager.Strings
import kotlin.reflect.KProperty

/**
 * A property delegate that provides a specific translation data class.
 *
 * This class is the core of the library's declarative API. When you create a property using
 * `by stringResource(...)`, you are creating an instance of this class.
 *
 * **How it works:**
 * 1.  **Registration:** The `init` block automatically calls `Strings.register()`, passing the
 *     unique key and translations to the central manager. This happens lazily when the property
 *     is first accessed.
 * 2.  **Retrieval:** The `getValue` operator is `@Composable`. When you access the property
 *     within a Composable function (e.g., `Text(strings.welcome)`), this method is called.
 *     It delegates to `Strings.get()`, which is state-aware and will trigger recomposition
 *     whenever the application's locale changes.
 *
 * @param T The type of the translation data class.
 * @property key The unique key for this resource.
 * @property translations The map of translations provided for this resource.
 */
class StringsProperty<T>(
    private val key: String,
    private val translations: Map<LocaleCode, T>,
) {
    init {
        Strings.register(key, translations)
    }

    /**
     * Provides the value of the property in a Composable context.
     *
     * This operator allows the property to be used as a delegate (`val myStrings by stringResource(...)`).
     * It fetches the correct translation for the current locale from the central `Strings` manager.
     *
     * @param thisRef The object instance for which the property is being read (not used).
     * @param property The metadata for the property being read (not used).
     * @return The translation data class of type [T] for the currently active locale.
     */
    @Composable
    operator fun getValue(
        thisRef: Any?,
        property: KProperty<*>
    ): T = Strings.get(key)
}
