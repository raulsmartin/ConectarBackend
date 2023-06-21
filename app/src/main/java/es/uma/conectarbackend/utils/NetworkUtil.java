package es.uma.conectarbackend.utils;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import es.uma.conectarbackend.R;

public class NetworkUtil {
    private static NetworkUtil instance;
    private RequestQueue requestQueue;
    private final Context ctx;

    private NetworkUtil(Context context) {
        // getApplicationContext() is key, it keeps you from leaking the
        // Activity or BroadcastReceiver if someone passes one in.
        ctx = context.getApplicationContext();
        requestQueue = getRequestQueue();
    }

    public static synchronized NetworkUtil getInstance(Context context) {
        if (instance == null) {
            instance = new NetworkUtil(context);
        }
        return instance;
    }

    public static String getToken() {
        return "eyJhbGciOiJSUzI1NiIsImtpZCI6IjA1MTUwYTEzMjBiOTM5NWIwNTcxNjg3NzM3NjkyODUwOWJhYjQ0YWMiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiI1MjM4MjkzNjkxMDQtNjk0ZzhkNWs1NjJsdnJyZG43djhzNzRydWQzMGxha2EuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI1MjM4MjkzNjkxMDQtZWE2ZTU4NzVocTQzOGNtZnJmdmZmaXE1OGNtNDNvaWEuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMTIwMDE3MDExMTU4NzI5NDcyODciLCJlbWFpbCI6InJhdWxzYWxhcy5tYXJ0aW4wMkBnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6IlJhw7psIFNhbGFzIE1hcnTDrW4iLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDMuZ29vZ2xldXNlcmNvbnRlbnQuY29tL2EvQUFjSFR0ZHVGU0IzLXlsSUFaMGVwWWFtdGhCV2FNU3F1NVZwZEVoNnZud249czk2LWMiLCJnaXZlbl9uYW1lIjoiUmHDumwiLCJmYW1pbHlfbmFtZSI6IlNhbGFzIE1hcnTDrW4iLCJsb2NhbGUiOiJlcyIsImlhdCI6MTY4NzE3NDIxNCwiZXhwIjoxNjg3MTc3ODE0fQ.ejhbw04mjG2mHRmrRZie72ywOwZCW3m9B0oIQTjmObfK8lQSeuT_mK66UUwH22EDiiHUnCT0hofFTAhDtY1zRtvaI8g8KJJy8Tb5svPDPkgpClTgtLfjSVdeRB9jFPNb5np24cpx6ZIVxZymvicrgag6W1BtLl4OxN65ihLJ0z9UqR-QXJ7aR1ei7mYZ6otfg0vo0geWDEiiwxACh84m6i_V2fPbBkB9tyJK8IJEb2GPNTI-ujfkXH9lLsAHt6rEiotHrQmEigdgeXr8GD6ylk1yv6vbtpGc5nVP-WUu_rK-laMUfah_SkbgtxI__11-UyA5flWZsu1ayXM7yHa_jA";
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx);
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public void cancelAllRequests(Object tag) {
        getRequestQueue().cancelAll(tag);
    }

    public void createLoginRequest(Object tag, TextView output) {
        String url = getURL("login/");

        // Request a json response from the provided URL.
        JsonRequest request = new JsonRequest(Request.Method.POST, url, null,
                response -> {
                    // Display the response string.
                    output.setText(ctx.getString(R.string.successText, response.toString()));
                },
                error -> {
                    // Display the error message.
                    output.setText(ctx.getString(R.string.errorText, error.networkResponse != null ? error.networkResponse.statusCode : 0, error.getMessage()));
                });
        request.setTag(tag);
        // Add the request to the RequestQueue.
        addToRequestQueue(request);
    }

    public String getURL(String suffix) {
        return ctx.getString(R.string.backend_url).concat(suffix);
    }

    public static class JsonRequest extends JsonObjectRequest {

        public JsonRequest(int method, String url, @Nullable JSONObject jsonRequest, Response.Listener<JSONObject> listener, @Nullable Response.ErrorListener errorListener) {
            super(method, url, jsonRequest, listener, errorListener);
        }

        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String> headers = super.getHeaders();
            if (headers == null || headers.equals(Collections.emptyMap())) {
                headers = new HashMap<>();
            }
            headers.put("Content-Type", "application/json");
            headers.put("Authorization", NetworkUtil.getToken());
            return headers;
        }
    }
}

