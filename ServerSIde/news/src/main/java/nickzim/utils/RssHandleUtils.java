package nickzim.utils;

import nickzim.model.News;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static nickzim.utils.StringHandleUtils.handleString;

public class RssHandleUtils {

    public static List<News> getNewsListFromRSS(URL feed, String name) throws IOException {

        List<News> newsList = new ArrayList<>();

        String charset;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(feed.openStream()))) {
            charset = br.readLine().contains("windows-1251") ? "Windows-1251" : StandardCharsets.UTF_8.toString();
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(feed.openStream(), charset))) {

            Pattern titlePattern = Pattern.compile("<title>.+</title>");
            Pattern linkPattern = Pattern.compile("<link>.+</link>");
            Pattern datePattern = Pattern.compile("<pubDate>.+</pubDate>");
            Pattern descriptionPattern = Pattern.compile("<description>.+</description>");
            Pattern categoryPattern = Pattern.compile("<category>.+</category>");

            StringBuilder lines = new StringBuilder();
            br.lines().forEach(lines::append);

            for (String it : lines.toString().split("<item>|</item>\\n<item>|</item>")) {

                if (!it.trim().isEmpty()) {
                    News news = new News();

                    Matcher matcher = titlePattern.matcher(it);
                    if (matcher.find()) {
                        news.setTitle(handleString(matcher.group()));
                    }

                    matcher = linkPattern.matcher(it);
                    if (matcher.find()) {
                        news.setLink(handleString(matcher.group()));
                    }

                    matcher = datePattern.matcher(it);
                    if (matcher.find()) {
                        news.setPubDate(DateUtils.getNewsPubDate(matcher.group()));
                    }

                    matcher = descriptionPattern.matcher(it);
                    if (matcher.find()) {
                        news.setDescription(handleString(matcher.group()));
                    }

                    matcher = categoryPattern.matcher(it);
                    if (matcher.find()) {
                        news.setCategory(handleString(matcher.group()));
                    }

                    news.setAgency(name);
                    newsList.add(news);
                }
            }
        }

        if (!newsList.isEmpty()){
            newsList.remove(0);
            newsList.remove(newsList.size() - 1);
        }

        return newsList;
    }

    public static Map<String, Integer> getCategoryMap(List<News> newsList){

        Map<String,Integer> categoriesMap = new HashMap<>();

        for (News it: newsList){
            if (it.getCategory() != null) {
                categoriesMap.put(it.getCategory(), categoriesMap.getOrDefault(it.getCategory(), 0) + 1);
            }
        }

        return categoriesMap;
    }
}
