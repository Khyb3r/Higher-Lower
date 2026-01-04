import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Card {
    // Suit of the card
    private final Suit suitType;
    private final Rank rankType;
    private final int cardValue;

    public Card(Suit suitType, Rank rankType) {
        this.suitType = suitType;
        this.rankType = rankType;
        this.cardValue = this.rankType.getCardNumber();
    }

    public Suit getSuitType() {
        return this.suitType;
    }

    public Rank getRankType() {
        return this.rankType;
    }

    public int getCardValue() {
        return this.cardValue;
    }
}
