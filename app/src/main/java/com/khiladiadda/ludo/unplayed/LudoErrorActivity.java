package com.khiladiadda.ludo.unplayed;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.ludo.unplayed.interfaces.ILudoErrorPresenter;
import com.khiladiadda.ludo.unplayed.interfaces.ILudoErrorView;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.LudoErrorRequest;
import com.khiladiadda.network.model.request.LudoResultRequest;
import com.khiladiadda.network.model.response.UploadImageResponse;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.ImageUtils;
import com.khiladiadda.utility.NetworkStatus;
import com.khiladiadda.utility.PermissionUtils;
import com.khiladiadda.utility.providers.GenericFileProvider;
import com.moengage.core.Properties;
import com.moengage.core.analytics.MoEAnalyticsHelper;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;

import butterknife.BindView;

import static com.khiladiadda.utility.AppConstant.REQUEST_GALLERY;

public class LudoErrorActivity extends BaseActivity implements ILudoErrorView {

    @BindView(R.id.iv_back)
    ImageView mBackIV;
    @BindView(R.id.tv_activity_name)
    TextView mActivityNameTV;
    @BindView(R.id.iv_notification)
    ImageView mNotificationIV;
    @BindView(R.id.iv_upload)
    ImageView mUploadIV;
    @BindView(R.id.btn_add_screenshot)
    Button mAddScreenshotBTN;
    @BindView(R.id.et_other_reason)
    EditText mOtherReasonET;
    @BindView(R.id.btn_confirm)
    Button mConfirmBTN;
    @BindView(R.id.spn_reason)
    Spinner mReasonSPN;

    private String mImagePath, mId, mFrom, mReason;
    private ILudoErrorPresenter mPresenter;

    @Override
    protected int getContentView() {
        return R.layout.activity_challenge_error;
    }

    @Override
    protected void initViews() {
        mBackIV.setOnClickListener(this);
        mNotificationIV.setVisibility(View.GONE);
        mAddScreenshotBTN.setOnClickListener(this);
        mConfirmBTN.setOnClickListener(this);
        mAppPreference.setBoolean(AppConstant.IS_DATA_CHANGE, false);
        mFrom = getIntent().getStringExtra(AppConstant.FROM);
        mId = getIntent().getStringExtra(AppConstant.ID);
        int mContestType = getIntent().getIntExtra(AppConstant.CONTEST_TYPE, 0);
        if (mContestType == AppConstant.TYPE_LUDO && mFrom.equalsIgnoreCase(AppConstant.LUDO_ERROR)) {
            mActivityNameTV.setText(R.string.text_ludo_error);
        } else if (mContestType == AppConstant.TYPE_SNAKE_LADDER && mFrom.equalsIgnoreCase(AppConstant.LUDO_ERROR)) {
            mActivityNameTV.setText(R.string.text_snake_error);
        } else if (mFrom.equalsIgnoreCase(AppConstant.LUDO_WON)) {
            if (mContestType == AppConstant.TYPE_LUDO) {
                mActivityNameTV.setText(R.string.ludo_result);
            } else {
                mActivityNameTV.setText(R.string.snake_result);
            }
            mReasonSPN.setVisibility(View.GONE);
            mOtherReasonET.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initVariables() {
        mPresenter = new LudoErrorPresenter(this);
        ArrayAdapter<String> mArrayAdapterBrand = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, AppUtilityMethods.getLudoErrorReason());
        mArrayAdapterBrand.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mReasonSPN.setAdapter(mArrayAdapterBrand);

        mReasonSPN.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                mReason = (String) parent.getItemAtPosition(position);
                if (position > 0 & position < 4) {
                    mReason = (String) parent.getItemAtPosition(position);
                }
                if (position == 4) {
                    mOtherReasonET.setVisibility(View.VISIBLE);
                    mOtherReasonET.requestFocus();
                } else {
                    mOtherReasonET.setText("");
                    mOtherReasonET.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.btn_add_screenshot:
                if (SDK_INT >= Build.VERSION_CODES.R) {
                    if (Environment.isExternalStorageManager()) {
                        choosePhotoFromGallery();
                    } else {
                        AppUtilityMethods.showStoragePermisisionMsg(this, "", false);
                    }
                } else {
                    if (!PermissionUtils.hasStoragePermission(this)) {
                        Snackbar.make(mConfirmBTN, R.string.txt_allow_permission, Snackbar.LENGTH_SHORT).show();
                    } else {
                        choosePhotoFromGallery();
                    }
                }
                break;
            case R.id.btn_confirm:
                if (mFrom.equalsIgnoreCase(AppConstant.LUDO_WON)) {
                    if (TextUtils.isEmpty(mImagePath)) {
                        AppUtilityMethods.showMsg(this, getString(R.string.text_select_image), false);
                    } else {
                        wonAlert(this);
                    }
                } else {
                    if (TextUtils.isEmpty(mImagePath)) {
                        AppUtilityMethods.showMsg(this, getString(R.string.text_select_image), false);
                    } else if (TextUtils.isEmpty(mReason) || mReason.equalsIgnoreCase("Select Reason")) {
                        AppUtilityMethods.showMsg(this, "Please select reason", false);
                    } else if (mReason.equalsIgnoreCase("Other") && TextUtils.isEmpty(mOtherReasonET.getText().toString().trim())) {
                        AppUtilityMethods.showMsg(this, "Please provide reason", false);
                    } else {
                        wonAlert(this);
                    }
                }
                break;
        }
    }

    @Override
    public void onUploadImageComplete(UploadImageResponse response) {
        if (new NetworkStatus(this).isInternetOn()) {
            if (mFrom.equalsIgnoreCase(AppConstant.LUDO_WON)) {
                LudoResultRequest request = new LudoResultRequest(AppConstant.FROM_WON, response.getResponse());
                mPresenter.updateLudoResult(mId, request);
            } else {
                LudoErrorRequest request = new LudoErrorRequest(mReason, response.getResponse());
                mPresenter.errorPlay(mId, request);
            }
        } else {
            Snackbar.make(mActivityNameTV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUploadImageFailure(ApiError error) {
        hideProgress();
        Snackbar.make(mActivityNameTV, error.getMessage(), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onLudoResultSuccess(BaseResponse response) {
        hideProgress();
        if (response.isStatus()) {
            showMsg(this, getString(R.string.text_ludo_result_won), false);
        } else {
            AppUtilityMethods.showMsg(this, response.getMessage(), false);
        }
    }

    @Override
    public void onLudoResultFailure(ApiError error) {
        hideProgress();
        Snackbar.make(mActivityNameTV, error.getMessage(), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onLudoErrorSuccess(BaseResponse response) {
        hideProgress();
        if (response.isStatus()) {
            showMsg(this, getString(R.string.text_ludo_result_error), false);
        } else {
            AppUtilityMethods.showMsg(this, response.getMessage(), false);
        }
    }

    @Override
    public void onLudoErrorFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == AppConstant.RC_ASK_PERMISSIONS_STORAGE) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Snackbar.make(mConfirmBTN, getString(R.string.txt_storage_permission), Snackbar.LENGTH_LONG).show();
            } else {
                choosePhotoFromGallery();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @SuppressLint("IntentReset")
    private void choosePhotoFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");
        galleryIntent.putExtra("return-data", true);
        ludoResultActivityResultLauncher.launch(galleryIntent);
    }

//    ActivityResultLauncher<Intent> ludoResultActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
//        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
//            Uri selectedImage = result.getData().getData();
//            String[] filePathColumn = {MediaStore.Images.Media.DATA};
//            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//            cursor.moveToFirst();
//            cursor.close();
//            try {
//                Bitmap bmp = getBitmapFromUri(selectedImage);
//                String extension = AppUtilityMethods.getMimeType(this, selectedImage);
//                mImagePath = AppConstant.APP_DIRECTORY_PATH + AppConstant.IMAGE_PATH + extension;
//                ImageUtils.saveLudoImageToFile(bmp, mImagePath);
//                mUploadIV.setImageBitmap(bmp);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    });

    ActivityResultLauncher<Intent> ludoResultActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
            Uri selectedImage = result.getData().getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            cursor.close();
            try {
                Bitmap bmp = getBitmapFromUri(selectedImage);
                String extension = AppUtilityMethods.getMimeType(this, selectedImage);
                mImagePath = getExternalMediaDirs()[0].getAbsolutePath() + File.separator + AppConstant.IMAGE_PATH + extension;
                ImageUtils.saveLudoImageToFile(bmp, mImagePath);
                mUploadIV.setImageBitmap(bmp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });
    
    public void wonAlert(final Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_delete);
        TextView tv_msg = (TextView) dialog.findViewById(R.id.tv_msg);
        if (mFrom.equalsIgnoreCase(AppConstant.LUDO_WON)) {
            tv_msg.setText(getString(R.string.text_alert_won_ludo));
        } else {
            tv_msg.setText(getString(R.string.text_alert_error_ludo));
        }
        Button mOkBTN = dialog.findViewById(R.id.btn_ok);
        mOkBTN.setText(R.string.ok);
        mOkBTN.setOnClickListener(arg0 -> {
            Properties properties = new Properties();
            properties.addAttribute("game", "LudoKing");
            MoEAnalyticsHelper.INSTANCE.trackEvent(this, tv_msg.getText().toString(), properties);
            dialog.dismiss();
            uploadImage();
        });
        Button mNoBTN = dialog.findViewById(R.id.btn_no);
        mNoBTN.setText(R.string.text_cancel);
        mNoBTN.setOnClickListener(arg0 -> dialog.dismiss());
        dialog.show();
    }

    private void uploadImage() {
        if (mImagePath != null) {
            if (new NetworkStatus(this).isInternetOn()) {
                Uri organizerImageUri = getImageUri(mImagePath);
                File organizerImageFile = getImageFile(mImagePath);
                showProgress(getString(R.string.txt_progress_uploading_image));
                mPresenter.uploadImage(organizerImageUri, organizerImageFile, this.getContentResolver());
            } else {
                Snackbar.make(mActivityNameTV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
            }
        } else {
            AppUtilityMethods.showMsg(this, getString(R.string.text_select_image), false);
        }
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

    public void showMsg(final Context activity, String msg, boolean isCancel) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(isCancel);
        dialog.setContentView(R.layout.challenge_add_complete_popup);
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        tv_msg.setText(getString(R.string.text_success_confirm));
        TextView tv_help = dialog.findViewById(R.id.tv_help);
        tv_help.setText(msg);
        Button okBTN = dialog.findViewById(R.id.btn_ok);
        okBTN.setOnClickListener(arg0 -> {
            dialog.dismiss();
            openLudoChallengeActivity();
        });
        dialog.show();
    }

    private void openLudoChallengeActivity() {
        mAppPreference.setBoolean(AppConstant.IS_DATA_CHANGE, true);
        Intent intent = new Intent();
        setResult(AppConstant.FROM_LUDO_LIST, intent);
        finish();
    }

}