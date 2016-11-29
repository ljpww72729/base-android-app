package com.ww.lp.base.modules.webview;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ww.lp.base.BaseActivity;
import com.ww.lp.base.R;

/**
 *
 * Created by LinkedME06 on 16/11/25.
 */

public class NormalWVActvity extends BaseActivity {

    private WebView normal_wv;
    public static final String LOADURL = "loadUrl";
    private String loadUrl = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_wv_act);
        if (getIntent() != null) {
            loadUrl = getIntent().getStringExtra(LOADURL);
        }
        initView();
    }

    private void initView() {
        normal_wv = (WebView) findViewById(R.id.normal_wv);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            normal_wv.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            normal_wv.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        }
        normal_wv.getSettings().setJavaScriptEnabled(true);
        normal_wv.loadUrl(loadUrl);
        normal_wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                showLoading();
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                showContent();
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(final WebView view, WebResourceRequest request, WebResourceError error) {
                showErrorDefault(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        normal_wv.loadUrl(loadUrl);
                    }
                });
                super.onReceivedError(view, request, error);
            }
        });
    }

}
