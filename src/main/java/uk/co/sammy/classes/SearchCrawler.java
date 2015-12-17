package uk.co.sammy.classes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.*;

public class SearchCrawler extends JFrame {

    //Max URls dropdown values.
    private static final String[] MAX_URLS = {"50", "100", "500", "1000"};

    private SearchCrawlerData data = new SearchCrawlerData(new HashMap<String, ArrayList<String>>());

    //constructor for search web crawler
    public SearchCrawler() {
        //Setting application title
        setTitle("Search Crawler");

        //Sets window size
        setSize(1600, 1200);

        //Handles window closing events.
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                actionExit();
            }
        });

        //Sets up file menu
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem fileExitMenuItem = new JMenuItem("Exit", KeyEvent.VK_X);
        fileExitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionExit();
            }
        });
        fileMenu.add(fileExitMenuItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        //Sets up search panel.
        JPanel searchPanel = new JPanel();
        GridBagConstraints constraints;
        GridBagLayout layout = new GridBagLayout();
        searchPanel.setLayout(layout);

        JLabel startLabel = new JLabel("Start URL:");
        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.EAST;
        constraints.insets = new Insets(5, 5, 0, 0);
        layout.setConstraints(startLabel, constraints);
        searchPanel.add(startLabel);

        data.setStartTextField(new JTextField());
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 5, 0, 0);
        layout.setConstraints(data.getStartTextField(), constraints);
        searchPanel.add(data.getStartTextField());

        JLabel maxLabel = new JLabel("Max URLs to Crawl:");
        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.EAST;
        constraints.insets = new Insets(5, 5, 0, 0);
        layout.setConstraints(maxLabel, constraints);
        searchPanel.add(maxLabel);

        data.setMaxComboBox(new JComboBox(MAX_URLS));
        data.getMaxComboBox().setEditable(true);
        constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 0, 0);
        layout.setConstraints(data.getMaxComboBox(), constraints);
        searchPanel.add(data.getMaxComboBox());

        data.setLimitCheckBox(new JCheckBox("Limit crawling to Start URL site"));
        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(0, 10, 0, 0);
        layout.setConstraints(data.getLimitCheckBox(), constraints);
        searchPanel.add(data.getLimitCheckBox());

        JLabel blankLabel = new JLabel();
        constraints = new GridBagConstraints();
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(blankLabel, constraints);
        searchPanel.add(blankLabel);

        JLabel logLabel = new JLabel("Matches Log File:");
        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.EAST;
        constraints.insets = new Insets(5, 5, 0, 0);
        layout.setConstraints(logLabel, constraints);
        searchPanel.add(logLabel);

        String file = getProperty("user.dir") + getProperty("file.separator") + "crawler.log";
        data.setLogTextField(new JTextField(file));
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 5, 0, 5);
        layout.setConstraints(data.getLogTextField(), constraints);
        searchPanel.add(data.getLogTextField());

        JLabel searchLabel = new JLabel("Search String:");
        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.EAST;
        constraints.insets = new Insets(5, 5, 0, 0);
        layout.setConstraints(searchLabel, constraints);
        searchPanel.add(searchLabel);

        data.setSearchTextField(new JTextField());
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 5, 0, 0);
        constraints.gridwidth = 2;
        constraints.weightx = 1.0d;
        layout.setConstraints(data.getSearchTextField(), constraints);
        searchPanel.add(data.getSearchTextField());

        data.setCaseCheckBox(new JCheckBox("Case Sensitive"));
        constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 0, 5);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(data.getCaseCheckBox(), constraints);
        searchPanel.add(data.getCaseCheckBox());

        data.setSearchButton(new JButton("Search"));
        data.getSearchButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionSearch();
            }
        });

        constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        layout.setConstraints(data.getSearchButton(), constraints);
        searchPanel.add(data.getSearchButton());

        JSeparator separator = new JSeparator();
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 5, 5, 5);
        layout.setConstraints(separator, constraints);
        searchPanel.add(separator);

        JLabel crawlingLabel1 = new JLabel("Crawling:");
        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.EAST;
        constraints.insets = new Insets(5, 5, 0, 0);
        layout.setConstraints(crawlingLabel1, constraints);
        searchPanel.add(crawlingLabel1);

        data.setCrawlingLabel2(new JLabel());
        data.getCrawlingLabel2().setFont(data.getCrawlingLabel2().getFont().deriveFont(Font.PLAIN));
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 5, 0, 5);
        layout.setConstraints(data.getCrawlingLabel2(), constraints);
        searchPanel.add(data.getCrawlingLabel2());

        JLabel crawledLabel1 = new JLabel("Crawled URLs:");
        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.EAST;
        constraints.insets = new Insets(5, 5, 0, 0);
        layout.setConstraints(crawledLabel1, constraints);
        searchPanel.add(crawledLabel1);

        data.setCrawledLabel2(new JLabel());
        data.getCrawledLabel2().setFont(data.getCrawledLabel2().getFont().deriveFont(Font.PLAIN));
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 5, 0, 5);
        layout.setConstraints(data.getCrawledLabel2(), constraints);
        searchPanel.add(data.getCrawledLabel2());

        JLabel toCrawlLabel1 = new JLabel("URLs to crawl:");
        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.EAST;
        constraints.insets = new Insets(5, 5, 0, 0);
        layout.setConstraints(toCrawlLabel1, constraints);
        searchPanel.add(toCrawlLabel1);

        data.setToCrawlLabel2(new JLabel());
        data.getToCrawlLabel2().setFont(data.getToCrawlLabel2().getFont().deriveFont(Font.PLAIN));
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 5, 0, 5);
        layout.setConstraints(data.getToCrawlLabel2(), constraints);
        searchPanel.add(data.getToCrawlLabel2());

        JLabel progressLabel = new JLabel("Crawling Progress:");
        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.EAST;
        constraints.insets = new Insets(5, 5, 0, 0);
        layout.setConstraints(progressLabel, constraints);
        searchPanel.add(progressLabel);

        data.setProgressBar(new JProgressBar());
        data.getProgressBar().setMinimum(0);
        data.getProgressBar().setStringPainted(true);
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 5, 0, 5);
        layout.setConstraints(data.getProgressBar(), constraints);
        searchPanel.add(data.getProgressBar());

        JLabel matchesLabel1 = new JLabel("Search Matches:");
        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.EAST;
        constraints.insets = new Insets(5, 5, 10, 0);
        layout.setConstraints(matchesLabel1, constraints);
        searchPanel.add(matchesLabel1);

        data.setMatchesLabel2(new JLabel());
        data.getMatchesLabel2().setFont(data.getMatchesLabel2().getFont().deriveFont(Font.PLAIN));
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 5, 10, 5);
        layout.setConstraints(data.getMatchesLabel2(), constraints);
        searchPanel.add(data.getMatchesLabel2());

        //Sets up matches table
        data.setTable(new JTable(new DefaultTableModel(new Object[][]{}, new String[]{"URL"}) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        }));

        //Sets up matches panel.
        JPanel matchesPanel = new JPanel();
        matchesPanel.setBorder(BorderFactory.createTitledBorder("Matches"));
        matchesPanel.setLayout(new BorderLayout());
        matchesPanel.add(new JScrollPane(data.getTable()), BorderLayout.CENTER);

        //Adds panels to display
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(searchPanel, BorderLayout.NORTH);
        getContentPane().add(matchesPanel, BorderLayout.CENTER);
    }

    //Exits the program.
    private void actionExit() {
        System.exit(0);
    }

    //Handles Search/Stop button being clicked
    private void actionSearch() {
        //If stop button is clicked, turn crawling flag off.
        if (data.isCrawling()) {
            data.setCrawling(false);
            return;
        }

        ArrayList<String> errorList = new ArrayList<>();

        //Validates that start URL has been entered.
        String startUrl = data.getStartTextField().getText().trim();
        if (startUrl.length() < 1) {
            errorList.add("Missing Start URL.");
        } else if (verifyUrl(startUrl) == null) { //Verifies start URL.
            errorList.add("Invalid Start URL.");
        }

        //Validates that Max URLs is either empty or is a number.
        int maxUrls = 0;
        String max = ((String) data.getMaxComboBox().getSelectedItem()).trim();
        if (max.length() > 0) {
            try {
                maxUrls = Integer.parseInt(max);
            } catch (NumberFormatException nfe) {
                nfe.getStackTrace();
            }
            if (maxUrls < 1) {
                errorList.add("Invalid Max URLs Value.");
            }
        }

        //Validates that matches log file has been entered.
        String logFile = data.getLogTextField().getText().trim();
        if (logFile.length() < 1) {
            errorList.add("Missing Matches Log File.");
        }

        //Validates that search string has been entered.
        String searchString = data.getSearchTextField().getText().trim();
        if (searchString.length() < 1) {
            errorList.add("Missing Search String.");
        }

        //Shows errors, if any, and return.
        if (errorList.size() > 0) {
            StringBuffer message = new StringBuffer();

            //Concatenates errors into single message.
            for (int i = 0; i < errorList.size(); i++) {
                message.append(errorList.get(i));
                if (i + 1 < errorList.size()) {
                    message.append("\n");
                }
            }
            showError(message.toString());
            return;
        }

        //Removes "www" from the start URL if present.
        startUrl = removeWwwFromUrl(startUrl);

        //Starts the Search Crawler
        search(logFile, startUrl, maxUrls, searchString);
    }

    private void search(final String logFile, final String startUrl, final int maxUrls, final String searchString) {
        //Starts the search in a new thread.
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //Shows hour glass cursor while crawling is under way.
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                //Disables search controls.
                data.getStartTextField().setEnabled(false);
                data.getMaxComboBox().setEnabled(false);
                data.getLimitCheckBox().setEnabled(false);
                data.getLogTextField().setEnabled(false);
                data.getSearchTextField().setEnabled(false);
                data.getCaseCheckBox().setEnabled(false);

                //Switches Search button to "Stop"
                data.getSearchButton().setText("Stop");

                //Resets stats.
                data.getTable().setModel(new DefaultTableModel(new Object[][]{}, new String[]{"URL"}) {
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                });
                updateStats(startUrl, 0, 0, maxUrls);

                //Opens matches log file.
                try {
                    data.setLogFileWriter(new PrintWriter(new FileWriter(logFile)));
                } catch (Exception e) {
                    showError("Unable to open matches log file.");
                    return;
                }

                //Turns crawling flag on.
                data.setCrawling(true);

                //Performs the actual crawling.
                crawl(startUrl, maxUrls, data.getLimitCheckBox().isSelected(), searchString, data.getCaseCheckBox().isSelected());

                //Turns crawling flag off.
                data.setCrawling(false);

                //Closes matches log file.
                try {
                    data.getLogFileWriter().close();
                } catch (Exception e) {
                    showError("Unable to close matches log file.");
                }

                //Marks search as done.
                data.getCrawlingLabel2().setText("Done");

                //Enables search controls.
                data.getStartTextField().setEnabled(true);
                data.getMaxComboBox().setEnabled(true);
                data.getLimitCheckBox().setEnabled(true);
                data.getLogTextField().setEnabled(true);
                data.getSearchTextField().setEnabled(true);
                data.getCaseCheckBox().setEnabled(true);

                //Switches search button back to "Search."
                data.getSearchButton().setText("Search");

                //Returns to default cursor.
                setCursor(Cursor.getDefaultCursor());

                //Shows message if search string not found.
                if (data.getTable().getRowCount() == 0) {
                    JOptionPane.showMessageDialog(SearchCrawler.this, "Your Search String was not found. Please try another.",
                            "Search String notFound", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        thread.start();
    }

    //Shows dialog box with error message.
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    //Updates crawling stats.
    private void updateStats(String crawling, int crawled, int toCrawl, int maxUrls) {
        data.getCrawlingLabel2().setText(crawling);
        data.getCrawledLabel2().setText("" + crawled);
        data.getToCrawlLabel2().setText("" + toCrawl);

        //Updates progress bar.
        if (maxUrls == -1) {
            data.getProgressBar().setMaximum(crawled + toCrawl);
        } else {
            data.getProgressBar().setMaximum(maxUrls);
        }
        data.getProgressBar().setValue(crawled);
        data.getMatchesLabel2().setText("" + data.getTable().getRowCount());
    }

    //Adds match to matches tables and log file.
    private void addMatch(String url) {
        //Adds URL to matches table.
        DefaultTableModel model = (DefaultTableModel) data.getTable().getModel();
        model.addRow(new Object[]{url});

        //Adds URL to matches log file.
        try {
            data.getLogFileWriter().println(url);
        } catch (Exception e) {
            showError("Unable to log match.");
        }
    }

    //Verifies URL format.
    private URL verifyUrl(String url) {
        //Only allow HTTP URLs.
        if (!url.toLowerCase().startsWith("http://")) {
            return null;
        }

        //Verifies format of URL.
        URL verifiedUrl = null;
        try {
            verifiedUrl = new URL(url);
        } catch (MalformedURLException mue) {
            return null;
        }
        return verifiedUrl;
    }

    //Checks if robot is allowed to access the given URL.
    private boolean isRobotAllowed(URL urlToCheck) {
        String host = urlToCheck.getHost().toLowerCase();

        //Retrieves host's disallow list from cache.
        ArrayList<String> disallowList = (ArrayList) data.getDisallowListCache().get(host);

        //If list is not in the cache, download and cache it.
        if (disallowList == null) {
            disallowList = new ArrayList<>();

            try {
                URL robotsFileUrl = new URL("http://" + host + "/robots.txt");

                //Opens connection to robot file URL for reading.
                BufferedReader reader = new BufferedReader(new InputStreamReader(robotsFileUrl.openStream()));

                //Reads robot file, creating list of disallowed paths.
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.indexOf("Disallow:") == 0) {
                        String disallowPath = line.substring("Disallow:".length());

                        //Checks disallow path for comments and remove if present.
                        int commentIndex = disallowPath.indexOf("#");
                        if (commentIndex != -1) {
                            disallowPath.substring(0, commentIndex);
                        }

                        //Removes leading or trailing spaces from disallow path.
                        disallowPath = disallowPath.trim();

                        //Adds disallow path to list.
                        disallowList.add(disallowPath);
                    }
                }

                //Adds new disallow to cache
                data.getDisallowListCache().put(host, disallowList);
            } catch (Exception e) {
                //Assume robot is allowed as exception would be thrown if robot file doesn't exist
                return true;
            }
        }

        //Loops through disallow list to know if crawling is allowed for URL.
        String file = urlToCheck.getFile();
        for (int i = 0; i < disallowList.size(); i++) {
            String disallow = disallowList.get(i);
            if (file.startsWith(disallow)) {
                return false;
            }
        }
        return true;
    }

    //Downloads page at given URL.
    private String downloadPage(URL pageUrl) {
        try {
            //Opens connection to URL for reading.
            BufferedReader reader = new BufferedReader(new InputStreamReader(pageUrl.openStream()));

            //Reads page into buffer.
            String line;
            StringBuffer pageBuffer = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                pageBuffer.append(line);
            }
            return pageBuffer.toString();
        } catch (IOException ioe) {
            ioe.getStackTrace();
        }
        return null;
    }

    //Removes leading "www" from a URL's host if present.
    private String removeWwwFromUrl(String url) {
        int index = url.indexOf("://www.");
        if (index != -1) {
            return url.substring(0, index + 3) + url.substring(index + 7);
        }
        return url;
    }

    //Parses through page contents and retrieves links.
    private ArrayList<String> retrieveLinks(URL pageUrl, String pageContents, HashSet<String> crawledList, boolean limitHost) {
        //Compiles link matching pattern.
        Pattern patterns = Pattern.compile("<a\\s+href\\s*==\\s*\"?(.*?)[\"|>]", Pattern.CASE_INSENSITIVE);
        Matcher match = patterns.matcher(pageContents);

        //Creates a list of link matches.
        ArrayList<String> linkList = new ArrayList<>();
        while (match.find()) {
            String links = match.group(1).trim();

            //Skips empty links.
            if (links.length() < 1) {
                continue;
            }

            //Skips page anchor links
            if (links.charAt(0) == '#') {
                continue;
            }

            //Skips mailto links.
            if (links.indexOf("mailto:") != -1) {
                continue;
            }

            //Skips JavaScript links.
            if (links.toLowerCase().indexOf("javascript") != -1) {
                continue;
            }

            //Prefixes absolute and relative URLS if necessary
            if (links.indexOf("://") == -1) {
                //Handles absolute URLs.
                if (links.charAt(0) == '/') {
                    links = "http://" + pageUrl.getHost() + links;

                    //Handles relative URLs.
                } else {
                    String file = pageUrl.getFile();
                    if (file.indexOf('/') == -1) {
                        links = "http://" + pageUrl.getHost() + "/" + links;
                    } else {
                        String path = file.substring(0, file.lastIndexOf('/') + 1);
                        links = "http://" + pageUrl.getHost() + path + links;
                    }
                }
            }

            //Removes anchors from link.
            int index = links.indexOf('#');
            if (index != -1) {
                links = links.substring(0, index);
            }

            //Removes leading "www" from URLs host if present.
            links = removeWwwFromUrl(links);

            //Verifies link and skips if invalid
            URL verifiedLink = verifyUrl(links);
            if (verifiedLink == null) {
                continue;
            }

            //Limits links with same host as start URL.
            if (limitHost && !pageUrl.getHost().toLowerCase().equals(verifiedLink.getHost().toLowerCase())) {
                continue;
            }

            //Skips link if already crawled
            if (crawledList.contains(links)) {
                continue;
            }

            //Adds links to list.
            linkList.add(links);
        }

        return linkList;
    }

    //Determines if search String has been matched
    private boolean searchStringMatches(String pageContents, String searchString, boolean caseSensitive) {
        String searchContents = pageContents;

        //Lowers case of page contents
        if (!caseSensitive) {
            searchContents = pageContents.toLowerCase();
        }

        //Splits search string into individual terms.
        Pattern patterns = Pattern.compile("[\\s]+");
        String[] terms = patterns.split(searchString);

        //Checks to see if each term matches.
        for (int i = 0; i < terms.length; i++) {
            if (caseSensitive) {
                if (searchContents.indexOf(terms[i]) == -1) {
                    return false;
                }
            } else {
                if (searchContents.indexOf(terms[i].toLowerCase()) == -1) {
                    return false;
                }
            }
        }
        return true;
    }

    //Performs actual crawling by searching for search string.
    public void crawl(String startUrl, int maxUrls, boolean limitHost, String searchString, boolean caseSensitive) {
        //Sets up craw list.
        HashSet<String> crawledList = new HashSet<>();
        LinkedList<String> toCrawList = new LinkedList<>();

        //Adds start URL ToCrawl list
        toCrawList.add(startUrl);

        //Performs crawling by looping through ToCrawl List.
        while (data.isCrawling() && toCrawList.size() > 0) {
            //Checks if max URL count has been reached
            if (maxUrls != -1) {
                if (crawledList.size() == maxUrls) {
                    break;
                }
            }

            //Gets URL at bottom of list.
            String url = toCrawList.iterator().next();

            //Removes URL from the ToCrawl list.
            toCrawList.remove(url);

            //Converts string url to a URL object
            URL verifiedUrl = verifyUrl(url);

            //Skips URL if robots are not allowed to access it.
            if (!isRobotAllowed(verifiedUrl)) {
                continue;
            }

            //Updates crawling stats.
            updateStats(url, crawledList.size(), toCrawList.size(), maxUrls);

            //Adds page to the crawled list.
            crawledList.add(url);

            //Downloads the page at the given URL.
            String pageContents = downloadPage(verifiedUrl);

            //On successful page download, retrieve links and search for search string
            if (pageContents != null && pageContents.length() > 0) {
                //Retrieves list valid links from page.
                ArrayList<String> links = retrieveLinks(verifiedUrl, pageContents, crawledList, limitHost);

                //Adds links to the ToCrawl list.
                toCrawList.addAll(links);

                //Checks for search string and records a match if found
                if (searchStringMatches(pageContents, searchString, caseSensitive)) {
                    addMatch(url);
                }
            }

            //Updates crawling stats.
            updateStats(url, crawledList.size(), toCrawList.size(), maxUrls);
        }
    }
}