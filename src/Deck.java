import java.util.ArrayList;
import java.util.List;

public class Deck {
    private final List<Card> deck;

    public Deck() {
        this.deck = createDeck();
    }
    public static List<Card> createDeck() {
        List<Card> deck = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                deck.add(new Card(suit, rank));
            }
        }
        return deck;
    }

    public static void printDeck(List<Card> deck) {
        for (Card card : deck) {
            System.out.println(card.getRankType()  + " of " + card.getSuitType());
        }
    }

    public List<Card> getDeck() {
        return deck;
    }
}
