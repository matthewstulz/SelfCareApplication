/*
Copyright 2019 Matthew Stulz

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is
 distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 implied. See the License for the specific language governing permissions and limitations under the
 License.
 */

package com.github.stulzm2.selfcare.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.github.stulzm2.selfcare.R;

import java.util.Objects;

public class SettingsHolder extends PreferenceFragmentCompat {

    private Preference.OnPreferenceChangeListener mListener = new
            Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object o) {
                    //String listValue = (String) o;
                    //if (preference.getKey().equals("listPreferenceTheme") && listValue.equals("White")) {
                    //SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
                    //mPrefs.edit().putString("listPreferenceTheme", "White").apply();
                    Intent intent = new Intent(getContext(), SettingsActivity.class);
                    Objects.requireNonNull(getActivity()).finish();
                    startActivity(intent);
                    //}
                    return true;
                }
            };

    @Override
    public void onCreatePreferences(Bundle bundle, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        final ListPreference listPreferenceTheme = (ListPreference)
                findPreference("listPreferenceTheme");
        listPreferenceTheme.setOnPreferenceChangeListener(mListener);
    }
}