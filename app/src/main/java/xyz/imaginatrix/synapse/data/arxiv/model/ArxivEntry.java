package xyz.imaginatrix.synapse.data.arxiv.model;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "entry", strict = false)
public class ArxivEntry {

    @Element
    public String title;

    @Element
    public String id;

    @Element
    public String published;

    @Element
    public String updated;

    @Element
    public String summary;

    @ElementList(entry = "author", inline = true)
    public List<ArxivAuthor> arxivAuthors;

    @Namespace(prefix = "arxiv")
    @Element(name = "primary_category", required = false)
    public ArxivCategory primaryCategory;

    @ElementList(inline = true)
    public List<ArxivCategory> category;

    @ElementList(entry = "link", inline = true)
    public List<ArxivLink> links;

    @Namespace(prefix = "arxiv")
    @Element(name = "doi", required = false)
    public String doi;

    @Namespace(prefix = "arxiv")
    @Element(name ="journal_ref", required = false)
    public String journalRef;

    @Namespace(prefix = "arxiv")
    @Element(name = "comment", required = false)
    public String comment;
}
