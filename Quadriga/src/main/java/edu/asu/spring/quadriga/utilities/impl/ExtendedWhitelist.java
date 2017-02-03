package edu.asu.spring.quadriga.utilities.impl;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;

/**
 * Custom Whitelist using {@link org.jsoup.safety.Whitelist} to validate encoded
 * images.
 * 
 * @author Nischal Samji
 *
 */
public class ExtendedWhitelist extends Whitelist {

    public ExtendedWhitelist() {
        addTags("a", "b", "blockquote", "br", "cite", "code", "dd", "dl", "dt", "em", "i", "li", "ol", "p", "pre", "q",
                "small", "span", "strike", "strong", "sub", "sup", "u", "ul");
        addAttributes("a", "href");
        addAttributes("blockquote", "cite");
        addAttributes("q", "cite");
        addProtocols("a", "href", "ftp", "http", "https", "mailto");
        addProtocols("blockquote", "cite", "http", "https");
        addProtocols("cite", "cite", "http", "https");
        addEnforcedAttribute("a", "rel", "nofollow");
        addTags("img");
        addAttributes("img", "align", "alt", "height", "src", "title", "width");
        addProtocols("img", "src", "http", "https");
    }

    @Override
    protected boolean isSafeAttribute(String tagName, Element el, Attribute attr) {
        return ("img".equals(tagName) && "src".equals(attr.getKey())
                && attr.getValue().startsWith("data:image/png;base64")) || super.isSafeAttribute(tagName, el, attr);
    }

    public static Whitelist extendedWhiteListWithBase64() {
        return new ExtendedWhitelist();
    }

}
