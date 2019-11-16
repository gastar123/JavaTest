import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

public class XMLFileWriter {

    public void writeXML(List<String> fieldList) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

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

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
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
}
