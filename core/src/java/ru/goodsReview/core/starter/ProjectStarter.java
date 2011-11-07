package ru.goodsReview.core.starter;
/*
 *  Date: 07.11.11
 *  Time: 09:27
 *  Author: 
 *     Vanslov Evgeny 
 *     vans239@gmail.com
 */

import org.apache.log4j.Logger;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class ProjectStarter {
	private static final Logger log = Logger.getLogger(ProjectStarter.class);

	public static void main(String argv[]){
		log.info("Project started");
		final FileSystemXmlApplicationContext frontend = new FileSystemXmlApplicationContext("/frontend/src/scripts/beans.xml");
		final FileSystemXmlApplicationContext indexer = new FileSystemXmlApplicationContext("/indexer/src/scripts/beans.xml");
		final FileSystemXmlApplicationContext extractor = new FileSystemXmlApplicationContext("/extractor/src/scripts/beans.xml");
		//final FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext();

	}
}
