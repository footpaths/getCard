/*
 * Copyright (C) 2016 Jared Rummler <jared.rummler@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package vn.dhstudio.topinstall.Utils;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Web scrape app details pages on Google Play's website.
 */
public class GooglePlayScraper {

  /**
   * Web scrape app data from https://play.google.com
   *
   * @param packageName The app's package name
   * @return The app details scraped from the Play Store.
   * @throws WebScrapeException if an error occurred while scraping Google Play.
   */
  public static AppDetails scrapeAppDetails(String packageName) throws WebScrapeException {
    try {
      Document document = Jsoup.connect("https://play.google.com/store/apps/details?id=" + packageName).get();
      NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.US);

      AppDetails details = new AppDetails(packageName);

//      Element element = document.body().getElementsByClass("dQrBL");
      String icon = document.body().getElementsByClass("dQrBL").get(0).child(0).attr("src");

      Elements es =  document.body().getElementsByClass("oQ6oV");
      String title = es.select("h1.AHFaub").text();

    details.icon =  icon;
    details.title =title;


      return details;
    } catch (Exception e) {
      if (e instanceof HttpStatusException) {
        throw new WebScrapeException("HTTP response was not OK. Are you sure " + packageName + " is on Google Play?");
      }
      throw new WebScrapeException("Error parsing response for " + packageName, e);
    }
  }



  private static boolean hasNoSelection(Elements select) {
    return (select == null || select.size() == 0);
  }

  private static boolean hasSelection(Elements select) {
    return !hasNoSelection(select);
  }



  public static class AppDetails {

    public final String packageName;
    public String title;
    public String icon;


    public AppDetails(String packageName) {
      this.packageName = packageName;
    }

  }

  public static class WebScrapeException extends Exception {

    public WebScrapeException(String message) {
      super(message);
    }

    public WebScrapeException(String message, Throwable cause) {
      super(message, cause);
    }

  }

}
