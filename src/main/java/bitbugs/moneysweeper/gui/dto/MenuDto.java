package bitbugs.moneysweeper.gui.dto;

import bitbugs.moneysweeper.backend.Playground;
import bitbugs.moneysweeper.gui.Difficulty;

public record MenuDto(Playground playground, String highscore) {
}
