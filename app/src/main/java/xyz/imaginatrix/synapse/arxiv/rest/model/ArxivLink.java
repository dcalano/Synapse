package xyz.imaginatrix.synapse.arxiv.rest.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class ArxivLink {

    @Attribute
    public String rel;

    @Attribute(name = "href")
    public String url;

    @Attribute(required = false)
    public String type;

    @Attribute(required = false)
    public String title;
}
