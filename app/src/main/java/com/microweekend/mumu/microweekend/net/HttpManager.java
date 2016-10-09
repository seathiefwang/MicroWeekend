package com.microweekend.mumu.microweekend.net;

import android.text.TextUtils;
import android.util.Log;

import com.microweekend.mumu.microweekend.api.MkParameters;
import com.microweekend.mumu.microweekend.util.AlxBitMapUtil;
import com.microweekend.mumu.microweekend.util.Utility;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpProtocolParams;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by mumu on 2016/9/20.
 */
public class HttpManager {
    private static final String BOUNDARY = getBoundry();
    private static final String MP_BOUNDARY;
    private static final String END_MP_BOUNDARY;
    public static String TAG = "HttpManager";

    static {
        MP_BOUNDARY = "--" + BOUNDARY;
        END_MP_BOUNDARY = "--" + BOUNDARY + "--";
    }

    public static String openUrl(String url, String method, MkParameters params, String file) {
        String result = "";

        try {
            HttpClient e = getNewHttpClient();
            Object request = null;
            ByteArrayOutputStream bos = null;
            //e.getParams().setParameter("http.route.default-proxy", NetStateManager.getAPN());//设置代理
            StatusLine status;
            if(method.equals("GET")) {
                url = url + "?" + Utility.encodeUrl(params);
                HttpGet response = new HttpGet(url);
                request = response;
            } else if(method.equals("POST")) {
                HttpPost response1 = new HttpPost(url);
                request = response1;
                status = null;
                String statusCode = params.getValue("content-type");
                bos = new ByteArrayOutputStream();
                byte[] status1;
                if(!TextUtils.isEmpty(file)) {
                    paramToUpload(bos, params);
                    response1.setHeader("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
                    //Utility.UploadImageUtils.revitionPostImageSize(file);//改变图片的大小；
                    //imageContentToUpload(bos, file);
                    imgToUpload(bos, file);//图片经过压缩
                } else {
                    if(statusCode != null) {
                        params.remove("content-type");
                        response1.setHeader("Content-Type", statusCode);
                    } else {
                        response1.setHeader("Content-Type", "application/x-www-form-urlencoded");
                    }

                    String formEntity = Utility.encodeParameters(params);
                    status1 = formEntity.getBytes("UTF-8");
                    bos.write(status1);
                }

                status1 = bos.toByteArray();
                bos.close();
                ByteArrayEntity formEntity1 = new ByteArrayEntity(status1);
                response1.setEntity(formEntity1);

                Log.d(TAG, "HEAD:" + response1.getAllHeaders().toString());
            } else if(method.equals("DELETE")) {
                request = new HttpDelete(url);
            }

            HttpResponse response2 = e.execute((HttpUriRequest)request);
            status = response2.getStatusLine();
            int statusCode1 = status.getStatusCode();
            if(statusCode1 != 200) {
                result = readHttpResponse(response2);
                //throw new Exception(result, statusCode1);
                Log.d(TAG, "statusCode:"+statusCode1+"--"+result);
            } else {
                result = readHttpResponse(response2);
                return result;
            }
        } catch (IOException var12) {
            //throw new IOException(var12);
            Log.d(TAG, ""+var12);
        }
        return null;
    }

    private static HttpClient getNewHttpClient() {
        try {
            KeyStore e = KeyStore.getInstance(KeyStore.getDefaultType());
            e.load((InputStream)null, (char[])null);
            HttpManager.MySSLSocketFactory sf = new HttpManager.MySSLSocketFactory(e);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            BasicHttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000);
            HttpConnectionParams.setSoTimeout(params, 10000);
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, "UTF-8");
            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));
            ThreadSafeClientConnManager ccm = new ThreadSafeClientConnManager(params, registry);
            HttpConnectionParams.setConnectionTimeout(params, 5000);
            HttpConnectionParams.setSoTimeout(params, 20000);
            DefaultHttpClient client = new DefaultHttpClient(ccm, params);
            return client;
        } catch (Exception var6) {
            return new DefaultHttpClient();
        }
    }

    private static String readHttpResponse(HttpResponse response) {
        String result = "";
        HttpEntity entity = response.getEntity();

        try {
            Object inputStream = entity.getContent();
            ByteArrayOutputStream content = new ByteArrayOutputStream();
            Header header = response.getFirstHeader("Content-Encoding");
            if(header != null && header.getValue().toLowerCase().indexOf("gzip") > -1) {
                inputStream = new GZIPInputStream((InputStream)inputStream);
            }

            boolean readBytes = false;
            byte[] sBuffer = new byte[512];

            int readBytes1;
            while((readBytes1 = ((InputStream)inputStream).read(sBuffer)) != -1) {
                content.write(sBuffer, 0, readBytes1);
            }

            result = new String(content.toByteArray(), "UTF-8");
            return result;
        } catch (IllegalStateException var8) {
            ;
        } catch (IOException var9) {
            ;
        }

        return result;
    }

    private static void imageContentToUpload(OutputStream out, String imgpath) throws IOException {
        if(imgpath != null) {
            StringBuilder temp = new StringBuilder();
            temp.append(MP_BOUNDARY).append("\r\n");
            temp.append("Content-Disposition: form-data; name=\"pic\"; filename=\"").append(imgpath).append("\"\r\n");
            String filetype = "image/jpg";
            temp.append("Content-Type: ").append(filetype).append("\r\n\r\n");
            byte[] res = temp.toString().getBytes();
            FileInputStream input = null;
            try {
                out.write(res);
                input = new FileInputStream(imgpath);
                byte[] e = new byte['저'];

                while(true) {
                    int count = input.read(e);
                    if(count == -1) {
                        out.write("\r\n".getBytes());
                        out.write(("\r\n" + END_MP_BOUNDARY).getBytes());
                        return;
                    }

                    out.write(e, 0, count);
                }
            } catch (IOException var15) {
                throw var15;
            } finally {
                if(input != null) {
                    try {
                        input.close();
                    } catch (IOException var14) {
                        throw var14;
                    }
                }

            }
        }
    }

    private static void imgToUpload(OutputStream out, String imgpath) throws IOException {
        if(imgpath != null) {
            StringBuilder temp = new StringBuilder();
            temp.append(MP_BOUNDARY).append("\r\n");
            temp.append("Content-Disposition: form-data; name=\"pic\"; filename=\"").append(imgpath).append("\"\r\n");
            String filetype = "image/jpg";
            temp.append("Content-Type: ").append(filetype).append("\r\n\r\n");
            byte[] res = temp.toString().getBytes();
            out.write(res);
            File input = null;
            try {
                input = new File(imgpath);
                AlxBitMapUtil.compressImage(input, null, out, false);

                out.write("\r\n".getBytes());
                out.write(("\r\n" + END_MP_BOUNDARY).getBytes());
            } catch (IOException e) {
                throw e;
            }
        }
    }

    private static void paramToUpload(OutputStream baos, MkParameters params) throws IOException{
        String key = "";

        for(int loc = 0; loc < params.size(); ++loc) {
            key = params.getKey(loc);
            StringBuilder temp = new StringBuilder(10);
            temp.setLength(0);
            temp.append(MP_BOUNDARY).append("\r\n");
            temp.append("content-disposition: form-data; name=\"").append(key).append("\"\r\n\r\n");
            temp.append(params.getValue(key)).append("\r\n");
            byte[] res = temp.toString().getBytes();

            try {
                baos.write(res);
            } catch (IOException var7) {
                throw var7;
            }
        }

    }

    static String getBoundry() {
        StringBuffer _sb = new StringBuffer();

        for(int t = 1; t < 12; ++t) {
            long time = System.currentTimeMillis() + (long)t;
            if(time % 3L == 0L) {
                _sb.append((char)((int)time) % 9);
            } else if(time % 3L == 1L) {
                _sb.append((char)((int)(65L + time % 26L)));
            } else {
                _sb.append((char)((int)(97L + time % 26L)));
            }
        }

        return _sb.toString();
    }

    private static class MySSLSocketFactory extends SSLSocketFactory {
        SSLContext sslContext = SSLContext.getInstance("TLS");

        public MySSLSocketFactory(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
            super(truststore);
            X509TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            this.sslContext.init((KeyManager[])null, new TrustManager[]{tm}, (SecureRandom)null);
        }

        public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
            return this.sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
        }

        public Socket createSocket() throws IOException {
            return this.sslContext.getSocketFactory().createSocket();
        }
    }
}
