package xyz.imaginatrix.synapse.ui.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import xyz.imaginatrix.synapse.R;
import xyz.imaginatrix.synapse.util.RealmUtil;


public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity.class.getSimpleName();

    @BindView(R.id.splash_infoText) TextView infoText;

    Realm realm = null;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();

        if (realm.isEmpty()) {
            new CreateDatabaseTask().execute();
        } else {
            startActivity(MainActivity.createIntent(this));
            finish();
        }
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private class CreateDatabaseTask extends AsyncTask<Void, Void, Void> {
        @Override protected void onPreExecute() {
            super.onPreExecute();
            infoText.setText("Initializing database...");
        }

        @Override protected Void doInBackground(Void... voids) {
            RealmUtil.firstRunPopulateDatabase();
            return null;
        }

        @Override protected void onPostExecute(Void result) {
            infoText.setText("");
            infoText.setVisibility(View.GONE);

            startActivity(MainActivity.createIntent(SplashActivity.this));
            finish();
        }
    }
}
