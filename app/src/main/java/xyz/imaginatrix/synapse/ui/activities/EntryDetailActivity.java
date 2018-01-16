package xyz.imaginatrix.synapse.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;
import xyz.imaginatrix.synapse.R;
import xyz.imaginatrix.synapse.data.arxiv.ArxivAPI;
import xyz.imaginatrix.synapse.data.arxiv.ArxivClient;
import xyz.imaginatrix.synapse.data.arxiv.model.SearchResult;
import xyz.imaginatrix.synapse.data.models.Entry;
import xyz.imaginatrix.synapse.ui.fragments.EntryDocumentFragment;
import xyz.imaginatrix.synapse.ui.fragments.EntryMetaFragment;
import xyz.imaginatrix.synapse.util.ArxivUtil;

/**
 * Three entry vectors:
 *  - New internal load
 *  - Recycled load from savedInstanceState
 *  - External load from user's web browser
 */
public class EntryDetailActivity extends AppCompatActivity {

    private static final String TAG = EntryDetailActivity.class.getSimpleName();
    private static final String ARG_ENTRY = "entry";

    @BindView(R.id.entry_detail_coordinator) CoordinatorLayout viewRoot;
    @BindView(R.id.entry_detail_appbar) AppBarLayout appBar;
    @BindView(R.id.entry_detail_toolbar) Toolbar toolbar;
    @BindView(R.id.entry_detail_tabLayout) TabLayout tabLayout;
    @BindView(R.id.entry_detail_viewPager) ViewPager viewPager;
    @BindView(R.id.entry_detail_bookmark_fab) FloatingActionButton bookmarkButton;

    // Composite Disposable for handling URL to app entry loading
    private CompositeDisposable disposables = new CompositeDisposable();

    private Realm realm = null;
    private Entry entry = null;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
        setContentView(R.layout.activity_entry_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        if (intent != null) {
            // Incoming from web browser
            Uri incomingUrl = intent.getData();
            if (incomingUrl != null) {
                Log.d(TAG, "Loading entry from URI intent: [ " + incomingUrl.toString() + " ]");
                List<String> params = incomingUrl.getPathSegments();
                String entryId = params.get(1);

                // Retrieve entry object from API
                ArxivAPI arxivApi = ArxivClient.createService(ArxivAPI.class);
                disposables.add(
                        arxivApi.getEntry(entryId)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeWith(new DisposableObserver<SearchResult>() {
                                       @Override public void onNext(SearchResult searchResult) {
                                           entry = ArxivUtil.parseRawEntry( searchResult.entries.get(0) );
                                       }

                                       @Override public void onError(Throwable e) {
                                           Log.e(TAG, e.getMessage(), e.fillInStackTrace());
                                       }

                                       @Override public void onComplete() {
                                            setEntryUI();
                                       }
                                   }));
            } else if (intent.getParcelableExtra(ARG_ENTRY) != null) {
                entry = intent.getParcelableExtra(ARG_ENTRY);
                setEntryUI();
            }
            // Reconstruct entry from saved activity state
        } else if (savedInstanceState != null && savedInstanceState.containsKey(ARG_ENTRY)) {
            entry = savedInstanceState.getParcelable(ARG_ENTRY);
            setEntryUI();
        }
    }

    @Override protected void onDestroy() {
        realm.close();
        disposables.clear();
        super.onDestroy();
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ARG_ENTRY, entry);
    }

    @Override protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            entry = savedInstanceState.getParcelable(ARG_ENTRY);
        }
    }

    /**
     * Abstracted method to load the UI.
     * This method was previously onStart, but in order to load external URLs an API call must be made
     * and the result loaded asynchronously. Abstracting the previous code allows the ui to load
     * upon data load
     */
    private void setEntryUI() {
        tabLayout.setupWithViewPager(viewPager, false);
        viewPager.setAdapter(new EntryDetailPagerAdapter(getSupportFragmentManager()));

        // Check bookmark state and set fab element accordingly
        Entry bookmarkedEntry = realm.where(Entry.class).equalTo("idUrl", entry.getIdUrl()).findFirst();
        if (bookmarkedEntry == null) {
            bookmarkButton.setImageResource(R.drawable.bookmark_border);
        } else {
            bookmarkButton.setImageResource(R.drawable.bookmark);
        }
    }

    public static Intent createIntent(Context context, @NonNull Entry entry) {
        Intent intent = new Intent(context, EntryDetailActivity.class);
        intent.putExtra(ARG_ENTRY, entry);
        return intent;
    }

    @OnClick(R.id.entry_detail_bookmark_fab)
    void bookmarkClicked() {
        RealmResults<Entry> results = realm.where(Entry.class).equalTo("idUrl", entry.getIdUrl()).findAll();
        if (results != null && results.size() > 0) {
            final boolean[] bookmarkDeleted = {false};
            realm.executeTransaction(realm -> bookmarkDeleted[0] = results.deleteAllFromRealm());

            if (bookmarkDeleted[0]) {
                Snackbar.make(viewRoot, "Bookmark deleted", Snackbar.LENGTH_SHORT).show();
                bookmarkButton.setImageResource(R.drawable.bookmark_border);
            }
        } else {
            realm.executeTransaction(realm -> realm.copyToRealm(entry));
            Snackbar.make(viewRoot, "Bookmark added", Snackbar.LENGTH_SHORT).show();
            bookmarkButton.setImageResource(R.drawable.bookmark);
        }
    }

    private class EntryDetailPagerAdapter extends FragmentPagerAdapter {

        public EntryDetailPagerAdapter(FragmentManager fm) { super(fm); }

        @Override public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return EntryDocumentFragment.newInstance(entry);
                case 1:
                    return EntryMetaFragment.newInstance(entry);
                default:
                    return null;
            }
        }

        @Override public int getCount() { return 2; }

        @Override public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Paper";
                case 1:
                    return "Meta";
                default:
                    return null;
            }
        }
    }
}
