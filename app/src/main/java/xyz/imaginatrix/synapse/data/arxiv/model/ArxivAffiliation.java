package xyz.imaginatrix.synapse.data.arxiv.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

/**
 * Created by cyberpunk on 8/24/17.
 */

@Namespace(prefix = "arxiv")
@Root(name = "affiliation", strict = false)
public class ArxivAffiliation {

    @Namespace(prefix = "arxiv")
    @Attribute(required = false)
    public String arxiv = null;

    @Text
    public String value = null;
}
