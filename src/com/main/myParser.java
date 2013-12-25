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
			 parser = new PDFParser();// ����PDF�ĵ�
		}else if(fileURL.endsWith("doc")){
			parser = new OfficeParser();//����΢���ʽ�ĵ�
		}else if(fileURL.endsWith("docx")){
			parser =new OOXMLParser();
		}
   
		if(parser==null)
			return "";
		
		InputStream iStream = new BufferedInputStream(new FileInputStream(
				new File(fileURL)));// ����������
		// OutputStream oStream = new BufferedOutputStream(new
		// FileOutputStream(new File(OUTPATH)));//���������

		// ���涨�����ݴ�����
		// ContentHandler iHandler = new BodyContentHandler(oStream);
		// ContentHandler iHandler = new BodyContentHandler(System.out);
		ContentHandler iHandler = new BodyContentHandler();
		Metadata meta = new Metadata();
		meta.add(Metadata.CONTENT_ENCODING, "utf-8");
		parser.parse(iStream, iHandler, meta, new ParseContext());// ����

		// ����������������������ķ�ʽ��ֱ����������л�ý������
		System.out.println(iHandler.toString());
		return iHandler.toString();
	}
}