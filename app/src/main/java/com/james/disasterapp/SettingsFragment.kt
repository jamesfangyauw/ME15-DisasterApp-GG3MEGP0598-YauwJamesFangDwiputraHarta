package com.james.disasterapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference

class SettingsFragment : PreferenceFragmentCompat() {


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        val preferencesTheme = findPreference<ListPreference>(getString(R.string.pref_key_dark))
        preferencesTheme?.setOnPreferenceChangeListener { _, newValue ->
            if (newValue == "off") {
                updateTheme(AppCompatDelegate.MODE_NIGHT_NO)
            } else if (newValue == "on") {
                updateTheme(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                updateTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }
        val notifPreferences = findPreference<SwitchPreference>(getString(R.string.pref_key_notify))
        val reminderDaily = DailyAlert()
        notifPreferences?.setOnPreferenceChangeListener { _, newValue ->
            when (newValue){
                true -> reminderDaily.setDailyReminder(requireContext())
                else -> reminderDaily.cancelAlarm(requireContext())
            }
            true
        }

    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }
}