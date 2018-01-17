package ru.testAssignment.voting.util.ToUtil;

import ru.testAssignment.voting.model.Vote;
import ru.testAssignment.voting.to.VoteTo;

public class VoteUtil {
    public static Vote createNewFromTo(VoteTo newVote) {
        return new Vote(newVote.getDateTime(), newVote.getRestaurantId());
    }

    public static VoteTo asTo(Vote vote) {
        return new VoteTo(vote.getId(), vote.getDateTime(), vote.getRestaurantId());
    }

    public static Vote updateFromTo(Vote vote, VoteTo voteTo) {
        vote.setDateTime(voteTo.getDateTime());
        vote.setRestaurantId(voteTo.getRestaurantId());
        return vote;
    }
}
