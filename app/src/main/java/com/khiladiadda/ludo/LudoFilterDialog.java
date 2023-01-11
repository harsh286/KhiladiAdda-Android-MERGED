package com.khiladiadda.ludo;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.khiladiadda.R;
import com.khiladiadda.dialogs.interfaces.IOnFilterChallengeListener;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LudoFilterDialog extends Dialog implements View.OnClickListener {

    @BindView(R.id.tv_fifty)
    TextView mFiftyTV;
    @BindView(R.id.tv_hundred)
    TextView mHundredTV;
    @BindView(R.id.tv_two_hundred)
    TextView mTwoHundredTV;
    @BindView(R.id.tv_five_hundred)
    TextView mFiveHundredTV;
    @BindView(R.id.tv_lowest)
    TextView mLowestTV;
    @BindView(R.id.tv_highest)
    TextView mHighestTV;
    @BindView(R.id.btn_set)
    Button mConfirmBTN;
    @BindView(R.id.btn_cancel)
    Button mCancelBTN;

    private final IOnFilterChallengeListener onFilterChallengeListener;
    private final Context mContext;
    private int mFilters = 0, mFrom;

    public LudoFilterDialog(@NonNull Context context, IOnFilterChallengeListener onFilterChallengeListener, int from) {
        super(context);
        this.mContext = context;
        this.onFilterChallengeListener = onFilterChallengeListener;
        this.mFrom = from;
        init();
    }

    private void init() {
        initDialog();
        initViews();
        show();
    }

    private void initDialog() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (mFrom == AppConstant.FROM_LOST) {
            setContentView(R.layout.dialog_filter_ludo_universe);
        } else {
            setContentView(R.layout.dialog_filter_challange);
        }
        getWindow().setBackgroundDrawableResource(R.color.colorTransparent);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    private void initViews() {
        ButterKnife.bind(this);
        mConfirmBTN.setOnClickListener(this);
        mCancelBTN.setOnClickListener(this);
        mFiftyTV.setOnClickListener(this);
        mHundredTV.setOnClickListener(this);
        mTwoHundredTV.setOnClickListener(this);
        mFiveHundredTV.setOnClickListener(this);
        mLowestTV.setOnClickListener(this);
        mHighestTV.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                cancel();
                break;
            case R.id.btn_set:
                if (mFilters == 0) {
                    AppUtilityMethods.showMsg(mContext, "Please select atleast on filter", false);
                } else {
                    onFilterChallengeListener.onFilterChallengeListener(mFilters);
                    cancel();
                }
                break;
            case R.id.tv_fifty:
                setFilter(1);
                break;
            case R.id.tv_hundred:
                setFilter(2);
                break;
            case R.id.tv_two_hundred:
                setFilter(3);
                break;
            case R.id.tv_five_hundred:
                setFilter(4);
                break;
            case R.id.tv_lowest:
                setFilter(5);
                break;
            case R.id.tv_highest:
                setFilter(6);
                break;
        }
    }

    private void setFilter(int filter) {
        mFilters = filter;
        mFiftyTV.setSelected(false);
        mHundredTV.setSelected(false);
        mTwoHundredTV.setSelected(false);
        mFiveHundredTV.setSelected(false);
        mLowestTV.setSelected(false);
        mHighestTV.setSelected(false);
        if (filter == 1) {
            mFiftyTV.setSelected(true);
        } else if (filter == 2) {
            mHundredTV.setSelected(true);
        } else if (filter == 3) {
            mTwoHundredTV.setSelected(true);
        } else if (filter == 4) {
            mFiveHundredTV.setSelected(true);
        } else if (filter == 5) {
            mLowestTV.setSelected(true);
        } else if (filter == 6) {
            mHighestTV.setSelected(true);
        }
    }

}