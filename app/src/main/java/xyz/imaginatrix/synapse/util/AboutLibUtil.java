package xyz.imaginatrix.synapse.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;

import com.danielstone.materialaboutlibrary.ConvenienceBuilder;
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem;
import com.danielstone.materialaboutlibrary.items.MaterialAboutTitleItem;
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard;
import com.danielstone.materialaboutlibrary.model.MaterialAboutList;
import com.danielstone.materialaboutlibrary.util.OpenSourceLicense;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import xyz.imaginatrix.synapse.R;
import xyz.imaginatrix.synapse.ui.activities.LicenseActivity;


public class AboutLibUtil {

    public static MaterialAboutList createMaterialAboutList(final Context c, final int colorIcon, final int theme) {
        MaterialAboutCard.Builder appCardBuilder = new MaterialAboutCard.Builder();

        appCardBuilder.addItem(new MaterialAboutTitleItem.Builder()
                .text("Synapse")
                .desc("Arxiv Android client")
                .icon(R.mipmap.ic_launcher_circle)
                .build());

        appCardBuilder.addItem(ConvenienceBuilder.createVersionActionItem(c,
                new IconicsDrawable(c)
                        .icon(GoogleMaterial.Icon.gmd_info_outline)
                        .color(ContextCompat.getColor(c, colorIcon))
                        .sizeDp(18),
                "Version",
                false));

        appCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text("Changelog")
                .icon(new IconicsDrawable(c)
                        .icon(CommunityMaterial.Icon.cmd_history)
                        .color(ContextCompat.getColor(c, colorIcon))
                        .sizeDp(18))
                .setOnClickAction(ConvenienceBuilder.createWebViewDialogOnClickAction(c, "Releases", "https://github.com/Imaginatrixyz/synapse/releases", true, false))
                .build());

        appCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text("Licenses")
                .icon(new IconicsDrawable(c)
                        .icon(GoogleMaterial.Icon.gmd_book)
                        .color(ContextCompat.getColor(c, colorIcon))
                        .sizeDp(18))
                .setOnClickAction(() -> {
                    Intent intent = new Intent(c, LicenseActivity.class);
                    intent.putExtra(LicenseActivity.ARG_THEME, theme);
                    c.startActivity(intent);
                })
                .build());

        MaterialAboutCard.Builder authorCardBuilder = new MaterialAboutCard.Builder();
        authorCardBuilder.title("Author");
        authorCardBuilder.titleColor(ContextCompat.getColor(c, R.color.colorAccent));

        authorCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text("David Calano")
                .subText("United States")
                .icon(new IconicsDrawable(c)
                        .icon(GoogleMaterial.Icon.gmd_person)
                        .color(ContextCompat.getColor(c, colorIcon))
                        .sizeDp(18))
                .build());

        authorCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text("Fork on GitHub")
                .icon(new IconicsDrawable(c)
                        .icon(CommunityMaterial.Icon.cmd_github_circle)
                        .color(ContextCompat.getColor(c, colorIcon))
                        .sizeDp(18))
                .setOnClickAction(ConvenienceBuilder.createWebsiteOnClickAction(c, Uri.parse("https://github.com/Imaginatrixyz/synapse")))
                .build());

        MaterialAboutCard.Builder convenienceCardBuilder = new MaterialAboutCard.Builder();

        convenienceCardBuilder.title("Social");

        convenienceCardBuilder.addItem(ConvenienceBuilder.createRateActionItem(c,
                new IconicsDrawable(c)
                        .icon(CommunityMaterial.Icon.cmd_star)
                        .color(ContextCompat.getColor(c, colorIcon))
                        .sizeDp(18),
                "Rate this app",
                null
        ));

        convenienceCardBuilder.addItem(ConvenienceBuilder.createEmailItem(c,
                new IconicsDrawable(c)
                        .icon(CommunityMaterial.Icon.cmd_email)
                        .color(ContextCompat.getColor(c, colorIcon))
                        .sizeDp(18),
                "E-Mail",
                true,
                "imaginatrix@protonmail.com",
                "Synapse question"));

        return new MaterialAboutList(appCardBuilder.build(), authorCardBuilder.build(), convenienceCardBuilder.build());
    }

    public static MaterialAboutList createMaterialAboutLicenseList(final Context c, int colorIcon) {

        MaterialAboutCard materialAboutLibraryLicenseCard = ConvenienceBuilder.createLicenseCard(c,
                new IconicsDrawable(c)
                        .icon(GoogleMaterial.Icon.gmd_book)
                        .color(ContextCompat.getColor(c, colorIcon))
                        .sizeDp(18),
                "material-about-library", "2016", "Daniel Stone",
                OpenSourceLicense.APACHE_2);

        MaterialAboutCard androidIconicsLicenseCard = ConvenienceBuilder.createLicenseCard(c,
                new IconicsDrawable(c)
                        .icon(GoogleMaterial.Icon.gmd_book)
                        .color(ContextCompat.getColor(c, colorIcon))
                        .sizeDp(18),
                "Android Iconics", "2016", "Mike Penz",
                OpenSourceLicense.APACHE_2);

        MaterialAboutCard fastAdapterLicenseCard = ConvenienceBuilder.createLicenseCard(c,
                new IconicsDrawable(c)
                        .icon(GoogleMaterial.Icon.gmd_book)
                        .color(ContextCompat.getColor(c, colorIcon))
                        .sizeDp(18),
                "Fast Adapter", "2016", "Mike Penz",
                OpenSourceLicense.APACHE_2);

        // Square
        MaterialAboutCard leakCanaryLicenseCard = ConvenienceBuilder.createLicenseCard(c,
                new IconicsDrawable(c)
                        .icon(GoogleMaterial.Icon.gmd_book)
                        .color(ContextCompat.getColor(c, colorIcon))
                        .sizeDp(18),
                "LeakCanary", "2015", "Square, Inc",
                OpenSourceLicense.APACHE_2);

        MaterialAboutCard retrofitLicenseCard = ConvenienceBuilder.createLicenseCard(c,
                new IconicsDrawable(c)
                        .icon(GoogleMaterial.Icon.gmd_book)
                        .color(ContextCompat.getColor(c, colorIcon))
                        .sizeDp(18),
                "Retrofit", "2013", "Square, Inc",
                OpenSourceLicense.APACHE_2);

        MaterialAboutCard particlesLicenseCard = ConvenienceBuilder.createLicenseCard(c,
                new IconicsDrawable(c)
                        .icon(GoogleMaterial.Icon.gmd_book)
                        .color(ContextCompat.getColor(c, colorIcon))
                        .sizeDp(18),
                "ParticlesDrawable", "2017", "Yaroslav Mytkalyk",
                OpenSourceLicense.APACHE_2);

        MaterialAboutCard glideLicenseCard = ConvenienceBuilder.createLicenseCard(c,
                new IconicsDrawable(c)
                        .icon(GoogleMaterial.Icon.gmd_book)
                        .color(ContextCompat.getColor(c, colorIcon))
                        .sizeDp(18),
                "Glide", "2017", "Bumptech",
                OpenSourceLicense.BSD);

        MaterialAboutCard circleImageViewLicenseCard = ConvenienceBuilder.createLicenseCard(c,
                new IconicsDrawable(c)
                        .icon(GoogleMaterial.Icon.gmd_book)
                        .color(ContextCompat.getColor(c, colorIcon))
                        .sizeDp(18),
                "CircleImageView", "2017", "Henning Dodenhof",
                OpenSourceLicense.APACHE_2);

        MaterialAboutCard rxJavaViewLicenseCard = ConvenienceBuilder.createLicenseCard(c,
                new IconicsDrawable(c)
                        .icon(GoogleMaterial.Icon.gmd_book)
                        .color(ContextCompat.getColor(c, colorIcon))
                        .sizeDp(18),
                "RxJava", "2017", "RxJava Contributors",
                OpenSourceLicense.APACHE_2);

        MaterialAboutCard rxAndroidViewLicenseCard = ConvenienceBuilder.createLicenseCard(c,
                new IconicsDrawable(c)
                        .icon(GoogleMaterial.Icon.gmd_book)
                        .color(ContextCompat.getColor(c, colorIcon))
                        .sizeDp(18),
                "RxAndroid", "2015", "The RxAndroid authors",
                OpenSourceLicense.APACHE_2);

        MaterialAboutCard butterknifeViewLicenseCard = ConvenienceBuilder.createLicenseCard(c,
                new IconicsDrawable(c)
                        .icon(GoogleMaterial.Icon.gmd_book)
                        .color(ContextCompat.getColor(c, colorIcon))
                        .sizeDp(18),
                "Butterknife", "2013", "Jake Wharton",
                OpenSourceLicense.APACHE_2);

        return new MaterialAboutList(
                materialAboutLibraryLicenseCard,
                leakCanaryLicenseCard,
                retrofitLicenseCard,
                androidIconicsLicenseCard,
                fastAdapterLicenseCard,
                particlesLicenseCard,
                glideLicenseCard,
                circleImageViewLicenseCard,
                butterknifeViewLicenseCard,
                rxJavaViewLicenseCard,
                rxAndroidViewLicenseCard
        );
    }
}
