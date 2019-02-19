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

package com.github.stulzm2.selfcare.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.stulzm2.selfcare.R;
import com.github.stulzm2.selfcare.settings.DefaultSettings;

public class BaseActivity extends AppCompatActivity {

    private String mCurrentTheme;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCurrentTheme = DefaultSettings.getListPreferenceValue(this);
        setAppTheme(mCurrentTheme);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String theme = DefaultSettings.getListPreferenceValue(this);

        if (!mCurrentTheme.equals(theme)) {
            recreate();
        }
    }

    private void setAppTheme(String currentTheme) {
        if (currentTheme.equals("White")) {
            setTheme(R.style.AppTheme);
        } else if (currentTheme.equals("Green")) {
            setTheme(R.style.Theme_App_Green);
        } else if (currentTheme.equals("Blue")) {
            setTheme(R.style.Theme_App_Blue);
        } else {
            setTheme(R.style.Theme_App_Yellow);
        }
    }
}
