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

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

public class DefaultSettings {
    private static SharedPreferences sharedPreferences;

    private static void getSharedPreferences(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static String getListPreferenceValue(Context context) {
        getSharedPreferences(context);
        return sharedPreferences.getString("listPreferenceTheme", "White");
    }
}