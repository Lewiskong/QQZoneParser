package com.qzoneparser.utils;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qzoneparser.bean.Message;
import com.qzoneparser.config.Constants;

public class CrawlProcessUtils {
	public static ArrayList<Message> getCurrentPageMsgs(int startPage) throws Exception {
		StringBuilder url=new StringBuilder();
		url.append(Constants.PREFIX_URL);
		url.append("uin=847496788&hostUin=");
		url.append(Constants.HOST_UIN);
		url.append("&start=");
		url.append(startPage*Constants.NUM_PER_PAGE);
		url.append("&s=0.530155199393928&format=jsonp&num=");
		url.append(Constants.NUM_PER_PAGE);
		url.append("&inCharset=utf-8&outCharset=utf-8&g_tk=636777834");
		CloseableHttpClient client=HttpClients.createDefault();
		HttpGet get=new HttpGet();
		get.setURI(URI.create(url.toString()));
		RequestConfig config=RequestConfig.custom().setSocketTimeout(1000).setConnectTimeout(2000).build();
		get.setConfig(config);
		
		CloseableHttpResponse response=client.execute(get);
		HttpEntity entity=response.getEntity();
		String str=EntityUtils.toString(entity);
		String content=str.substring(0, str.length()-5).substring(str.indexOf("commentList")+"commentList\":".length());
		ArrayList<Message> msgs=new ArrayList<Message>();
		ObjectMapper map=new ObjectMapper();
		try{
			msgs=map.readValue(content, ArrayList.class);
		}catch(Exception e){
			System.out.println("亲，该QQ空间不对外开放，请先以可访问好友身份登陆~");
			return null;
		}
		EntityUtils.consume(entity);
		return msgs;
	}
	
	public static ArrayList<Message> getAllMsgs() throws Exception{
		ArrayList<Message> msgs=new ArrayList<Message>();
		for (int page=0;;page++){
			ArrayList<Message> temp=getCurrentPageMsgs(page);
			if (temp==null) {msgs=null;break;}
			else if (temp.size()==0) break;
			else msgs.addAll(temp);
		}
		return msgs;
	}
}
