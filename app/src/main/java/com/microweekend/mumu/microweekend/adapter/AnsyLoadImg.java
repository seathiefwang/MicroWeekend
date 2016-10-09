package com.microweekend.mumu.microweekend.adapter;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;

import com.microweekend.mumu.microweekend.customui.CircleImageView;
import com.microweekend.mumu.microweekend.util.BitmapUtil;

/**
 * Created by mumu on 2016/10/2.
 */
public class AnsyLoadImg {
    private LruCache<String, Bitmap> mMemoryCache;

    public AnsyLoadImg() {
        // 获取到可用内存的最大值，使用内存超出这个值会引起OutOfMemory异常。
        // LruCache通过构造函数传入缓存值，以KB为单位。
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        // 使用最大可用内存值的1/8作为缓存的大小。
        int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // 重写此方法来衡量每张图片的大小，默认返回图片数量。
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    public void addBitmapToMemoryCache(String url, Bitmap bitmap) {
        if (mMemoryCache.get(url) == null && bitmap != null) {
            mMemoryCache.put(url, bitmap);
        }
    }

    public void setBitmapOfImageView(String url, ImageView imageView) {
        Bitmap bitmap=null;
        if (url == null || imageView == null) return;
        if((bitmap=mMemoryCache.get(url)) != null){
            imageView.setImageBitmap(bitmap);
        } else {
            BitmapWorkerTask bwt = new BitmapWorkerTask(imageView);
            bwt.execute(url);
        }
    }

    class BitmapWorkerTask extends AsyncTask<String, Integer, Bitmap> {
        private ImageView imageView;
        public BitmapWorkerTask(ImageView iv) {
            imageView = iv;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //在doInBackground之前运行在主线程
        }
        // 在后台加载图片。
        @Override
        protected Bitmap doInBackground(String... params) {
            final Bitmap bitmap = BitmapUtil.getBit(params[0]);
            addBitmapToMemoryCache(String.valueOf(params[0]), bitmap);
            System.out.println("下载完放入" + params[0]);

//            final Bitmap bitmap = decodeSampledBitmapFromResource(
//                    getResources(), params[0], 100, 100);
            return bitmap;
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //在publishProgress方法被调用后，UI thread将调用这个方法从而在界面上展示任务的进展情况
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            //在doInBackground 执行完成后，onPostExecute 方法将被UI thread调用
            if (bitmap != null)imageView.setImageBitmap(bitmap);
        }

    }
}
