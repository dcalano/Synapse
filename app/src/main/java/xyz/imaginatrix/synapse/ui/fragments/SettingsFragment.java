package xyz.imaginatrix.synapse.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import xyz.imaginatrix.synapse.R;
import xyz.imaginatrix.synapse.data.models.Category;
import xyz.imaginatrix.synapse.data.models.Classification;
import xyz.imaginatrix.synapse.data.models.Subject;
import xyz.imaginatrix.synapse.util.PrefsUtil;


public class SettingsFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public static final String TAG = SettingsFragment.class.getSimpleName();


    @BindView(R.id.prefs_default_subject_spinner) Spinner subjectSpinner;
    @BindView(R.id.prefs_default_category_spinner) Spinner categorySpinner;

    private Realm realm = null;

    // Default classification preference
    private RealmList<Subject> subjects = null;
    private Classification preferredClassification = null;
    private int preferredSubjectIndex = 0;
    private int preferredCategoryIndex = -1;

    public SettingsFragment() { }

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.frag_settings, container, false);
        ButterKnife.bind(this, fragView);
        return fragView;
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        realm = Realm.getDefaultInstance();
    }

    @Override public void onResume() {
        super.onResume();
        preferredClassification = PrefsUtil.retrieveDefaultClassificaion(getContext());
        initClassificationSpinners();
    }

    @Override public void onDestroyView() {
        this.realm.close();
        super.onDestroyView();
    }

    // Set preferred classification spinner from saved preference
    private void initClassificationSpinners() {
        if (subjects == null) {
            RealmResults<Subject> results = realm.where(Subject.class).findAll();
            subjects = new RealmList<>();
            subjects.addAll(results);
        }

        subjectSpinner.setOnItemSelectedListener(this);
        categorySpinner.setOnItemSelectedListener(this);


        setSubjectSpinner();
        updateCategorySpinner();
    }

    private void setSubjectSpinner() {
        // Init subject spinner
        ArrayAdapter<CharSequence> subjectAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item);
        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subjectSpinner.setAdapter(subjectAdapter);

        for (Subject subject : subjects) {
            subjectAdapter.add(subject.getName());
            if (preferredClassification != null) {
                if (preferredClassification.getSubjectKey().equals(subject.getKey())) {
                    preferredSubjectIndex = subjects.indexOf(subject);
                }
            }
        }

        subjectSpinner.setSelection(preferredSubjectIndex);
    }

    private void updateCategorySpinner() {
        ArrayAdapter<CharSequence> categoryAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        Subject activeSubject = subjects.get(preferredSubjectIndex);
        if (activeSubject.getCategories() != null) {
            categorySpinner.setEnabled(true);

            RealmList<Category> categories = activeSubject.getCategories();
            for (Category category : categories)
                categoryAdapter.add(category.getName());

            if (preferredClassification != null && preferredClassification.getCategory() != null) {
                preferredCategoryIndex = categories.indexOf(preferredClassification.getCategory());
                categorySpinner.setSelection(preferredCategoryIndex);
            }
        } else {
            categorySpinner.setEnabled(false);
        }
    }


    // Interface for default classification spinner prefs
    @Override public void onItemSelected(AdapterView<?> spinnerView, View view, int position, long l) {
        Classification classificationToSave = null;
        switch (spinnerView.getId()) {
            case R.id.prefs_default_subject_spinner:
                preferredSubjectIndex = position;
                Subject subject = subjects.get(preferredSubjectIndex);
                classificationToSave = new Classification(subject.getName(), subject.getKey(), null);

                // New subject selected, initialize preferred category to null or first available category
                RealmList<Category> tempCategories = subject.getCategories();
                if (tempCategories != null && !tempCategories.isEmpty()) {
                    preferredCategoryIndex = 0;
                    classificationToSave.setCategory(tempCategories.get(preferredCategoryIndex));
                } else {
                    classificationToSave.setCategory(null);
                    preferredCategoryIndex = -1;
                }

                updateCategorySpinner();
                break;
            case R.id.prefs_default_category_spinner:
                preferredCategoryIndex = position;
                classificationToSave = new Classification(subjects.get(preferredSubjectIndex).getName(), subjects.get(preferredSubjectIndex).getKey(), null);
                classificationToSave.setCategory( subjects.get(preferredSubjectIndex).getCategories().get(preferredCategoryIndex) );
                break;
        }

        PrefsUtil.updateDefaultClassification(getContext(), classificationToSave);
    }

    @Override public void onNothingSelected(AdapterView<?> adapterView) { }
}
