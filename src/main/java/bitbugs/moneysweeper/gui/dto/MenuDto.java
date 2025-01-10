package bitbugs.moneysweeper.gui.dto;

import bitbugs.moneysweeper.gui.Difficulty;

public record MenuDto(int bombs, int fieldWidth, int fieldHeight, String highscore, Difficulty difficulty) {
}
