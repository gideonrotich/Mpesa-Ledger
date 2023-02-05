package com.swayy.core_database.local

import android.content.Context
import com.swayy.core.utils.PreferenceUtils
import com.swayy.core_database.models.ThemePreference

interface AppPreferences {
    var currentVersion: Int
    var latestVersion: Int
    var skipUpdateVer: Int
    var themePreference: ThemePreference
    var hasSeenExportPrompt: Boolean
    var hasSeenFilterPrompt: Boolean
}

class AppPreferencesImpl(private val context: Context) : AppPreferences {
    private val prefutils by lazy {
        PreferenceUtils(
            context.getSharedPreferences(
               "com.swayy.mpesa",
                Context.MODE_PRIVATE
            )
        )
    }

    override var currentVersion: Int
        get() = prefutils.get(CURRENT_VERSION, 1)
        set(value) {
            prefutils.set(CURRENT_VERSION, value)
        }

    override var latestVersion: Int
        get() = prefutils.get(LATEST_VERSION, 1)
        set(value) {
            prefutils.set(LATEST_VERSION, value)
        }

    override var skipUpdateVer: Int
        get() = prefutils.get(SKIP_UPDATE_VER, 1)
        set(value) {
            prefutils.set(SKIP_UPDATE_VER, value)
        }

    override var themePreference: ThemePreference
        get() = ThemePreference.valueOf(prefutils.get(THEME_PREFERENCE, ThemePreference.DEFAULT.name))
        set(value) {
            prefutils.set(THEME_PREFERENCE, value.name)
        }

    override var hasSeenExportPrompt: Boolean
        get() = prefutils.get(HAS_SEEN_PREFERENCE_PROMPT, false)
        set(value) {
            prefutils.set(HAS_SEEN_PREFERENCE_PROMPT, value)
        }

    override var hasSeenFilterPrompt: Boolean
        get() = prefutils.get(HAS_SEEN_FILTER_PROMPT, false)
        set(value) {
            prefutils.set(HAS_SEEN_FILTER_PROMPT, value)
        }

    companion object {
        private const val CURRENT_VERSION = "current_version"
        private const val LATEST_VERSION = "latest_version"
        private const val SKIP_UPDATE_VER = "skip_update_version"
        private const val THEME_PREFERENCE = "theme_preference"
        private const val HAS_SEEN_PREFERENCE_PROMPT = "has_seen_preference_prompt"
        private const val HAS_SEEN_FILTER_PROMPT = "has_seen_filter_prompt"
    }
}