package xyz.imaginatrix.synapse.arxiv.rest.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "category")
public class ArxivCategory {

    @Attribute
    public String term;

    @Attribute(required = false)
    public String scheme;
}
