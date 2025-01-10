package bitbugs.moneysweeper.gui.dto;

public record ScoreboardEntry(String username, String time) {
    @Override
    public String toString() {
        return time + "s - " + username;
    }
}
