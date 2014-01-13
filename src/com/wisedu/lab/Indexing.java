/*
 * Class Indexing
 * Author: wisedulab
 * Date: 2014-01-13
 * Version: 2
 * Function: index files by three fields(id, content, url) on solrcloud with zookeeper
 * i use it after packed the project into a fat_jar, so the code includes the process of handling params of main 
 */
package com.wisedu.lab;

import com.main.*;
import java.io.IOException;
import java.net.MalformedURLException;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;


public class Indexing {

	private CloudSolrServer cloudSolrServer;

	public synchronized void open(final String zkHost,
			final String defaultCollection, int zkClientTimeout,
			final int zkConnectTimeout) {
		if (cloudSolrServer == null) {
			try {
				cloudSolrServer = new CloudSolrServer(zkHost);
				cloudSolrServer.setDefaultCollection(defaultCollection);
				cloudSolrServer.setZkClientTimeout(zkClientTimeout);
				cloudSolrServer.setZkConnectTimeout(zkConnectTimeout);
			} catch (MalformedURLException e) {
				System.out
						.println("The URL of zkHost is not correct!! Its form must as below:\n zkHost:port");
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void addDoc(long id, String url, String content) {
		try {

				SolrInputDocument doc = new SolrInputDocument();
				doc.addField("id", id);
				doc.addField("url", url);
				doc.addField("content", content);
				cloudSolrServer.add(doc);
		} catch (SolrServerException e) {
			System.err.println("Add docs Exception !!!");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("Unknowned Exception!!!!!");
			e.printStackTrace();
		}

	}

	//main funtion
	//args[0] id type:long
	//args[1] url type:string
	public static void main(String[] args) {
		final String zkHost="localhost:2181";
		final String defaultCollection = "mycollection2";
		final int zkClientTimeout = 20000;
		final int zkConnectTimeout = 1000;

		Indexing client = new Indexing();
		client.open(zkHost, defaultCollection, zkClientTimeout,
				zkConnectTimeout);
		if(args==null||args.length<2)
			return ;

		
		//args[0]:id long
		//args[1]:url string
		long id = Integer.parseInt(args[0]);
		String url=args[1];
		
		System.out.println(url);
		
		String content="";
		try {
			content=myParser.parse(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		client.addDoc(id, url, content);
		System.out.println("Finish!");

	}

}