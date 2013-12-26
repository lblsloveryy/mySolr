package com.main;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import org.apache.tika.metadata.Metadata;

import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.microsoft.ooxml.OOXMLParser;
import org.apache.tika.parser.microsoft.OfficeParser;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;

public class myParser {

	public static String parse(String fileURL) throws Exception {
		
		
		Parser parser = null;
		if (fileURL.endsWith("pdf")) {
			 parser = new PDFParser();
		}else if(fileURL.endsWith("doc")){
			parser = new OfficeParser();
		}else if(fileURL.endsWith("docx")){
			parser =new OOXMLParser();
		}
   
		if(parser==null)
		{
			System.out.println("couldn't analyse");//TEST
			return "";
			}
		
		InputStream iStream = new BufferedInputStream(new FileInputStream(
				new File(fileURL)));

		ContentHandler iHandler = new BodyContentHandler();
		Metadata meta = new Metadata();
		meta.add(Metadata.CONTENT_ENCODING, "utf-8");
		parser.parse(iStream, iHandler, meta, new ParseContext());

		System.out.println(iHandler.toString());
		
		return iHandler.toString();
	}
}