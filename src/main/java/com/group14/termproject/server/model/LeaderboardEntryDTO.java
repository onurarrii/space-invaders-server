package com.group14.termproject.server.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class LeaderboardEntryDTO {

    @Getter
    @Setter
    private String user;

    @Getter
    @Setter
    private double score;

    @Getter
    @Setter
    private Date createdOn;

    public LeaderboardEntryDTO(LeaderboardEntry leaderboardEntry) {
        this.user = leaderboardEntry.getUser().getUsername();
        this.score = leaderboardEntry.getScore();
        this.createdOn = leaderboardEntry.getCreatedOn();
    }
}
