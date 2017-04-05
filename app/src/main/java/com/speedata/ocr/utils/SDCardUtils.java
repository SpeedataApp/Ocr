package com.speedata.ocr.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;

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
 * <p>
 * 创   建:Reginer in  2017/2/17 11:36.
 * 联系方式:QQ:282921012
 * 功能描述:SD卡相关工具类
 */
public class SDCardUtils {

    private SDCardUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断SD卡是否可用.
     *
     * @return true : 可用<br>false : 不可用
     */
    private static boolean isSDCardEnable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }




    /**
     * 获取缓存路径
     * @param mContext  Context
     * @return path
     */
    public static File getDateCache(Context mContext) {
        if (mContext.getExternalCacheDir() != null && mContext.getExternalCacheDir().exists()) {
            return mContext.getExternalCacheDir();
        } else {
            return new File(String.valueOf(mContext.getExternalCacheDir()));
        }
    }

    /**
     * 获取SD卡信息.
     *
     * @return SDCardInfo
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static String getSDCardInfo() {
        SDCardInfo sd = new SDCardInfo();
        if (!isSDCardEnable()) return "sdcard unable!";
        sd.isExist = true;
        StatFs sf = new StatFs(Environment.getExternalStorageDirectory().getPath());
        sd.totalBlocks = sf.getBlockCountLong();
        sd.blockByteSize = sf.getBlockSizeLong();
        sd.availableBlocks = sf.getAvailableBlocksLong();
        sd.availableBytes = sf.getAvailableBytes();
        sd.freeBlocks = sf.getFreeBlocksLong();
        sd.freeBytes = sf.getFreeBytes();
        sd.totalBytes = sf.getTotalBytes();
        return sd.toString();
    }

    public static class SDCardInfo {
        boolean isExist;
        long totalBlocks;
        long freeBlocks;
        long availableBlocks;
        long blockByteSize;
        long totalBytes;
        long freeBytes;
        long availableBytes;

        @Override
        public String toString() {
            return "isExist=" + isExist +
                    "\ntotalBlocks=" + totalBlocks +
                    "\nfreeBlocks=" + freeBlocks +
                    "\navailableBlocks=" + availableBlocks +
                    "\nblockByteSize=" + blockByteSize +
                    "\ntotalBytes=" + totalBytes +
                    "\nfreeBytes=" + freeBytes +
                    "\navailableBytes=" + availableBytes;
        }
    }
}