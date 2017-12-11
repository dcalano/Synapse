package xyz.imaginatrix.synapse.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.imaginatrix.synapse.R;


public class WebActivity extends AppCompatActivity {

    private static final String TAG = WebActivity.class.getSimpleName();
    private static final String ARG_URL = "url";

    @BindView(R.id.web_appbar) AppBarLayout appBar;
    @BindView(R.id.web_toolbar) Toolbar toolbar;
    @BindView(R.id.web_webView) WebView webView;

    private String url = null;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (getIntent() != null && getIntent().getStringExtra(ARG_URL) != null) {
            url = getIntent().getStringExtra(ARG_URL);
        } else if (savedInstanceState != null) {
            url = savedInstanceState.getString(ARG_URL);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override protected void onStart() {
        super.onStart();
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ARG_URL, url);
    }

    @Override protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null)
            url = savedInstanceState.getString(ARG_URL);
    }

    public static Intent createIntent(Context context, @NonNull String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(ARG_URL, url);
        return intent;
    }
}
