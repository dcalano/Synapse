package xyz.imaginatrix.synapse.data.arxiv.model;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "feed", strict = false)
public class SearchResult {

    @Element
    public String id;

    @Element
    public ArxivLink link;

    @Element
    public String title;

    @Element
    public String updated;

    @Namespace(prefix = "opensearch")
    @Element(name = "totalResults", required = false)
    public int totalResults;

    @Namespace(prefix = "opensearch")
    @Element(name = "startIndex", required = false)
    public int startIndex;

    @Namespace(prefix = "opensearch")
    @Element(name = "itemsPerPage", required = false)
    public int itemsPerPage;

    @ElementList(entry = "entry", inline = true)
    public List<ArxivEntry> entries;
}
