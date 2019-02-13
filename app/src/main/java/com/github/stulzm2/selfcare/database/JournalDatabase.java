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

package com.github.stulzm2.selfcare.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.github.stulzm2.selfcare.dao.JournalDao;
import com.github.stulzm2.selfcare.model.Journal;

import java.util.Calendar;
import java.util.Date;

@Database(entities = { Journal.class }, version = 1, exportSchema = false)
public abstract class JournalDatabase extends RoomDatabase {

    private static JournalDatabase instance;
    public abstract JournalDao journalDao();

    public static synchronized JournalDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), JournalDatabase.class,
                    "journal_database").fallbackToDestructiveMigration()
                    .addCallback(roomCallBack).build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private JournalDao journalDao;
        private String introduction = "Welcome to the Self Care Journal. Simply swipe left or" +
                " right on an entry to delete. Click on an entry to view or edit the post. All " +
                "entries are stored locally on your phone so everything is safe and sound.";

        private PopulateDbAsyncTask(JournalDatabase db) {
            journalDao = db.journalDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (journalDao.getFirstJournalEntry().length < 1) {
                Date date = Calendar.getInstance().getTime();
                Journal journal = new Journal(introduction, date);
                journalDao.insert(journal);
            }
            return null;
        }
    }
}
