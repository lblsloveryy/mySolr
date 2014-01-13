/*
 * Class Searching
 * Author: wisedulab
 * Date: 2014-01-13
 * Version: 2
 * Function: search content field on solrcloud with zookeeper, the result format by json
 * i use it after packed the project into a fat_jar, so the code includes the process of handling params of main 
 */
package com.wisedu.lab;

import java.net.MalformedURLException;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.SolrParams;
import net.sf.json.JSONArray;


public class Searching {

	private CloudSolrServer cloudSolrServer;


    public synchronized void open(final String zkHost, 
    		final String  defaultCollection, int  zkClientTimeout, 
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
    
	//query search the "content" field 
	public String query(String param) {
		SolrParams params = new SolrQuery("content:"+param);

		SolrDocumentList list = null;
		JSONArray jsonArray_tmp = null;
		String String_result=null;
		try {
			//send search query to  solrcloudServer
            QueryResponse response = cloudSolrServer.query(params);
            //get results from solrcloudServer
            list = response.getResults();
            if(list.size()==0)
            {
            	return String_result;
            }
            jsonArray_tmp = JSONArray.fromObject(list); 
            String_result = jsonArray_tmp.toString();
            } 
			catch (SolrServerException e) {
				e.printStackTrace();
				}
		return String_result;
	}

	//main funtion
	//args[0] the keyword for searching
	public static void main(String[] args) {
		 final String zkHost="localhost:2181";
		final String defaultCollection = "mycollection2";
		final int zkClientTimeout = 20000;
		final int zkConnectTimeout = 1000;

		Searching client = new Searching();
		client.open(zkHost, defaultCollection, zkClientTimeout,
				zkConnectTimeout);
        if(args.length<1)
        	System.out.println("params  needed");
 
        String param=args[0]; 
        System.out.println(client.query(param)); 
	}}
