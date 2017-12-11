package xyz.imaginatrix.synapse.ui.fragments;

import android.app.SearchManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.ArrayRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.doctoror.particlesdrawable.ParticlesView;
import com.mikepenz.fastadapter.adapters.ItemAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter_extensions.items.ProgressItem;
import com.mikepenz.fastadapter_extensions.scroll.EndlessRecyclerOnScrollListener;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import xyz.imaginatrix.synapse.R;
import xyz.imaginatrix.synapse.arxiv.rest.ArxivClient;
import xyz.imaginatrix.synapse.arxiv.rest.model.ArxivEntry;
import xyz.imaginatrix.synapse.arxiv.rest.model.SearchResult;
import xyz.imaginatrix.synapse.arxiv.rest.service.ArxivService;
import xyz.imaginatrix.synapse.data.models.Category;
import xyz.imaginatrix.synapse.data.models.Classification;
import xyz.imaginatrix.synapse.data.models.Entry;
import xyz.imaginatrix.synapse.data.models.Query;
import xyz.imaginatrix.synapse.data.models.Subject;
import xyz.imaginatrix.synapse.ui.activities.EntryDetailActivity;
import xyz.imaginatrix.synapse.ui.list.items.EntryListItem;
import xyz.imaginatrix.synapse.util.ArxivUtil;

import static android.content.Context.SEARCH_SERVICE;
import static com.mikepenz.fastadapter.adapters.ItemAdapter.items;


public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private static final String TAG = HomeFragment.class.getSimpleName();
    private static final String ARG_QUERY = "query";
    private static final int MAX_SEARCH_RESULTS = 15;

    private static final int VIEW_LIST = 0;
    private static final int VIEW_ERROR = 1;

    public HomeFragment() { }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    //<editor-fold desc="{ View bindings }">
    @BindView(R.id.home_root) LinearLayout viewRoot;

    // Search panel components
    @BindView(R.id.panel_search_options_root) CardView searchOptionsPanel;
    @BindView(R.id.search_panel_titleCategory) TextView searchPanelCategoryTitle;
    @BindView(R.id.search_panel_spinner_subject) Spinner subjectSpinner;
    @BindView(R.id.search_panel_spinner_category) Spinner categorySpinner;
    @BindView(R.id.search_panel_spinner_filter) Spinner filterSpinner;
    @BindView(R.id.search_panel_spinner_sortBy) Spinner sortBySpinner;
    @BindView(R.id.search_panel_spinner_sortOrder) Spinner sortOrderSpinner;

    // Loading frame components
    @BindView(R.id.home_listFrame) RelativeLayout listFrame;
    @BindView(R.id.home_particlesView) ParticlesView particlesView;
    @BindView(R.id.home_swipeRefresher) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.home_recycler) RecyclerView recycler;
    //</editor-fold>

    //<editor-fold desc="{ Member variables }">
    private Realm realm = null;

    // Simple reactive handling of search queries, cleaned up along side life cycle
    private CompositeDisposable disposables = new CompositeDisposable();

    // RecyclerView adapters
    private FastItemAdapter<EntryListItem> listAdapter = null;
    ItemAdapter footerAdapter = null;
    EndlessRecyclerOnScrollListener scrollListenner = null;

    // Preferred classification parameters
    private RealmResults<Subject> subjects = null;
    private int activeSubjectIndex = 0;
    private int activeCategoryIndex = -1;

    // Current search state
    private Query searchQuery = null;
    private boolean isLoading = false;
    //</editor-fold>

    //<editor-fold desc="{ Lifecycle Overrides }">
    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.frag_home, container, false);
        ButterKnife.bind(this, fragView);
        setHasOptionsMenu(true);
        return fragView;
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        realm = Realm.getDefaultInstance();
        subjects = realm.where(Subject.class).findAll();

        // Adapter initialization
        listAdapter = new FastItemAdapter<>();
        listAdapter.withSelectable(true);

        footerAdapter = items();
        listAdapter.addAdapter(1, footerAdapter);

        listAdapter.withOnClickListener((v, adapter, item, position) -> {
            startActivity(EntryDetailActivity.createIntent(getActivity(), item.getEntry()));
            return true;
        });

        // Recycler initialization
        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setAdapter(listAdapter);
        scrollListenner = new EndlessRecyclerOnScrollListener(footerAdapter) {
            @Override public void onLoadMore(int currentPage) {
                Handler handler = new Handler();
                handler.postDelayed(() -> {
                    footerAdapter.clear();
                    footerAdapter.add(new ProgressItem().withEnabled(false));
                    loadResults();
                }, 1000);
            }
        };
        recycler.addOnScrollListener(scrollListenner);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            searchQuery.setCurrentStartIndex(0);
            searchQuery. setTotalResults(-1);
            listAdapter.clear();
            scrollListenner.resetPageCount();
            loadResults();
        });

        listAdapter.withSavedInstanceState(savedInstanceState);
        initSearchQuery(savedInstanceState);
        initSearchPanel();
        loadResults();
    }

    @Override public void onDestroy() {
        disposables.dispose();
        realm.close();
        super.onDestroy();
    }

    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.frag_home, menu);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home_search:
                final SearchView searchView = (SearchView) item.getActionView();
                SearchManager searchManager = (SearchManager) getActivity().getSystemService(SEARCH_SERVICE);
                searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override public boolean onQueryTextSubmit(String query) {
                        searchQuery.setQuery(query);
                        searchQuery.setCurrentStartIndex(0);
                        searchQuery.setTotalResults(-1);
                        loadResults();
                        if (searchOptionsPanel.getVisibility() != View.GONE) {
                            hideSearchPanel();
                        }

                        searchView.clearFocus();
                        searchView.setIconified(true);
                        return true;
                    }

                    @Override public boolean onQueryTextChange(String newText) { return false; }
                });

                if (searchOptionsPanel.getVisibility() == View.GONE) {
                    revealSearchPanel();
                }
                return true;
            case R.id.menu_home_expand_search_options:
                if (searchOptionsPanel.getVisibility() == View.GONE) {
                    revealSearchPanel();
                } else {
                    hideSearchPanel();
                }
                return true;
        }

        return false;
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        outState = listAdapter.saveInstanceState(outState);
        outState.putParcelable(ARG_QUERY, searchQuery);
        super.onSaveInstanceState(outState);
    }

    @Override public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        initSearchQuery(savedInstanceState);
    }
    //</editor-fold>

    private void initSearchQuery(Bundle savedInstanceState) {
        Classification classification = null;
        if (savedInstanceState != null) {
            searchQuery = savedInstanceState.getParcelable(ARG_QUERY);
        } else {
            searchQuery = new Query();
        }

        Subject subject = subjects.get(0);
        classification = new Classification(subject.getName(), subject.getKey(), null);
        if (subject.getCategories() != null && !subject.getCategories().isEmpty()) {
            classification.setCategory(subject.getCategories().get(0));
        }

        searchQuery.setClassification(classification);
    }

    //<editor-fold desc="{ View control methods }">
    private void updateView(int frame) {
        switch (frame) {
            case VIEW_LIST:
                recycler.setVisibility(View.VISIBLE);
                particlesView.setVisibility(View.GONE);
                break;
            case VIEW_ERROR:
                recycler.setVisibility(View.GONE);
                particlesView.setVisibility(View.VISIBLE);
                particlesView.setDotColor(ContextCompat.getColor(getContext(), R.color.googleRed));
                randomizeParticleSettings();
                break;
        }
    }

    /**
     * Decorative function to give the particle views a bit of randomness
     */
    private void randomizeParticleSettings() {
        if (particlesView == null)
            return;

        // No sense to abstract out; better to keep packaged inside method due to exclusivity
        int PARTICLES_FLOOR = 50;
        int PARTICLES_RANGE = 120;

        int LINE_DIST_FLOOR = 100;
        int LINE_DIST_RANGE = 60;

        int PARTICLE_SIZE_MIN = 2;
        int PARTICLE_SIZE_RANGE = 5;

        Random random = new Random();
        particlesView.setLineDistance(random.nextInt(LINE_DIST_RANGE) + LINE_DIST_FLOOR);
        particlesView.setDotRadiusRange(PARTICLE_SIZE_MIN, random.nextInt(PARTICLE_SIZE_RANGE) + PARTICLE_SIZE_MIN);
        particlesView.setNumDots(random.nextInt(PARTICLES_RANGE) + PARTICLES_FLOOR);
    }

    private void initSearchPanel() {
        searchOptionsPanel.setVisibility(View.GONE);

        subjectSpinner.setOnItemSelectedListener(this);
        categorySpinner.setOnItemSelectedListener(this);
        filterSpinner.setOnItemSelectedListener(this);
        sortBySpinner.setOnItemSelectedListener(this);
        sortOrderSpinner.setOnItemSelectedListener(this);

        setSubjectSpinner();
        setSpinnerTitles(filterSpinner, R.array.array_filters_titles);
        setSpinnerTitles(sortBySpinner, R.array.array_sort_by_titles);
        setSpinnerTitles(sortOrderSpinner, R.array.array_sort_order_titles);

        searchQuery.setFilter((String) filterSpinner.getAdapter().getItem(0));
        searchQuery.setSortBy((String) sortBySpinner.getAdapter().getItem(0));
        searchQuery.setSortOrder((String) sortOrderSpinner.getAdapter().getItem(0));
    }

    private void setSubjectSpinner() {
        // Init subject spinner
        ArrayAdapter<CharSequence> subjectAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item);
        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subjectSpinner.setAdapter(subjectAdapter);

        for (Subject subject : subjects) {
            subjectAdapter.add(subject.getName());
            if (searchQuery.getClassification() != null && searchQuery.getClassification().getSubjectKey() != null) {
                if (searchQuery.getClassification().getSubjectKey().equals(subject.getKey())) {
                    activeSubjectIndex = subjects.indexOf(subject);
                }
            }
        }

        subjectSpinner.setSelection(activeSubjectIndex);
    }

    private void updateCategorySpinner() {
        ArrayAdapter<CharSequence> categoryAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        Subject activeSubject = subjects.get(activeSubjectIndex);
        if (activeSubject.getCategories() != null) {
            categorySpinner.setEnabled(true);
            searchPanelCategoryTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.googleGreen));
            Classification classification = searchQuery.getClassification();
            RealmList<Category> categories = activeSubject.getCategories();
            for (Category category : categories)
                categoryAdapter.add(category.getName());

            if (classification != null && classification.getCategory() != null) {
                activeCategoryIndex = categories.indexOf(classification.getCategory());
                categorySpinner.setSelection(activeCategoryIndex);
            }
        } else {
            categorySpinner.setEnabled(false);
            searchPanelCategoryTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.md_light_disabled));

        }
    }

    private void setSpinnerTitles(Spinner spinner, @ArrayRes int array) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void hideSearchPanel() {
        searchOptionsPanel.setVisibility(View.GONE);
//        searchOptionsPanel.animate()
//                .translationY(0)
//                .alpha(1.0f)
//                .setListener(new AnimatorListenerAdapter() {
//                    @Override public void onAnimationEnd(Animator animation) {
//                        super.onAnimationEnd(animation);
//                        searchOptionsPanel.setVisibility(View.GONE);
//                    }
//                });
    }

    private void revealSearchPanel() {
        // Prepare the View for the animation
        searchOptionsPanel.setVisibility(View.VISIBLE);
//        searchOptionsPanel.setAlpha(1.0f);
//
//        searchOptionsPanel.animate()
//                .translationY(searchOptionsPanel.getHeight())
//                .alpha(0.0f)
//                .setListener(new AnimatorListenerAdapter() {
//                    @Override public void onAnimationEnd(Animator animation) {
//                        super.onAnimationEnd(animation);
//                        searchOptionsPanel.setVisibility(View.VISIBLE);
//                    }
//                });
    }
    //</editor-fold>

    private void loadResults() {
        if (listAdapter == null || isLoading)
            return;

        isLoading = true;
        swipeRefreshLayout.setRefreshing(true);
        ArxivService api = ArxivClient.createService(ArxivService.class);
        disposables.add(api.query(searchQuery.getQuery(),
                searchQuery.getCurrentStartIndex(),
                MAX_SEARCH_RESULTS,
                searchQuery.getSortBy(),
                searchQuery.getSortOrder())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<SearchResult>() {
                    @Override public void onNext(SearchResult searchResult) {
                        if (searchResult.totalResults == 0) {
                            swipeRefreshLayout.setRefreshing(false);
                            updateView(VIEW_ERROR);
                            isLoading = false;
                            return;
                        }

                        updateView(VIEW_LIST);
                        if (searchQuery.getTotalResults() == -1) {
                            searchQuery.setTotalResults(searchResult.totalResults);
                        }

                        for (ArxivEntry arxivEntry : searchResult.entries) {
                            Entry entry = ArxivUtil.parseRawEntry(arxivEntry);
                            listAdapter.add(new EntryListItem(entry));
                        }
                    }

                    @Override public void onError(Throwable e) {
                        isLoading = false;
                        swipeRefreshLayout.setRefreshing(false);
                        updateView(VIEW_ERROR);
                    }

                    @Override public void onComplete() {
                        searchQuery.setCurrentStartIndex(searchQuery.getCurrentStartIndex() + MAX_SEARCH_RESULTS);
                        if (searchQuery.getCurrentStartIndex() > searchQuery.getTotalResults()) {
                            searchQuery.setTotalResults(searchQuery.getTotalResults() - 1);
                        }
                        swipeRefreshLayout.setRefreshing(false);
                        isLoading = false;
                    }
                }));
    }

    //<editor-fold desc="{ Search panel interface methods }">
    @Override public void onItemSelected(AdapterView<?> spinnerView, View itemView, int pos, long id) {
        switch (spinnerView.getId()) {
            case R.id.search_panel_spinner_subject:
                // Update warm parameter
                activeSubjectIndex = pos;

                // Initialize new classification
                Subject selectedSubject = subjects.get(activeSubjectIndex);
                Classification updatedClassification = new Classification();
                updatedClassification.setSubjectKey(selectedSubject.getKey());
                updatedClassification.setSubjectName(selectedSubject.getName());
                RealmList<Category> tempCategories = selectedSubject.getCategories();
                if (tempCategories != null && !tempCategories.isEmpty()) {
                    activeCategoryIndex = 0;
                    updatedClassification.setCategory(tempCategories.get(0));
                } else {
                    activeCategoryIndex = -1;
                }

                searchQuery.setClassification(updatedClassification);
                updateCategorySpinner();
                break;
            case R.id.search_panel_spinner_category:
                activeCategoryIndex = pos;
                Subject subject = subjects.get(activeSubjectIndex);
                Category category = subject.getCategories().get(activeCategoryIndex);
                searchQuery.getClassification().setCategory(category);
                break;
            case R.id.search_panel_spinner_filter:
                searchQuery.setFilter(getResources().getStringArray(R.array.array_filters_keys)[pos]);
                break;
            case R.id.search_panel_spinner_sortBy:
                searchQuery.setSortBy(getResources().getStringArray(R.array.array_sort_by_keys)[pos]);
                break;
            case R.id.search_panel_spinner_sortOrder:
                searchQuery.setSortOrder(getResources().getStringArray(R.array.array_sort_order_keys)[pos]);
                break;
        }
    }

    @Override public void onNothingSelected(AdapterView<?> adapterView) { }
    //</editor-fold>
}
