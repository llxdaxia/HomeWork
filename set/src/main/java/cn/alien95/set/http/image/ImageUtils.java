package cn.alien95.set.http.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by linlongxin on 2015/12/29.
 */
public class ImageUtils {

    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

    /**
     * 压缩从网络获取的图片，加载到内存
     *
     * @param inputStream 网络获取的输入流
     * @param inSampleSize  压缩的长或宽比例，大小缩小平方倍
     * @return
     */
    public static Bitmap compressBitmapFromInputStream(InputStream inputStream,int inSampleSize) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;
        return BitmapFactory.decodeStream(inputStream,null, options);
    }

    /**
     * 为图片压缩做准备，通过设置inSampleSize参数来压缩
     * 通过reqWidth和reqHeight来计算出合理的inSampleSize
     *
     * @param options   BitmapFactory.Options bitmap参数
     * @param reqWidth  需要设置的宽
     * @param reqHeight 需要设置的高
     * @return int 返回一个inSampleSize值来压缩图片
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 设置图片硬盘缓存路径
     *
     * @param uniqueName 路径名，在APP缓存目录下
     * @return
     */
    public static File setDiskCacheDir(String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = mContext.getExternalCacheDir().getPath();
        } else {
            cachePath = mContext.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * MD5加密，把字符串加密成32位乱码
     *
     * @param key 传入加密的字符串
     * @return 返回MD5加密后的字符串
     */
    public static String MD5(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

}
