package es.uma.conectarbackend;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import es.uma.conectarbackend.utils.NetworkUtil;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "ConectarBackend-MAIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NetworkUtil.getInstance(this).createLoginRequest(TAG, findViewById(R.id.text));
    }

    @Override
    protected void onStop() {
        super.onStop();

        NetworkUtil.getInstance(this).cancelAllRequests(TAG);
    }
}