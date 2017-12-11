package xyz.imaginatrix.synapse.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import xyz.imaginatrix.synapse.R;
import xyz.imaginatrix.synapse.data.models.Entry;
import xyz.imaginatrix.synapse.ui.activities.EntryDetailActivity;
import xyz.imaginatrix.synapse.ui.list.items.EntryListItem;


public class BookmarksFragment extends Fragment {

    public static final String TAG = BookmarksFragment.class.getSimpleName();

    @BindView(R.id.bookmarks_recycler) RecyclerView recycler;
    @BindView(R.id.bookmarks_empty_display) RelativeLayout emptyBookmarksView;

    private Realm realm;
    private FastItemAdapter<EntryListItem> bookmarksAdapter = null;

    public BookmarksFragment() { }

    public static BookmarksFragment newInstance() {
        return new BookmarksFragment();
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.frag_bookmarks, container, false);
        ButterKnife.bind(this, fragView);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        return fragView;
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        realm = Realm.getDefaultInstance();

        bookmarksAdapter = new FastItemAdapter<>();
        bookmarksAdapter.withOnClickListener((v, adapter, item, position) -> {
            startActivity(EntryDetailActivity.createIntent(getActivity(), item.getEntry()));
            return true;
        });

        recycler.setAdapter(bookmarksAdapter);
    }

    @Override public void onResume() {
        super.onResume();
        bookmarksAdapter.clear();
        RealmResults<Entry> bookmarkResults = realm.where(Entry.class).findAll();
        if (bookmarkResults.size() == 0) {
            emptyBookmarksView.setVisibility(View.VISIBLE);
            recycler.setVisibility(View.GONE);
            return;
        } else {
            emptyBookmarksView.setVisibility(View.GONE);
            recycler.setVisibility(View.VISIBLE);

            for (Entry entry : bookmarkResults) {
                bookmarksAdapter.add(new EntryListItem(entry));
            }
        }
    }

    @Override public void onDestroyView() {
        realm.close();
        super.onDestroyView();
    }
}
