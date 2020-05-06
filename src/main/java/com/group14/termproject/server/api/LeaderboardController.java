package com.group14.termproject.server.api;

import com.group14.termproject.server.model.LeaderboardEntry;
import com.group14.termproject.server.model.LeaderboardEntryDTO;
import com.group14.termproject.server.service.LeaderboardService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("leaderboard")
public class LeaderboardController {
    private final LeaderboardService leaderboardService;

    public LeaderboardController(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Returns leaderboard using the object limit and the day limit",
                  notes = "Must provide a limit for objects to be fetched. Latest days limit input can be provided " +
                          "if the amount of days need to be limited",
                    response = List.class)
    public List<LeaderboardEntryDTO> getLeaderboard(@RequestParam Integer limit,
                                                    @RequestParam(required = false, name = "latest-days-limit") Integer latestDaysLimit) {
        List<LeaderboardEntry> entries =  latestDaysLimit != null ?
                leaderboardService.getLeaderboard(limit, latestDaysLimit) :
                leaderboardService.getLeaderboard(limit);
        return entries.stream().map(LeaderboardEntryDTO::new).collect(Collectors.toList());
    }
}
