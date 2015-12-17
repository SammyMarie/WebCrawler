package uk.co.sammy.classes;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.JTextField;

public class SearchCrawlerData {
    private HashMap<String, ArrayList<String>> disallowListCache;
    private JTextField startTextField;
    private JComboBox<String> maxComboBox;
    private JCheckBox limitCheckBox;
    private JTextField logTextField;
    private JTextField searchTextField;
    private JCheckBox caseCheckBox;
    private JButton searchButton;
    private JLabel crawlingLabel2;
    private JLabel crawledLabel2;
    private JLabel toCrawlLabel2;
    private JProgressBar progressBar;
    private JLabel matchesLabel2;
    private JTable table;
    private boolean crawling;
    private PrintWriter logFileWriter;

    public SearchCrawlerData(HashMap<String, ArrayList<String>> disallowListCache) {
        this.disallowListCache = disallowListCache;
    }

    public void setStartTextField(JTextField startTextField) {
        this.startTextField = startTextField;
    }

    public void setMaxComboBox(JComboBox<String> maxComboBox) {
        this.maxComboBox = maxComboBox;
    }

    public void setLimitCheckBox(JCheckBox limitCheckBox) {
        this.limitCheckBox = limitCheckBox;
    }

    public void setLogTextField(JTextField logTextField) {
        this.logTextField = logTextField;
    }

    public void setSearchTextField(JTextField searchTextField) {
        this.searchTextField = searchTextField;
    }

    public void setCaseCheckBox(JCheckBox caseCheckBox) {
        this.caseCheckBox = caseCheckBox;
    }

    public void setSearchButton(JButton searchButton) {
        this.searchButton = searchButton;
    }

    public void setCrawlingLabel2(JLabel crawlingLabel2) {
        this.crawlingLabel2 = crawlingLabel2;
    }

    public void setProgressBar(JProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public void setCrawledLabel2(JLabel crawledLabel2) {
        this.crawledLabel2 = crawledLabel2;
    }

    public void setToCrawlLabel2(JLabel toCrawlLabel2) {
        this.toCrawlLabel2 = toCrawlLabel2;
    }

    public void setCrawling(boolean crawling) {
        this.crawling = crawling;
    }

    public void setMatchesLabel2(JLabel matchesLabel2) {
        this.matchesLabel2 = matchesLabel2;
    }

    public void setLogFileWriter(PrintWriter logFileWriter) {
        this.logFileWriter = logFileWriter;
    }

    public HashMap<String, ArrayList<String>> getDisallowListCache() {
        return disallowListCache;
    }

    public JTextField getStartTextField() {
        return startTextField;
    }

    public JComboBox<String> getMaxComboBox() {
        return maxComboBox;
    }

    public JCheckBox getLimitCheckBox() {
        return limitCheckBox;
    }

    public JTextField getLogTextField() {
        return logTextField;
    }

    public JTextField getSearchTextField() {
        return searchTextField;
    }

    public JCheckBox getCaseCheckBox() {
        return caseCheckBox;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JLabel getCrawlingLabel2() {
        return crawlingLabel2;
    }

    public JLabel getCrawledLabel2() {
        return crawledLabel2;
    }

    public JLabel getToCrawlLabel2() {
        return toCrawlLabel2;
    }

    public JProgressBar getProgressBar() {
        return progressBar;
    }

    public JLabel getMatchesLabel2() {
        return matchesLabel2;
    }

    public JTable getTable() {
        return table;
    }

    public boolean isCrawling() {
        return crawling;
    }

    public PrintWriter getLogFileWriter() {
        return logFileWriter;
    }
}

