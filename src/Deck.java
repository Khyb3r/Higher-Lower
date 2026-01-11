import java.util.ArrayList;
import java.util.List;

public class Deck {
    private final List<Card> deck;

    public Deck() {
        this.deck = createDeck();
    }
    public static List<Card> createDeck() {
        List<Card> deck = new ArrayList<>();

        // Add all Cards to deck excluding jokers
        for (Suit suit : Suit.values()) {
            if (suit.equals(Suit.JOKER)) continue;
            for (Rank rank : Rank.values()) {
                if (rank.equals(Rank.JOKER)) continue;
                deck.add(new Card(suit, rank));
            }
        }

        // Manually add jokers to deck as they have their own Card constructor
        deck.add(new Card(Suit.JOKER, Rank.JOKER, Suit.JokerType.BLACK));
        deck.add(new Card(Suit.JOKER, Rank.JOKER, Suit.JokerType.RED));
        return deck;
    }

    public static void printDeck(List<Card> deck) {
        for (Card card : deck) {
            if (card.getRankType().equals(Rank.JOKER)) {System.out.println(card.getJokerType() + " " + card.getRankType()); continue;}
            System.out.println(card.getRankType()  + " of " + card.getSuitType());
        }
    }

    public List<Card> getDeck() {
        return deck;
    }

    // Pick card from top/end of deck and remove
    public static Card chooseCard(List<Card> deck) {
        return deck.removeLast();
    }

    // Returns true if nextCard in deck is higher in value than current, otherwise false
    public static boolean cardNumberCheck(Card card, Card nextCard) {
        return nextCard.getCardValue() > card.getCardValue();
    }
}
