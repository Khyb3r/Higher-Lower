import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Deck {
    private final List<Card> deck;

    public Deck() {
        this.deck = createDeck();
    }
    public static List<Card> createDeck() {
        List<Card> deck = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            if (suit.equals(Suit.JOKER)) continue;
            for (Rank rank : Rank.values()) {
                if (rank.equals(Rank.JOKER)) continue;
                deck.add(new Card(suit, rank));
            }
        }
        // add jokers
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

    public static Card chooseRandomCard(List<Card> deck) {
        Random randomGenerator = new Random();
        int randomNumber = randomGenerator.nextInt(deck.size());
        return deck.get(randomNumber);
    }
}
