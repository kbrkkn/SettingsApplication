package com.example.kubra.settingsapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener
{
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_settings);

        SharedPreferences sharedPreferences=getPreferenceScreen().getSharedPreferences();
        PreferenceScreen preferenceScreen=getPreferenceScreen();
        int count=preferenceScreen.getPreferenceCount();
        for(int i=0;i<count;i++){
            Preference preference=preferenceScreen.getPreference(i);
            if(!(preference instanceof CheckBoxPreference)){
                String prefKey=preference.getKey();
                String summary=sharedPreferences.getString(prefKey,"");
                setPreferenceSummary(preference,summary);
            }
        }

    }

    private void setPreferenceSummary(Preference preference, String summary) {
        if(preference instanceof ListPreference){
            ListPreference listPreference= (ListPreference) preference;
            int preferenceIndex=listPreference.findIndexOfValue(summary);
            if(preferenceIndex>=0){
                listPreference.setSummary(listPreference.getEntries()[preferenceIndex]);
            }
        }
        else if(preference instanceof EditTextPreference){
            preference.setSummary(summary);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference=findPreference(key);
        if(preference!=null){
            if(!(preference instanceof CheckBoxPreference)){
                String summary=sharedPreferences.getString(preference.getKey(),"");
                setPreferenceSummary(preference,summary);
            }}
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
