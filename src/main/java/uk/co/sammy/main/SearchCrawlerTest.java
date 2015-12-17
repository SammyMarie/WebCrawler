package uk.co.sammy.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import uk.co.sammy.classes.SearchCrawler;

/**
 * Created by smlif on 07/12/2015.
 */
public class SearchCrawlerTest {

    private static ApplicationContext context;

    public static void main(String... args){
        context = new ClassPathXmlApplicationContext("spring.xml");
        SearchCrawler crawler = (SearchCrawler) context.getBean("crawler");
        crawler.setVisible(true);
    }
}
