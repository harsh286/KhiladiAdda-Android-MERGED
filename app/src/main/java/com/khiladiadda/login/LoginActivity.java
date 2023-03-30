package com.khiladiadda.login;

import static com.khiladiadda.utility.AppConstant.RC_ASK_PERMISSIONS_GPS;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AppsFlyerLib;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.FirebaseMessaging;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.dialogs.AppDialog;
import com.khiladiadda.dialogs.interfaces.IOnSocialLoginErrorListener;
import com.khiladiadda.forgotpassword.ForgotPasswordActivity;
import com.khiladiadda.login.interfaces.ILoginView;
import com.khiladiadda.login.interfaces.ITrueCallerView;
import com.khiladiadda.main.MainActivity;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.GmailRegisterRequest;
import com.khiladiadda.network.model.response.MasterResponse;
import com.khiladiadda.network.model.response.SocialResponse;
import com.khiladiadda.otp.OtpActivity;
import com.khiladiadda.preference.AppSharedPreference;
import com.khiladiadda.registration.RegistrationActivity;
import com.khiladiadda.socialverify.SocialVerifyActivity;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.LocationCheckUtils;
import com.khiladiadda.utility.NetworkStatus;
import com.truecaller.android.sdk.ITrueCallback;
import com.truecaller.android.sdk.TrueError;
import com.truecaller.android.sdk.TrueProfile;
import com.truecaller.android.sdk.TruecallerSDK;
import com.truecaller.android.sdk.TruecallerSdkScope;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class LoginActivity extends BaseActivity implements ILoginView, ITrueCallerView, LocationCheckUtils.IOnAdressPassed {

    @BindView(R.id.et_mobile)
    EditText mEmailET;
    @BindView(R.id.btn_login)
    Button mLoginBTN;
    @BindView(R.id.tv_signup)
    TextView mSignUpTV;
    @BindView(R.id.iv_fb)
    TextView mFacebookIV;
    @BindView(R.id.iv_google)
    TextView mGoogleIV;
    @BindView(R.id.ll_need_support)
    LinearLayout mNeedSupportLL;
    @BindView(R.id.tv_login_via)
    TextView mLoginViaTV;
    @BindView(R.id.iv_truecaller)
    TextView mTruecaller;
    private static int RC_SIGN_IN = 101;
    private CallbackManager mCallbackManager;
    private LoginPresenter mPresenter;
    private GmailRegisterRequest mGmailRequest;
    private TrueCallerPresenter mPresenterTrueCaller;
    private String mFBToken, mUserName, mMobileNumber, mEmail, socialLogin;
    private boolean isAllowed = true;
    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFirebaseId();
        setUpGoogle();
        setUpFb();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_new_login;
    }

    @Override
    protected void initViews() {
        Window window = this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        mLoginBTN.setOnClickListener(this);
        mFacebookIV.setOnClickListener(this);
        mSignUpTV.setOnClickListener(this);
        mGoogleIV.setOnClickListener(this);
        mNeedSupportLL.setOnClickListener(this);
        mTruecaller.setOnClickListener(this);
        LocationCheckUtils.initialize(this, this, this);
        SpannableString loginviaString = new SpannableString(mLoginViaTV.getText().toString());
        loginviaString.setSpan(new UnderlineSpan(), 0, 12, 0);
        mLoginViaTV.setText(loginviaString);
        SpannableString signupString = new SpannableString(mSignUpTV.getText().toString());
        signupString.setSpan(new UnderlineSpan(), 0, mSignUpTV.length(), 0);
        mSignUpTV.setText(signupString);
        if (mAppPreference.getBoolean(AppConstant.IS_LOCATION_ENABLED, false)) {
            if (!LocationCheckUtils.getInstance().hasLocationPermission()) {
                LocationCheckUtils.getInstance().statusCheck();
            } else {
                LocationCheckUtils.getInstance().requestNewLocationData();
            }
        }
    }

    @Override
    protected void initVariables() {
        mPresenter = new LoginPresenter(this);
        mPresenterTrueCaller = new TrueCallerPresenter(this);
        if (mAppPreference.getIsVersionUpdated()) {
            showVersionDialog();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_forgot_pwd:
                Intent forgotPwd = new Intent(this, ForgotPasswordActivity.class);
                forgotPwd.putExtra(AppConstant.FROM, AppConstant.FROM_FORGOT_PASSWORD);
                startActivity(forgotPwd);
                finish();
                break;
            case R.id.btn_login:
                if (mAppPreference.getBoolean(AppConstant.IS_LOCATION_ENABLED, false)) {
                    if (LocationCheckUtils.getInstance().hasLocationPermission()) {
                        LocationCheckUtils.getInstance().requestNewLocationData();
                        if (isAllowed)
                            mPresenter.validateData();
                        else
                            Snackbar.make(mLoginBTN, R.string.not_allowed, Snackbar.LENGTH_SHORT).show();

                    } else {
                        AppDialog.DialogWithLocationCallBack(this, "KhiladiAdda need to access your location.");
                    }
                } else {
                    mPresenter.validateData();

                }
                break;
            case R.id.iv_fb:
                if (new NetworkStatus(this).isInternetOn()) {
                    if (LocationCheckUtils.getInstance().hasLocationPermission()) {
                        LocationCheckUtils.getInstance().requestNewLocationData();
                        if (isAllowed) {
                            if (mAppPreference.getBoolean(AppConstant.IS_FB_ENABLED, false)) {
                                socialLogin = "facebook";
                                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"));
                            } else {
                                new FBErrorDialog(this, 0, onSocialLoginErrorListener);
                            }
                        } else
                            Snackbar.make(mLoginBTN, R.string.not_allowed, Snackbar.LENGTH_SHORT).show();
                    } else {
                        AppDialog.DialogWithLocationCallBack(this, "KhiladiAdda need to access your location.");
                    }
                } else {
                    Snackbar.make(mLoginBTN, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_google:
                if (LocationCheckUtils.getInstance().hasLocationPermission()) {
                    LocationCheckUtils.getInstance().requestNewLocationData();
                    if (isAllowed) {
                        if (new NetworkStatus(this).isInternetOn()) {
                            if (mAppPreference.getBoolean(AppConstant.IS_GMAIL_ENABLED, false)) {
                                socialLogin = "gmail";
                                googleSignIn();
                            } else {
                                new FBErrorDialog(this, 1, onSocialLoginErrorListener);
                            }
                        } else
                            Snackbar.make(mLoginBTN, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
                    } else {
                        Snackbar.make(mLoginBTN, R.string.not_allowed, Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    AppDialog.DialogWithLocationCallBack(this, "KhiladiAdda need to access your location.");
                }
                break;
            case R.id.ll_need_support:
                openWhatsappContact();
                break;
            case R.id.tv_signup:
                if (isAllowed) {
                    startActivity(new Intent(this, RegistrationActivity.class));
                    finish();
                } else
                    Snackbar.make(mLoginBTN, R.string.not_allowed, Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.iv_truecaller:
                if (LocationCheckUtils.getInstance().hasLocationPermission()) {
                    LocationCheckUtils.getInstance().requestNewLocationData();
                    if (new NetworkStatus(this).isInternetOn()) {
                        if (isAllowed) {
                            if (mAppPreference.getBoolean(AppConstant.IS_TRUECALLER_ENABLED, false)) {
                                if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
                                    return;
                                }
                                mLastClickTime = SystemClock.elapsedRealtime();
                                socialLogin = "truecaller";
                                setupTruecaller();
                            } else
                                new FBErrorDialog(this, 2, onSocialLoginErrorListener);
                        } else
                            Snackbar.make(mLoginBTN, R.string.not_allowed, Snackbar.LENGTH_SHORT).show();
                    } else {
                        Snackbar.make(mLoginBTN, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    AppDialog.DialogWithLocationCallBack(this, "KhiladiAdda need to access your location.");
                }
                break;
        }
    }

    @Override
    public String getEmailAddress() {
        return mEmailET.getText().toString();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public void onValidationComplete() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.doLogin(mEmailET.getText().toString().trim());
        } else {
            Snackbar.make(mLoginBTN, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onValidationFailure(String errorMsg) {
        AppUtilityMethods.showMsg(this, errorMsg, false);
    }

    @Override
    public void onLoginComplete(BaseResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            Intent otp = new Intent(this, OtpActivity.class);
            otp.putExtra(AppConstant.FROM, AppConstant.FROM_LOGIN);
            otp.putExtra(AppConstant.MobileNumber, mEmailET.getText().toString().trim());
            startActivity(otp);
            finish();
        } else {
            AppUtilityMethods.showMsg(this, responseModel.getMessage(), false);
        }
    }

    @Override
    public void onLoginFailure(ApiError error) {
        hideProgress();
        AppUtilityMethods.showMsg(this, error.getMessage(), false);
    }

    @Override
    public void onGmailLoginComplete(SocialResponse responseModel) {
        if (!responseModel.isStatus()) {
            hideProgress();
            openGmailVerification(mGmailRequest);
        } else {
            setData(responseModel.getJwt());
        }
    }

    @Override
    public void onGmailLoginFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onFbLoginComplete(SocialResponse responseModel) {
        if (!responseModel.isStatus()) {
            hideProgress();
            openFBVerification(mFBToken);
        } else {
            setData(responseModel.getJwt());
        }
    }

    @Override
    public void onFbLoginFailure(ApiError error) {
        hideProgress();
    }

    private void setData(String jwt) {
        mAppPreference.setIsLogin(true);
        mAppPreference.setSessionToken(jwt);
        mPresenter.getMasterData();
    }

    @Override
    public void onMasterComplete(MasterResponse responseModel) {
        AppUtilityMethods.saveMasterData(responseModel);
        appsFlyer("direct", AppSharedPreference.getInstance().getMobile());
        hideProgress();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onMasterFailure(ApiError error) {
        hideProgress();
    }

    private void getFirebaseId() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        return;
                    }
                    mAppPreference.setDeviceToken(task.getResult());
                    if (!mAppPreference.getIsUUID()) {
                        mAppPreference.setString(AppConstant.UUID, AppUtilityMethods.getDeviceUniqueID(this));
                        mAppPreference.setIsUUID(true);
                    }
                });
    }

    // Set up Google
    private GoogleSignInOptions setUpGoogle() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.server_client_id)).requestEmail().requestProfile().build();
        return gso;
    }

    //Google SignIn
    private void googleSignIn() {
        Intent signInIntent = GoogleSignIn.getClient(this, setUpGoogle()).getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
//        gmailActivityResultLauncher.launch(signInIntent);
    }

    private void handleGoogleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            mGmailRequest = new GmailRegisterRequest(account.getId(), account.getDisplayName(), account.getGivenName(), account.getEmail(), String.valueOf(account.getPhotoUrl()), "", "", "", "", "");
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.doGmailLogin(account.getId());
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    // Set up Facebook
    private void setUpFb() {
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mFBToken = loginResult.getAccessToken().getToken();
                showProgress(getString(R.string.txt_progress_authentication));
                mPresenter.doFbLogin(mFBToken);
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Facebook Cancel", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(LoginActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void openGmailVerification(GmailRegisterRequest request) {
        Intent gmail = new Intent(this, SocialVerifyActivity.class);
        gmail.putExtra(AppConstant.FROM, AppConstant.FROM_GMAIL);
        gmail.putExtra(AppConstant.DATA, request);
        startActivity(gmail);
        finish();
    }

    private void openFBVerification(String fbToken) {
        Intent fb = new Intent(this, SocialVerifyActivity.class);
        fb.putExtra(AppConstant.FROM, AppConstant.FROM_FB);
        fb.putExtra(AppConstant.DATA, fbToken);
        startActivity(fb);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TruecallerSDK.SHARE_PROFILE_REQUEST_CODE) {
            TruecallerSDK.getInstance().onActivityResultObtained(this, requestCode, resultCode, data);
        }
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            try {
                // The Task returned from this call is always completed, no need to attach a listener.
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                GoogleSignInAccount account = task.getResult(ApiException.class);
                handleGoogleSignInResult(task);
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
        // Result returned from launching the Intent from Facebook;
        else if (mCallbackManager.onActivityResult(requestCode, resultCode, data)) {
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        try {
            builder.setMessage("Location Required\nWe need to insure this game is allowed in your state of residence.\nPlease allow to access your location for the first time.")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, final int id) {
                            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.destroy();
        super.onDestroy();
        TruecallerSDK.clear();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case AppConstant.RC_ASK_PERMISSIONS_STORAGE:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, getString(R.string.txt_storage_permission), Toast.LENGTH_SHORT).show();
                } else {
//                    PermissionUtils.hasSMSReadPermission(this);
                }
                break;
            case AppConstant.RC_ASK_PERMISSIONS_MSG:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, getString(R.string.txt_read_sms_permission), Toast.LENGTH_SHORT).show();
                } else
                    break;
            case RC_ASK_PERMISSIONS_GPS:
                for (int i = 0, len = permissions.length; i < len; i++) {
                    String permission = permissions[i];
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        // user rejected the permission
                        boolean showRationale = false;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            showRationale = shouldShowRequestPermissionRationale(permission);
                        }
                        if (!showRationale) {
                            // user also CHECKED "never ask again"
                            // you can either enable some fall back,
                            // disable features of your app
                            // or open another dialog explaining
                            // again the permission and directing to
                            // the app setting
//                            AppDialog.DialogWithLocationCallBack(this, "KhiladiAdda need to access your location.");
                        } else if (Manifest.permission.ACCESS_FINE_LOCATION.equals(permission)) {
//                        showRationale(permission, R.string.permission_denied_contacts);
                            // user did NOT check "never ask again"
                            // this is a good place to explain the user
                            // why you need the permission and ask if he wants
                            // to accept it (the rationale)
                        }
                    }
                }

                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private IOnSocialLoginErrorListener onSocialLoginErrorListener = new IOnSocialLoginErrorListener() {
        @Override
        public void onForgotPassword() {
            Intent forgotPwd = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            forgotPwd.putExtra(AppConstant.FROM, AppConstant.FROM_FORGOT_PASSWORD);
            startActivity(forgotPwd);
            finish();
        }

        @Override
        public void onRegister() {
            Intent forgotPwd = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(forgotPwd);
            finish();
        }
    };

    private void openWhatsappContact() {
        String url = "https://wa.me/91" + mAppPreference.getSupportNumber() + "" + "?text=Hello%20I%20have%20a%20problem";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    /**
     * TrueCaller Setup
     */
    private void setupTruecaller() {
        TruecallerSdkScope trueScope = new TruecallerSdkScope.Builder(this, sdkCallbackTrueCaller)
                .consentMode(TruecallerSdkScope.CONSENT_MODE_BOTTOMSHEET)
                .buttonColor(Color.RED)
                .buttonTextColor(Color.WHITE)
                .loginTextPrefix(TruecallerSdkScope.LOGIN_TEXT_PREFIX_TO_GET_STARTED)
                .loginTextSuffix(TruecallerSdkScope.LOGIN_TEXT_SUFFIX_PLEASE_VERIFY_MOBILE_NO)
                .ctaTextPrefix(TruecallerSdkScope.CTA_TEXT_PREFIX_USE)
                .buttonShapeOptions(TruecallerSdkScope.BUTTON_SHAPE_ROUNDED)
                .privacyPolicyUrl(AppConstant.TRUECALLER_POLICY_URL)
                .termsOfServiceUrl(AppConstant.TRUECALLER_PRIVACY_URL)
                .footerType(TruecallerSdkScope.FOOTER_TYPE_NONE)
                .consentTitleOption(TruecallerSdkScope.SDK_CONSENT_TITLE_LOG_IN)
                .sdkOptions(TruecallerSdkScope.SDK_OPTION_WITHOUT_OTP)
                .build();

        TruecallerSDK.init(trueScope);
        if (TruecallerSDK.getInstance().isUsable()) {
            hideProgress();
            TruecallerSDK.getInstance().getUserProfile(this);
        } else {
            hideProgress();
            Snackbar.make(mLoginBTN, "TrueCaller is not installed in your device or you are not registered on truecaller", Snackbar.LENGTH_SHORT).show();
            //truecaller is not installed
        }
    }

    /**
     * TrueCaller Callback
     */
    ITrueCallback sdkCallbackTrueCaller = new ITrueCallback() {
        @Override
        public void onSuccessProfileShared(@NonNull TrueProfile trueProfile) {
            TrueCallerRequest request = new TrueCallerRequest();
            request.setPayload(trueProfile.payload);
            request.setSignature(trueProfile.signature);
            mUserName = "" + trueProfile.firstName + " " + trueProfile.lastName;
            mMobileNumber = "" + trueProfile.phoneNumber;
            mEmail = "" + trueProfile.email;
            Log.e("TAG", "onSuccessProfileShared: " + trueProfile);
            request.setSignatureAlgorithm(trueProfile.signatureAlgorithm);
            if (new NetworkStatus(mEmailET.getContext()).isInternetOn()) {
                showProgress(getString(R.string.txt_progress_authentication));
                mPresenterTrueCaller.callTrueCallerApi(request);
            } else {
                Snackbar.make(mLoginBTN, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onFailureProfileShared(@NonNull TrueError trueError) {
            hideProgress();
//            Snackbar.make(mLoginBTN, "TrueCaller is not installed in your device or you are not registered on truecaller", Snackbar.LENGTH_SHORT).show();
            if (trueError.getErrorType() == TrueError.ERROR_TYPE_INVALID_ACCOUNT_STATE) {
                Snackbar.make(mLoginBTN, "TrueCaller is not installed in your device or you are not registered on truecaller", Snackbar.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onVerificationRequired(TrueError trueError) {
            hideProgress();
        }
    };

    @Override
    public void onTrueCallerCompletion(TrueCallerResponse response) {
        hideProgress();
        try {
            if (response.isStatus()) {
                mAppPreference.setIsLogin(true);
                mAppPreference.setSessionToken(response.jwt);
                mPresenter.getMasterData();
            } else {
                if (!response.isExists) {
                    hideProgress();
                    Intent intent = new Intent(this, RegistrationActivity.class);
//                startActivity(new Intent(this, RegistrationActivity.class));
                    intent.putExtra("name", mUserName);
                    intent.putExtra("mobile_number", mMobileNumber);
                    if (mEmail != null && !mEmail.equals("null"))
                        intent.putExtra("email", mEmail);
                    else
                        intent.putExtra("email", "");

                    startActivity(intent);
                    finish();
                } else {
                    showErrorDialog(response.getMessage());
                }
            }
        } catch (Exception e) {
            Log.e("TAG", "onTrueCallerCompletion: " + e.getLocalizedMessage());
        }
    }

    @Override
    public void onTrueCallerFailure(ApiError errorMsg) {
        hideProgress();
//        Toast.makeText(this, "Failed Truecaller Api" + errorMsg.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void showErrorDialog(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Set the message show for the Alert time
        builder.setMessage(msg);
        // Set Alert Title
        builder.setTitle("Alert !");
        // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
        builder.setCancelable(false);
        // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            // When the user click yes button then app will close
            finish();
        });
        // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            // If user click no then dialog box is canceled.
            dialog.cancel();
        });
        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();
        // Show the Alert Dialog box
        alertDialog.show();
    }

    @Override
    public void iOnAddressSuccess() {
        isAllowed = true;
    }

    @Override
    public void iOnAddressFailure() {
        isAllowed = false;
//        LocationCheckUtils.getInstance().DialogNotAllowed(this, "You are not allowed to play skill-based real money gaming in your state.");
    }

    private void appsFlyer(String socialName, String mMobileNumber) {
        Map<String, Object> eventParameters2 = new HashMap<>();
        eventParameters2.put(AppConstant.LOGIN_NUMBER, mMobileNumber);
        eventParameters2.put(AppConstant.LOGIN_METHOD, socialName);
        eventParameters2.put(AppConstant.LOGIN, "login");
        AppsFlyerLib.getInstance().logEvent(getApplicationContext(), AppConstant.INVEST, eventParameters2);
    }
}
