package persistencia;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
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

        public void leerConfiguracionEscenario() {
            try {
                File archivoFisico = RUTA_XML.toFile();
                if (!archivoFisico.exists()) {
                    System.out.println("El archivo de configuración XML no se encuentra alojado en el disco.");
                    return;
                }

                DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();
                DocumentBuilder constructor = factoria.newDocumentBuilder();
                Document documento = constructor.parse(archivoFisico);

                documento.getDocumentElement().normalize();

                NodeList listaLaboratorios = documento.getElementsByTagName("laboratorio");
                Node nodoPrincipal = listaLaboratorios.item(0);

                if (nodoPrincipal.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) nodoPrincipal;

                    String tipo = elemento.getAttribute("tipo");
                    String capacidad = elemento.getElementsByTagName("capacidad_presion").item(0).getTextContent();
                    String extractor = elemento.getElementsByTagName("extractor_aire").item(0).getTextContent();

                    System.out.println("--- CONFIGURACIÓN ESTRUCTURAL (XML) ---");
                    System.out.println("Instalación registrada: " + tipo);
                    System.out.println("Límite de presión térmica: " + capacidad + " PSI");
                    System.out.println("Estado del extractor: " + extractor);
                }
            } catch (Exception e) {
                System.err.println("Fallo al leer la estructura XML: " + e.getMessage());
            }
    }

    public void exportarElementosAfectados(String elemento) {
        try (java.io.FileWriter fw = new java.io.FileWriter("datos/elementos_afectados.xml")) {
            fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            fw.write("<Incidente>\n");
            fw.write("  <ElementoComprometido>" + elemento + "</ElementoComprometido>\n");
            fw.write("</Incidente>");
        } catch (java.io.IOException e) {
            System.err.println("Fallo al exportar el XML de daños.");
        }
    }
    }
