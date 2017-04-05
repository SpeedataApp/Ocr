package com.speedata.ocr;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.googlecode.tesseract.android.TessBaseAPI;
import com.speedata.ocr.net.NetManager;
import com.speedata.ocr.utils.Constants;
import com.speedata.ocr.utils.SDCardUtils;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DownloadInter {

    private final int TAKE_PHOTO = 1;// 拍照
    private final int PHOTO_RESULT = 3;// 结果
    private ImageView mImgOcrPic;

    private TessBaseAPI mTessBaseAPI;
    private TextView mTvOcrResult;
    //语言包上层目录
    public static final String DATA_PATH = Environment.getExternalStorageDirectory() + "/tesseract/";
    //语言包目录，目录名不可变更
    public static final String LANGUAGE_PATH = DATA_PATH + "tessdata/";
    private String language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    /**
     * 拍照
     */
    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                SDCardUtils.getDateCache(this), "photo.jpg")));
        startActivityForResult(intent, TAKE_PHOTO);
    }

    /**
     * 裁剪图片
     *
     * @param uri uri
     */
    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        String IMAGE_UNSPECIFIED = "image/*";
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_RESULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 0)
            return;
        if (requestCode == TAKE_PHOTO) {
            // 设置文件保存路径这里放在跟目录下
            File picture = new File(SDCardUtils.getDateCache(this)
                    + "/photo.jpg");
            startPhotoZoom(Uri.fromFile(picture));
        }
        if (data == null) {
            return;
        }

        // 处理结果
        if (requestCode == PHOTO_RESULT) {
            Bundle extras = data.getExtras();

            if (extras == null) {
                return;
            }
            Bitmap photo = extras.getParcelable("data");
            mImgOcrPic.setImageBitmap(photo);
            setOcr(photo);
        }

    }

    private void setOcr(Bitmap photo) {
        mTessBaseAPI.setImage(photo);
        String result = mTessBaseAPI.getUTF8Text();
        mTvOcrResult.setText(result);
    }

    private void initView() {
        mImgOcrPic = (ImageView) findViewById(R.id.img_ocr_pic);
        mTvOcrResult = (TextView) findViewById(R.id.tv_ocr_result);
        findViewById(R.id.btn_start).setOnClickListener(this);
        initOcr();
    }

    private void initOcr() {
        //语言名称
        language = "eng";
        mTessBaseAPI = new TessBaseAPI();
        File languageFile = new File(LANGUAGE_PATH + "eng.traineddata");
        //判断语言包是否存在
        if (!languageFile.exists()) {
            ProgressDialog dialog = new ProgressDialog(this);
            dialog.setMessage(getString(R.string.loading_data));
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            NetManager.getLanguage(dialog, this);
        } else {
            getInitResult();
        }

    }

    private void getInitResult() {
        boolean isSuccess = mTessBaseAPI.init(DATA_PATH, language);
        Toast.makeText(MainActivity.this,isSuccess?R.string.init_successful:R.string.init_failed,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                takePhoto();
                break;

            default:
                break;
        }
    }

    @Override
    public void loadComplete(int code) {
        if (code == Constants.REQUEST_SUCCESS) {
            getInitResult();
        } else {
            Toast.makeText(MainActivity.this, R.string.failed_operation,Toast.LENGTH_SHORT).show();
        }
    }
}