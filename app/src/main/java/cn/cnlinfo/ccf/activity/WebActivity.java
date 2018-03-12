package cn.cnlinfo.ccf.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.cnlinfo.ccf.CCFApplication;
import cn.cnlinfo.ccf.R;

public class WebActivity extends BaseActivity {

    @BindView(R.id.ibt_back)
    ImageButton ibtBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ibt_add)
    ImageButton ibtAdd;
    @BindView(R.id.wv)
    WebView wv;

    private Intent intent;
    private String url;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        ibtAdd.setVisibility(View.INVISIBLE);
        intent = getIntent();
        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
       /* webSettings.setUseWideViewPort(true);//关键点
        webSettings.setDisplayZoomControls(false);
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮
        webSettings.setSupportZoom(true); // 支持缩放
        webSettings.setLoadWithOverviewMode(true);*/

       /* DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int mDensity = metrics.densityDpi;
        if (mDensity == 240) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == 160) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        } else if(mDensity == 120) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        }else if(mDensity == DisplayMetrics.DENSITY_XHIGH){
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        }else if (mDensity == DisplayMetrics.DENSITY_TV){
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        }else{
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        }*/

        /**
         * 用WebView显示图片，可使用这个参数 设置网页布局类型： 1、LayoutAlgorithm.NARROW_COLUMNS ：
         * 适应内容大小 2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放
         */
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        //设置WebView加载页面的模式
       // webSettings.setLoadWithOverviewMode(true);
        //设置是否使用WebView推荐使用的窗口
        //webSettings.setUseWideViewPort(true);
        url = intent.getStringExtra("url");
       /* if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wv.addJavascriptInterface(new JsInterface(), "wv");
        } else {
            wv.addJavascriptInterface(this, "wv");
        }*/
        //wv.loadUrl("file:///android_asset/js_webView");
        wv.loadUrl(url);
       // wv.loadUrl("file:///android_asset/index.html");

        wv.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Logger.d(url+"\n"+message+"\n"+result.toString());
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                tvTitle.setText(title);
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
                //ibtBack.setImageBitmap(icon);
            }

        });

        wv.setWebViewClient(new WebViewClient() {

          /*  @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                view.loadUrl("javascript:window.wv.getSource('<head>'+" +
                        "document.getElementsByTagName('html')[0].innerHTML+'</head>');");
                Logger.d("onPageFinished");
                super.onPageFinished(view, url);
            }*/

          //这种方式只支持android5.1及以上版本，在webview中跳转超链接，避免浏览器加载超链接
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());//不用系统默认浏览器
                return true;
            }

            /**
             * 过时的方法一般能够支持所有手机,这个方法支持android低版本和高版本
             * @param view
             * @param url
             * @return
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);//不用系统默认浏览器
                return true;
            }


            // 重写此方法可以让webview处理https请求
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, android.net.http.SslError error) {
                handler.proceed();
            }
        });

        ibtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * 调用js中的方法用wv的post方法中调用
                 */
/*                wv.post(new Runnable() {
                    @Override
                    public void run() {
                        wv.loadUrl("javascript:alerts()");
                    }
                });*/

                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyWebView();
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onDestroy();
        CCFApplication.getRefWatcher().watch(this);
    }

    private void destroyWebView() {
        if (wv != null) {
            wv.pauseTimers();
            wv.removeAllViews();
            wv.destroy();
            wv = null;
        }
    }
/*    @JavascriptInterface
    public void getSource(String msg) {
        Logger.d(msg);
        toast(msg);
    }

    @JavascriptInterface
    public void test(){
        toast("hello world");
    }

    public class JsInterface {
        public void test() {
            toast("hello world");
        }
    }*/
}
