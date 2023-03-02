package com.khiladiadda.gameleague;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.gameleague.adapter.TournamentMoreGameAdapter;
import com.khiladiadda.gameleague.adapter.TrendingGameAdapter;
import com.khiladiadda.gameleague.interfaces.IOnGamesClickListener;
import com.khiladiadda.gameleague.interfaces.ITrendingTournamentPresenter;
import com.khiladiadda.gameleague.interfaces.ITrendingTournamentView;
import com.khiladiadda.gameleague.ip.TrendingTournamentPresenter;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.droid_doresponse.MyTournamentResponse;
import com.khiladiadda.network.model.response.droid_doresponse.TournamentTrendingList;
import com.khiladiadda.network.model.response.droid_doresponse.TrendingTournamentResponse;
import com.khiladiadda.utility.NetworkStatus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TrendingTournamentActivity {
}