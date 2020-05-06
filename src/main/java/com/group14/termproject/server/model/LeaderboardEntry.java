package com.group14.termproject.server.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "leaderboard")
public class LeaderboardEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private long id;

    @ManyToOne
    @JoinColumn(name = "user")
    @Getter
    @Setter
    private User user;

    @Getter
    @Setter
    private double score;

    @Temporal(TemporalType.DATE)
    @Column(columnDefinition = "TIMESTAMP DEFAULT NOW()")
    @Getter
    @Setter
    private Date createdOn;

    public LeaderboardEntry() {
    }

    public LeaderboardEntry(User user, double score) {
        this.user = user;
        this.score = score;
    }

}
