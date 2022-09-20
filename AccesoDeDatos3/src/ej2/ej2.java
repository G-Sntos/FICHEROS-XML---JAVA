package ej2;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.InputMismatchException;

import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;

import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;



import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;

import com.thoughtworks.xstream.*;

public class ej2 {

	//Biblioteca->Libro->titulo-autor-ISBN-ano-editorial-numPagina
	
	public static void main(String[] args) {
			byte opc=0;
			Scanner sc = new Scanner(System.in);
			System.out.println("AVISO: POR DEFECTO SIEMPRE SE CREA UN FICHERO SI NO EXISTE UNO.");
			boolean fichero = checkFichero(); 
			if(!fichero)crearFicheroXML(); // si no existe , se crea uno. La primera entrada seria vacio.
			
			while(opc !=5) {
			
			System.out.println("OPCIÓN 1.- INSERTAR LIBRO");
			System.out.println("OPCIÓN 2.- ACTUALIZAR. - Crear un XML"); // entiendo que este es para resetear el XML entero?
			System.out.println("OPCIÓN 3.- GENERAR XML CON XSTREAM");
			System.out.println("OPCIÓN 4.- VISUALIZAR VIA DOM.");
			System.out.println("OPCIÓN 5.- VISUALIZAR VIA SAX.");
			System.out.println("OPCIÓN 6.- GENERAR HTML CON SXL");
			System.out.println("OPCIÓN 7.- SALIR");
	
			String titulo= "",autor = "",ISBN = "",editorial = "";
			int numPagina=0, ano=0;
			boolean opcion=false;
			while(opcion==false) {
	        	try {	
	        	opc = sc.nextByte();
	        	}catch(InputMismatchException e) {
	        		System.out.println("Error, Programa terminado.");
	        		System.exit(0);
	        	}
	        	if(opc <7 && opc>0){
         			 opcion=true;
         		 	}else{
         		 		System.out.println("Numero invalido, vuelva a introducir.");
         		 	}	           
	        	}
			switch(opc) {
				case 1: 
					 
					System.out.println("Introduzca datos de Libro: ");
					
					System.out.println("Titulo: ");
					titulo = sc.next();
					
					System.out.println("Autor: ");
					autor = sc.next();
					
					System.out.println("ISBN: ");
					ISBN = sc.next();
					
					System.out.println("Editorial: ");
					editorial = sc.next();
					try {
					System.out.println("Ano: ");
					ano = sc.nextInt();
					
					System.out.println("Numero de Paginas: ");
					numPagina = sc.nextInt();
					}catch (InputMismatchException a) {
						System.out.println("Datos invalidos");
						break;
					}
					meterDatos(titulo,autor,ISBN,editorial,ano,numPagina);
					
					System.out.println("Datos anadido!");
					break;
				case 2:
					crearFicheroXML();
					System.out.println("Fichero creado!");
					System.out.println("Si hay un xml antes, ha sido reemplazado!");
					break;
				case 3:
					 crearXMLXstream();
					break;
				case 4:
					leerXML();
					break;
				case 5:
					leerSax();
					break;
				case 6:
					generarHTML();
					break;
				case 7:
					System.out.println("Gracias por usar la programa.");
					opc = 6;
					break;
			}
			
			
			}
			sc.close();
			System.exit(0);
	}
	private static void generarHTML() {
		 try {

			    TransformerFactory tFactory = TransformerFactory.newInstance();

			    Transformer transformer =
			      tFactory.newTransformer
			         (new javax.xml.transform.stream.StreamSource
			            ("biblioteca.xsl"));

			    transformer.transform
			      (new javax.xml.transform.stream.StreamSource
			            ("biblioteca.xml"),
			       new javax.xml.transform.stream.StreamResult
			            ( new FileOutputStream("biblioteca.html")));
			    }
			  catch (Exception e) {
			    e.printStackTrace( );
			    }
		 
		 System.out.println("Fichero creado!");
		 File fichero = new File("biblioteca.html");
		 System.out.println(fichero.getAbsolutePath());

			 }
		
	
	public static void leerXML(){
		File fichero = new File("biblioteca.xml");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
			Document doc = db.parse(fichero);
			NodeList nl = doc.getElementsByTagName("Libro");
			for(int itr =0; itr<nl.getLength(); itr++) {
				Node node = nl.item(itr);
				System.out.println();
				if(node.getNodeType()== Node.ELEMENT_NODE) {
					Element eElement = (Element)node;
					
					System.out.println("Titulo: "+ eElement.getElementsByTagName("Titulo").item(0).getTextContent());
					System.out.println("Autor: "+eElement.getElementsByTagName("Autor").item(0).getTextContent());
					System.out.println("ISBN: "+eElement.getElementsByTagName("ISBN").item(0).getTextContent());
					System.out.println("Editorial: "+eElement.getElementsByTagName("Editorial").item(0).getTextContent());
					System.out.println("Ano: "+eElement.getElementsByTagName("Ano").item(0).getTextContent());
					System.out.println("Numero de Pagina: "+eElement.getElementsByTagName("Paginas").item(0).getTextContent());
					System.out.println();
					
				}
			}
			
			
			
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void meterDatos(String titulo_,String autor_,String ISBN_,String editorial_, int ano_, int numPagina_) {
		File fichero = new File("biblioteca.xml");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		
		try {
			db = dbf.newDocumentBuilder();
			Document doc = db.parse(fichero);
			
			Element docElement = doc.getDocumentElement();
			
			Element textNode = doc.createElement("Titulo");
			textNode.setTextContent(titulo_);
			
			Element textNode1 = doc.createElement("Autor");
			textNode1.setTextContent(autor_);
			
			Element textNode2 = doc.createElement("ISBN");
			textNode2.setTextContent(ISBN_);
			
			Element textNode3 = doc.createElement("Editorial");
			textNode3.setTextContent(editorial_);
			
			Element textNode4 = doc.createElement("Ano");
			textNode4.setTextContent(String.valueOf(ano_));
			
			Element textNode5 = doc.createElement("Paginas");
			textNode5.setTextContent(String.valueOf(numPagina_));
			
			Element nodeElement = doc.createElement("Libro");
			
			nodeElement.appendChild(textNode);
			nodeElement.appendChild(textNode1);
			nodeElement.appendChild(textNode2);
			nodeElement.appendChild(textNode3);
			nodeElement.appendChild(textNode4);
			nodeElement.appendChild(textNode5);
			
			docElement.appendChild(nodeElement);
			
			doc.replaceChild(docElement, docElement);
			
			Transformer tFormer = TransformerFactory.newInstance().newTransformer();
			tFormer.setOutputProperty(OutputKeys.METHOD, "xml");
			Source source = new DOMSource(doc);
			Result result = new StreamResult(fichero);
			tFormer.transform(source, result);
		
			System.out.println("Anadido");
			
		} catch (ParserConfigurationException | SAXException | IOException | TransformerException | TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	public static void crearFicheroXML() {
		try {
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
			
			Document ficheroXML = documentBuilder.newDocument();
			
			Element documentoBiblioteca = ficheroXML.createElement("Biblioteca");
			ficheroXML.appendChild(documentoBiblioteca);
			
			Element libro = ficheroXML.createElement("Libro");
			documentoBiblioteca.appendChild(libro);
			
			Element modelo = ficheroXML.createElement("Titulo");
			libro.appendChild(modelo);
			
			Element autor = ficheroXML.createElement("Autor");
			libro.appendChild(autor);
			
			Element isbn = ficheroXML.createElement("ISBN");
			libro.appendChild(isbn);
			
			Element editorial = ficheroXML.createElement("Editorial");
			libro.appendChild(editorial);
			
			Element anos = ficheroXML.createElement("Ano"); // me daba error cuando uso 'n'
			libro.appendChild(anos);
			
			Element numPagina = ficheroXML.createElement("Paginas");
			libro.appendChild(numPagina);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			
			DOMSource domSource = new DOMSource(ficheroXML);
			StreamResult streamResult = new StreamResult(new File("biblioteca.xml"));
			
            transformer.transform(domSource, streamResult);
            System.out.println("Fichero creado!");
            System.out.println();
		} catch (ParserConfigurationException | TransformerException e) {
			e.printStackTrace();
		}
	
	}
	
	public static boolean checkFichero() {
		File fichero = new File("biblioteca.xml");
		if(fichero.exists()) {
			return true;
		}else {
			return false;
		}
	}

	public static void leerSax() {
		
	 try {
         SAXParserFactory factory = SAXParserFactory.newInstance();
         SAXParser saxParser = factory.newSAXParser();
         DefaultHandler handler = new DefaultHandler() {
        	 
        	boolean ztitulo = false;
 			boolean zautor = false;
 			boolean zisbn = false;
 			boolean zeditorial = false;
 			boolean zano = false;
 			boolean zapiginas = false;
 			
             public void startElement(String uri, String localName, String qName,  Attributes attributes) throws SAXException {
           
                if(qName.equalsIgnoreCase("Titulo")&&!qName.isBlank()) ztitulo=true;
 				if(qName.equalsIgnoreCase("Autor")&&!qName.isBlank()) zautor=true;
 				if(qName.equalsIgnoreCase("ISBN")&&!qName.isBlank()) zisbn=true;
 				if(qName.equalsIgnoreCase("Editorial")&&!qName.isBlank()) zeditorial=true;
 				if(qName.equalsIgnoreCase("Ano")&&!qName.isBlank()) zano=true;
 				if(qName.equalsIgnoreCase("Paginas")&&!qName.isBlank()) zapiginas=true;
             }
             
             public void endElement(String uri, String localName, String qName) throws SAXException {
            
             }
             public void characters(char ch[], int start, int length) throws SAXException {
                    
                 if (ztitulo) {
                     System.out.println("Titulo: " + new String(ch, start, length));
                     ztitulo = false;
                     
                 }
                 if (zautor) {
                     System.out.println("Autor: " + new String(ch, start, length));
                     zautor= false;
                 }
                 if (zisbn) {
                     System.out.println("ISBN: " + new String(ch, start, length));
                     zisbn= false;
                 }
                 if (zeditorial) {
                     System.out.println("Editorial: " + new String(ch, start, length));
                     zeditorial= false;
                 }
                 if (zano) {
                     System.out.println("Ano: " + new String(ch, start, length));
                     zano= false;
                 }
                 if (zapiginas) {
                     System.out.println("Paginas: " + new String(ch, start, length));
                     zapiginas= false;
                     System.out.println("");
                 }
              
               
             }     
           
         };
         
         File file = new File("biblioteca.xml");
         InputStream inputStream = new FileInputStream(file);
         Reader reader = new InputStreamReader(inputStream, "UTF-8");
         InputSource is = new InputSource(reader);
         is.setEncoding("UTF-8");
         saxParser.parse(is, handler);
       
        
     } catch (Exception e) {
         e.printStackTrace();
     }

}
	
	public static void crearXMLXstream() {
		XStream xstream = new XStream();
		xstream.alias("Biblioteca", biblioteca.class);
		biblioteca b = new biblioteca();
		String xml = xstream.toXML(b);
		System.out.println(xml);
		
		
        try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("biblioteca.xml",false));
			BufferedReader reader = new BufferedReader(new StringReader(xml));
			int c = reader.read();
			writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
			while(c!=-1) {
				
				writer.write(c);
				c = reader.read();
			}
			System.out.println("Fichero Creado! via Xstream!");
			writer.close();
			reader.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}


}

