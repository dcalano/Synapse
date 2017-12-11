package xyz.imaginatrix.synapse.ui.fragments;

import android.content.Context;

import com.danielstone.materialaboutlibrary.MaterialAboutFragment;
import com.danielstone.materialaboutlibrary.model.MaterialAboutList;

import xyz.imaginatrix.synapse.R;
import xyz.imaginatrix.synapse.util.AboutLibUtil;


public class AboutFragment extends MaterialAboutFragment {

    public static final String TAG = AboutFragment.class.getSimpleName();

    public static final int THEME_LIGHT_DARKBAR = 1;

    public AboutFragment() { }

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Override protected MaterialAboutList getMaterialAboutList(final Context activityContext) {
        return AboutLibUtil.createMaterialAboutList(activityContext, R.color.googleBlue, THEME_LIGHT_DARKBAR);
    }

    @Override protected int getTheme() {
        return R.style.AppTheme_MaterialAboutActivity_Fragment;
    }
}
