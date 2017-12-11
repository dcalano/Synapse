package xyz.imaginatrix.synapse.ui.activities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.MenuItem;

import com.danielstone.materialaboutlibrary.MaterialAboutActivity;
import com.danielstone.materialaboutlibrary.model.MaterialAboutList;

import xyz.imaginatrix.synapse.R;
import xyz.imaginatrix.synapse.util.AboutLibUtil;


public class LicenseActivity extends MaterialAboutActivity {

    public static final String ARG_THEME = "theme";

    @NonNull @Override
    protected MaterialAboutList getMaterialAboutList(@NonNull final Context context) {
        return AboutLibUtil.createMaterialAboutLicenseList(context, R.color.googleBlue);
    }

    @Override protected CharSequence getActivityTitle() {
        return getString(R.string.title_license_activity);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return false;
        }
    }
}
