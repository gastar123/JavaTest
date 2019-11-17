package com.xe72;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class XMLUtils {

    private TransformerFactory transformerFactory;
    private DocumentBuilder docBuilder;

    public void writeXML(List<String> fieldList) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element rootEl = doc.createElement("entries");
            doc.appendChild(rootEl);

            for (String field : fieldList) {
                Element entryEl = doc.createElement("entry");
                Element fieldEl = doc.createElement("field");
                rootEl.appendChild(entryEl);
                fieldEl.appendChild(doc.createTextNode(field));
                entryEl.appendChild(fieldEl);
            }

            transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult streamResult = new StreamResult(new File("1.xml"));

            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, streamResult);

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public void transformXML() {
        try {
            Source xslt = new StreamSource(this.getClass().getClassLoader().getResourceAsStream("XMLTransformer.xsl"));
            Transformer transformerXSLT = transformerFactory.newTransformer(xslt);

            Source text = new StreamSource(new File("1.xml"));
            transformerXSLT.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformerXSLT.transform(text, new StreamResult(new File("2.xml")));
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public void parseXML() {
        Document parsedDoc = null;
        try {
            parsedDoc = docBuilder.parse(new File("2.xml"));
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int sum = 0;
        NodeList entryElements = parsedDoc.getDocumentElement().getElementsByTagName("entry");
        for (int i = 0; i < entryElements.getLength(); i++) {
            Node entry = entryElements.item(i);
            String entryAttr = entry.getAttributes().getNamedItem("field").getNodeValue();
            sum += Integer.parseInt(entryAttr);
        }

        System.out.println(sum);
    }
}
