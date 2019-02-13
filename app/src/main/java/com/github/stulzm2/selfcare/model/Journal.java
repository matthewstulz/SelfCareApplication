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

package com.github.stulzm2.selfcare.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.github.stulzm2.selfcare.database.DateConverter;

import java.util.Date;

@Entity(tableName = "journal_table")
public class Journal {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String entry;
    @TypeConverters(DateConverter.class)
    private Date date;

    public Journal(String entry, Date date) {
        this.entry = entry;
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getEntry() {
        return entry;
    }

    public Date getDate() {
        return date;
    }
}
