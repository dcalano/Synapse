package xyz.imaginatrix.synapse.data.arxiv;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import xyz.imaginatrix.synapse.data.arxiv.model.SearchResult;


public interface ArxivAPI {

    // {encoded = true} required so Retrofit does not auto convert "+" to " " for Arxiv API
    @GET("query")
    Observable<SearchResult> query(
            @Query(value = "search_query", encoded = true) String query,
            @Query("start") int startingIndex,
            @Query("max_results") int maxResults,
            @Query("sortBy") String sortBy,
            @Query("sortOrder") String sortOrder);

    @GET("query")
    Observable<SearchResult> getEntry(@Query("id_list") String articleId);
}
