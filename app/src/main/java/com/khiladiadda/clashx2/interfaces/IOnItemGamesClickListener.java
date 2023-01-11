package com.khiladiadda.clashx2.interfaces;

import android.view.View;


public interface IOnItemGamesClickListener {
    //method called when an item of the recycler view is clicked
    public void onGamesItemClick(View view, int position, int tag);

}