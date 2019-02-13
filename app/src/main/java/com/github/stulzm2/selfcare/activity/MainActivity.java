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

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.stulzm2.selfcare.R;
import com.github.stulzm2.selfcare.adapter.CategoryAdapter;
import com.github.stulzm2.selfcare.model.Category;
import com.github.stulzm2.selfcare.viewmodel.CategoryViewModel;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private CategoryViewModel mCategoryViewModel;
    private CategoryAdapter mAdapter;
    private RecyclerView mRecyclerView;

    private static Bundle mBundleRecyclerViewState;
    private final String KEY_RECYCLER_STATE = "com.github.stulzm2.selfcare.KEY_RECYCLER_STATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        initRecyclerView();

        mAdapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Category category) {
                Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
                intent.putExtra(CategoryActivity.EXTRA_CATEGORY_TITLE, category.getCategoryTitle());
                intent.putExtra(CategoryActivity.EXTRA_CATEGORY_STRING, mCategoryViewModel.getCategoryWebView(category));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBundleRecyclerViewState = new Bundle();
        Parcelable listState = Objects.requireNonNull(mRecyclerView.getLayoutManager()).onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mBundleRecyclerViewState != null) {
            Parcelable listState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            Objects.requireNonNull(mRecyclerView.getLayoutManager()).onRestoreInstanceState(listState);
        }
    }

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.recycler_view_category);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new CategoryAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mCategoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        mAdapter.setCategories(mCategoryViewModel.getCategories());
    }

    private void onJournalSelected() {
        Intent intent = new Intent(this, JournalActivity.class);
        startActivity(intent);
    }

    private void onResourceSelected() {
        DialogFragment dialogFragment = ResourceDialog.newInstance();
        dialogFragment.show(getSupportFragmentManager(), ResourceDialog.TAG);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_journal:
                onJournalSelected();
                return true;
            case R.id.action_resources:
                onResourceSelected();
                return true;
            case R.id.action_settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
