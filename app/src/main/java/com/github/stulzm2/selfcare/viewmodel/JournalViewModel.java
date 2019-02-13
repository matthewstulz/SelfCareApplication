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

package com.github.stulzm2.selfcare.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.github.stulzm2.selfcare.model.Journal;
import com.github.stulzm2.selfcare.repository.JournalRepository;

import java.util.List;

public class JournalViewModel extends AndroidViewModel {

    private JournalRepository mRepository;
    private LiveData<List<Journal>> mAllJournals;

    public JournalViewModel(@NonNull Application application) {
        super(application);
        mRepository = new JournalRepository(application);
        mAllJournals = mRepository.getAllJournals();
    }

    public LiveData<List<Journal>> getAllJournals() { return mAllJournals; }
    public void insert(Journal journal) { mRepository.insert(journal); }
    public void update(Journal journal) { mRepository.update(journal); }
    public void deleteJournal(Journal journal) { mRepository.deleteJournal(journal); }
    public void deleteAllJournals() { mRepository.deleteAllJournals(); }
}
