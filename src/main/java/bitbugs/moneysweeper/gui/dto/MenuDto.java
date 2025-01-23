package bitbugs.moneysweeper.gui.dto;

import bitbugs.moneysweeper.backend.Playground;

public record MenuDto(Playground playground, String highscore) {
}
