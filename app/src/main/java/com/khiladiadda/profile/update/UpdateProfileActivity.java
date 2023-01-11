package com.khiladiadda.profile.update;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.fcm.NotificationActivity;
import com.khiladiadda.main.MainActivity;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.SurepassResponse;
import com.khiladiadda.network.model.response.ProfileDetails;
import com.khiladiadda.network.model.response.ProfileResponse;
import com.khiladiadda.network.model.response.UploadImageResponse;
import com.khiladiadda.network.model.response.WIthdrawLimitResponse;
import com.khiladiadda.profile.ProfileActivity;
import com.khiladiadda.profile.update.interfaces.IUpdateProfilePresenter;
import com.khiladiadda.profile.update.interfaces.IUpdateProfileView;
import com.khiladiadda.network.model.response.AadharCaptchaResponse;
import com.khiladiadda.network.model.response.AadharDetailsResponse;
import com.khiladiadda.network.model.response.VerifiBaseResponse;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.ImageUtils;
import com.khiladiadda.utility.NetworkStatus;
import com.khiladiadda.utility.PermissionUtils;
import com.khiladiadda.utility.providers.GenericFileProvider;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;

import butterknife.BindView;

import static com.khiladiadda.utility.AppConstant.REQUEST_GALLERY;

public class UpdateProfileActivity extends BaseActivity implements IUpdateProfileView {

    @BindView(R.id.iv_back)
    ImageView mBackIV;
    @BindView(R.id.tv_activity_name)
    TextView mActivityNameTV;
    @BindView(R.id.iv_notification)
    ImageView mNotificationIV;
    @BindView(R.id.iv_profile)
    ImageView mProfileIV;
    @BindView(R.id.btn_upload)
    Button mUploadBTN;
    @BindView(R.id.et_user_name)
    TextView mUserNameET;
    @BindView(R.id.et_name)
    TextView mNameET;
    @BindView(R.id.et_email)
    TextView mEmailET;
    @BindView(R.id.et_mobile)
    TextView mMobileET;
    @BindView(R.id.rg_gender)
    RadioGroup mGenderRG;
    @BindView(R.id.et_state)
    TextView mStateET;
    @BindView(R.id.et_country)
    TextView mCountryET;
    @BindView(R.id.btn_update)
    Button mUpdateBTN;
    @BindView(R.id.tv_address)
    TextView mAddressTV;
    @BindView(R.id.tv_name)
    TextView mNameTV;
    @BindView(R.id.tv_state)
    TextView mStateTV;
    @BindView(R.id.tv_country)
    TextView mCountryTV;

    private String mGender = "male";
    private String mImagePath, mProfileImage;
    private IUpdateProfilePresenter mPresenter;

    @Override
    protected int getContentView() {
        return R.layout.activity_update_profile;
    }

    @Override
    protected void initViews() {
        mActivityNameTV.setText(R.string.txt_profile_update);
        mBackIV.setOnClickListener(this);
        mNotificationIV.setOnClickListener(this);
        mProfileIV.setOnClickListener(this);
        mUploadBTN.setOnClickListener(this);
        mUpdateBTN.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {
        mPresenter = new UpdateProfilePresenter(this);
        setProfileData();
        mGenderRG.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton rb = group.findViewById(checkedId);
            if (null != rb) {
                int selectedId = mGenderRG.getCheckedRadioButtonId();
                RadioButton radioVehicleButton = mGenderRG.findViewById(selectedId);
                mGender = String.valueOf(radioVehicleButton.getText());
            }
        });
    }

    private void setProfileData() {
        mNameET.setText(mAppPreference.getName());
        mEmailET.setText(mAppPreference.getEmail());
        if (!TextUtils.isEmpty(mAppPreference.getUrl())) {
            Glide.with(this).load(mAppPreference.getUrl()).thumbnail(Glide.with(this).load(mAppPreference.getUrl())).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).error(R.drawable.ic_profile).into(mProfileIV);
        }
        ProfileDetails profileData = mAppPreference.getProfileData();
        if (TextUtils.isEmpty(profileData.getUsername()) || profileData.getUsername().startsWith("8888888888")) {
            mUserNameET.setText("");
        } else {
            mUserNameET.setText(profileData.getUsername());
        }
        if (!TextUtils.isEmpty(profileData.getGender()) && profileData.getGender().equalsIgnoreCase("Male")) {
            mGenderRG.check(R.id.rb_male);
        } else if (!TextUtils.isEmpty(profileData.getGender()) && profileData.getGender().equalsIgnoreCase("Female")) {
            mGenderRG.check(R.id.rb_female);
        } else {
            mGenderRG.check(R.id.rb_male);
        }
        if ((mAppPreference.getProfileData().getAadharUpdated() == 3)) {
//            mNameET.setEnabled(false);
            mStateET.setVisibility(View.GONE);
            mCountryET.setVisibility(View.GONE);
            mNameTV.setVisibility(View.VISIBLE);
            mAddressTV.setText(mAppPreference.getProfileData().getAddress());
            mAddressTV.setVisibility(View.VISIBLE);
        } else {
            if (!TextUtils.isEmpty(profileData.getState())) {
                mStateET.setText(profileData.getState());
            }
            if (!TextUtils.isEmpty(profileData.getCountry())) {
                mCountryET.setText(profileData.getCountry());
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                startActivity(new Intent(this, ProfileActivity.class));
                finish();
                break;
            case R.id.iv_notification:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
            case R.id.iv_profile:
                if (SDK_INT >= Build.VERSION_CODES.R) {
                    if (Environment.isExternalStorageManager()){
                        choosePhotoFromGallery();
                    }else {
                        Snackbar.make(mBackIV, R.string.txt_allow_permission, Snackbar.LENGTH_SHORT).show();
                        Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }

                } else  {
                    choosePhotoFromGallery();
                    ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE}, 101);

                }
                break;

            case R.id.btn_upload:
                if (mImagePath != null) {
                    Uri organizerImageUri = getImageUri(mImagePath);
                    File organizerImageFile = getImageFile(mImagePath);
                    showProgress(getString(R.string.txt_progress_uploading_image));
                    mPresenter.uploadImage(organizerImageUri, organizerImageFile, this.getContentResolver(), 1);
                } else {
                    AppUtilityMethods.showMsg(this, getString(R.string.text_select_image), false);
                }
                break;
            case R.id.btn_update:
                mPresenter.validateData();
                break;
        }
    }

    @SuppressLint("IntentReset")
    private void choosePhotoFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");
        launcher.launch(galleryIntent);
    }

    //    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK && null != data) {
//            Uri selectedImage = data.getData();
//            String[] filePathColumn = {MediaStore.Images.Media.DATA};
//            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//            cursor.moveToFirst();
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String mSelectedImagePath = cursor.getString(columnIndex);
//            cursor.close();
//            Bitmap decoded = null;
//            try {
//                Bitmap bmp = getBitmapFromUri(selectedImage);
//                String extension = AppUtilityMethods.getMimeType(this, selectedImage);
//                mImagePath = AppConstant.APP_DIRECTORY_PATH + AppConstant.IMAGE_PATH + extension;
//                ImageUtils.saveLudoImageToFile(bmp, mImagePath);
//                Glide.with(this).load(bmp).error(R.drawable.ic_profile).into(mProfileIV);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
    private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK
                        && result.getData() != null) {
                    Uri photoUri = result.getData().getData();
                    //use photoUri here
                    Bitmap bmp;
                    try {
                        bmp = getBitmapFromUri(photoUri);
                        String extension = AppUtilityMethods.getMimeType(this, photoUri);
                        mImagePath = AppConstant.APP_DIRECTORY_PATH + AppConstant.IMAGE_PATH + extension;
                        ImageUtils.saveLudoImageToFile(bmp, mImagePath);
                        Glide.with(this).load(photoUri).error(R.drawable.ic_profile).into(mProfileIV);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
    );


    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    public Uri getImageUri(String path) {
        return FileProvider.getUriForFile(this, GenericFileProvider.AUTHORITY, new File(path));
    }

    public File getImageFile(String path) {
        return new File(path);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case AppConstant.RC_ASK_PERMISSIONS_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (PermissionUtils.hasCameraPermission(this)) {
                        Intent pictureActionIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        pictureActionIntent.setType("image/*");
                        pictureActionIntent.putExtra("return-data", true);
                        startActivityForResult(pictureActionIntent, REQUEST_GALLERY);
                    } else {
                        Snackbar.make(mUpdateBTN, R.string.txt_allow_permission, Snackbar.LENGTH_SHORT).show();
                    }
                }
                break;
            case AppConstant.RC_ASK_PERMISSIONS_CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent pictureActionIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    pictureActionIntent.setType("image/*");
                    pictureActionIntent.putExtra("return-data", true);
                    startActivityForResult(pictureActionIntent, REQUEST_GALLERY);
                } else {
                    Snackbar.make(mUpdateBTN, R.string.txt_allow_permission, Snackbar.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public String getName() {
        return mNameET.getText().toString().trim();
    }

    @Override
    public String getUsername() {
        return mUserNameET.getText().toString().trim();
    }

    @Override
    public String getEmailAddress() {
        return mEmailET.getText().toString();
    }

    @Override
    public String getMobile() {
        return mMobileET.getText().toString().trim();
    }

    @Override
    public String getGender() {
        return mGender;
    }

    @Override
    public String getState() {
        return mStateET.getText().toString();
    }

    @Override
    public String getCountry() {
        return mCountryET.getText().toString();
    }

    @Override
    public void onValidationComplete() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.doUpdateProfile(mProfileImage);
        } else {
            Snackbar.make(mUpdateBTN, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onValidationFailure(String errorMsg) {
        AppUtilityMethods.showMsg(this, errorMsg, false);
    }

    @Override
    public void onUploadImageComplete(UploadImageResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            mProfileImage = responseModel.getResponse();
            AppUtilityMethods.showMsg(this, getString(R.string.text_uploaded_succefully), false);
        } else {
            AppUtilityMethods.showMsg(this, responseModel.getMessage(), false);
        }
    }

    @Override
    public void onUploadImageFailure(ApiError error) {
        hideProgress();
        AppUtilityMethods.showMsg(this, error.getMessage(), false);
    }

    @Override
    public void onUpdateProfileComplete(ProfileResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            mAppPreference.setProfileData(responseModel.getResponse());
            AppUtilityMethods.showMsg(this, responseModel.getMessage(), false);
        } else {
            AppUtilityMethods.showMsg(this, responseModel.getMessage(), true);
        }
    }

    @Override
    public void onUpdateProfileFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onUpdatePANComplete(ProfileResponse responseModel) {

    }

    @Override
    public void onUpdatePANFailure(ApiError error) {

    }

    @Override
    public void onGetCaptchaComplete(AadharCaptchaResponse responseModel) {

    }

    @Override
    public void onGetCaptchaFailure(ApiError error) {

    }

    @Override
    public void onGetAadhaarOtpComplete(VerifiBaseResponse responseModel) {

    }

    @Override
    public void onGetAadhaarOtpFailure(ApiError error) {

    }

    @Override
    public void onVerifyAadhaarOtpComplete(VerifiBaseResponse responseModel) {

    }

    @Override
    public void onVerifyAadhaarOtpFailure(ApiError error) {

    }

    @Override
    public void onGetAadhaarKYCComplete(AadharDetailsResponse responseModel) {

    }

    @Override
    public void onGetAadhaarKYCFailure(ApiError error) {

    }

    @Override
    public void onUpdateAadharComplete(ProfileResponse responseModel) {

    }

    @Override
    public void onUpdateAadhaarFailure(ApiError error) {

    }

    @Override
    public void onCheckAdharComplete(BaseResponse responseModel) {

    }

    @Override
    public void onCheckAadharFailure(ApiError error) {

    }

    @Override
    public void onAadharVerifyViaComplete(WIthdrawLimitResponse response) {

    }

    @Override
    public void onAadharVerifyViaFailed(ApiError error) {

    }

    @Override
    public void onVerifyAadhaarOtpSurepassComplete(ProfileResponse responseModel) {

    }

    @Override
    public void onVerifyAadhaarOtpSurepassFailure(ApiError error) {

    }

    @Override
    public void onVerifyBeneficiaryComplete(BaseResponse responseModel) {

    }

    @Override
    public void onVerifyBeneficiaryFailure(ApiError error) {

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ProfileActivity.class));
        finish();
        super.onBackPressed();
    }
}