package com.khiladiadda.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.khiladiadda.dialogs.AppDialog;
import com.khiladiadda.preference.AppSharedPreference;
import com.khiladiadda.utility.AppUtilityMethods;

import butterknife.ButterKnife;

/**
 * Base fragment for all other fragments
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    protected Activity mActivity;
    private Dialog mDialog;
    protected AppSharedPreference mPreference;
    protected AppUtilityMethods mUtility;

    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContentView(), container, false);
        ButterKnife.bind(this, view);
        initViews(view);
        return view;
    }

    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = getActivity();
        mPreference = AppSharedPreference.getInstance();
        mUtility = new AppUtilityMethods(getContext());
        Bundle bundle = getArguments();
        initBundle(bundle);
        initVariables();
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    @Override public void onAttach(Context context) {
        super.onAttach(context);
    }

    /**
     * Used to show the progress dialog
     */
    protected void showProgress(String message) {
        if (mActivity == null) {
            return;
        }
        hideProgress();
        mDialog = AppDialog.getAppProgressDialog(mActivity, message);
        mDialog.show();
    }

    protected void hideProgress() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    protected abstract int getContentView();

    protected abstract void initViews(View view);

    protected abstract void initBundle(Bundle bundle);

    protected abstract void initVariables();

}