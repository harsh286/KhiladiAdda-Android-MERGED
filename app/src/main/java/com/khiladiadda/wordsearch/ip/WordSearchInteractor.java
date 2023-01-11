package com.khiladiadda.wordsearch.ip;

import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;
import com.khiladiadda.network.model.response.WordSearchCategoriesQuizzesMainResponse;
import com.khiladiadda.network.model.response.WordSearchLeaderBoardMainResponse;
import com.khiladiadda.network.model.response.WordSearchLiveLeaderBoardMainResponse;
import com.khiladiadda.network.model.response.WordSearchMyQuizzesMainResponse;
import com.khiladiadda.network.model.response.WordSearchStartMainResponse;
import com.khiladiadda.network.model.response.WordSearchTrendingMainResponse;
import com.khiladiadda.network.model.response.wordsearch_new.WordSearchCategoryMainResponse;

import rx.Subscription;

public class WordSearchInteractor {

    public Subscription getTrending(IApiListener<WordSearchTrendingMainResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getAllQuizzes()).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription getCategoryQuiz(IApiListener<WordSearchCategoriesQuizzesMainResponse> listener, String quizId) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getCategoryQuizzes(quizId)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription getQuizParticipants(IApiListener<WordSearchLeaderBoardMainResponse> listener, String quizId) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getQuizParticipants(quizId)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription getStartQuiz(IApiListener<WordSearchStartMainResponse> listener, String quizId) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getStartQuiz(quizId)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription getMyQuizzes(IApiListener<WordSearchMyQuizzesMainResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getMyQuizzesQuiz()).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription getLiveLeaderBoard(IApiListener<WordSearchLiveLeaderBoardMainResponse> listener, String quizId) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getLiveLeaderBoard(quizId)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription getTournamentCategories(IApiListener<WordSearchCategoryMainResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getWordSearchCategories()).subscribe(new SubscriberCallback<>(listener));
    }
}
