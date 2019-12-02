package com.xe72;

import javax.xml.stream.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

public class XMLUtils {

    private TransformerFactory transformerFactory;
    private long sum;
    private XMLOutputFactory factory;
    private XMLStreamWriter writer;

    public void createFile() throws FileNotFoundException, XMLStreamException {
        factory = XMLOutputFactory.newFactory();
        writer = factory.createXMLStreamWriter(new FileOutputStream("1.xml"));
        writer.writeStartDocument();
        writer.writeDTD("\n");
        writer.writeStartElement("entries");
        writer.writeDTD("\n");
    }

    public void closeFile() throws XMLStreamException {
        writer.writeEndElement();
        writer.writeEndDocument();
        writer.flush();
        writer.close();
    }

    public void addEntry(String field) throws XMLStreamException {
//        StAX create!!!!!!!!!!!!
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

    public void transformXML() throws TransformerException {
        Source xslt = new StreamSource(this.getClass().getClassLoader().getResourceAsStream("XMLTransformer.xsl"));
        transformerFactory = TransformerFactory.newInstance();
        Transformer transformerXSLT = transformerFactory.newTransformer(xslt);

        Source text = new StreamSource(new File("1.xml"));
        transformerXSLT.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformerXSLT.transform(text, new StreamResult(new File("2.xml")));
    }

    public void parseXML() throws IOException, XMLStreamException {
////        SAX parser!!!!!!!!!!!!!
//        DefaultHandler handler = new DefaultHandler() {
//            @Override
//            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
//                String field = attributes.getValue("field");
//                if (field != null && !field.isEmpty()) {
//                    sum += Integer.parseInt(field);
//                }
//            }
//        };
//
//        SAXParserFactory factory = SAXParserFactory.newInstance();
//        SAXParser parser = factory.newSAXParser();
//        parser.parse(new File("2.xml"), handler);


//        StAX parser!!!!!!!!!!
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader parser = factory.createXMLStreamReader(new FileInputStream("2.xml"));
        while (parser.hasNext()) {
            int event = parser.next();
            if (event == XMLStreamConstants.START_ELEMENT && parser.getLocalName().equals("entry")) {
                sum += Integer.parseInt(parser.getAttributeValue(null, "field"));
            }
        }

        System.out.println("Sum: " + sum);
    }
}
