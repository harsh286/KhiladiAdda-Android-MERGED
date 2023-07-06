package com.khiladiadda.registration;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.dialogs.AppDialog;
import com.khiladiadda.login.LoginActivity;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.otp.OtpActivity;
import com.khiladiadda.registration.interfaces.IRegistrationView;
import com.khiladiadda.terms.TermsActivity;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.LocationCheckUtils;
import com.khiladiadda.utility.NetworkStatus;
import com.khiladiadda.utility.PermissionUtils;

import butterknife.BindView;
public class RegistrationActivity extends BaseActivity implements IRegistrationView {
    @BindView(R.id.et_name)
    EditText mNameET;
    @BindView(R.id.et_mobile)
    EditText mMobileET;
    @BindView(R.id.et_email)
    EditText mEmailET;
    @BindView(R.id.et_reference_code)
    EditText mReferenceET;
    @BindView(R.id.cb_terms)
    CheckBox mTermCB;
    @BindView(R.id.cb_ageterms)
    CheckBox mAgeTermCB;
    @BindView(R.id.btn_next)
    Button mNextBTN;
    @BindView(R.id.tv_terms)
    TextView mTermTV;
//    @BindView(R.id.tv_privacy_policy)
//    TextView mPrivacyPolicyTV;
    @BindView(R.id.ll_need_support)
    LinearLayout mNeedSupportLL;
    @BindView(R.id.tv_user)
    TextView mExistingUserTV;
    @BindView(R.id.rl_email)
    RelativeLayout mEmailRl;
    @BindView(R.id.v_email)
    View mEmailView;

    private RegistrationPresenter mPresenter;
    private String mUsername = "", mMobileno = "", mEmail = "";
    private int RC_SIGN_IN = 101;
    private String mGmailId;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGoogleSignInClient = GoogleSignIn.getClient(this, setUpGoogle());
        signOut();
        revokeAccess();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_new_registration;
    }

    @Override
    protected void initViews() {
        Window window = this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        if (getIntent().getStringExtra("name") != null) {
            mUsername = getIntent().getStringExtra("name");
            mMobileno = getIntent().getStringExtra("mobile_number").substring(3);
            mEmail = getIntent().getStringExtra("email");
        }
        mNextBTN.setOnClickListener(this);
        mTermTV.setOnClickListener(this);
//        mPrivacyPolicyTV.setOnClickListener(this);
        mNeedSupportLL.setOnClickListener(this);
        mEmailRl.setOnClickListener(this);
        mEmailView.setOnClickListener(this);

        mExistingUserTV.setOnClickListener(this);
        if (mAppPreference.getBoolean(AppConstant.IS_LOCATION_ENABLED, false)) {
            if (PermissionUtils.hasGpsPermission(this)) {
                if (!PermissionUtils.hasSMSReadPermission(this)) {
                    Snackbar.make(mNextBTN, R.string.txt_allow_permission, Snackbar.LENGTH_SHORT).show();
                }
            }
            if (!LocationCheckUtils.getInstance().hasLocationPermission()) {
                LocationCheckUtils.getInstance().statusCheck();
            } else {
                LocationCheckUtils.getInstance().requestNewLocationData();
            }
        }
        mNameET.setText(mUsername);
        mMobileET.setText(mMobileno);
        mEmailET.setText(mEmail);
        /*Add terms and condition,privacy policy below method*/
        customTextView(mTermTV);
    }

    @Override
    protected void initVariables() {
        mPresenter = new RegistrationPresenter(this);
        if (mAppPreference.getBoolean(AppConstant.IS_GMAIL_ENABLED, false)) {
            mEmailView.setVisibility(View.VISIBLE);
            mEmailET.setEnabled(false);
        } else {
            mEmailView.setVisibility(View.GONE);
            mEmailET.setEnabled(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                if (mAppPreference.getBoolean(AppConstant.IS_LOCATION_ENABLED, false)) {
                    if (LocationCheckUtils.getInstance().hasLocationPermission()) {
                        LocationCheckUtils.getInstance().requestNewLocationData();
                        mPresenter.validateData();
                    } else {
                        AppDialog.DialogWithLocationCallBack(this, "KhiladiAdda need to access your location.");
                    }
                } else {
                    if (mEmailET.getText().toString().isEmpty()) {
                        Snackbar.make(mEmailET, "Please enter email id", Snackbar.LENGTH_SHORT).show();
                    } else if (!Patterns.EMAIL_ADDRESS.matcher(mEmailET.getText().toString()).matches()) {
                        Snackbar.make(mEmailET, "Email is not valid", Snackbar.LENGTH_SHORT).show();
                    } else {
                        mPresenter.validateData();
                    }
                }
                break;
            case R.id.tv_terms:
//                Uri uri = Uri.parse("https://www.khiladiadda.com/terms-and-condition");
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                startActivity(intent);
                break;
            case R.id.tv_privacy_policy:
                Uri uriPrivacy = Uri.parse("https://www.khiladiadda.com/privacy-policy/");
                Intent intentPrivacy = new Intent(Intent.ACTION_VIEW, uriPrivacy);
                startActivity(intentPrivacy);
                break;
            case R.id.ll_need_support:
                openWhatsappContact();
                break;
            case R.id.tv_user:
                onBackPressed();
                break;
            case R.id.v_email:
                googleSignIn();
                break;
        }
    }

    //get email
    //Google SignIn
    private GoogleSignInOptions setUpGoogle() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.server_client_id)).requestEmail().requestProfile().build();
        return gso;
    }

    private void googleSignIn() {
        Intent signInIntent = GoogleSignIn.getClient(this, setUpGoogle()).getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
//        gmailActivityResultLauncher.launch(signInIntent);
    }

    private void handleGoogleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            mEmailET.setText(account.getEmail());
            mGmailId = account.getId();
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            try {
                // The Task returned from this call is always completed, no need to attach a listener.
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                GoogleSignInAccount account = task.getResult(ApiException.class);
                handleGoogleSignInResult(task);
            } catch (ApiException e) {
                e.printStackTrace();
                Log.e("TAG", "onActivityResult: " + e.getLocalizedMessage());
            }
        }
    }

    @Override
    public String getName() {
        return mNameET.getText().toString().trim();
    }

    @Override
    public String getMobileNumber() {
        return mMobileET.getText().toString().trim();
    }

    @Override
    public String getEmail() {
        return mEmailET.getText().toString().trim();
    }

    @Override
    public String getReferenceNo() {
        return mReferenceET.getText().toString().trim();
    }

    @Override
    public void onValidationComplete() {
        if (mTermCB.isChecked()) {
                if (mAgeTermCB.isChecked()) {
                    if (new NetworkStatus(this).isInternetOn()) {
                        showProgress(getString(R.string.txt_progress_authentication));
                        mPresenter.doRegister(mGmailId);
                    } else {
                        Snackbar.make(mNextBTN, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    Snackbar.make(mNextBTN, R.string.text_select_term_condition, Snackbar.LENGTH_SHORT).show();
                }
           }else {
            Snackbar.make(mNextBTN, R.string.text_select_term_condition, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onValidationFailure(String errorMsg) {
        AppUtilityMethods.showMsg(this, errorMsg, false);
    }

    @Override
    public void onRegisterComplete(BaseResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            Intent otp = new Intent(this, OtpActivity.class);
            otp.putExtra(AppConstant.FROM, AppConstant.FROM_REGISTRATION_OTP);
            otp.putExtra(AppConstant.MobileNumber, mMobileET.getText().toString());
            startActivity(otp);
            finish();
        } else {
            Snackbar.make(mNextBTN, responseModel.getMessage(), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRegisterFailure(ApiError error) {
        hideProgress();
        AppUtilityMethods.showMsg(this, error.getMessage(), false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent login = new Intent(this, LoginActivity.class);
        startActivity(login);
        finish();
    }

    @Override
    protected void onDestroy() {
        mPresenter.destroy();
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == AppConstant.RC_ASK_PERMISSIONS_MSG) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, getString(R.string.txt_read_sms_permission), Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == AppConstant.RC_ASK_PERMISSIONS_GPS) {
            AppDialog.DialogWithLocationCallBack(this, "KhiladiAdda need to access your location.");
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void openWhatsappContact() {
        String url = "https://wa.me/91" + mAppPreference.getSupportNumber() + "" + "?text=Hello%20I%20have%20a%20problem";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }

    private void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }
    private void customTextView(TextView view){
        SpannableStringBuilder spanTxt = new SpannableStringBuilder(
                "Please check ");
        spanTxt.append("Terms & Condition and Legality & Legality & Responsible Gaming ");
        spanTxt.append("&");
        spanTxt.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Uri uri = Uri.parse("https://www.khiladiadda.com/terms-and-condition");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        },spanTxt.length() - "Terms & Condition and Legality & Legality & Responsible Gaming ".length(), spanTxt.length(), 0);
        spanTxt.setSpan(new ForegroundColorSpan(Color.RED), 13, 75, 0);
        spanTxt.append(" Privacy Policy");
        spanTxt.setSpan(new ForegroundColorSpan(Color.WHITE), 76, 77, 0);

        spanTxt.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Uri uriPrivacy = Uri.parse("https://www.khiladiadda.com/privacy-policy/");
                Intent intentPrivacy = new Intent(Intent.ACTION_VIEW, uriPrivacy);
                startActivity(intentPrivacy);
            }
        }, spanTxt.length() - "Privacy Policy".length(), spanTxt.length(), 0);
        spanTxt.append(" to further proceed");
        spanTxt.setSpan(new ForegroundColorSpan(Color.RED), 77, 92, 0);
        view.setMovementMethod(LinkMovementMethod.getInstance());
        view.setText(spanTxt, TextView.BufferType.SPANNABLE);
    }
}