package com.group14.termproject.server.service;

import com.group14.termproject.server.model.LeaderboardEntry;
import com.group14.termproject.server.repository.LeaderboardRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
class LeaderboardServiceImpl implements LeaderboardService {

    private final LeaderboardRepository leaderboardRepository;

    @Value("${custom.leaderboard.entry.size.limit}")
    private int ENTRY_COUNT_LIMIT;


    public LeaderboardServiceImpl(LeaderboardRepository leaderboardRepository) {
        this.leaderboardRepository = leaderboardRepository;
    }


    @Override
    public LeaderboardEntry submitScore(LeaderboardEntry leaderboardEntry) {
        return leaderboardRepository.save(leaderboardEntry);
    }

    @Override
    public List<LeaderboardEntry> getLeaderboard(int limit) throws IllegalArgumentException {
        if (isLimitParameterInvalid(limit)) {
            throw new IllegalArgumentException();
        }
        return leaderboardRepository.findEntriesWithHighestScore(limit);

    }

    @Override
    public List<LeaderboardEntry> getLeaderboard(int limit, int lastDaysLimit) throws IllegalArgumentException {
        if (isLimitParameterInvalid(limit) || isLastDaysLimitParameterInvalid(lastDaysLimit)) {
            throw new IllegalArgumentException();
        }
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -lastDaysLimit);
        return leaderboardRepository.findEntriesWithHighestScore(limit, cal.getTime());

    }

    private boolean isLimitParameterInvalid(int limit) {
        return limit > ENTRY_COUNT_LIMIT || limit <= 0;
    }

    private boolean isLastDaysLimitParameterInvalid(int lastDaysLimit) {
        return lastDaysLimit < 0;
    }
}
