package com.comerzzia.core.util.xml;

/**
 * Thrown when a required XML node is not found.
 */
public class XMLDocumentNodeNotFoundException extends Exception {
    public XMLDocumentNodeNotFoundException(String message) {
        super(message);
    }
}
