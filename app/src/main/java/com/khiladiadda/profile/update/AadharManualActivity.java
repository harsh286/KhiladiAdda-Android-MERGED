package com.khiladiadda.profile.update;

import androidx.core.content.FileProvider;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.interfaces.IOnDateSetListener;
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
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;

import static com.khiladiadda.utility.AppConstant.REQUEST_GALLERY;

public class AadharManualActivity extends BaseActivity implements IUpdateProfileView, IOnDateSetListener {

    @BindView(R.id.iv_back)
    ImageView mBackIV;
    @BindView(R.id.tv_activity_name)
    TextView mActivityNameTV;
    @BindView(R.id.iv_notification)
    ImageView mNotificationIV;
    @BindView(R.id.iv_aadhar_front)
    ImageView mAdharFrontIV;
    @BindView(R.id.iv_aadhar_back)
    ImageView mAdharBackIV;
    @BindView(R.id.tv_dob)
    TextView mDobTV;
    @BindView(R.id.et_name)
    EditText mNameET;
    @BindView(R.id.et_number)
    EditText mAadharNumberET;
    @BindView(R.id.et_address)
    EditText mAddressET;
    @BindView(R.id.et_city)
    EditText mCityET;
    @BindView(R.id.et_state)
    EditText mStateET;
    @BindView(R.id.et_pincode)
    EditText mPincodeET;
    @BindView(R.id.btn_send)
    Button mSendBtn;

    @BindView(R.id.tv_front)
    TextView mFrontTV;
    @BindView(R.id.tv_back)
    TextView mBackTV;

    private String mFrontImagePath, mBackImagePath, mAadharFrontURL;
    private IUpdateProfilePresenter mPresenter;
    private int mFrom;
    private boolean mIsFrontUploaded;

    @Override
    protected int getContentView() {
        return R.layout.activity_aadhar_manual;
    }

    @Override
    protected void initViews() {
        mBackIV.setOnClickListener(this);
        mNotificationIV.setVisibility(View.GONE);
        mActivityNameTV.setText(R.string.text_update_aadhar_details);
        mAdharFrontIV.setOnClickListener(this);
        mAdharBackIV.setOnClickListener(this);
        mSendBtn.setOnClickListener(this);
        mDobTV.setOnClickListener(this);
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
            case R.id.iv_aadhar_front:
                mFrom = 1;
                if (!PermissionUtils.hasStoragePermission(this)) {
                    Snackbar.make(mSendBtn, R.string.txt_allow_permission, Snackbar.LENGTH_SHORT).show();
                } else {
                    choosePhotoFromGallery();
                }
                break;
            case R.id.iv_aadhar_back:
                mFrom = 2;
                if (!PermissionUtils.hasStoragePermission(this)) {
                    Snackbar.make(mSendBtn, R.string.txt_allow_permission, Snackbar.LENGTH_SHORT).show();
                } else {
                    choosePhotoFromGallery();
                }
                break;
            case R.id.tv_dob:
                Date date = AppUtilityMethods.getDateFromString(mDobTV.getText().toString());
                AppUtilityMethods.openDOBDatePickerDialog(this, this, date);
                break;
            case R.id.btn_send:
                if (TextUtils.isEmpty(mNameET.getText().toString().trim())) {
                    AppUtilityMethods.showMsg(this, "Please provide name as printed on Aadhar card", true);
                } else if (TextUtils.isEmpty(mAadharNumberET.getText().toString().trim())) {
                    AppUtilityMethods.showMsg(this, "Please provide aadhar number as printed on Aadhar card", true);
                } else if (mAadharNumberET.getText().toString().trim().length() < 12) {
                    AppUtilityMethods.showMsg(this, "Please provide valid aadhar number as printed on Aadhar card", true);
                } else if (TextUtils.isEmpty(mDobTV.getText().toString().trim())) {
                    AppUtilityMethods.showMsg(this, "Please select date", true);
                } else if ((TextUtils.isEmpty(mAddressET.getText().toString().trim())) || (mAddressET.getText().toString().trim().length() < 10)) {
                    AppUtilityMethods.showMsg(this, "Please provide valid address as printed on Aadhar card", true);
                } else if ((TextUtils.isEmpty(mAddressET.getText().toString().trim())) || (mCityET.getText().toString().trim().length() < 3)) {
                    AppUtilityMethods.showMsg(this, "Please provide valid city", true);
                } else if ((TextUtils.isEmpty(mAddressET.getText().toString().trim())) || (mStateET.getText().toString().trim().length() < 3)) {
                    AppUtilityMethods.showMsg(this, "Please provide valid state", true);
                } else if ((TextUtils.isEmpty(mAddressET.getText().toString().trim())) || (mPincodeET.getText().toString().trim().length() < 6)) {
                    AppUtilityMethods.showMsg(this, "Please provide valid pincode number", true);
                } else if (mFrontImagePath == null) {
                    AppUtilityMethods.showMsg(this, "Please upload aadhar front ", false);
                } else if (mBackImagePath == null) {
                    AppUtilityMethods.showMsg(this, "Please upload aadhar back", false);
                } else {
                    //Upload Aadhar Image and then update details
                    uploadFrontImage();
                }
                break;
            default:
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
            try {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                cursor.close();
                Bitmap bmp = getBitmapFromUri(selectedImage);
                String extension = AppUtilityMethods.getMimeType(this, selectedImage);
                if (mFrom == 1) {
                    mFrontImagePath = AppConstant.APP_DIRECTORY_PATH + AppConstant.AADHAR_IMAGE_PATH + System.currentTimeMillis() + "." + extension;
                    ImageUtils.saveAadharImageToFile(bmp, mFrontImagePath);
                    mAdharFrontIV.setImageBitmap(bmp);
                    mFrontTV.setVisibility(View.GONE);
                } else {
                    mBackImagePath = AppConstant.APP_DIRECTORY_PATH + AppConstant.AADHAR_IMAGE_PATH + System.currentTimeMillis() + "." + extension;
                    ImageUtils.saveAadharImageToFile(bmp, mBackImagePath);
                    mAdharBackIV.setImageBitmap(bmp);
                    mBackTV.setVisibility(View.GONE);
                }

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

    private void uploadFrontImage() {
        if (new NetworkStatus(this).isInternetOn()) {
            Uri organizerImageUri = getImageUri(mFrontImagePath);
            File organizerImageFile = getImageFile(mFrontImagePath);
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.uploadImage(organizerImageUri, organizerImageFile, this.getContentResolver(), 4);
        } else {
            Snackbar.make(mActivityNameTV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    private void uploadBackImage() {
        if (new NetworkStatus(this).isInternetOn()) {
            Uri organizerImageUri = getImageUri(mBackImagePath);
            File organizerImageFile = getImageFile(mBackImagePath);
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
            String address = mAddressET.getText().toString().trim() + "," + mCityET.getText().toString().trim() + "," + mStateET.getText().toString().trim() + "," + mPincodeET.getText().toString().trim();
            if (!mIsFrontUploaded) {
                mAadharFrontURL = responseModel.getResponse();
                mIsFrontUploaded = true;
                uploadBackImage();
            } else {
                mPresenter.updateAadhaarServer(mNameET.getText().toString().trim(), mAadharFrontURL, responseModel.getResponse(), address, "", "", mDobTV.getText().toString().trim(), mAadharNumberET.getText().toString().trim(), "", "", 1);
            }
        }
        else{
            AppUtilityMethods.showMsg(this, responseModel.getMessage(), false);
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
        showMsg(responseModel.getMessage());
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

    public void showMsg(String msg) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.popup);
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        tv_msg.setText(msg);
        Button okBTN = dialog.findViewById(R.id.btn_ok);
        okBTN.setOnClickListener(arg0 -> {
            dialog.dismiss();
            finish();
        });
        dialog.show();
    }

    @Override
    public void onDateSet(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        String dateString = AppUtilityMethods.getDateStringFromDate(calendar.getTime());
        mDobTV.setText(dateString);
    }

}