/*
    Date: 10/26/11
    Time: 06:19
    Author: Alexander Marchuk
            aamarchuk@gmail.com
*/
package ru.goodsReview.miner;

import org.apache.log4j.Logger;
import org.webharvest.definition.ScraperConfiguration;
import org.webharvest.runtime.Scraper;
import org.xml.sax.SAXException;
import ru.common.FileUtil;
import ru.common.Serializer;
import ru.goodsReview.core.exception.DeleteException;
import ru.goodsReview.miner.listener.CitilinkNotebooksScraperRuntimeListener;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.util.*;

public class GrabberCitilink extends WebHarvestGrabber {
    private static final Logger log = Logger.getLogger(GrabberCitilink.class);
    private static final String site = "http://www.citilink.ru";

    @Override
    protected void cleanFolders() throws DeleteException {
        FileUtil.cleanFolder(new File(getPath() + "Pages/"));
        FileUtil.cleanFolder(new File(getPath() + "Descriptions/"));
    }

    @Override
    protected void createFolders() throws IOException {
        new File(getPath() + "Pages").mkdirs();
        new File(getPath() + "Descriptions").mkdirs();
        new File(getPath() + "list").mkdirs();
    }

    @Override
    protected void updateList() {
        try {
            log.info("Update list started");
            ScraperConfiguration config = new ScraperConfiguration(getDownloadConfig());
            Scraper scraper = new Scraper(config, ".");
            scraper.addVariableToContext("path", getPath());
            scraper.setDebug(true);
            scraper.execute();
            log.info("Update list successful");
        } catch (Exception e) {
            log.error("Cannot process update list", e);
        }
    }

    @Override
    //TODO: use RandomAcessFile and update lines with old product, but new reviews
    protected void downloadPages() {
        try {
            log.info("Adding download pages started");

            File newLinksFile = new File(getPath() + "list/NewLinks.xml");
            Map<String, Integer> newLinksMap = Serializer.instance().readMap(newLinksFile);

            File allLinksFile = new File(getPath() + "list/AllLinks.xml");
            Map<String, Integer> allLinksMap = new HashMap<String, Integer>();
            if (allLinksFile.exists()) {
                allLinksMap = Serializer.instance().readMap(allLinksFile);
            }

            List<String> linksToDownload = new ArrayList<String>();
            Iterator<String> iterator = newLinksMap.keySet().iterator();
            while (iterator.hasNext()) {
                String productUrl = iterator.next();
                Integer reviewNumber = newLinksMap.get(productUrl);
                String url = site + productUrl + "?opinion";
                linksToDownload.add(url);
            }
            allLinksMap.putAll(newLinksMap);
            Serializer.instance().write(allLinksMap, allLinksFile);
            Downloader.getInstance().addLinks(linksToDownload, getPath() + "Pages", "windows-1251");
            log.info("Adding download pages successful");
        } catch (Exception e) {
            log.error("Cannot process download pages", e);
        }
    }

    @Override
    //TODO:: not add, if review number changes, only update
    protected void findPages() throws IOException, ParserConfigurationException, SAXException, TransformerException {
        log.info("Find pages started");

        //what links we visited before and count of reviews
        File allLinksFile = new File(getPath() + "list/AllLinks.xml");
        Map<String, Integer> allLinksMap = new HashMap<String, Integer>();
        if (allLinksFile.exists()) {
            allLinksMap = Serializer.instance().readMap(allLinksFile);
        }

        File latterLinksFile = new File(getPath() + "list/LatterLinks.xml");
        Map<String, Integer> latterLinksMap = Serializer.instance().readMap(latterLinksFile);
        Iterator<String> iterator = latterLinksMap.keySet().iterator();
        Map<String, Integer> newLinksMap = new HashMap<String, Integer>();
        while (iterator.hasNext()) {
            String reviewUrl = iterator.next();
            Integer reviewNumber = latterLinksMap.get(reviewUrl);
            // if new url or added review
            if (!allLinksMap.containsKey(reviewUrl) || allLinksMap.get(reviewUrl) != reviewNumber) {
                newLinksMap.put(reviewUrl, reviewNumber);
            }
        }
        Serializer.instance().write(newLinksMap, new File(getPath() + "list/NewLinks.xml"));
        log.info("Find pages successful");
    }

    @Override
    protected void grabPages() throws IOException {
        log.info("Grabbing started");
        ScraperConfiguration config = new ScraperConfiguration(getGrabberConfig());
        Scraper scraper = new Scraper(config, ".");
        scraper.addRuntimeListener(new CitilinkNotebooksScraperRuntimeListener(jdbcTemplate));
        scraper.addVariableToContext("path", getPath() + "Pages/");
        scraper.addVariableToContext("numberOfFirstReview", 0);
        scraper.setDebug(true);
        scraper.execute();
        log.info("Grabbing ended successful");
    }
}
