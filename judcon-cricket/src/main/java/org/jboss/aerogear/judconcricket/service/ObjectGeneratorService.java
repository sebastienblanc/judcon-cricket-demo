package org.jboss.aerogear.judconcricket.service;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jboss.aerogear.judconcricket.rest.dto.MatchDTO;
import org.jboss.aerogear.judconcricket.utils.StringUtils;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class ObjectGeneratorService {

	private static final Logger logger = Logger.getLogger(ObjectGeneratorService.class
			.getName());
	
	

	public List<MatchDTO> getMatches(String xmlRecords) {

		List<MatchDTO> matches = new ArrayList<MatchDTO>();
		try {

			DocumentBuilder db = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xmlRecords));

			Document doc = db.parse(is);
			NodeList nodes = doc.getElementsByTagName("item");

			for (int i = 0; i < nodes.getLength(); i++) {

				Element element = (Element) nodes.item(i);
				NodeList name = element.getElementsByTagName("guid");
				Element matchId = (Element) name.item(0);
				NodeList title = element.getElementsByTagName("title");
				Element line = (Element) title.item(0);
				String detail = getCharacterDataFromElement(line);
				String[] teams = detail.split(" v ");
				MatchDTO match = new MatchDTO();
				match.setTeamOne(StringUtils.getNonNumeric(teams[0]).trim());
				match.setTeamTwo(StringUtils.getNonNumeric(teams[1]).trim());
				match.setTitle(match.getTeamOne() + " Vs " + match.getTeamTwo());
				String stringId = StringUtils
						.getOnlyNumbers(getCharacterDataFromElement(matchId));
				match.setId(stringId);
				matches.add(match);
			}

		} catch (ParserConfigurationException e) {
			logger.severe(e.getMessage());
		} catch (SAXException e) {
			logger.severe(e.getMessage());
		} catch (IOException e) {
			logger.severe(e.getMessage());
		}
		logger.fine("Number of matches retrieved " + matches.size());
		return matches;
	}

	

	private String getSimpleString(String livescore, int id) {
		String detail = null;
		try {

			DocumentBuilder db = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(livescore));

			Document doc = db.parse(is);
			NodeList nodes = doc.getElementsByTagName("item");

			for (int i = 0; i < nodes.getLength(); i++) {

				Element element = (Element) nodes.item(i);
				NodeList name = element.getElementsByTagName("guid");
				Element line = (Element) name.item(0);
				int matchId = Integer.parseInt(StringUtils
						.getOnlyNumbers(getCharacterDataFromElement(line)));
				if (matchId != id) {
					continue;
				}
				NodeList title = element.getElementsByTagName("title");
				line = (Element) title.item(0);
				detail = getCharacterDataFromElement(line);
			}

		} catch (ParserConfigurationException e) {
			logger.severe(e.getMessage());
		} catch (SAXException e) {
			logger.severe(e.getMessage());
		} catch (IOException e) {
			logger.severe(e.getMessage());
		}
		logger.fine("Simple return from the RSS " + detail);
		return detail;
	}

	private String getCharacterDataFromElement(Element element) {
		Node child = element.getFirstChild();
		if (child instanceof CharacterData) {
			CharacterData cd = (CharacterData) child;
			return cd.getData().trim().replaceAll("\\s+", " ");
		}
		return "";
	}
}
