package com.comerzzia.core.util.xml;

/**
 * Exception thrown when XML transformation fails.
 */
public class XMLDocumentTransformerException extends Exception {
    public XMLDocumentTransformerException(String message) {
        super(message);
    }

    public XMLDocumentTransformerException(Throwable cause) {
        super(cause);
    }
}
