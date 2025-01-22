package bitbugs.moneysweeper.gui.dto;

public record ScoreboardEntry(int wins, String username, String time) {
    @Override
    public String toString() {
        return "Completions: " + wins + " - Name: " + username + " - Shortest completion: " + time;
    }
}
