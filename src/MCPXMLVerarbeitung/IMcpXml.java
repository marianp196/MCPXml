/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MCPXMLVerarbeitung;

import org.jdom2.Element;

/**
 *
 * @author marian
 */
public interface IMcpXml {

    /**
     * Erstellt einen Knoten mit neuen Unterpunkten
     *  {<name>
     *      <Attribut1></Attribut1>
     *  </name>}
     *
     * Hier überlegn ob nicht grundsätzlich erst klassendeklaration und instanzierung über methode
     * @param pathParent Ort wo angelegt werden soll
     * @param name
     * @param Attribute
     */
    void createXMLObject(String pathParent, String name, String[] Attribute) throws Exception;

    /**
     * Prüft ob Pfad existiert und Datei lesbar ist als Xml Doc
     * @return
     */
    boolean existiert();

    /**
     * über diese methode können werte in XML-Baum geschrieben werden.
     * @param path
     * @return Element-Objekt hält den wert des attributes
     * @throws Exception
     */
    Element getKnoten(String path) throws Exception;

    /**
     * Speichert Document an im Konstruktor übergebener Stelle
     * wenn Datei nicht vorhanden, wird sie erstellt. Ordenrstruktur muss aber da sein
     */
    void speichern() throws Exception;
    
}
