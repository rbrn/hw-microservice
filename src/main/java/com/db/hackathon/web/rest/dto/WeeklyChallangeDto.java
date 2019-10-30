package com.db.hackathon.web.rest.dto;

import com.db.hackathon.domain.Challenge;
import com.db.hackathon.domain.Status;
import com.db.hackathon.domain.WeeklyChallengeStatus;

public class WeeklyChallangeDto {
    public WeeklyChallengeStatus getWeeklyChallengeStatus() {
        return weeklyChallengeStatus;
    }

    public void setWeeklyChallengeStatus(WeeklyChallengeStatus weeklyChallengeStatus) {
        this.weeklyChallengeStatus = weeklyChallengeStatus;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    private WeeklyChallengeStatus weeklyChallengeStatus;
    private Challenge challenge;
    private Status status;

    public static WeeklyChallangeDto of(WeeklyChallengeStatus weeklyChallengeStatus, Challenge challenge, Status status){
        WeeklyChallangeDto result = new WeeklyChallangeDto();
        result.setChallenge(challenge);
        result.setStatus(status);
        result.setWeeklyChallengeStatus(weeklyChallengeStatus);
        return result;
    }
}
