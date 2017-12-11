package xyz.imaginatrix.synapse.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.imaginatrix.synapse.R;
import xyz.imaginatrix.synapse.data.models.Author;
import xyz.imaginatrix.synapse.data.models.Classification;
import xyz.imaginatrix.synapse.data.models.Entry;
import xyz.imaginatrix.synapse.ui.list.items.EntryAuthorItem;
import xyz.imaginatrix.synapse.ui.list.items.EntryClassificationItem;

// arxivAuthors, published, updated, category,
public class EntryMetaFragment extends Fragment {

    private static final String TAG = EntryMetaFragment.class.getSimpleName();
    private static final String ARG_ENTRY = "entry";

    @BindView(R.id.entry_detail_meta_title) TextView title;
    @BindView(R.id.entry_detail_publishedDate) TextView publishedDate;
    @BindView(R.id.entry_detail_updatedFlv) TextView updatedFlv;
    @BindView(R.id.entry_detail_updatedDate) TextView updatedDate;
    @BindView(R.id.entry_detail_authors_card) CardView authorsCard;
    @BindView(R.id.entry_detail_authors_list) RecyclerView authorsRecycler;
    @BindView(R.id.entry_detail_classifications_card) CardView classificationsCard;
    @BindView(R.id.entry_detail_classifications_list) RecyclerView classificationsRecycler;

    private Entry entry = null;

    public EntryMetaFragment() { }

    public static EntryMetaFragment newInstance(@NonNull Entry entry) {
        EntryMetaFragment fragment = new EntryMetaFragment();

        Bundle args = new Bundle();
        args.putParcelable(ARG_ENTRY, entry);
        fragment.setArguments(args);

        return fragment;
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        entry = getArguments().getParcelable(ARG_ENTRY);
        super.onCreate(savedInstanceState);
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.frag_entry_detail_meta, container, false);
        ButterKnife.bind(this, fragView);
        authorsRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        classificationsRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        return fragView;
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title.setText(entry.getTitle());

        if (entry.getUpdatedDate() != null) {
            updatedDate.setText(entry.getUpdatedDate());
        } else {
            updatedFlv.setVisibility(View.GONE);
            updatedDate.setVisibility(View.GONE);
        }

        if (entry.getPublishedDate() != null) {
            publishedDate.setText(entry.getPublishedDate());
        } else {
            publishedDate.setText("----");
        }

        if (entry.getAuthors() != null) {
            FastItemAdapter<EntryAuthorItem> authorAdapter = new FastItemAdapter<>();
            authorsRecycler.setAdapter(authorAdapter);
            for (Author author : entry.getAuthors()) {
                authorAdapter.add(new EntryAuthorItem(author));
            }
        } else {
            authorsCard.setVisibility(View.GONE);
        }

        if (entry.getClassifications() != null) {
            FastItemAdapter<EntryClassificationItem> classificationAdapter = new FastItemAdapter<>();
            classificationsRecycler.setAdapter(classificationAdapter);
            for (Classification classification : entry.getClassifications()) {
                classificationAdapter.add(new EntryClassificationItem(classification));
            }
        } else {
            classificationsCard.setVisibility(View.GONE);
        }
    }

    @Override public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ARG_ENTRY, entry);
    }

    @Override public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            entry = savedInstanceState.getParcelable(ARG_ENTRY);
        }
    }
}
