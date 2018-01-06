package MCPXMLVerarbeitung;


import java.io.File;
import java.io.FileWriter;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author marian
 */
public class XmlDoc implements IMcpXml
{
    File speicherPfad = null;
    Document xmlDoc = null;
    
    /**
     * 
     * @param f wenn pfad nicht vorhande dann wir neues dokument erstellt
     */
    public XmlDoc(File f) throws Exception
    {
        if(f==null)
        {
            throw new Exception("Keine File-Instanz!");
        }
        
        this.speicherPfad = f;
        
        if(this.existiert())
        {
            this.xmlDoc = getDoc();
        }else
        {
            this.xmlDoc = new Document(new Element("root"));
        }
    }
    
    /**
     * Prüft ob Pfad existiert und Datei lesbar ist als Xml Doc
     * @return 
     */
    @Override
    public boolean existiert()
    {
        return this.speicherPfad!=null && this.speicherPfad.exists() && this.speicherPfad.canRead();
    }
    
    private Document getDoc() throws Exception
    {
        SAXBuilder XMLleser = new SAXBuilder();
        return XMLleser.build(this.speicherPfad);
        
    }
    
    /**
     * Speichert Document an im Konstruktor übergebener Stelle
     * wenn Datei nicht vorhanden, wird sie erstellt. Ordenrstruktur muss aber da sein
     */
    @Override
    public void speichern() throws Exception
    {
       if(!this.speicherPfad.exists())
       {
           if(!this.speicherPfad.createNewFile())
           {
               throw new Exception("Datei: " + this.speicherPfad.getAbsolutePath() + " konnte nicht erstellt werden");
           }
       }
       
        FileWriter fw = new FileWriter(this.speicherPfad);
        XMLOutputter out = new XMLOutputter();
        out.output(this.xmlDoc, fw);
    }
    
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
    @Override
    public void createXMLObject(String pathParent, String name, String[] Attribute) throws Exception
    {
        
        Element parent = getKnoten(pathParent);
        
        
        
        Element neuesElement = new Element(name);
        
        for(String atr : Attribute)
        {
            if(atr.equals(""))
            {
                throw new Exception("Leerstring nicht erlaubt als XML-Attribut");
            }
            neuesElement.addContent(new Element(atr));
        }
        
        
        parent.addContent(neuesElement);
    }
    
    /**
     * über diese methode können werte in XML-Baum geschrieben werden.
     * @param path
     * @return Element-Objekt hält den wert des attributes
     * @throws Exception 
     */
    @Override
    public Element getKnoten(String path) throws Exception
    {
        String[] ar_pfad = path.split("/");
        Element result = this.xmlDoc.getRootElement();
        
        if(ar_pfad.length == 0 || !result.getName().equals(ar_pfad[1]))
        {
            throw new Exception("Pfad nicht vorhanden");
        }
        
        for(int i =2 ; i < ar_pfad.length; i++)
        {
            String knoten = getKnotenNamen(ar_pfad[i]);
            int index = getIndex(ar_pfad[i]);
            
            List<Element> list = result.getChildren(knoten);
            if(list.isEmpty() || list.size() <= index)
            {
                throw new Exception("Element : "  + knoten + " an " + String.valueOf(index) + " nicht gefunden");
            }
            
            result = result.getChildren(knoten).get(index);
        }
        
        return result;
    }
    
    /**
     * wenn mehrere Knoten mit gleichem anmen in ebene vorhanden, dann kann über
     * Syntax knoten[i] das entsprechende Element angegeben werden.
     * @param str
     * @return Default ist 0. ansonsten liefert i zurück
     */
    private int getIndex(String str) throws Exception
    {
        if(str.indexOf("<") + str.indexOf(">") == -2)
        {
            return 0; //keine angabe vorhanden
        }
        
        if(str.indexOf("<") >= str.indexOf(">")-1) //prüft ob inhalt da und grundlegende Syntax
        {
            throw new Exception("Falsche Syntax bei Pfadelement: " + str);
        }
        
        return Integer.parseInt(str.substring(str.indexOf("<")+1, str.indexOf(">")));
    }
    
    /**
     * wenn mehrere Knoten mit gleichem anmen in ebene vorhanden, dann kann über
     * Syntax knoten[i] das entsprechende Element angegeben werden.
     * @param str
     * @return liefert knoten zurück
     */
    private String getKnotenNamen(String str)
    {
        if(str.indexOf("<") == -1)
        {
            return str;
        }else
        {
            return str.substring(0, str.indexOf("<"));
        }
    }
    
    //[ToDo] viellecht noch methode für getvalue and setvalue, aber könnte ja mit getKnoten femacht werden
   
}
