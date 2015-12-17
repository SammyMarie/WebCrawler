package uk.co.sammy.classes;

import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.System.getProperty;
import static org.junit.Assert.assertEquals;

/**
 * Created by smlif on 09/12/2015.
 */
public class SearchCrawlerDataTest {
    private String file;
    private JCheckBox checkBox;
    private JCheckBox checkBoxes;
    private JTextField fieldTwo;
    private JButton searchButton;
    private JComboBox<String> comboBox;
    private SearchCrawlerData searchData;
    private JTable table;
    private boolean crawling = false;
    private JLabel crawlLabel = new JLabel();
    private JLabel crawledLabel = new JLabel();
    private JProgressBar progressBar = new JProgressBar();
    private JTextField field = new JTextField();
    private JLabel crawlingLabel = new JLabel();
    private HashMap<String, ArrayList<String>> disallowMap;
    private static final String[] MAX_URLS = {"50", "100", "500", "1000"};


    @Before
    public void setUp(){
        disallowMap = new HashMap<>();
        searchData = new SearchCrawlerData(disallowMap);
        checkBox = new JCheckBox("Limit crawling to Start URL site");
        file = getProperty("user.dir") + getProperty("file.separator") + "crawler.log";
        fieldTwo = new JTextField(file);
        comboBox = new JComboBox(MAX_URLS);
        checkBoxes = new JCheckBox("Case Sensitive");
        searchButton = new JButton("Search");
        table = new JTable(new DefaultTableModel(new Object[][]{}, new String[]{"URL"}) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }

    @Test
    public void checkConstructor(){
        String host = "Http://www.sony.com";
        ArrayList<String> disallowList = new ArrayList<>();
        disallowList.add("not allowed for protocol reasons");
        disallowList.add("not allowed for security reasons");
        disallowList.add("not allowed by flash authentication");

        disallowMap.put(host, disallowList);
        assertEquals(disallowMap, searchData.getDisallowListCache());
    }

    @Test
    public void checkSettersAndGetters(){
        settingSwingObjects();

        assertEquals(field, searchData.getStartTextField());
        assertEquals(field, searchData.getSearchTextField());
        assertEquals(comboBox, searchData.getMaxComboBox());
        assertEquals(checkBox, searchData.getLimitCheckBox());
        assertEquals(fieldTwo, searchData.getLogTextField());
        assertEquals(checkBoxes, searchData.getCaseCheckBox());
        assertEquals(searchButton, searchData.getSearchButton());
        assertEquals(crawlingLabel, searchData.getCrawlingLabel2());
        assertEquals(progressBar, searchData.getProgressBar());
        assertEquals(table, searchData.getTable());
        assertEquals(crawledLabel, searchData.getCrawledLabel2());
        assertEquals(crawlLabel, searchData.getToCrawlLabel2());
        assertEquals(crawling, searchData.isCrawling());
    }

    private void settingSwingObjects() {
        searchData.setStartTextField(field);
        searchData.setSearchTextField(field);
        searchData.setMaxComboBox(comboBox);
        searchData.setLimitCheckBox(checkBox);
        searchData.setLogTextField(fieldTwo);
        searchData.setCaseCheckBox(checkBoxes);
        searchData.setSearchButton(searchButton);
        searchData.setCrawlingLabel2(crawlingLabel);
        searchData.setProgressBar(progressBar);
        searchData.setTable(table);
        searchData.setCrawledLabel2(crawledLabel);
        searchData.setToCrawlLabel2(crawlLabel);
        searchData.setCrawling(crawling);
    }
}
