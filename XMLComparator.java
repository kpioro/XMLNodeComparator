
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XMLComparator {


    public static boolean compareTwoXML( String actual, String expected ) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse( new InputSource( new StringReader( actual ) ) );
        Document doc2 = db.parse( new InputSource( new StringReader( expected ) ) );

        Node actualNode = doc.getDocumentElement();
        Node expectedNode = doc2.getDocumentElement();

        return compareNodes( actualNode, expectedNode );
    }


    private static boolean compareNodes( Node actual, Node expected ) throws Exception {

        if ( actual.hasChildNodes() && expected.hasChildNodes() ) {
            return compareNodeChildrenValues( actual, expected );
        } else if ( !actual.hasChildNodes() && !expected.hasChildNodes() ) {
            return compareNodeValues( actual, expected );
        } else {
            return false;
        }
    }


    private static boolean compareNodeValues( Node actual, Node expected ) {
        return ( actual.getNodeName().equals( expected.getNodeName() ) && actual.getTextContent().equals( expected.getTextContent() ) );
    }


    private static boolean compareNodeChildrenValues( Node actual, Node expected ) throws Exception {

        boolean found = false;
        NodeList actualChildNodes = actual.getChildNodes();
        NodeList expectedChildNodes = expected.getChildNodes();
        for ( int i = 0; i < actualChildNodes.getLength(); i++ ) {
            found = false;
            for ( int j = 0; j < expectedChildNodes.getLength(); j++ ) {
                Node expectedChild = expectedChildNodes.item( j );
                Node actualChild = actualChildNodes.item( i );
                if ( compareNodes( actualChild, expectedChild ) ) {
                    found = true;
                    break;
                }
            }
            if ( !found ) {
                break;
            }
        }
        return found;
    }
}


