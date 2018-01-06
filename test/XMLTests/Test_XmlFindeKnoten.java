package XMLTests;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import MCPXMLVerarbeitung.XmlDoc;
import java.io.File;
import java.io.FileWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Text;
import org.jdom2.output.XMLOutputter;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author marian
 */
public class Test_XmlFindeKnoten {
    static File testXMLContent;
    static File testXMLOhneContent;   //wenn Zei, dann ohne COntent geschichten in neue klasse auslagern. macht das ganze sonst unsauber :)
    private XmlDoc myXMLKlasseContent;
    private XmlDoc myXMLKlasseOhneContent;
    
    public Test_XmlFindeKnoten() {
    }
    
    @BeforeClass
    public static void setUpClass() {
       
    }
    
    private static File erstelleTestXMLOhneContent() throws Exception
    {
        File fxml;
        fxml = new File(System.getProperty("user.dir") + "test2.xml");
        if(!fxml.exists())
        {
            fxml.createNewFile();
        }
        
        return fxml;
    }
    
    private static File erstelleTestXMLContent() throws Exception
    {
       File fxml;
       FileWriter io;
              
        /*
        Xml-Struktur festlegen
        */
        Element root = new Element("mcp");
                
        Element unterknoten = new Element("Unter1");
        root.addContent(unterknoten);
        
        Element container = new Element("Objekt0");
        unterknoten.addContent(container);
        
        Element datenfeld = new Element("Attribut1");
        datenfeld.setContent(new Text("Attribut1"));
        container.addContent(datenfeld);
        
        datenfeld = new Element("Attribut2");
        datenfeld.setContent(new Text("Attribut2"));
        container.addContent(datenfeld);
        
        container = new Element("Objekt0");
        unterknoten.addContent(container);
        
        datenfeld = new Element("Attribut1");
        datenfeld.setContent(new Text("Attribut1"));
        container.addContent(datenfeld);
        
        datenfeld = new Element("Attribut2");
        datenfeld.setContent(new Text("Attribut2"));
        container.addContent(datenfeld);
        
        
        /*
        Dokument speichern
        */
          
        XMLOutputter out = new XMLOutputter();
        Document xml = new Document(root);
            
        fxml = new File(System.getProperty("user.dir") + "test.xml");
        if(!fxml.exists())
        {
            fxml.createNewFile();
        }
        io = new FileWriter(fxml);
        out.output(xml, io);
        return fxml;
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
         try { 
            testXMLContent = erstelleTestXMLContent();
            testXMLOhneContent = erstelleTestXMLOhneContent();
        } catch (Exception ex) {
            System.out.println("Test-XML konnte nicht erstellt werden");
        }
        try {
            this.myXMLKlasseContent = new XmlDoc(testXMLContent);
            this.myXMLKlasseOhneContent = new XmlDoc(testXMLOhneContent);
        } catch (Exception ex) {
            System.out.println("Instanz con XMLdoc konnte nicht erstellt werden");
        }
    }
    
    @After
    public void tearDown()
    {
        testXMLContent.delete();
        testXMLContent.delete();
    }

    /*
     Tests
    */
    
    @Test
    public void test_FindeKnotenEinfacherPfad()
    {
        try {
            Element knoten = this.myXMLKlasseContent.getKnoten("/mcp/Unter1/Objekt0/Attribut1");
            Assert.assertEquals("Attribut1", knoten.getValue());
        } catch (Exception ex) {
           Assert.assertFalse("Knoten konnte nicht gefunden werden",true);
        }
    }
    
    @Test
    public void test_FindeKnotenPfadMitMehrerenObjekten()
    {
        try {
            Element knoten = this.myXMLKlasseContent.getKnoten("/mcp/Unter1/Objekt0<1>/Attribut1");
            Assert.assertEquals("Attribut1", knoten.getValue());
        } catch (Exception ex) {
           Assert.assertFalse("Knoten konnte nicht gefunden werden",true);
        }
    }
    
     @Test
    public void neuesDocErstelln()
    {
        String[] ar = {"Objekt", "Objekt"};
        try {
            this.myXMLKlasseOhneContent.createXMLObject("/root", "Container1",ar);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Assert.assertFalse(true);
        }
        Assert.assertFalse(false);
    }
   
}
