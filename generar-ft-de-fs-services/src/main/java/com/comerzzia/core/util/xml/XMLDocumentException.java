package com.comerzzia.core.util.xml;

/**
 * Generic exception for XMLDocument operations.
 */
public class XMLDocumentException extends Exception {
    public XMLDocumentException(String message) {
        super(message);
    }

    public XMLDocumentException(String message, Throwable cause) {
        super(message, cause);
    }
}
