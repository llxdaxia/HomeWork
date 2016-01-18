package cn.alien95.set.http.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.alien95.set.http.request.HttpQueue;
import cn.alien95.set.http.util.DebugUtils;

/**
 * Created by linlongxin on 2015/12/26.
 */
public class HttpRequestImage {

    private final String TAG = "HttpRequestImage";

    private static HttpRequestImage instance;
    private Handler handler;

    private HttpRequestImage() {
        handler = new Handler();
    }

    public static HttpRequestImage getInstance() {
        if (instance == null) {
            synchronized (HttpRequestImage.class) {
                if (instance == null) {
                    instance = new HttpRequestImage();
                }
            }
        }
        return instance;
    }

    public void requestImage(String url, ImageCallBack callBack) {
        if (loadImageFromMemory(url) != null) {
            Log.i(TAG, "从内存读取图片");
            callBack.success(loadImageFromMemory(url));
        } else if (loadImageFromDisk(url) != null) {
            Log.i(TAG, "硬盘读取图片");
            callBack.success(loadImageFromDisk(url));
        } else {
            Log.i(TAG, "从网络获取图片");
            loadImageFromNet(url, callBack);
        }
    }

    /**
     * 图片网络请求压缩处理
     *
     * @param url
     * @param callBack
     */
    public void requestImageWithCompress(String url, int inSampleSize, ImageCallBack callBack) {
        if (loadImageFromMemory(url) != null) {
            Log.i(TAG, "从内存读取图片");
            callBack.success(loadImageFromMemory(url));
        } else if (loadImageFromDisk(url) != null) {
            Log.i(TAG, "硬盘读取图片");
            callBack.success(loadImageFromDisk(url));
        } else {
            Log.i(TAG, "从网络获取图片");
            loadImageFromNetWithCompress(url, inSampleSize, callBack);
        }
    }

    /**
     * 从内存缓存中获取已经从网络获取过的图片
     *
     * @param key
     * @return
     */
    private Bitmap loadImageFromMemory(String key) {
        return MemoryCache.getInstance().getBitmapFromMemCache(key);
    }

    /**
     * 从硬盘缓存中读取图片
     *
     * @param imageUrl
     * @return
     */
    private Bitmap loadImageFromDisk(String imageUrl) {
        return DiskCache.getInstance().readImageFromDisk(imageUrl);
    }

    public HttpURLConnection getHttpUrlConnection(String url) {
        DebugUtils.requestLog(url);
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) new URL(url).openConnection();
            urlConnection.setRequestMethod("GET");
        } catch (IOException e) {
            e.printStackTrace();
        }
//                urlConnection.setDoOutput(true);   //沃日，为毛请求图片不能添加这句
        urlConnection.setDoInput(true);
        urlConnection.setConnectTimeout(10 * 1000);
        urlConnection.setReadTimeout(10 * 1000);
        //对HttpURLConnection对象的一切配置都必须要在connect()函数执行之前完成。
        return urlConnection;
    }

    /**
     * 从网络加载图片
     *
     * @param url
     * @param callBack
     */
    private void loadImageFromNet(final String url, final ImageCallBack callBack) {
        HttpQueue.getInstance().addQuest(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection urlConnection = getHttpUrlConnection(url);
                int respondCode;
                try {
                    urlConnection.connect();
                    final InputStream inputStream = urlConnection.getInputStream();
                    respondCode = urlConnection.getResponseCode();
                    if (respondCode == HttpURLConnection.HTTP_OK) {
                        final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.success(bitmap);
                                MemoryCache.getInstance().putBitmapToCache(url, bitmap);
                                DiskCache.getInstance().writeImageToDisk(url);
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 从网络加载并压缩图片
     *
     * @param url
     * @param inSampleSize
     * @param callBack
     */
    public void loadImageFromNetWithCompress(final String url, final int inSampleSize, final ImageCallBack callBack) {
        HttpQueue.getInstance().addQuest(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection urlConnection = getHttpUrlConnection(url);
                int respondCode;
                try {
                    final InputStream inputStream = urlConnection.getInputStream();
                    respondCode = urlConnection.getResponseCode();
                    if (respondCode == HttpURLConnection.HTTP_OK) {
                        final Bitmap bitmap = ImageUtils.compressBitmapFromInputStream(inputStream, inSampleSize);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.success(bitmap);
                                if (bitmap != null)
                                    MemoryCache.getInstance().putBitmapToCache(url, bitmap);
                                if (inSampleSize <= 1)
                                    DiskCache.getInstance().writeImageToDisk(url);
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
