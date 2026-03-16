package model;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;
import java.util.Optional;

public final class Scoreboard {
    private String homeName = "";
    private String awayName = "";
    private int homeScore = 0;
    private int awayScore = 0;
    private final Deque<ScoreChange> history = new ArrayDeque<>();
    private ScoreChange lastAction = null;

    public synchronized void setTeamNames(String home, String away) {
        if (home == null || away == null) throw new IllegalArgumentException("Names cannot be null");
        String trimmedHome = home.trim();
        String trimmedAway = away.trim();
        if (trimmedHome.isEmpty() || trimmedAway.isEmpty()) {
            throw new IllegalArgumentException("Team names cannot be blank");
        }
        this.homeName = trimmedHome;
        this.awayName = trimmedAway;
    }

    public synchronized void addPointsToHome(int pts, String label) {
        validateNamesSet();
        validatePoints(pts);
        homeScore += pts;
        ScoreChange sc = new ScoreChange(ScoreChange.Team.HOME, pts, label);
        history.push(sc);
        lastAction = sc;
    }

    public synchronized void addPointsToAway(int pts, String label) {
        validateNamesSet();
        validatePoints(pts);
        awayScore += pts;
        ScoreChange sc = new ScoreChange(ScoreChange.Team.AWAY, pts, label);
        history.push(sc);
        lastAction = sc;
    }

    public synchronized void undoLast() {
        if (history.isEmpty()) {
            throw new IllegalStateException("No actions to undo");
        }
        ScoreChange sc = history.pop();
        if (sc.getTeam() == ScoreChange.Team.HOME) {
            homeScore -= sc.getPoints();
            if (homeScore < 0) homeScore = 0;
        } else {
            awayScore -= sc.getPoints();
            if (awayScore < 0) awayScore = 0;
        }
        lastAction = history.peek();
    }

    public synchronized void clearGame() {
        homeScore = 0;
        awayScore = 0;
        history.clear();
        lastAction = null;
    }

    private void validateNamesSet() {
        if (homeName == null || awayName == null || homeName.isEmpty() || awayName.isEmpty()) {
            throw new IllegalStateException("Team names must be set before scoring");
        }
    }

    private void validatePoints(int pts) {
        if (pts <= 0) throw new IllegalArgumentException("Points must be positive");
    }

    public synchronized String getHomeName() { return homeName; }
    public synchronized String getAwayName() { return awayName; }
    public synchronized int getHomeScore() { return homeScore; }
    public synchronized int getAwayScore() { return awayScore; }
    public synchronized Optional<String> getLastActionDescription() {
        return lastAction == null ? Optional.empty() : Optional.of(lastAction.toString());
    }

    // convenience methods using common labels
    public void addTouchdownHome() { addPointsToHome(6, "TD"); }
    public void addFieldGoalHome() { addPointsToHome(3, "FG"); }
    public void addTwoHome() { addPointsToHome(2, "2PT"); }
    public void addOneHome() { addPointsToHome(1, "XP"); }

    public void addTouchdownAway() { addPointsToAway(6, "TD"); }
    public void addFieldGoalAway() { addPointsToAway(3, "FG"); }
    public void addTwoAway() { addPointsToAway(2, "2PT"); }
    public void addOneAway() { addPointsToAway(1, "XP"); }
}
