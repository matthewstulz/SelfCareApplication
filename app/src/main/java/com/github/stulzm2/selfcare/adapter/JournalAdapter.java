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

package com.github.stulzm2.selfcare.adapter;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.stulzm2.selfcare.database.DateConverter;
import com.github.stulzm2.selfcare.R;
import com.github.stulzm2.selfcare.model.Journal;

public class JournalAdapter extends ListAdapter<Journal, JournalAdapter.JournalViewHolder> {

    private OnItemClickListener mListener;

    public JournalAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Journal> DIFF_CALLBACK = new DiffUtil.ItemCallback<Journal>() {
        @Override
        public boolean areItemsTheSame(@NonNull Journal journal, @NonNull Journal t1) {
            return journal.getId() == t1.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Journal journal, @NonNull Journal t1) {
            return journal.getEntry().equals(t1.getEntry()) && journal.getDate().equals(t1.getDate());
        }
    };

    @NonNull
    @Override
    public JournalViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_view_journal_item, viewGroup, false);
        return new JournalViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull JournalViewHolder journalViewHolder, int i) {
        Journal currentJournal = getItem(i);
        journalViewHolder.textViewJournalEntry.setText(currentJournal.getEntry());
        journalViewHolder.textViewJournalDate.setText(DateConverter.getDateFormat()
                .format(currentJournal.getDate()));
    }

    public Journal getJournalAt(int i) {
        return getItem(i);
    }

    class JournalViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewJournalEntry;
        private TextView textViewJournalDate;

        public JournalViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewJournalEntry = itemView.findViewById(R.id.text_view_journal_entry);
            textViewJournalDate = itemView.findViewById(R.id.text_view_journal_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (mListener != null && position != RecyclerView.NO_POSITION) {
                        mListener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Journal journal);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

}
