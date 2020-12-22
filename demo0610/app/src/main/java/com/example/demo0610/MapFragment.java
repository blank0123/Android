package com.example.demo0610;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;


import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

public class MapFragment extends Fragment {

//    private static String ARG_PARAM = "param_key";
//    private String mParam;
//    private Activity mActivity;



    public void onAttach(Context context) {
        super.onAttach(context);
//        mActivity = (Activity) context;
//        mParam = getArguments().getString(ARG_PARAM);  //获取参数
    }

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_map, container, false);
//        TextView view = root.findViewById(R.id.text);
//        view.setText(mParam);
        root.setClickable(true);

//        ImageButton btn_back = (ImageButton) root.findViewById(R.id.btn_back);
//        btn_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        WebView webView = (WebView) root.findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //webView.getSettings().getJavaScriptEnabled();
        //webView.getSettings().getUseWideViewPort();
        //webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl("http://172.20.10.4:8080/#/map");
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl("http://172.20.10.4:8080/#/map");
                return true;
            }
        });

//        WebView webView = (WebView) root.findViewById(R.id.webView);
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setAppCacheEnabled(true);
//        webView.loadUrl("http://47.97.173.230:8080/#/map");

        return root;
    }

//    public static MapFragment newInstance(String str) {
//        MapFragment frag = new MapFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString(ARG_PARAM, str);
//        frag.setArguments(bundle);   //设置参数
//        return frag;
//    }
}
