package com.mapzen.android.app;

import com.mapzen.tangram.HttpHandler;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;

class TileHttpHandler extends HttpHandler {
    static final String PARAM_API_KEY = "?api_key=";

    private final String apiKey;

    TileHttpHandler(String apiKey) {
        this.apiKey = apiKey;
    }

    TileHttpHandler(String apiKey, Request.Builder okRequestBuilder) {
        this.apiKey = apiKey;
        this.okRequestBuilder = okRequestBuilder;
    }

    @Override public boolean onRequest(String url, Callback cb) {
        final String urlWithKey = url + PARAM_API_KEY + apiKey;
        return super.onRequest(urlWithKey, cb);
    }

    @Override public void onCancel(String url) {
        final String urlWithKey = url + PARAM_API_KEY + apiKey;
        super.onCancel(urlWithKey);
    }
}
