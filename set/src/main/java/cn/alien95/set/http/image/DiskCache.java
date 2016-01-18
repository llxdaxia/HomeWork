package cn.alien95.set.http.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.alien95.set.http.request.HttpQueue;
import cn.alien95.set.util.Utils;

/**
 * Created by linlongxin on 2015/12/29.
 */
public class DiskCache {

    private final String IMAGE_CACHE_PATH = "IMAGE_CACHE";
    private DiskLruCache diskLruCache;
    private static DiskCache instance;

    private DiskCache() {
        try {
            File cacheDir = ImageUtils.setDiskCacheDir(IMAGE_CACHE_PATH);
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            //20MB硬盘缓存
            diskLruCache = DiskLruCache.open(cacheDir, Utils.getAppVersion(), 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DiskCache getInstance() {
        if (instance == null) {
            synchronized (DiskCache.class) {
                if (instance == null)
                    instance = new DiskCache();
            }
        }
        return instance;
    }

    /**
     * 写入缓存到硬盘
     *
     * @param imageUrl 图片地址
     */
    public void writeImageToDisk(final String imageUrl) {
        final String key = ImageUtils.MD5(imageUrl);
        HttpQueue.getInstance().addQuest(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpURLConnection urlConnection = (HttpURLConnection) new URL(imageUrl).openConnection();
                    urlConnection.setRequestMethod("GET");
                    InputStream in = urlConnection.getInputStream();
                    DiskLruCache.Editor editor;
                    editor = diskLruCache.edit(key);
                    if (editor != null) {
                        OutputStream outputStream = editor.newOutputStream(0);
                        if (loadImageToStream(in, outputStream)) {
                            editor.commit();
                        } else {
                            editor.abort();
                        }
                    }
                    diskLruCache.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 读取硬盘缓存
     *
     * @param imageUrl 图片地址
     * @return
     */
    public Bitmap readImageFromDisk(String imageUrl) {
        try {
            String key = ImageUtils.MD5(imageUrl);
            DiskLruCache.Snapshot snapShot = diskLruCache.get(key);
            if (snapShot != null) {
                InputStream is = snapShot.getInputStream(0);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                return bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取输入流到硬盘
     *
     * @param outputStream
     * @return
     */
    public boolean loadImageToStream(InputStream in, OutputStream outputStream) {
        BufferedOutputStream out;
        BufferedInputStream inputStream = new BufferedInputStream(in, 24 * 1024);
        try {
            out = new BufferedOutputStream(outputStream, 8 * 1024);
            byte[] buffer = new byte[1024 * 8];
            while (inputStream.read(buffer, 0, buffer.length) != -1) {
                out.write(buffer);
            }
            return true;
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
