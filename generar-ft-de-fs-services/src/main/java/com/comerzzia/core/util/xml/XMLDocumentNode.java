package com.comerzzia.core.util.xml;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLDocumentNode {

    private static Logger log = Logger.getLogger(XMLDocumentNode.class);

    private Node nodo;
    private Document document;

    public XMLDocumentNode(XMLDocument document, String nombre) {
        this.nodo = document.getDocument().createElement(nombre);
        this.document = document.getDocument();
    }

    public XMLDocumentNode(XMLDocument document, String nombre, String valor) {
        this.nodo = document.getDocument().createElement(nombre);
        this.document = document.getDocument();
        this.nodo.setTextContent(valor);
    }

    protected XMLDocumentNode(Document document, Node nodo) {
        this.nodo = nodo;
        this.document = document;
    }

    public static XMLDocumentNode importarNodo(XMLDocument document, Node nodoOrigen) {
        Node nodo = document.getDocument().importNode(nodoOrigen, true);
        return new XMLDocumentNode(document.getDocument(), nodo);
    }

    public void añadirSeccionCData(String cdata) {
        CDATASection cdataSection = this.document.createCDATASection(cdata);
        this.nodo.appendChild(cdataSection);
    }

    public void añadirHijo(XMLDocumentNode nodo) {
        this.nodo.appendChild(nodo.getNode());
    }

    public void añadirHijo(String nombre, String valor) {
        Node nodo = document.createElement(nombre);
        nodo.setTextContent(valor);
        this.nodo.appendChild(nodo);
    }

    public void añadirAtributo(String nombre, String valor) {
        Attr a = document.createAttribute(nombre);
        a.setNodeValue(valor);
        nodo.getAttributes().setNamedItem(a);
    }

    public Node getNode() {
        return this.nodo;
    }

    public XMLDocumentNode getNodo(String nombre) throws XMLDocumentNodeNotFoundException {
        return getNodo(nombre, false);
    }

    public XMLDocumentNode getNodo(String nombre, boolean opcional) throws XMLDocumentNodeNotFoundException {
        Node node = null;
        for (node = this.nodo.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                if (((Element) node).getTagName().equals(nombre)) {
                    return new XMLDocumentNode(document, node);
                }
            }
        }
        if (!opcional) {
            throw new XMLDocumentNodeNotFoundException("El nodo " + this.nodo.getNodeName() + " no contiene el nodo " + nombre);
        }
        return null;
    }

    public List<XMLDocumentNode> getHijos() {
        NodeList nodeList = nodo.getChildNodes();
        List<XMLDocumentNode> xmlNodeList = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node nodo = nodeList.item(i);
            if (nodo instanceof Element) {
                xmlNodeList.add(new XMLDocumentNode(document, nodo));
            }
        }
        return xmlNodeList;
    }

    public boolean tieneHijos() {
        NodeList nodeList = nodo.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node nodoHijo = nodeList.item(i);
            if (nodoHijo.getNodeType() == Node.ELEMENT_NODE) {
                return true;
            }
        }
        return false;
    }

    public List<XMLDocumentNode> getHijos(String nombre) {
        NodeList nodeList = nodo.getChildNodes();
        List<XMLDocumentNode> xmlNodeList = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node nodo = nodeList.item(i);
            if (nodo instanceof Element) {
                if (((Element) nodo).getTagName().equals(nombre)) {
                    xmlNodeList.add(new XMLDocumentNode(document, nodo));
                }
            }
        }
        return xmlNodeList;
    }

    public String getAtributoValue(String atributo, boolean opcional) throws XMLDocumentNodeNotFoundException {
        String value = ((Element) nodo).getAttribute(atributo);
        if ((value == null || value.isEmpty()) && !opcional) {
            throw new XMLDocumentNodeNotFoundException("El nodo " + this.nodo.getNodeName() + " no contiene el atributo " + atributo);
        }
        return value;
    }

    public Long getAtributoValueAsLong(String atributo, boolean opcional) throws XMLDocumentNodeNotFoundException {
        return Long.parseLong(getAtributoValue(atributo, opcional));
    }

    public Integer getAtributoValueAsInteger(String atributo, boolean opcional) throws XMLDocumentNodeNotFoundException {
        return Integer.parseInt(getAtributoValue(atributo, opcional));
    }

    public Double getAtributoValueAsDouble(String atributo, boolean opcional) throws XMLDocumentNodeNotFoundException {
        return Double.parseDouble(getAtributoValue(atributo, opcional));
    }

    public BigDecimal getAtributoValueAsBigDecimal(String atributo, boolean opcional) throws XMLDocumentNodeNotFoundException {
        return new BigDecimal(getAtributoValueAsDouble(atributo, opcional));
    }

    public Date getdAtributoValueAsDouble(String atributo, boolean opcional) throws XMLDocumentNodeNotFoundException {
        String fecha = getAtributoValue(atributo, opcional);
        return getFecha(fecha);
    }

    private Date getFecha(String fecha) {
        if (fecha != null && fecha.length() > 0) {
            try {
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                df.setLenient(false);
                return df.parse(fecha);
            } catch (ParseException e) {
                // ignore
            }
        }
        return null;
    }

    public boolean getAtributoValueAsBoolean(String atributo, boolean opcional) throws XMLDocumentNodeNotFoundException {
        return Boolean.valueOf(getAtributoValue(atributo, opcional));
    }

    public String getNombre() {
        return this.nodo.getNodeName();
    }

    public String getValue() {
        return this.nodo.getTextContent();
    }

    public Long getValueAsLong() {
        return Long.parseLong(this.nodo.getTextContent());
    }

    public Integer getValueAsInteger() {
        return Integer.parseInt(this.nodo.getTextContent());
    }

    public Double getValueAsDouble() {
        return Double.parseDouble(this.nodo.getTextContent());
    }

    public BigDecimal getValueAsBigDecimal() {
        return new BigDecimal(getValueAsDouble());
    }

    public Date getValueAsFecha() {
        return getFecha(getValue());
    }

    public boolean getValueAsBoolean() {
        return Boolean.valueOf(this.nodo.getTextContent());
    }

    public void setValue(String value) {
        if (value != null) {
            this.nodo.setTextContent(value);
        }
    }

    public void setValue(Long value) {
        if (value != null) {
            this.nodo.setTextContent(value.toString());
        }
    }

    public void setValue(Integer value) {
        if (value != null) {
            this.nodo.setTextContent(value.toString());
        }
    }

    public void setValue(Double value) {
        if (value != null) {
            this.nodo.setTextContent(value.toString());
        }
    }

    public void setValue(BigDecimal value) {
        if (value != null) {
            this.nodo.setTextContent(value.toString());
        }
    }

    public void setValue(Boolean value) {
        if (value != null) {
            this.nodo.setTextContent(value.toString());
        }
    }

    @Override
    public String toString() {
        try {
            StringWriter sw = new StringWriter();
            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            t.transform(new DOMSource(nodo), new StreamResult(sw));
            return sw.toString();
        } catch (TransformerException e) {
            log.error("toString() - " + e.getMessage());
            return "## ERROR ##";
        }
    }

    public void eliminarAtributo(String nombre) {
        nodo.getAttributes().removeNamedItem(nombre);
    }
}
