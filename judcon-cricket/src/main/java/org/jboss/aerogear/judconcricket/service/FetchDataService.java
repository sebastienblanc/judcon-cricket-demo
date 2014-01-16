/**
 * 
 */
package org.jboss.aerogear.judconcricket.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Logger;

/**
 * @author sakthipriyan
 * 
 */
public class FetchDataService {
	
	private static final Logger logger = Logger.getLogger(FetchDataService.class
			.getName());

	public static String BASE_URL = "http://www.espncricinfo.com/ci/engine/match/";
	public static String LIVE_SCORE_URL = "http://static.espncricinfo.com/rss/livescores.xml";

	public String getScore(String id) {

		String title = null;

		try {
			URL url = new URL(BASE_URL + id + ".html");
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					url.openStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				if (line.contains("<title>")) {
					title = line.substring(line.indexOf("<title>")+7,
							line.indexOf("</title>"));
					title = title.substring(0, title.indexOf("|")).trim();
					break;
				}
			}
			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		logger.fine("Title for match id " + id + " is " + title);
		return title;
	}

	public String getMatches() {

		StringBuilder builder = new StringBuilder();
		try {
			URL url = new URL(LIVE_SCORE_URL);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					url.openStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				builder.append(inputLine);
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String matches = builder.toString();
		logger.fine("Live score RSS: " + matches);
		return matches;
	}

}
