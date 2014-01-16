package org.jboss.aerogear.judconcricket.service;

import java.util.List;
import java.util.logging.Logger;

import org.jboss.aerogear.judconcricket.rest.dto.MatchDTO;

public class CricScoreService {
	
	private static final Logger logger = Logger.getLogger(CricScoreService.class
			.getName());

	private FetchDataService fetchDataService;
	private ObjectGeneratorService objectGeneratorService;

	public CricScoreService() {
		this.fetchDataService = new FetchDataService();
		this.objectGeneratorService = new ObjectGeneratorService();
	}

	public List<MatchDTO> getMatches() {
		String livescore = getMatchesFromLocalOrRemote();
		List<MatchDTO> matches = objectGeneratorService.getMatches(livescore);
		for(MatchDTO matchDTO : matches){
			matchDTO.setScore(this.getScore(matchDTO.getId()));
		}
		logger.info("Total matches returned: " + matches.size());
		return matches;
	}

	public String getScore(long id) {

		String score = fetchDataService.getScore(id);
		return score;
	}
//	
//	private boolean isScoreUpdated(SimpleScore oldScore, SimpleScore newScore){
//		boolean updated = false;
//		if(newScore != null	&& newScore.getSimple() != null && newScore.getDetail() != null
//				&& (!newScore.getSimple().equals(oldScore.getSimple())
//					|| !newScore.getDetail().equals(oldScore.getDetail()))){
//				logger.info("SimpleScore score is udpated");
//				updated = true;
//		}
//		return updated;		
//	}

//	private SimpleScore fetchSimpleScore(int id) {
//		String detail = fetchDataService.getScore(id);
//		String livescore = getMatchesFromLocalOrRemote();
//		SimpleScore simpleScore = objectGeneratorService.getScore(detail, livescore, id);
//		return simpleScore;
//	}

	private String getMatchesFromLocalOrRemote() {
		String livescore = fetchDataService.getMatches();
		return livescore;
	}

}
