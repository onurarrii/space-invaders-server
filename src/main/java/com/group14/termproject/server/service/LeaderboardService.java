package com.group14.termproject.server.service;

import com.group14.termproject.server.model.LeaderboardEntry;

import java.util.List;

public interface LeaderboardService {
    /**
     * Saves leaderboard entries to persistence storage.
     *
     * @param leaderboardEntry leaderboard entry to be saved.
     * @return the entry that is saved and whose id is set.
     */
    LeaderboardEntry submitScore(LeaderboardEntry leaderboardEntry);

    /**
     * <p>
     * Length of the returned list is determined by {@code limit} parameter. <br>
     * Note that, its size might be less than {@code limit} as there might not exist that many elements.
     * </p>
     *
     * @param limit how many entries to be requested. It should be positive and less than or equal to 10,000.
     * @return a <code>LeaderboardEntry</code> list which is sorted (descending) by their scores.
     * @throws IllegalArgumentException thrown when {@code limit} parameter is either too big or negative.
     */
    List<LeaderboardEntry> getLeaderboard(int limit) throws IllegalArgumentException;

    /**
     * <p>
     * Works exactly like {@link LeaderboardService#getLeaderboard(int)}. <br>
     * In addition, returned list only consists of elements which are submitted within last {@code latestDaysLimit} days.
     * </p>
     *
     * <p>
     * For example, if one wants to get leaderboard for the last week, the method should be called with parameter
     * {@code latestDaysLimit = 7}.
     * </p>
     *
     * @param limit how many entries to be requested. It should be positive and less than or equal to 10,000.
     * @param latestDaysLimit how many previous days to be included in the query.
     * @return a <code>LeaderboardEntry</code> list which is sorted (descending) by their scores.
     * @throws IllegalArgumentException thrown when {@code latestDaysLimit} is non positive.
     */
    List<LeaderboardEntry> getLeaderboard(int limit, int latestDaysLimit) throws IllegalArgumentException;
}
