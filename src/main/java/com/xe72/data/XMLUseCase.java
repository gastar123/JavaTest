package com.xe72.data;

import javax.xml.stream.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class XMLUseCase {

    public void createXmlFile(List<String> fieldList, String fileName) throws FileNotFoundException, XMLStreamException {
        XMLOutputFactory factory = XMLOutputFactory.newFactory();
        XMLStreamWriter writer = factory.createXMLStreamWriter(new FileOutputStream(fileName));
        writer.writeStartDocument();
        writer.writeDTD("\n");
        writer.writeStartElement("entries");
        writer.writeDTD("\n");
        for (String field: fieldList) {
            writer.writeDTD("\t");
            writer.writeStartElement("entry");
            writer.writeDTD("\n");
            writer.writeDTD("\t\t");
            writer.writeStartElement("field");
            writer.writeCharacters(field);
            writer.writeEndElement();
            writer.writeDTD("\n");
            writer.writeDTD("\t");
            writer.writeEndElement();
            writer.writeDTD("\n");
        }
        writer.writeEndElement();
        writer.writeEndDocument();
        writer.flush();
        writer.close();
    }

    public void transformXML(String oldFileName, String newFileName, String xsltName) throws TransformerException {
        Source xslt = new StreamSource(this.getClass().getClassLoader().getResourceAsStream(xsltName));
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformerXSLT = transformerFactory.newTransformer(xslt);

        Source text = new StreamSource(new File(oldFileName));
        transformerXSLT.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformerXSLT.transform(text, new StreamResult(new File(newFileName)));
    }

    public List<Integer> parseXml(String fileName) throws FileNotFoundException, XMLStreamException {
        List<Integer> fieldsValues = new ArrayList<>();
//        StAX parser!!!!!!!!!!
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader parser = factory.createXMLStreamReader(new FileInputStream(fileName));
        while (parser.hasNext()) {
            int event = parser.next();
            if (event == XMLStreamConstants.START_ELEMENT && parser.getLocalName().equals("entry")) {
                fieldsValues.add(Integer.parseInt(parser.getAttributeValue(null, "field")));
            }
        }
        return fieldsValues;
    }
}
