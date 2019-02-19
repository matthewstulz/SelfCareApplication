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

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.stulzm2.selfcare.R;
import com.github.stulzm2.selfcare.adapter.JournalAdapter;
import com.github.stulzm2.selfcare.model.Journal;
import com.github.stulzm2.selfcare.settings.SettingsActivity;
import com.github.stulzm2.selfcare.viewmodel.JournalViewModel;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class JournalActivity extends BaseActivity {

    public static final int ADD_JOURNAL_REQUEST = 1;
    public static final int EDIT_JOURNAL_REQUEST = 2;
    private JournalViewModel mJournalViewModel;
    private JournalAdapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);

        Toolbar toolbar = findViewById(R.id.toolbar_journal);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initRecyclerView();

        mJournalViewModel = ViewModelProviders.of(this).get(JournalViewModel.class);
        mJournalViewModel.getAllJournals().observe(this, new Observer<List<Journal>>() {
            @Override
            public void onChanged(@Nullable List<Journal> journals) {
                mAdapter.submitList(journals);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                mJournalViewModel.deleteJournal(mAdapter.getJournalAt(viewHolder.getAdapterPosition()));
                Toast.makeText(JournalActivity.this, R.string.journal_deleted, Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(mRecyclerView);

        mAdapter.setOnItemClickListener(new JournalAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Journal journal) {
                launchUpdateJournalActivity(journal);
            }
        });
    }

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.recycler_view_journal);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new JournalAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        Date date = Calendar.getInstance().getTime();
//        if (requestCode == ADD_JOURNAL_REQUEST && resultCode == RESULT_OK) {
//            String entry = data.getStringExtra(AddEditJournalActivity.EXTRA_JOURNAL_ENTRY);
//            Journal journal = new Journal(entry, date);
//            mJournalViewModel.insert(journal);
//            Toast.makeText(this, "Journal entry saved", Toast.LENGTH_SHORT).show();
//        } else if (requestCode == EDIT_JOURNAL_REQUEST && resultCode == RESULT_OK) {
//            int id = data.getIntExtra(AddEditJournalActivity.EXTRA_JOURNAL_ID, -1);
//
//            if (id == -1) {
//                Toast.makeText(this, "Journal can't be updated", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            String entry = data.getStringExtra(AddEditJournalActivity.EXTRA_JOURNAL_ENTRY);
//            Journal journal = new Journal(entry, date);
//            journal.setId(id);
//            mJournalViewModel.update(journal);
//            Toast.makeText(this, "Journal updated", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "Journal wasn't saved", Toast.LENGTH_SHORT).show();
//        }

        Date date = Calendar.getInstance().getTime();
        if (requestCode == ADD_JOURNAL_REQUEST && resultCode == RESULT_OK) {
            Journal journal = new Journal((data.getStringExtra
                    (AddEditJournalActivity.EXTRA_JOURNAL_ENTRY)), date);
            mJournalViewModel.insert(journal);
            Toast.makeText(this, R.string.journal_success, Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_JOURNAL_REQUEST && resultCode == RESULT_OK) {
            String entry = data.getStringExtra(AddEditJournalActivity.EXTRA_JOURNAL_ENTRY);
            int id = data.getIntExtra(AddEditJournalActivity.EXTRA_JOURNAL_ID, -1);

            if (id != -1) {
                Journal journal = new Journal(entry, date);
                journal.setId(id);
                mJournalViewModel.update(journal);
                Toast.makeText(this, R.string.journal_updated, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, R.string.journal_not_saved, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void launchUpdateJournalActivity(Journal journal) {
        Intent intent = new Intent(JournalActivity.this, AddEditJournalActivity.class);
        intent.putExtra(AddEditJournalActivity.EXTRA_JOURNAL_ID, journal.getId());
        intent.putExtra(AddEditJournalActivity.EXTRA_JOURNAL_ENTRY, journal.getEntry());
        startActivityForResult(intent, EDIT_JOURNAL_REQUEST);
    }

    private void onNewJournalSelected() {
        Intent intent = new Intent(this, AddEditJournalActivity.class);
        startActivityForResult(intent, ADD_JOURNAL_REQUEST);
    }

    private void onDeleteAllJournalsSelected() {
        mJournalViewModel.deleteAllJournals();
        Toast.makeText(this, R.string.deleting_all_journals, Toast.LENGTH_SHORT).show();
    }

    private void onResourceSelected() {
        DialogFragment dialogFragment = ResourceDialog.newInstance();
        dialogFragment.show(getSupportFragmentManager(), ResourceDialog.TAG);
    }

    private void onSettingsSelected() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.journal_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new_journal:
                onNewJournalSelected();
                return true;
            case R.id.action_clear_data:
                onDeleteAllJournalsSelected();
                return true;
            case R.id.action_resources:
                onResourceSelected();
                return true;
            case R.id.action_settings:
                onSettingsSelected();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
