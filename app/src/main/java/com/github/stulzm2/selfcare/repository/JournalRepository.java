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

package com.github.stulzm2.selfcare.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.github.stulzm2.selfcare.dao.JournalDao;
import com.github.stulzm2.selfcare.database.JournalDatabase;
import com.github.stulzm2.selfcare.model.Journal;

import java.util.List;

public class JournalRepository {

    private JournalDao mJournalDao;
    private LiveData<List<Journal>> mAllJournals;

    public JournalRepository(Application application) {
        JournalDatabase database = JournalDatabase.getInstance(application);
        mJournalDao = database.journalDao();
        mAllJournals = mJournalDao.getAllJournals();
    }

    public LiveData<List<Journal>> getAllJournals() { return mAllJournals; }
    public void insert(Journal journal) { new insertAsyncTask(mJournalDao).execute(journal); }
    public void update(Journal journal) { new updateJournalAsyncTask(mJournalDao).execute(journal); }
    public void deleteJournal(Journal journal) { new deleteJournalAsyncTask(mJournalDao).execute(journal); }
    public void deleteAllJournals() { new deleteAllJournalsAsyncTask(mJournalDao).execute(); }

    private static class insertAsyncTask extends AsyncTask<Journal, Void, Void> {
        private JournalDao mAsyncTaskDao;

        insertAsyncTask(JournalDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Journal... journals) {
            mAsyncTaskDao.insert(journals[0]);
            return null;
        }
    }

    private static class updateJournalAsyncTask extends AsyncTask<Journal, Void, Void> {
        private JournalDao mAsyncTaskDao;

        updateJournalAsyncTask(JournalDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Journal... journals) {
            mAsyncTaskDao.update(journals[0]);
            return null;
        }
    }

    private static class deleteJournalAsyncTask extends AsyncTask<Journal, Void, Void> {
        private JournalDao mAsyncTaskDao;

        deleteJournalAsyncTask(JournalDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Journal... journals) {
            mAsyncTaskDao.deleteJournal(journals[0]);
            return null;
        }
    }

    private static class deleteAllJournalsAsyncTask extends AsyncTask<Void, Void, Void> {
        private JournalDao mAsyncTaskDao;

        deleteAllJournalsAsyncTask(JournalDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAllJournals();
            return null;
        }
    }
}
