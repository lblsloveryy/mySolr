package com.wisedu.lab;

import java.net.MalformedURLException;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.SolrParams;


public class Searching {

	private CloudSolrServer cloudSolrServer;


    public synchronized void open(final String zkHost, final String  defaultCollection,  
            int  zkClientTimeout, final int zkConnectTimeout) {  
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
	
	public final void fail(Object o) {
		System.out.println(o);
	}
	
	public String query(String query) {
		String result="";
		SolrParams params = new SolrQuery(query);
		try {
			QueryResponse response = cloudSolrServer.query(params);
			SolrDocumentList list = response.getResults();
			for (int i = 0; i < list.size(); i++) {
				fail(list.get(i));
				result+=list.get(i).toString();
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	 public static void main(String[] args) {  
          
         Searching client = new Searching();
         
         final String zkHost = "localhost:2181";           
         final String  defaultCollection = "mycollection2";  
         final int zkClientTimeout = 20000;  
         final int zkConnectTimeout = 1000;  

         client.open(zkHost, defaultCollection, zkClientTimeout, zkConnectTimeout);
         
 		if(args==null||args.length<2){
 			System.out.println("Parameter error!");
 		}
 		else
 		{
 			String field=args[0];
 			String keyword=args[1];
 			System.out.println(client.query(field+":"+keyword));//
 			System.out.println("finish!");
 		}
    }
}
