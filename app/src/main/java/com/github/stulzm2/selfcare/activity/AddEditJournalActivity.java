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
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.github.stulzm2.selfcare.R;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class AddEditJournalActivity extends AppCompatActivity {

    public static final String EXTRA_JOURNAL_ID = "com.github.stulzm2.selfcare.EXTRA_JOURNAL_ID";
    public static final String EXTRA_JOURNAL_ENTRY = "com.github.stulzm2.selfcare.EXTRA_JOURNAL_ENTRY";
    public static final String EXTRA_JOURNAL_DATE = "com.github.stulzm2.selfcare.EXTRA_JOURNAL_DATE";

    private EditText mEditTextJournalEntry;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivty_add_edit_note);

        mToolbar = findViewById(R.id.toolbar_add_edit_journal);
        mEditTextJournalEntry = findViewById(R.id.edit_text_add_edit_journal);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_JOURNAL_ID)) {
            mToolbar.setTitle(R.string.edit_journal);
            mEditTextJournalEntry.setText(intent.getStringExtra(EXTRA_JOURNAL_ENTRY));
        } else {
            mToolbar.setTitle(R.string.new_journal);
        }

        mEditTextJournalEntry.requestFocus();

        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    private void savedJournalEntry() {
        String entry = mEditTextJournalEntry.getText().toString();

        if (entry.trim().isEmpty()) {
            Toast.makeText(this, R.string.journal_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        Date date = Calendar.getInstance().getTime();
        data.putExtra(EXTRA_JOURNAL_ENTRY, entry);
        data.putExtra(EXTRA_JOURNAL_DATE, date);
        int id = getIntent().getIntExtra(EXTRA_JOURNAL_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_JOURNAL_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_edit_journal_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_journal_save:
                savedJournalEntry();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
