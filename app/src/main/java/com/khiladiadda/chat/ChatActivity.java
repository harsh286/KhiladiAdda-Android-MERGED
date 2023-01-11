package com.khiladiadda.chat;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.chat.adapters.ChatAdapter;
import com.khiladiadda.chat.interfaces.IChatPresenter;
import com.khiladiadda.chat.interfaces.IChatView;
import com.khiladiadda.chat.model.ChatMessage;
import com.khiladiadda.network.model.response.ProfileResponse;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.NotifyResponse;
import com.khiladiadda.utility.AppConstant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ChatActivity extends BaseActivity implements IChatView {

    @BindView(R.id.iv_back) ImageView mBackIV;
    @BindView(R.id.tv_activity_name) TextView mActivityNameTV;
    @BindView(R.id.et_message) EditText mMessageET;
    @BindView(R.id.ib_send) ImageButton mSendIB;
    @BindView(R.id.rv_chat) RecyclerView mChatRV;
    @BindView(R.id.iv_profile) ImageView mProfileIV;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mFirebaseDatabaseReference;
    private IChatPresenter mPresenter;
    private String mLudoContestId, mContestCode;
    private ChatAdapter mChatAdapter;
    private List<ChatMessage> mChatMessageList;
    private NotifyResponse mTokenList = null;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(true);
        mChatRV.setLayoutManager(mLinearLayoutManager);
    }

    @Override protected int getContentView() {
        return R.layout.activity_chat;
    }

    @Override protected void initViews() {
        mBackIV.setOnClickListener(this);
        mSendIB.setOnClickListener(this);
        mLudoContestId = getIntent().getStringExtra(AppConstant.ID);
        mContestCode = getIntent().getStringExtra(AppConstant.ROOM_ID);
        mActivityNameTV.setText(getString(R.string.text_chat) + " - " + getIntent().getStringExtra(AppConstant.USER_ID));
        if (!TextUtils.isEmpty(getIntent().getStringExtra(AppConstant.IMAGE_PATH))) {
            Glide.with(this).load(getIntent().getStringExtra(AppConstant.IMAGE_PATH)).placeholder(R.drawable.profile).into(mProfileIV);
        }
    }

    @Override protected void initVariables() {
        mPresenter = new ChatPresenter(this);
        getFirebaseAuth();
        if (mFirebaseUser != null) {
            updateUI(false);
        } else {
            showProgress(getString(R.string.text_waiting_progress));
            signInOnFirebaseUsingEmailPwd();
        }
    }

    private void initChat() {
        mChatMessageList = new ArrayList<>();
        mChatAdapter = new ChatAdapter(mChatMessageList, mAppPreference.getString(AppConstant.USER_ID, ""));
        mChatRV.setAdapter(mChatAdapter);
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ib_send:
                if (TextUtils.isEmpty(mMessageET.getText().toString())) {
                    Snackbar.make(mSendIB, R.string.text_message_empty, Snackbar.LENGTH_SHORT).show();
                } else if (mFirebaseUser != null) {
                    ChatMessage message = new ChatMessage();
                    message.setId(mAppPreference.getString(AppConstant.USER_ID, ""));
                    message.setText(mMessageET.getText().toString());
                    message.setPhotoUrl(getIntent().getStringExtra(AppConstant.IMAGE_PATH));
                    message.setSenderName(getIntent().getStringExtra(AppConstant.USER_ID));
                    message.setFcmToken(mTokenList.getResponse());
                    mFirebaseDatabaseReference.child("Rooms").child(mContestCode).child("messages").push().setValue(message);
                    mMessageET.setText("");
                }
                break;
        }
    }

    @Override public void onUpdateChatIdSuccess(ProfileResponse responseModel) {
        hideProgress();
        mAppPreference.setBoolean(AppConstant.CHAT_ID, true);
        updateUI(true);
    }

    @Override public void onUpdateChatIdFailure(ApiError error) {
        hideProgress();
        mAppPreference.setBoolean(AppConstant.CHAT_ID, false);
        updateUI(true);
    }

    @Override public void onGetOpponentTokenSuccess(NotifyResponse response) {
        hideProgress();
        mTokenList = response;
    }

    @Override public void onGetOpponentTokenFailure(ApiError error) {
        hideProgress();
    }

    //5.Check Firebase Id
    private void getFirebaseAuth() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
    }

    private void updateUI(boolean loggedIn) {
        getToken();
        if (loggedIn) {
            getFirebaseAuth();
        }
        initChat();
        if (mFirebaseUser != null) {
            if (mFirebaseUser.getPhotoUrl() != null) {
                Glide.with(this).load(mFirebaseUser.getPhotoUrl().toString()).diskCacheStrategy(DiskCacheStrategy.ALL).into(mProfileIV);
            }
            if (mAppPreference.getBoolean(AppConstant.CHAT_ID, false)) {
                readMessages();
            } else {
                showProgress(getString(R.string.text_waiting_progress));
                updateChatId(mFirebaseUser.getUid());
            }
        }
    }

    private void getToken() {
        if (mTokenList == null) {
            showProgress(getString(R.string.text_waiting_progress));
            mPresenter.getOpponentTokens(mLudoContestId);
        }
    }

    private void readMessages() {
        DatabaseReference child = mFirebaseDatabaseReference.child("Rooms").child(mContestCode).child("messages");

        child.addValueEventListener(new ValueEventListener() {
            @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mChatMessageList.clear();
                ChatMessage chatMessage;
                for (DataSnapshot chatMessageSnapshot : dataSnapshot.getChildren()) {
                    chatMessage = chatMessageSnapshot.getValue(ChatMessage.class);
                    mChatMessageList.add(chatMessage);
                }
                mChatAdapter.notifyDataSetChanged();
                mChatRV.scrollToPosition(mChatMessageList.size() - 1);
            }

            @Override public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void updateChatId(String firebaseId) {
        mPresenter.updateChatId(firebaseId);
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    private void signInOnFirebaseUsingEmailPwd() {
        mFirebaseAuth.signInWithEmailAndPassword(AppConstant.FIREBASE_CHAT_EMAIL, AppConstant.FIREBASE_CHAT_PWD).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (!TextUtils.isEmpty(mFirebaseUser.getUid())) {
                    updateChatId(mFirebaseUser.getUid());
                }
            } else {
                Toast.makeText(ChatActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}