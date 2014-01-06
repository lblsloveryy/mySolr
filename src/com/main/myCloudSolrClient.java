package com.main;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;


public class myCloudSolrClient {

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

	public void addDoc(long id, String area, int buildingType, String category,
			int temperature, String code, double latitude, double longitude,
			String when) {
		try {

				SolrInputDocument doc = new SolrInputDocument();
				doc.addField("id", id);
				doc.addField("area", area);
				doc.addField("building_type", buildingType);
				doc.addField("category", category);
				doc.addField("temperature", temperature);
				doc.addField("code", code);
				doc.addField("latitude", latitude);
				doc.addField("longitude", longitude);
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


	public static void main(String[] args) {
		final String zkHost="localhost:2181";
		final String defaultCollection = "mycollection2";
		final int zkClientTimeout = 20000;
		final int zkConnectTimeout = 1000;

		myCloudSolrClient client = new myCloudSolrClient();
		client.open(zkHost, defaultCollection, zkClientTimeout,
				zkConnectTimeout);
		if(args==null||args.length<1)
			return ;

		String path=args[0];
		System.out.println(path);
		
		String myare="";
		try {
			myare=myParser.parse(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		long id = 2222222;
		int buidingType = 1;
		double latitude = 2;
		String area = " �ص�";
		client.addDoc(id, myare, buidingType, "���", buidingType, "code",
				latitude, latitude, area);
		System.out.println("Finish!");

	}

}