package xyz.imaginatrix.synapse.arxiv.rest.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "author")
public class ArxivAuthor {

    @Element(name = "name")
    public String name = null;

    @Namespace(prefix = "arxiv")
    @ElementList(required = false, inline = true)
    public List<ArxivAffiliation> affiliation = null;
}
