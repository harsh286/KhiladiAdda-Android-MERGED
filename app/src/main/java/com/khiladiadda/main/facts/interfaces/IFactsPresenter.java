package com.khiladiadda.main.facts.interfaces;

import com.khiladiadda.base.interfaces.IBasePresenter;

public interface IFactsPresenter extends IBasePresenter {

    void getTrendingFacts();

    void getFacts();

    void getFactDetails(String id);

    void likeFact(String id);

    void bookmarkFact(String id);

    void getFactsFromDB();

}