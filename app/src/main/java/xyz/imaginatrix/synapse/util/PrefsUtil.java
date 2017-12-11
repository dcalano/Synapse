package xyz.imaginatrix.synapse.util;


import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

import xyz.imaginatrix.synapse.R;
import xyz.imaginatrix.synapse.data.models.Category;
import xyz.imaginatrix.synapse.data.models.Classification;

public class PrefsUtil {

    public static void updateDefaultClassification(final Context context, final Classification classification) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.preferences_file_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sharedPreferences.edit();

        // Serialize classification
        Set<String> classificationSet = new HashSet<>();
        classificationSet.add(classification.getSubjectKey());
        classificationSet.add(classification.getSubjectName());
        if (classification.getCategory() != null) {
            classificationSet.add(classification.getCategory().getKey());
            classificationSet.add(classification.getCategory().getName());
        }

        spEditor.putStringSet(context.getString(R.string.prefs_key_serializedClassification), classificationSet);
        spEditor.apply();
    }

    public static Classification retrieveDefaultClassificaion(final Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.preferences_file_name), Context.MODE_PRIVATE);

        // Deserialize classification
        Set<String> serializedClassificationSet = sharedPreferences.getStringSet(
                context.getString(R.string.prefs_key_serializedClassification), null);

        Classification classification = null;
        if (serializedClassificationSet != null) {
            String[] setArray = serializedClassificationSet.toArray(new String[serializedClassificationSet.size()]);
            classification = new Classification(setArray[1], setArray[0], null);
            if (setArray.length == 4)
                classification.setCategory(new Category(setArray[2], setArray[3]));
        }

        return classification;
    }
}
