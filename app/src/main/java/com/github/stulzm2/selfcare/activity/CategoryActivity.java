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

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import com.github.stulzm2.selfcare.R;

import java.util.Objects;

public class CategoryActivity extends BaseActivity {

    public static final String EXTRA_CATEGORY_TITLE = "com.github.stulzm2.selfcare.EXTRA_CATEGORY_TITLE";
    public static final String EXTRA_CATEGORY_STRING = "com.github.stulzm2.selfcare.EXTRA_CATEGORY_STRING";
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Toolbar toolbar = findViewById(R.id.toolbar_category);
        mWebView = findViewById(R.id.web_view_category);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_CATEGORY_TITLE)) {
            toolbar.setTitle(intent.getStringExtra(EXTRA_CATEGORY_TITLE));
            setUpWebView(intent.getStringExtra(EXTRA_CATEGORY_STRING));
        }

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    private void setUpWebView(String resource) {
        mWebView.loadDataWithBaseURL(null, resource, "text/html", "utf-8", null);
    }
}
