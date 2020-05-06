package com.group14.termproject.server.service;

import com.group14.termproject.server.AuthTestUtil;
import com.group14.termproject.server.model.LeaderboardEntry;
import com.group14.termproject.server.model.User;
import com.group14.termproject.server.repository.LeaderboardRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class LeaderboardServiceTests {

    final double highestScore = 25000;
    final int entryCount = 5;
    final List<LeaderboardEntry> leaderboardEntries = new ArrayList<>();
    @Autowired
    private LeaderboardService leaderboardService;
    @Autowired
    private LeaderboardRepository leaderboardRepository;
    @Autowired
    private AuthTestUtil authTestUtil;

    @Before
    public void setup() {
        for (int i = 0; i < entryCount; i++) {
            // Do not exceed the possible highest score.
            double randomScore = highestScore - new Random().nextDouble() * 10000;
            User user = authTestUtil.generateRegisteredUser();
            LeaderboardEntry dbEntry = leaderboardService.submitScore(new LeaderboardEntry(user, randomScore));
            leaderboardEntries.add(dbEntry);
        }

    }

    @After
    public void clear() {
        leaderboardEntries.clear();
    }


    @Test
    public void testSubmitScore() {
        LeaderboardEntry leaderboardEntry = leaderboardEntries.get(0); // Get a random entry

        leaderboardEntry = leaderboardService.submitScore(leaderboardEntry);
        assertEquals(leaderboardEntry.getScore(),
                leaderboardRepository.getOne(leaderboardEntry.getId()).getScore(),
                0.0);
    }

    @Test
    public void testGetLeaderboardWithoutDayLimit() {
        List<LeaderboardEntry> entries = leaderboardService.getLeaderboard(entryCount);
        assertEquals(entryCount, entries.size());
        // Check if the list is sorted in correct order
        List<LeaderboardEntry> sortedEntries = new ArrayList<>(leaderboardEntries);
        sortedEntries.sort(Comparator.comparing(LeaderboardEntry::getScore).reversed());
        for (int i = 0; i < entryCount; i++) {
            assertEquals(sortedEntries.get(i).getId(), entries.get(i).getId());
        }
    }

    @Test
    public void testIllegalParameters() {
        List<Integer> invalidLimitParameters = Arrays.asList(0, -1, 10001);
        for (Integer limit : invalidLimitParameters) {
            // When only limit is invalid
            assertThrows(IllegalArgumentException.class, () -> leaderboardService.getLeaderboard(limit));
            assertThrows(IllegalArgumentException.class, () -> leaderboardService.getLeaderboard(limit, 7));
            // When both parameters are invalid
            assertThrows(IllegalArgumentException.class, () -> leaderboardService.getLeaderboard(limit, -1));
        }

        // When only latestDaysLimit is invalid
        assertThrows(IllegalArgumentException.class, () -> leaderboardService.getLeaderboard(10, -1));
    }

    @Test
    public void testIfLatestDayLimitApplied() {
        LeaderboardEntry entryWithHighestScore = new LeaderboardEntry(authTestUtil.generateRegisteredUser(), highestScore + 10);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -20);
        entryWithHighestScore.setCreatedOn(calendar.getTime());
        long entryWithHighScoreId = leaderboardService.submitScore(entryWithHighestScore).getId();
        // Since the new entry has the highest score, getting only one element should be enough to return it.
        assertEquals(entryWithHighScoreId, leaderboardService.getLeaderboard(1).get(0).getId());
        // Take only last 7 days. Now the entry should not present in the list.
        assertNotEquals(entryWithHighScoreId, leaderboardService.getLeaderboard(1, 7).get(0).getId());
    }
}
