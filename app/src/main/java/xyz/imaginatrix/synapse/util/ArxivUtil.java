package xyz.imaginatrix.synapse.util;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import xyz.imaginatrix.synapse.arxiv.rest.model.ArxivAffiliation;
import xyz.imaginatrix.synapse.arxiv.rest.model.ArxivAuthor;
import xyz.imaginatrix.synapse.arxiv.rest.model.ArxivCategory;
import xyz.imaginatrix.synapse.arxiv.rest.model.ArxivEntry;
import xyz.imaginatrix.synapse.arxiv.rest.model.ArxivLink;
import xyz.imaginatrix.synapse.data.models.Author;
import xyz.imaginatrix.synapse.data.models.Category;
import xyz.imaginatrix.synapse.data.models.Classification;
import xyz.imaginatrix.synapse.data.models.Entry;
import xyz.imaginatrix.synapse.data.models.Subject;


public class ArxivUtil {

    public static Entry parseRawEntry(ArxivEntry rawEntry) {
        Entry formattedEntry = new Entry();

        formattedEntry.setIdUrl(rawEntry.id);

        // I notice some of the titles have random new line characters and double spaces.
        formattedEntry.setTitle(rawEntry.title
                .trim()
                .replace("\n", "")
                .replace("  ", " ")
        );

        // Summaries are normally find with a simple trim just in case of dangling spaces
        formattedEntry.setSummary(rawEntry.summary
                .trim()
                .replace("\n", "")
                .replace("  ", " ")
        );


        formattedEntry.setAuthors(parseAuthors(rawEntry.arxivAuthors));
        formattedEntry.setPublishedDate(rawEntry.published);
        formattedEntry.setUpdatedDate(rawEntry.updated);

        for (ArxivLink link : rawEntry.links) {
            if (link.rel.equals("alternate")) {
                formattedEntry.setWebUrl(link.url);
            } else if (link.rel.equals("related")) {
                if (link.title.equals("doi")) {
                    formattedEntry.setDoiUrl(link.url);
                } else if (link.title.equals("pdf")) {
                    formattedEntry.setPdfUrl(link.url);
                }
            }
        }

        RealmList<Classification> classifications = new RealmList<>();
        // Add primary classification as first index
        classifications.add(parseClassification(rawEntry.primaryCategory.term));
        if (rawEntry.category != null) {
            for (ArxivCategory category : rawEntry.category) {
                // Add other classifications behind primary
                if (!category.term.equals(rawEntry.primaryCategory.term)) {
                    Classification  classification = parseClassification(category.term);
                    if (classification != null) {
                        classifications.add(classification);
                    }
                }
            }
        }
        formattedEntry.setClassifications(classifications);

        formattedEntry.setJournalRef(rawEntry.journalRef);
        formattedEntry.setComment(rawEntry.comment);

        return formattedEntry;
    }

    private static RealmList<Author> parseAuthors(List<ArxivAuthor> authors) {
        RealmList<Author> convertedAuthors = new RealmList<>();
        for (ArxivAuthor author : authors) {
            List<String> affiliations = null;
            if (author.affiliation != null) {
                affiliations = new ArrayList<>();
                for (ArxivAffiliation affiliation : author.affiliation) affiliations.add(affiliation.value);
            }

            convertedAuthors.add(new Author(author.name, affiliations));
        }

        return convertedAuthors;
    }

    private static Classification parseClassification(String rawClassification) {
        Realm realm = Realm.getDefaultInstance();
        Classification classification = null;
        String subjectKey;
        String categoryKey = null;

        if (rawClassification.contains(".")) {
            // Classification is of format <subjectKey>.<categoryKey>
            subjectKey = rawClassification.substring(0, rawClassification.indexOf("."));
            categoryKey = rawClassification.substring(rawClassification.indexOf(".") + 1);
        } else {
            // Classification does not have category and only contains a <subjectKey>
            subjectKey = rawClassification;
        }

        Subject subject = realm.where(Subject.class).equalTo("key", subjectKey).findFirst();
        if (subject != null) {
            classification = new Classification();
            classification.setSubjectKey(subject.getKey());
            classification.setSubjectName(subject.getName());
            if (categoryKey != null) {
                Category category = subject.getCategories().where().equalTo("key", categoryKey).findFirst();
                classification.setCategory(category);
            }
        }

        realm.close();
        return classification;
    }
}
