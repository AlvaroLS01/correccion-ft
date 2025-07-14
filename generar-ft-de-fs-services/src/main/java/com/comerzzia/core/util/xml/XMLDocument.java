package com.comerzzia.core.util.xml;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XMLDocument {

    private static Logger log = Logger.getLogger(XMLDocument.class);

    protected static InheritableThreadLocal<DocumentBuilder> builderThreadLocal = new InheritableThreadLocal<DocumentBuilder>() {
        @Override
        protected DocumentBuilder initialValue() {
            try {
                return createDocumentBuilder();
            } catch (XMLDocumentException e) {
                throw new RuntimeException(e);
            }
        }
    };

    private Document document;
    private Exception exception;
    private XMLDocumentNode root;

    public XMLDocument() {
        try {
            this.document = getDocumentBuilder().newDocument();
        } catch (XMLDocumentException e) {
            exception = e;
        }
    }

    public XMLDocument(Document document) {
        this.document = document;
    }

    public XMLDocument(URL url) throws XMLDocumentException {
        try {
            log.trace("XMLDocument() - Obteniendo xml a partir de URL: " + url.toString());
            InputSource is = new InputSource(url.openStream());
            this.document = getDocumentBuilder().parse(is);
        } catch (XMLDocumentException e) {
            throw e;
        } catch (Exception e) {
            String msg = "Error obteniendo xml a partir de la url indicada: " + e.getMessage();
            log.error("XMLDocument() - " + msg);
            throw new XMLDocumentException(msg, e);
        }
    }

    public XMLDocument(File file) throws XMLDocumentException {
        try {
            log.trace("XMLDocument() - Obteniendo xml a partir de File: " + file.getAbsolutePath());
            this.document = getDocumentBuilder().parse(file);
        } catch (XMLDocumentException e) {
            throw e;
        } catch (Exception e) {
            String msg = "Error obteniendo xml a partir del fichero indicado: " + e.getMessage();
            log.error("XMLDocument() - " + msg);
            throw new XMLDocumentException(msg, e);
        }
    }

    public XMLDocument(byte[] bytes) throws XMLDocumentException {
        log.trace("XMLDocument() - Obteniendo xml a partir de array de bytes.");
        ByteArrayInputStream is = new ByteArrayInputStream(bytes);
        getXMLDocument(is);
    }

    public XMLDocument(InputStream is) throws XMLDocumentException {
        getXMLDocument(is);
    }

    private void getXMLDocument(InputStream is) throws XMLDocumentException {
        try {
            log.trace("XMLDocument() - Obteniendo xml a partir de InputStream.");
            this.document = getDocumentBuilder().parse(is);
        } catch (XMLDocumentException e) {
            throw e;
        } catch (Exception e) {
            String msg = "Error obteniendo xml a partir de array de bytes: " + e.getMessage();
            log.error("XMLDocument() - " + msg);
            throw new XMLDocumentException(msg, e);
        }
    }

    public void setRoot(XMLDocumentNode nodo) {
        document.appendChild(nodo.getNode());
        this.root = new XMLDocumentNode(document, document.getDocumentElement());
    }

    public XMLDocumentNode getRoot() {
        if (root == null) {
            this.root = new XMLDocumentNode(document, document.getDocumentElement());
        }
        return root;
    }

    public XMLDocumentNode getNodo(String nombre) throws XMLDocumentNodeNotFoundException {
        return getNodo(nombre, false);
    }

    public XMLDocumentNode getNodo(String nombre, boolean opcional) throws XMLDocumentNodeNotFoundException {
        return getRoot().getNodo(nombre, opcional);
    }

    public List<XMLDocumentNode> getNodos(String nombre) {
        NodeList nodeList = document.getElementsByTagName(nombre);
        List<XMLDocumentNode> xmlNodeList = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node nodo = nodeList.item(i);
            if (nodo instanceof Element) {
                xmlNodeList.add(new XMLDocumentNode(document, nodo));
            }
        }
        return xmlNodeList;
    }

    public List<XMLDocumentNode> getHijos() {
        NodeList nodeList = document.getChildNodes();
        List<XMLDocumentNode> xmlNodeList = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node nodo = nodeList.item(i);
            if (nodo instanceof Element) {
                xmlNodeList.add(new XMLDocumentNode(document, nodo));
            }
        }
        return xmlNodeList;
    }

    public Document getDocument() {
        return this.document;
    }

    public byte[] getBytes(String encoding) throws XMLDocumentTransformerException {
        return getString(encoding).getBytes();
    }

    public byte[] getBytes() throws XMLDocumentTransformerException {
        return getString().getBytes();
    }

    public String getString() throws XMLDocumentTransformerException {
        try {
            return XMLDocumentUtils.DocumentToString(document);
        } catch (XMLDocumentException e) {
            throw new XMLDocumentTransformerException(e);
        }
    }

    public String getString(String encoding) throws XMLDocumentTransformerException {
        try {
            return XMLDocumentUtils.DocumentToString(document, encoding);
        } catch (XMLDocumentException e) {
            throw new XMLDocumentTransformerException(e);
        }
    }

    public String getString(String encoding, boolean identar) throws XMLDocumentTransformerException {
        try {
            return XMLDocumentUtils.DocumentToString(document, encoding, identar);
        } catch (XMLDocumentException e) {
            throw new XMLDocumentTransformerException(e);
        }
    }

    public void toFile(File file, String encoding, boolean indentar) throws XMLDocumentTransformerException {
        FileOutputStream fileOutput = null;
        try {
            fileOutput = new FileOutputStream(file);
            Result result = new StreamResult(fileOutput);
            DOMSource source = new DOMSource(document);
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            if (encoding != null) {
                transformer.setOutputProperty(OutputKeys.ENCODING, encoding);
            }
            if (indentar) {
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            }
            transformer.transform(source, result);
        } catch (Exception e) {
            String msg = "Error al aplicar transformaci\u00F3n a XML para volcado a fichero: " + e.getMessage();
            log.error("toFile() - " + msg);
            throw new XMLDocumentTransformerException(msg);
        } finally {
            IOUtils.closeQuietly(fileOutput);
        }
    }

    @Override
    public String toString() {
        try {
            return getString("UTF-8");
        } catch (XMLDocumentTransformerException e) {
            log.error("toString() - " + e.getMessage());
            return "## ERROR ##";
        }
    }

    private static DocumentBuilder createDocumentBuilder() throws XMLDocumentException {
        try {
            log.debug("createDocumentBuilder() - Creando nuevo DocumentBuilder");
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            builderFactory.setNamespaceAware(true);
            return builderFactory.newDocumentBuilder();
        } catch (Exception e) {
            String msg = "Error al crear DocumentBuilder: " + e.getMessage();
            log.error("getDocumentBuilder() - " + msg);
            throw new XMLDocumentException(msg, e);
        }
    }

    private static DocumentBuilder getDocumentBuilder() throws XMLDocumentException {
        try {
            return builderThreadLocal.get();
        } catch (Exception e) {
            String msg = "Error al crear DocumentBuilder: " + e.getMessage();
            log.error("getDocumentBuilder() - " + msg);
            throw new XMLDocumentException(msg, e);
        }
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public void setDocument(String string) {
        try {
            document = XMLDocumentUtils.createDocumentFormString(string);
        } catch (Exception e) {
            exception = e;
        }
    }

    public void setDocument(Blob blob) {
        try {
            document = XMLDocumentUtils.createDocumentBuilder().parse(blob.getBinaryStream());
        } catch (Exception e) {
            exception = e;
        }
    }

    public Exception getException() {
        return exception;
    }
}
