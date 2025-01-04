package bitbugs.moneysweeper.gui.dto;

public record ScoreboardEntry(String username, int score) {
    @Override
    public String toString() {
        return score + "s - " + username;
    }
}
