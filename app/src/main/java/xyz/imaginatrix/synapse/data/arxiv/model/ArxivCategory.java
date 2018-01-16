package xyz.imaginatrix.synapse.data.arxiv.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "category")
public class ArxivCategory {

    @Attribute
    public String term;

    @Attribute(required = false)
    public String scheme;
}
