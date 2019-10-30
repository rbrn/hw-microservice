package com.db.hackathon.service.slope_one;

import com.db.hackathon.domain.Cause;
import com.db.hackathon.domain.WeeklyChallengeStatus;

import java.util.*;


public class InputData {


    public static Map<User, HashMap<Item, Double>> initializeData(Map<String, List<WeeklyChallengeStatus>> stringCauseMap) {
        Map<User, HashMap<Item, Double>> data = new HashMap<>();
        HashMap<Item, Double> newUser;
        Set<Item> newRecommendationSet;

        for (String key : stringCauseMap.keySet()) {
            newUser = new HashMap<Item, Double>();
            newRecommendationSet = new HashSet<>();
            for (WeeklyChallengeStatus cause : stringCauseMap.get(key)) {
                newRecommendationSet.add(new Item(cause.getChallenge().getTitle()));
            }
            for (Item item : newRecommendationSet) {
                newUser.put(item, Math.random());
            }
            data.put(new User(key), newUser);
        }

        return data;
    }

}
