package com.db.hackathon.service.slope_one;

import com.db.hackathon.domain.Challenge;
import com.db.hackathon.domain.WeeklyChallengeStatus;
import com.db.hackathon.repository.WeeklyChallengeStatusRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.when;


public class SlopeOneTest {

    @Mock
    private WeeklyChallengeStatusRepository weeklyChallengeStatusRepository;

    @InjectMocks
    private SlopeOne slopeOne = new SlopeOne();


    public void setup() {

    }

    private WeeklyChallengeStatus getFixture(String userId, List<String> title) {
        WeeklyChallengeStatus status = new WeeklyChallengeStatus();
        status.setUserId(userId);
        status.setChallenge(new Challenge());
        status.getChallenge().setTitle("Title");
        return status;
    }

    @Test
    public void testSimple() {

        when(weeklyChallengeStatusRepository.findAll()).thenReturn(
            asList(getFixture("user 1", asList("Title 1", "Title 2", "Title 3"))));

        slopeOne.slopeOne();

    }

}
