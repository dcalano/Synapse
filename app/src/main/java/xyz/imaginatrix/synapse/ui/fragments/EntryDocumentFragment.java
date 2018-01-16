package xyz.imaginatrix.synapse.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Space;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xyz.imaginatrix.synapse.R;
import xyz.imaginatrix.synapse.data.models.Entry;
import xyz.imaginatrix.synapse.ui.activities.WebActivity;

// title, summary, links
public class EntryDocumentFragment extends Fragment {

    private static final String TAG = EntryDocumentFragment.class.getSimpleName();
    private static final String ARG_ENTRY = "entry";

    @BindView(R.id.entry_detail_document_title) TextView title;
    @BindView(R.id.entry_detail_summary) TextView summary;
    @BindView(R.id.entry_detail_extrasSpace) Space extraSpace;
    @BindView(R.id.entry_detail_journalRef) TextView journalRef;
    @BindView(R.id.entry_detail_comment) TextView comment;
    @BindView(R.id.entry_detail_doiLink) TextView doiLink;
    @BindView(R.id.entry_detail_webLink) TextView webLink;
    @BindView(R.id.entry_detail_pdfLink) TextView pdfLink;

    private Entry entry = null;

    public EntryDocumentFragment() { }

    public static EntryDocumentFragment newInstance(Entry entry) {
        EntryDocumentFragment fragment = new EntryDocumentFragment();

        Bundle args = new Bundle();
        args.putParcelable(ARG_ENTRY, entry);
        fragment.setArguments(args);

        return fragment;
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            entry = savedInstanceState.getParcelable(ARG_ENTRY);
        } else {
            entry = getArguments().getParcelable(ARG_ENTRY);
        }
    }

    @Override public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.frag_entry_detail_document, container, false);
        ButterKnife.bind(this, fragView);
        return fragView;
    }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title.setText(entry.getTitle());
        summary.setText(entry.getSummary());

        if (entry.getComment() == null && entry.getJournalRef() == null) {
            extraSpace.setVisibility(View.GONE);
        }

        if (entry.getJournalRef() != null) {
            journalRef.setText(entry.getJournalRef());
        } else {
            journalRef.setVisibility(View.GONE);
        }

        if (entry.getComment() != null) {
            comment.setText(entry.getComment());
        } else {
            comment.setVisibility(View.GONE);
        }

        if (entry.getWebUrl() != null) {
            webLink.setVisibility(View.VISIBLE);
        } else {
            webLink.setVisibility(View.GONE);
        }

        if (entry.getPdfUrl() != null) {
            pdfLink.setVisibility(View.VISIBLE);
        } else {
            pdfLink.setVisibility(View.GONE);
        }

        if (entry.getDoiUrl() != null) {
            doiLink.setVisibility(View.VISIBLE);
        } else {
            doiLink.setVisibility(View.GONE);
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

    @OnClick(R.id.entry_detail_webLink)
    void webLinkClicked() {
        if (entry == null) return;
        startActivity(WebActivity.createIntent(getActivity(), entry.getWebUrl()));
    }

    @OnClick(R.id.entry_detail_pdfLink)
    void pdfLinkClicked() {
        if (entry == null) return;

        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
        browserIntent.setDataAndType(Uri.parse(entry.getPdfUrl()), "application/pdf");
        Intent chooser = Intent.createChooser(browserIntent, "Open PDF");
        chooser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(chooser);
//        Intent shareIntent = new Intent(Intent.ACTION_SEND);
//        shareIntent.setType("application/pdf");
//        shareIntent.putExtra(Intent.EXTRA_TEXT, entry.getPdfUrl());
//        startActivity(Intent.createChooser(shareIntent, "Open paper with..."));
    }

    @OnClick(R.id.entry_detail_doiLink)
    void doiLinkClicked() {
        if (entry == null)
            return;
        startActivity(WebActivity.createIntent(getActivity(), entry.getDoiUrl()));
    }
}
