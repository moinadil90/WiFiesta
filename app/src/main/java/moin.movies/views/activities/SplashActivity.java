package moin.movies.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import moin.movies.R;


public class SplashActivity extends AppCompatActivity {

    private final Handler waitHandler = new Handler();
    private Runnable waitCallbackMain = () -> {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        waitHandler.postDelayed(waitCallbackMain, 1000);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
