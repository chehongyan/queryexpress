package com.example.myapplication.utils;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;

/**
 * Created by 59427 on 2016/6/7.
 */
public class RequestUtil {
    private static RequestQueue requestQueue;

    public static RequestQueue getRequestQueue(Context context) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context);
        }
        return requestQueue;
    }

    public static void execute(Context context, String url, Response.Listener listener, Response.ErrorListener errorListener) {
        getRequestQueue(context);
        StringRequest stringRequest = new StringRequest(url, listener, errorListener);
        requestQueue.add(stringRequest);
    }

    public static void executeJSON(Context context, String url, Response.Listener listener, Response.ErrorListener errorListener) {

        getRequestQueue(context);
        //{name:value}
        JsonObjectRequest request = new JsonObjectRequest(url, null, listener, errorListener);

        //request.cancel();
        requestQueue.add(request);
    }

    public static void exceuteJSONObject(final Context context, String url, Response.Listener listener) {

        getRequestQueue(context);
        //{name:value}
        JsonObjectRequest request = new JsonObjectRequest(url, null, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));//请求超时时间

        //request.cancel();
        requestQueue.add(request);
    }
    public static void exceuteJSONObject1(final Context context, String url, Response.Listener listener) {

        getRequestQueue(context);
        //{name:value}
        JsonObjectRequest request = new JsonObjectRequest(url, null, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast toast = new Toast(context);
                View view=View.inflate(context, R.layout.toast_query_result,null);
                toast.setView(view);
                toast.show();

            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));//请求超时时间

        //request.cancel();
        requestQueue.add(request);
    }




}
