package com.qzoneparser.domain;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.qzoneparser.bean.Message;
import com.qzoneparser.config.Constants;
import com.qzoneparser.utils.CrawlProcessUtils;

public class QZoneParser {
	private static Logger log=Logger.getLogger("com.qzoneparser.domain");
	public static void main(String[] args) throws Exception{
		ArrayList<Message> msgs=CrawlProcessUtils.getAllMsgs();
		if (msgs!=null){
			for (int index=0;index<msgs.size();index++){
				System.out.println(msgs.get(index));
			}
			System.out.println("ÕËºÅ"+Constants.HOST_UIN+"¹²ÓÐ"+msgs.size()+"ÌõÁôÑÔ");
		}
	}
}
