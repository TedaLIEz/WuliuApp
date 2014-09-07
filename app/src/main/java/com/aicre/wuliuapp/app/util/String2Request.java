package com.aicre.wuliuapp.app.util;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;


import java.io.UnsupportedEncodingException;

/**
 * Created by wei on 14-7-6.
 */
/**
 * A canned request for retrieving the response body at a given URL as a String.
 */
public class String2Request extends Request<String> {
    private final Response.Listener<String> mListener;


    /**
     * the parse charset.
     */
    private String charset = "utf-8";

    /**
     * Creates a new request with the given method.
     *
     * @param method the request {@link Method} to use
     * @param url URL to fetch the string at
     * @param listener Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public String2Request(int method, String url, Response.Listener<String> listener,
                         Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
    }

    /**
     * Creates a new GET request.
     *
     * @param url URL to fetch the string at
     * @param listener Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public String2Request(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        this(Method.POST, url, listener, errorListener);
    }

    /**
     * Creates a new GET request with the given Charset.
     *
     * @param url URL to fetch the string at
     * @param listener Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public String2Request(String url, String charset, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        this(Method.POST, url, listener, errorListener);
        this.charset = charset;
    }

    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            if(charset != null) {
                parsed = new String(response.data, charset);
            } else {
                parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            }
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }

    /**
     * @return the Parse Charset Encoding
     */
    public String getCharset() {
        return charset;
    }

    /**
     * set the Parse Charset Encoding
     * @param charset
     */
    public void setCharset(String charset) {
        this.charset = charset;
    }

}
