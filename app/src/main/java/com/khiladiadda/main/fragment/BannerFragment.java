package com.khiladiadda.main.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseFragment;
import com.khiladiadda.help.HelpActivity;
import com.khiladiadda.leaderboard.NewLeaderboardActivity;
import com.khiladiadda.leaderboard.NewLeaderboardActivityTest;
import com.khiladiadda.league.LeagueActivity;
import com.khiladiadda.ludo.LudoChallengeActivity;
import com.khiladiadda.network.model.response.BannerDetails;
import com.khiladiadda.profile.ProfileActivity;
import com.khiladiadda.profile.StatActivity;
import com.khiladiadda.referhistory.InviteActivity;
import com.khiladiadda.referhistory.ReferActivity;
import com.khiladiadda.referhistory.ReferHelpActivity;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.wallet.AddWalletActivity;
import com.khiladiadda.wallet.WalletActivity;

import butterknife.BindView;

public class BannerFragment extends BaseFragment {

    @BindView(R.id.iv_advertisement)
    ImageView mAdvertisementIV;

    private BannerDetails mAdvertisement = null;

    public BannerFragment() {
    }

    public static Fragment getInstance(BannerDetails advertisement) {
        BannerFragment fragment = new BannerFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("advertisement", advertisement);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return (R.layout.fragment_banner);
    }

    @Override
    protected void initViews(View view) {
        mAdvertisementIV.setOnClickListener(this);
    }

    @Override
    protected void initBundle(Bundle bundle) {
        mAdvertisement = bundle.getParcelable("advertisement");
    }

    @Override
    protected void initVariables() {
        if (!TextUtils.isEmpty(mAdvertisement.getImg())) {
            Glide.with(mAdvertisementIV.getContext()).load(mAdvertisement.getImg()).transform(new CenterCrop(), new RoundedCorners(20)).into(mAdvertisementIV);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_advertisement) {
            if (mAdvertisement.getTitle().equalsIgnoreCase(AppConstant.LUDO)) {
                Intent ludo = new Intent(getActivity(), LudoChallengeActivity.class);
                ludo.putExtra(AppConstant.CONTEST_TYPE, AppConstant.TYPE_LUDO);
                startActivity(ludo);
            } else if (mAdvertisement.getTitle().equalsIgnoreCase("Ludo_Create")) {
                Intent ludo = new Intent(getActivity(), LudoChallengeActivity.class);
                ludo.putExtra(AppConstant.FROM, AppConstant.FROM_CREATE_LUDO);
                startActivity(ludo);
            } else if (mAdvertisement.getTitle().equalsIgnoreCase(mPreference.getString(AppConstant.PUBG_ID, ""))) {
                Intent pubG = new Intent(getActivity(), LeagueActivity.class);
                pubG.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_TDM);
                startActivity(pubG);
            } else if (mAdvertisement.getTitle().equals("INVITE")) {
                startActivity(new Intent(getActivity(), ReferHelpActivity.class));
            } else if (mAdvertisement.getTitle().equalsIgnoreCase("YOUTUBE")) {
                AppUtilityMethods.openYoutube(getActivity());
            } else if (mAdvertisement.getTitle().equalsIgnoreCase("INSTAGRAM")) {
                AppUtilityMethods.openInstagram(getActivity());
            } else if (mAdvertisement.getTitle().equalsIgnoreCase("FF_SOLO")) {
                Intent ludo = new Intent(getActivity(), LeagueActivity.class);
                ludo.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_FREEFIRE);
                ludo.putExtra(AppConstant.FROM_TYPE, AppConstant.FREEFIRE_SOLO);
                startActivity(ludo);
            } else if (mAdvertisement.getTitle().equalsIgnoreCase("FF_DUO")) {
                Intent ludo = new Intent(getActivity(), LeagueActivity.class);
                ludo.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_FREEFIRE);
                ludo.putExtra(AppConstant.FROM_TYPE, AppConstant.FREEFIRE_DUO);
                startActivity(ludo);
            } else if (mAdvertisement.getTitle().equalsIgnoreCase("FF_SQUAD")) {
                Intent ludo = new Intent(getActivity(), LeagueActivity.class);
                ludo.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_FREEFIRE);
                ludo.putExtra(AppConstant.FROM_TYPE, AppConstant.FREEFIRE_SQUAD);
                startActivity(ludo);
            } else if (mAdvertisement.getTitle().equalsIgnoreCase("FF_CS_SOLO")) {
                Intent ludo = new Intent(getActivity(), LeagueActivity.class);
                ludo.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_FF_CLASH);
                ludo.putExtra(AppConstant.FROM_TYPE, AppConstant.FF_CLASH_SOLO);
                startActivity(ludo);
            } else if (mAdvertisement.getTitle().equalsIgnoreCase("FF_CS_DUO")) {
                Intent ludo = new Intent(getActivity(), LeagueActivity.class);
                ludo.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_FF_CLASH);
                ludo.putExtra(AppConstant.FROM_TYPE, AppConstant.FF_CLASH_DUO);
                startActivity(ludo);
            } else if (mAdvertisement.getTitle().equalsIgnoreCase("FF_CS_SQUAD")) {
                Intent ludo = new Intent(getActivity(), LeagueActivity.class);
                ludo.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_FF_CLASH);
                ludo.putExtra(AppConstant.FROM_TYPE, AppConstant.FF_CLASH_SQUAD);
                startActivity(ludo);
            } else if (mAdvertisement.getTitle().equalsIgnoreCase("Profile")) {
                startActivity(new Intent(getActivity(), ProfileActivity.class));
            } else if (mAdvertisement.getTitle().equalsIgnoreCase("Stat")) {
                startActivity(new Intent(getActivity(), StatActivity.class));
            } else if (mAdvertisement.getTitle().equalsIgnoreCase("Wallet")) {
                startActivity(new Intent(getActivity(), WalletActivity.class));
            } else if (mAdvertisement.getTitle().equalsIgnoreCase("Add_Wallet")) {
                startActivity(new Intent(getActivity(), AddWalletActivity.class));
            } else if (mAdvertisement.getTitle().equalsIgnoreCase("Leaderboard")) {
                startActivity(new Intent(getActivity(), NewLeaderboardActivity.class));
            } else if (mAdvertisement.getTitle().equalsIgnoreCase("Refer")) {
                startActivity(new Intent(getActivity(), ReferActivity.class));
            } else if (mAdvertisement.getTitle().equals("Invite")) {
                startActivity(new Intent(getActivity(), InviteActivity.class));
            } else if (mAdvertisement.getTitle().equalsIgnoreCase("Help")) {
                startActivity(new Intent(getActivity(), HelpActivity.class));
            } else if (mAdvertisement.getTitle().equalsIgnoreCase("LINK")) {
                if (!TextUtils.isEmpty(mAdvertisement.getLink()) && (mAdvertisement.getLink().startsWith("http") || mAdvertisement.getLink().startsWith("https"))) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mAdvertisement.getLink()));
                    startActivity(browserIntent);
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}