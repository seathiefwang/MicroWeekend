package com.microweekend.mumu.microweekend.util;

import android.app.AlertDialog.Builder;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.microweekend.mumu.microweekend.api.MkParameters;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import org.json.JSONException;
import org.json.JSONObject;

public class Utility {
    private static char[] encodes = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
    private static byte[] decodes = new byte[256];

    public Utility() {
    }

    public static Bundle parseUrl(String url) {
        try {
            URL e = new URL(url);
            Bundle b = decodeUrl(e.getQuery());
            b.putAll(decodeUrl(e.getRef()));
            return b;
        } catch (MalformedURLException var3) {
            return new Bundle();
        }
    }

    public static void showToast(String content, Context ct) {
        Toast.makeText(ct, content, Toast.LENGTH_SHORT).show();
    }

    public static Bundle decodeUrl(String s) {
        Bundle params = new Bundle();
        if(s != null) {
            String[] array = s.split("&");
            String[] var6 = array;
            int var5 = array.length;

            for(int var4 = 0; var4 < var5; ++var4) {
                String parameter = var6[var4];
                String[] v = parameter.split("=");
                params.putString(URLDecoder.decode(v[0]), URLDecoder.decode(v[1]));
            }
        }

        return params;
    }

    public static String encodeUrl(MkParameters parameters) {
        if(parameters == null) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder();
            boolean first = true;

            for(int loc = 0; loc < parameters.size(); ++loc) {
                if(first) {
                    first = false;
                } else {
                    sb.append("&");
                }

                String _key = parameters.getKey(loc);
                String _value = parameters.getValue(_key);
                if(_value == null) {
                    Log.i("encodeUrl", "key:" + _key + " \'s value is null");
                } else {
                    sb.append(URLEncoder.encode(parameters.getKey(loc)) + "=" + URLEncoder.encode(parameters.getValue(loc)));
                }
                Log.i("encodeUrl", sb.toString());
            }

            return sb.toString();
        }
    }

    public static String encodeParameters(MkParameters httpParams) {
        if(httpParams != null && !isBundleEmpty(httpParams)) {
            StringBuilder buf = new StringBuilder();
            int j = 0;

            for(int loc = 0; loc < httpParams.size(); ++loc) {
                String key = httpParams.getKey(loc);
                if(j != 0) {
                    buf.append("&");
                }

                try {
                    buf.append(URLEncoder.encode(key, "UTF-8")).append("=").append(URLEncoder.encode(httpParams.getValue(key), "UTF-8"));
                } catch (UnsupportedEncodingException var6) {
                    ;
                }

                ++j;
            }

            return buf.toString();
        } else {
            return "";
        }
    }
/**
    public static Bundle formBundle(Oauth2AccessToken oat) {
        Bundle params = new Bundle();
        params.putString("access_token", oat.getToken());
        params.putString("refresh_token", oat.getRefreshToken());
        params.putString("expires_in", String.valueOf(oat.getExpiresTime()));
        return params;
    }
*/
    public static Bundle formErrorBundle(Exception e) {
        Bundle params = new Bundle();
        params.putString("error", e.getMessage());
        return params;
    }

    public static void showAlert(Context context, String title, String text) {
        Builder alertBuilder = new Builder(context);
        alertBuilder.setTitle(title);
        alertBuilder.setMessage(text);
        alertBuilder.create().show();
    }

    private static boolean isBundleEmpty(MkParameters bundle) {
        return bundle == null || bundle.size() == 0;
    }

    public static String encodeBase62(byte[] data) {
        StringBuffer sb = new StringBuffer(data.length * 2);
        int pos = 0;
        int val = 0;

        for(int c = 0; c < data.length; ++c) {
            val = val << 8 | data[c] & 255;

            for(pos += 8; pos > 5; val &= (1 << pos) - 1) {
                pos -= 6;
                char c1 = encodes[val >> pos];
                sb.append(c1 == 105?"ia":(c1 == 43?"ib":(c1 == 47?"ic":Character.valueOf(c1))));
            }
        }

        if(pos > 0) {
            char var6 = encodes[val << 6 - pos];
            sb.append(var6 == 105?"ia":(var6 == 43?"ib":(var6 == 47?"ic":Character.valueOf(var6))));
        }

        return sb.toString();
    }

    public static byte[] decodeBase62(String string) {
        if(string == null) {
            return null;
        } else {
            char[] data = string.toCharArray();
            ByteArrayOutputStream baos = new ByteArrayOutputStream(string.toCharArray().length);
            int pos = 0;
            int val = 0;

            for(int i = 0; i < data.length; ++i) {
                char c = data[i];
                if(c == 105) {
                    ++i;
                    c = data[i];
                    char var10000;
                    if(c == 97) {
                        var10000 = 105;
                    } else if(c == 98) {
                        var10000 = 43;
                    } else if(c == 99) {
                        var10000 = 47;
                    } else {
                        --i;
                        var10000 = data[i];
                    }

                    c = var10000;
                }

                val = val << 6 | decodes[c];

                for(pos += 6; pos > 7; val &= (1 << pos) - 1) {
                    pos -= 8;
                    baos.write(val >> pos);
                }
            }

            return baos.toByteArray();
        }
    }

    private static boolean deleteDependon(File file, int maxRetryCount) {
        int retryCount = 1;
        maxRetryCount = maxRetryCount < 1?5:maxRetryCount;
        boolean isDeleted = false;
        if(file != null) {
            while(!isDeleted && retryCount <= maxRetryCount && file.isFile() && file.exists()) {
                if(!(isDeleted = file.delete())) {
                    ++retryCount;
                }
            }
        }

        return isDeleted;
    }

    private static void mkdirs(File dir_) {
        if(dir_ != null) {
            if(!dir_.exists() && !dir_.mkdirs()) {
                throw new RuntimeException("fail to make " + dir_.getAbsolutePath());
            }
        }
    }

    private static void createNewFile(File file_) {
        if(file_ != null) {
            if(!__createNewFile(file_)) {
                throw new RuntimeException(file_.getAbsolutePath() + " doesn\'t be created!");
            }
        }
    }

    private static void delete(File f) {
        if(f != null && f.exists() && !f.delete()) {
            throw new RuntimeException(f.getAbsolutePath() + " doesn\'t be deleted!");
        }
    }

    private static boolean __createNewFile(File file_) {
        if(file_ == null) {
            return false;
        } else {
            makesureParentExist(file_);
            if(file_.exists()) {
                delete(file_);
            }

            try {
                return file_.createNewFile();
            } catch (IOException var2) {
                var2.printStackTrace();
                return false;
            }
        }
    }

    private static boolean deleteDependon(String filepath, int maxRetryCount) {
        return TextUtils.isEmpty(filepath)?false:deleteDependon(new File(filepath), maxRetryCount);
    }

    private static boolean deleteDependon(String filepath) {
        return deleteDependon((String)filepath, 0);
    }

    private static boolean doesExisted(File file) {
        return file != null && file.exists();
    }

    private static boolean doesExisted(String filepath) {
        return TextUtils.isEmpty(filepath)?false:doesExisted(new File(filepath));
    }

    private static void makesureParentExist(File file_) {
        if(file_ != null) {
            File parent = file_.getParentFile();
            if(parent != null && !parent.exists()) {
                mkdirs(parent);
            }

        }
    }

    private static void makesureFileExist(File file) {
        if(file != null) {
            if(!file.exists()) {
                makesureParentExist(file);
                createNewFile(file);
            }

        }
    }

    private static void makesureFileExist(String filePath_) {
        if(filePath_ != null) {
            makesureFileExist(new File(filePath_));
        }
    }

    public static boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager)mContext.getSystemService("connectivity");
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetInfo != null && activeNetInfo.getType() == 1;
    }

    public static Bundle errorSAX(String responsetext) {
        Bundle mErrorBun = new Bundle();
        if(responsetext != null && responsetext.indexOf("{") >= 0) {
            try {
                JSONObject e = new JSONObject(responsetext);
                mErrorBun.putString("error", e.optString("error"));
                mErrorBun.putString("error_code", e.optString("error_code"));
                mErrorBun.putString("error_description", e.optString("error_description"));
            } catch (JSONException var3) {
                mErrorBun.putString("error", "JSONExceptionerror");
            }
        }

        return mErrorBun;
    }

    public static boolean isNetworkAvailable(Context ct) {
        ConnectivityManager connectivity = (ConnectivityManager)ct.getSystemService("connectivity");
        if(connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if(info != null) {
                NetworkInfo[] var6 = info;
                int var5 = info.length;

                for(int var4 = 0; var4 < var5; ++var4) {
                    NetworkInfo name = var6[var4];
                    if(State.CONNECTED == name.getState()) {
                        return true;
                    }
                }
            }

            return false;
        }
    }
/**
    public static String getSign(Context context, String pkgName) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(pkgName, 64);
        } catch (NameNotFoundException var5) {
            return null;
        }

        for(int j = 0; j < packageInfo.signatures.length; ++j) {
            byte[] str = packageInfo.signatures[j].toByteArray();
            if(str != null) {
                return MD5.hexdigest(str);
            }
        }
        return null;
    }
*/
    public static final class UploadImageUtils {
        public UploadImageUtils() {
        }

        private static void revitionImageSizeHD(String picfile, int size, int quality) throws IOException {
            if(size <= 0) {
                throw new IllegalArgumentException("size must be greater than 0!");
            } else if(!Utility.doesExisted(picfile)) {
                throw new FileNotFoundException(picfile == null?"null":picfile);
            } else if(!BitmapUtil.verifyBitmap(picfile)) {
                throw new IOException("");
            } else {
                int photoSizesOrg = 2 * size;
                FileInputStream input = new FileInputStream(picfile);
                Options opts = new Options();
                opts.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(input, (Rect)null, opts);

                try {
                    input.close();
                } catch (Exception var14) {
                    var14.printStackTrace();
                }

                boolean rate = false;

                int temp;
                for(temp = 0; opts.outWidth >> temp > photoSizesOrg || opts.outHeight >> temp > photoSizesOrg; ++temp) {
                    ;
                }

                opts.inSampleSize = (int)Math.pow(2.0D, (double)temp);
                opts.inJustDecodeBounds = false;
                Bitmap var16 = safeDecodeBimtapFile(picfile, opts);
                if(var16 == null) {
                    throw new IOException("Bitmap decode error!");
                } else {
                    Utility.deleteDependon(picfile);
                    Utility.makesureFileExist(picfile);
                    int org = var16.getWidth() > var16.getHeight()?var16.getWidth():var16.getHeight();
                    float rateOutPut = (float)size / (float)org;
                    if(rateOutPut < 1.0F) {
                        Bitmap output;
                        while(true) {
                            try {
                                output = Bitmap.createBitmap((int)((float)var16.getWidth() * rateOutPut), (int)((float)var16.getHeight() * rateOutPut), Config.ARGB_8888);
                                break;
                            } catch (OutOfMemoryError var15) {
                                System.gc();
                                rateOutPut = (float)((double)rateOutPut * 0.8D);
                            }
                        }

                        if(output == null) {
                            var16.recycle();
                        }

                        Canvas e = new Canvas(output);
                        Matrix matrix = new Matrix();
                        matrix.setScale(rateOutPut, rateOutPut);
                        e.drawBitmap(var16, matrix, new Paint());
                        var16.recycle();
                        var16 = output;
                    }

                    FileOutputStream var17 = new FileOutputStream(picfile);
                    if(opts != null && opts.outMimeType != null && opts.outMimeType.contains("png")) {
                        var16.compress(CompressFormat.PNG, quality, var17);
                    } else {
                        var16.compress(CompressFormat.JPEG, quality, var17);
                    }

                    try {
                        var17.close();
                    } catch (Exception var13) {
                        var13.printStackTrace();
                    }

                    var16.recycle();
                }
            }
        }

        private static void revitionImageSize(String picfile, int size, int quality) throws IOException {
            if(size <= 0) {
                throw new IllegalArgumentException("size must be greater than 0!");
            } else if(!Utility.doesExisted(picfile)) {
                throw new FileNotFoundException(picfile == null?"null":picfile);
            } else if(!BitmapUtil.verifyBitmap(picfile)) {
                throw new IOException("");
            } else {
                FileInputStream input = new FileInputStream(picfile);
                Options opts = new Options();
                opts.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(input, (Rect)null, opts);

                try {
                    input.close();
                } catch (Exception var10) {
                    var10.printStackTrace();
                }

                boolean rate = false;

                int temp;
                for(temp = 0; opts.outWidth >> temp > size || opts.outHeight >> temp > size; ++temp) {
                    ;
                }

                opts.inSampleSize = (int)Math.pow(2.0D, (double)temp);
                opts.inJustDecodeBounds = false;
                Bitmap var11 = safeDecodeBimtapFile(picfile, opts);
                if(var11 == null) {
                    throw new IOException("Bitmap decode error!");
                } else {
                    Utility.deleteDependon(picfile);
                    Utility.makesureFileExist(picfile);
                    FileOutputStream output = new FileOutputStream(picfile);
                    if(opts != null && opts.outMimeType != null && opts.outMimeType.contains("png")) {
                        var11.compress(CompressFormat.PNG, quality, output);
                    } else {
                        var11.compress(CompressFormat.JPEG, quality, output);
                    }

                    try {
                        output.close();
                    } catch (Exception var9) {
                        var9.printStackTrace();
                    }

                    var11.recycle();
                }
            }
        }

        public static boolean revitionPostImageSize(String picfile) {
            try {
                //if(Weibo.isWifi()) {
                if(false) {
                    revitionImageSizeHD(picfile, 1600, 75);
                } else {
                    revitionImageSize(picfile, 1024, 75);
                }

                return true;
            } catch (IOException var2) {
                var2.printStackTrace();
                return false;
            }
        }

        private static Bitmap safeDecodeBimtapFile(String bmpFile, Options opts) {
            Options optsTmp = opts;
            if(opts == null) {
                optsTmp = new Options();
                optsTmp.inSampleSize = 1;
            }

            Bitmap bmp = null;
            FileInputStream input = null;
            boolean MAX_TRIAL = true;
            int i = 0;

            while(i < 5) {
                try {
                    input = new FileInputStream(bmpFile);
                    bmp = BitmapFactory.decodeStream(input, (Rect)null, opts);

                    try {
                        input.close();
                    } catch (IOException var9) {
                        var9.printStackTrace();
                    }
                    break;
                } catch (OutOfMemoryError var11) {
                    var11.printStackTrace();
                    optsTmp.inSampleSize *= 2;

                    try {
                        input.close();
                    } catch (IOException var10) {
                        var10.printStackTrace();
                    }

                    ++i;
                } catch (FileNotFoundException var12) {
                    break;
                }
            }

            return bmp;
        }
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }

    /**
     * Try to return the absolute file path from the given Uri
     *
     * @param context
     * @param uri
     * @return the file path or null
     */
    public static String getPathOfUri( final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
}