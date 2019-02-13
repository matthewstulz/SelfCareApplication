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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;

import com.github.stulzm2.selfcare.R;

public class ResourceDialog extends DialogFragment implements View.OnClickListener {

    private WebView mWebView;

    public static String TAG = "com.github.stulzm2.selfcare.FULLSCREEN_DIALOG";
    static ResourceDialog newInstance() { return new ResourceDialog(); }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullscreenDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resource, container, false);
        ImageButton close = view.findViewById(R.id.fullscreen_dialog_close);
        mWebView = view.findViewById(R.id.webView_resource);
        setUpWebView(getString(R.string.resources_data));

        close.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.fullscreen_dialog_close) {
            dismiss();
        }
    }

    private void setUpWebView(String res) {
        mWebView.loadDataWithBaseURL(null, res, "text/html", "utf-8", null);
    }
}
