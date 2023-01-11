package com.khiladiadda.scratchcard.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.khiladiadda.R;
import com.khiladiadda.scratchcard.ScratchCardActivity;
import com.khiladiadda.scratchcard.interfaces.ScratchCardListerner;

import dev.skymansandy.scratchcardlayout.listener.ScratchListener;
import dev.skymansandy.scratchcardlayout.ui.ScratchCardLayout;

public class ScratchCardDialog extends Dialog {

    TextView mAmount;
    ScratchCardLayout scratchCardLayout;
    private final Context mContext;

    public ScratchCardDialog(@NonNull Context context, Bundle bundle, int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.scratchcard_dialog);
        mContext = context;
        String cardId = bundle.getString("card_id");
        Double amount = bundle.getDouble("amount");
        mAmount = findViewById(R.id.tv_amount);
        ImageView mCloseIV = findViewById(R.id.iv_close);
        scratchCardLayout = findViewById(R.id.sl_scratchCard);
        mAmount.setText(String.format("%.2f", amount) + "\nCoins Won");

        scratchCardLayout.setScratchListener(new ScratchListener() {
            @Override
            public void onScratchStarted() {
            }

            @Override
            public void onScratchProgress(@NonNull ScratchCardLayout scratchCardLayout, int i) {
                scratchCardLayout.setRevealFullAtPercent(30);
            }

            @Override
            public void onScratchComplete() {
                ScratchCardListerner dialogListerner = (ScratchCardListerner) mContext;
                dialogListerner.onscratchcard_id(cardId);
                dismiss();
            }
        });
        mCloseIV.setOnClickListener(v -> dismiss());
    }

}