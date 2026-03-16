package model;

public final class ScoreChange {
    public enum Team { HOME, AWAY }
    private final Team team;
    private final int points;
    private final String label; // e.g., "TD", "FG"

    public ScoreChange(Team team, int points, String label) {
        this.team = team;
        this.points = points;
        this.label = label;
    }

    public Team getTeam() { return team; }
    public int getPoints() { return points; }
    public String getLabel() { return label; }

    @Override
    public String toString() {
        return (team == Team.HOME ? "Home" : "Away") + " +" + points + " (" + label + ")";
    }
}
