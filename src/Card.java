import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Card {
    // Suit of the card
    private final Suit suitType;
    private final Rank rankType;
    private final int cardValue;
    private final Suit.JokerType jokerType;


    public Card(Suit suitType, Rank rankType, Suit.JokerType jokerType) {
        this.suitType = suitType;
        this.rankType = rankType;
        this.cardValue = rankType.getCardNumber();
        this.jokerType = jokerType;
    }

    public Card(Suit suitType, Rank rankType) {
        this(suitType, rankType, null);
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

    public Suit.JokerType getJokerType() {
        return this.jokerType;
    }
}
