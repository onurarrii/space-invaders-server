package com.group14.termproject.server.repository;

import com.group14.termproject.server.model.LeaderboardEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LeaderboardRepository extends JpaRepository<LeaderboardEntry, Long> {
    @Query(value = "SELECT * FROM leaderboard ORDER BY score DESC LIMIT :limit", nativeQuery = true)
    List<LeaderboardEntry> findEntriesWithHighestScore(@Param("limit") int limit);

    @Query(value = "SELECT * FROM leaderboard WHERE created_on >= :since ORDER BY score DESC LIMIT :limit",
            nativeQuery = true)
    List<LeaderboardEntry> findEntriesWithHighestScore(@Param("limit") int limit,
                                                       @Param("since") Date since);
}
