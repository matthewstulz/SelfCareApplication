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
import android.support.annotation.NonNull;

import com.github.stulzm2.selfcare.R;
import com.github.stulzm2.selfcare.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryViewModel extends AndroidViewModel {

    private List<Category> mCategoryList;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
    }

    public List<Category> getCategories() {
        mCategoryList = new ArrayList<>();
        mCategoryList.add(new Category("Meditation", R.drawable.meditation));
        mCategoryList.add(new Category("Journal", R.drawable.journal));
        mCategoryList.add(new Category("Love", R.drawable.love));
        mCategoryList.add(new Category("Music", R.drawable.music));
        mCategoryList.add(new Category("Nature", R.drawable.nature));
        mCategoryList.add(new Category("Diet", R.drawable.plant));
        mCategoryList.add(new Category("Travel", R.drawable.travel));
        mCategoryList.add(new Category("Crystals", R.drawable.crystals));
        mCategoryList.add(new Category("Oils", R.drawable.essentialoils));
        mCategoryList.add(new Category("Chakras", R.drawable.chakras));
        return mCategoryList;
    }

    public String getCategoryWebView(Category category) {

        switch (category.getCategoryTitle()) {
            case "Meditation":
                return getApplication().getResources().getString(R.string.meditation_data);
            case "Journal":
                return getApplication().getResources().getString(R.string.journal_data);
            case "Love":
                return getApplication().getResources().getString(R.string.love_data);
            case "Music":
                return getApplication().getResources().getString(R.string.music_data);
            case "Nature":
                return getApplication().getResources().getString(R.string.nature_data);
            case "Diet":
                return getApplication().getResources().getString(R.string.diet_data);
            case "Travel":
                return getApplication().getResources().getString(R.string.travel_data);
            case "Crystals":
                return getApplication().getResources().getString(R.string.crystals_data);
            case "Oils":
                return getApplication().getResources().getString(R.string.oils_data);
            case "Chakras":
                return getApplication().getResources().getString(R.string.chakras_data);
            default:
                return "";
        }
    }
}
