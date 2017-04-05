package com.speedata.ocr.net;

import android.app.ProgressDialog;

import com.speedata.ocr.DownloadInter;
import com.speedata.ocr.MainActivity;
import com.speedata.ocr.utils.Constants;

import java.io.File;

import okhttp3.Call;
import xyz.reginer.http.RHttp;
import xyz.reginer.http.callback.FileCallBack;

/**
 * ----------Dragon be here!----------/
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃神兽保佑
 * 　　　　┃　　　┃代码无BUG！
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━神兽出没━━━━━━
 * 创   建:Reginer in  2017/2/23 20:28.
 * 联系方式:QQ:282921012
 * 功能描述:网络管理
 */
public class NetManager {


    /**
     * 下载语言包
     *
     * @param dialog        加载框
     * @param downloadInter 回调
     */
    public static void getLanguage(final ProgressDialog dialog, final DownloadInter downloadInter) {
        RHttp.get().url(Urls.OCR_ENG_LANGUAGE).build().execute(new FileCallBack(MainActivity.LANGUAGE_PATH, "eng.traineddata") {
            @Override
            public void onError(Call call, Exception e, int id) {
                downloadInter.loadComplete(Constants.REQUEST_ERROR);
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onResponse(File response, int id) {
                downloadInter.loadComplete(Constants.REQUEST_SUCCESS);
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                super.inProgress(progress, total, id);
                dialog.setMax((int) total);
                dialog.setProgress((int) (progress * total));
                dialog.show();
            }
        });


    }

}
