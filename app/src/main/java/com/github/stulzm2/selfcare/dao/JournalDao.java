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

package com.github.stulzm2.selfcare.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.github.stulzm2.selfcare.model.Journal;

import java.util.List;

@Dao
public interface JournalDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Journal journal);

    @Delete
    void deleteJournal(Journal journal);

    @Update
    void update(Journal... journal);

    @Query("Delete FROM journal_table")
    void deleteAllJournals();

    @Query("SELECT * from journal_table LIMIT 1")
    Journal[] getFirstJournalEntry();

    @Query("SELECT * from journal_table")
    LiveData<List<Journal>> getAllJournals();
}
