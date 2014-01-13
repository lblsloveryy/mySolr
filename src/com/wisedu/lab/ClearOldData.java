/*
 * Class ClearOldData
 * Author: wisedulab
 * Date: 2014-01-13
 * Version: 2
 * Function: clear old data
 */
package com.wisedu.lab;

import java.net.MalformedURLException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;

public class ClearOldData {

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
	
	public static void main(String[] args) {
		final String zkHost="localhost:2181";
		final String defaultCollection = "mycollection2";
		final int zkClientTimeout = 20000;
		final int zkConnectTimeout = 1000;

		ClearOldData client = new ClearOldData();
		client.open(zkHost, defaultCollection, zkClientTimeout,
				zkConnectTimeout);
		
		try {
			client.cloudSolrServer.deleteByQuery("*:*");
			client.cloudSolrServer.commit();
		} catch (Exception e) {
            e.printStackTrace();
        }
		
		System.out.println("Clear completed!");

	}
}
