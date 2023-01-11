package com.khiladiadda.profile.update;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.FileProvider;

import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.SurepassResponse;
import com.khiladiadda.network.model.response.ProfileResponse;
import com.khiladiadda.network.model.response.UploadImageResponse;
import com.khiladiadda.network.model.response.WIthdrawLimitResponse;
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

public class PanActivity extends BaseActivity implements IUpdateProfileView {

    @BindView(R.id.iv_back)
    ImageView mBackIV;
    @BindView(R.id.tv_activity_name)
    TextView mActivityNameTV;
    @BindView(R.id.iv_notification)
    ImageView mNotificationIV;
    @BindView(R.id.iv_pan)
    ImageView mPanIV;
    @BindView(R.id.et_pan_name)
    EditText mNameET;
    @BindView(R.id.et_pan_number)
    EditText mNumberET;
    @BindView(R.id.btn_send)
    Button mSendBtn;
    private String mImagePath;
    private IUpdateProfilePresenter mPresenter;

    @Override
    protected int getContentView() {
        return R.layout.activity_pan;
    }

    @Override
    protected void initViews() {
        mBackIV.setOnClickListener(this);
        mNotificationIV.setVisibility(View.GONE);
        mActivityNameTV.setText(R.string.text_update_pan_details);
        mPanIV.setOnClickListener(this);
        mSendBtn.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {
        mPresenter = new UpdateProfilePresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_pan:
                if (!PermissionUtils.hasStoragePermission(this)) {
                    Snackbar.make(mSendBtn, R.string.txt_allow_permission, Snackbar.LENGTH_SHORT).show();
                } else {
                    choosePhotoFromGallery();
                }
                break;
            case R.id.btn_send:
                if (TextUtils.isEmpty(mNameET.getText().toString().trim())) {
                    AppUtilityMethods.showMsg(this, "Please provide name as printed on PAN card", true);
                } else if (TextUtils.isEmpty(mNumberET.getText().toString().trim())) {
                    AppUtilityMethods.showMsg(this, "Please provide pan number as printed on PAN card", true);
                } else if (!AppUtilityMethods.isValidPanNumber(mNumberET.getText().toString().trim())) {
                    AppUtilityMethods.showMsg(this, "Please provide valid pan number", true);
                } else if (mImagePath == null) {
                    AppUtilityMethods.showMsg(this, getString(R.string.text_select_image), false);
                } else {
                    //Upload Pan Image and then update pan details
                    uploadImage();
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == AppConstant.RC_ASK_PERMISSIONS_STORAGE) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Snackbar.make(mSendBtn, getString(R.string.txt_storage_permission), Snackbar.LENGTH_LONG).show();
            } else {
                choosePhotoFromGallery();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void choosePhotoFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, AppConstant.REQUEST_GALLERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            cursor.close();
            try {
                Bitmap bmp = getBitmapFromUri(selectedImage);
                String extension = AppUtilityMethods.getMimeType(this, selectedImage);
                mImagePath = AppConstant.APP_DIRECTORY_PATH + AppConstant.IMAGE_PATH + extension;
                ImageUtils.saveLudoImageToFile(bmp, mImagePath);
                mPanIV.setImageBitmap(bmp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

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

    private void uploadImage() {
        if (new NetworkStatus(this).isInternetOn()) {
            Uri organizerImageUri = getImageUri(mImagePath);
            File organizerImageFile = getImageFile(mImagePath);
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.uploadImage(organizerImageUri, organizerImageFile, this.getContentResolver(), 4);
        } else {
            Snackbar.make(mActivityNameTV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public String getEmailAddress() {
        return null;
    }

    @Override
    public String getMobile() {
        return null;
    }

    @Override
    public String getGender() {
        return null;
    }

    @Override
    public String getState() {
        return null;
    }

    @Override
    public String getCountry() {
        return null;
    }

    @Override
    public void onValidationComplete() {
    }

    @Override
    public void onValidationFailure(String errorMsg) {
    }

    @Override
    public void onUploadImageComplete(UploadImageResponse responseModel) {
        if (responseModel.isStatus()) {
            mPresenter.doUpdatePAN(responseModel.getResponse(), mNameET.getText().toString().trim(), mNumberET.getText().toString().trim());
        }
    }

    @Override
    public void onUploadImageFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onUpdateProfileComplete(ProfileResponse responseModel) {
    }

    @Override
    public void onUpdateProfileFailure(ApiError error) {
    }

    @Override
    public void onUpdatePANComplete(ProfileResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            mAppPreference.setProfileData(responseModel.getResponse());
        }
        AppUtilityMethods.showMsg(this, responseModel.getMessage(), false);
    }

    @Override
    public void onUpdatePANFailure(ApiError error) {
        hideProgress();
        AppUtilityMethods.showMsg(this, error.getMessage(), false);
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
        hideProgress();
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

}