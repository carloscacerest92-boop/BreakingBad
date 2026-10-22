package persistencia;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GestorXML {

    private static final Path RUTA_XML = Paths.get("datos", "instalaciones.xml");

    public GestorXML() {
        try {

            if (!Files.exists(RUTA_XML.getParent())) {
                Files.createDirectories(RUTA_XML.getParent());
            }
        } catch (Exception e) {
            System.err.println("Error al crear la estructura de directorios XML: " + e.getMessage());
        }
    }

    /**
     * Construuye un documento XML en memoria usando DOM y lo vuelca al fichero.
     * Representa la configuración física y estructural del escenario.
     */
    public void guardarInstalacion(String tipoLaboratorio, int capacidadPresion, boolean extractorActivo) {
        try {
            // 1. Inicializamos las herramientas de la API DOM
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            // 2. Construimos el árbol en memoria
            // Elemento raíz <instalaciones>
            Element raiz = doc.createElement("instalaciones");
            doc.appendChild(raiz);

            // Elemento <laboratorio tipo="Autocaravana">
            Element laboratorio = doc.createElement("laboratorio");
            laboratorio.setAttribute("tipo", tipoLaboratorio);
            raiz.appendChild(laboratorio);

            // Subnodo <capacidad_presion>
            Element capacidad = doc.createElement("capacidad_presion");
            capacidad.appendChild(doc.createTextNode(String.valueOf(capacidadPresion)));
            laboratorio.appendChild(capacidad);

            // Subnodo <extractor_aire>
            Element extractor = doc.createElement("extractor_aire");
            extractor.appendChild(doc.createTextNode(extractorActivo ? "OPERATIVO" : "INACTIVO"));
            laboratorio.appendChild(extractor);

            // 3. Volcamos el árbol DOM al archivo físico
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            // Esta propiedad formatea el XML con saltos de línea e indentación (pretty print)
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(RUTA_XML.toFile());
            transformer.transform(source, result);

        } catch (Exception e) {
            System.err.println("Error al generar el XML de la infraestructura: " + e.getMessage());
        }
    }
}