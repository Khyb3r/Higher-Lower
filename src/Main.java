import java.util.*;
import java.util.spi.AbstractResourceBundleProvider;

public class Main {
    public static void main(String[] args) {
        // Deck instantiated
        List<Card> deck = Deck.createDeck();
        //Deck.printDeck(deck);
        Scanner scanner = new Scanner(System.in);

        for (;;) {
            System.out.print("Type Start and Press Enter to continue: ");
            if (!scanner.hasNext("Start")) continue;
            if (scanner.hasNextLine()) scanner.nextLine();
            break;
        }

        int scoreCounter = 0;
        Card prevCard = null;
        for (;;) {
            // give random card in deck
            Card card;
            if (prevCard == null) {
                 card = Deck.chooseRandomCard(deck);
            }
            else {
                card = prevCard;
            }
            Card nextCard = Deck.chooseRandomCard(deck);
            if (card.getSuitType() == Suit.JOKER && card.getJokerType() == Suit.JokerType.BLACK) {
                System.out.print("Card is: " + card.getJokerType() + " " + card.getSuitType());
            } else if (card.getSuitType() == Suit.JOKER && card.getJokerType() == Suit.JokerType.RED) {
                System.out.println("Card is: " + card.getJokerType() + " " + card.getSuitType());
            } else {
                System.out.println("Card is: " + card.getRankType() + " of " + card.getSuitType());
            }

            while (true) {
                System.out.println("Higher or Lower: ");
                //scanner.skip("\n");
                String isHigherOrLower = scanner.nextLine();
                if (isHigherOrLower.equalsIgnoreCase("Higher")) {
                    if (cardNumberCheck(card, nextCard)) {
                        scoreCounter++;
                        System.out.println("CORRECT");
                    } else {
                        System.out.println("WRONG");
                    }
                    break;
                }
                else if (isHigherOrLower.equalsIgnoreCase("Lower")) {
                    if (!cardNumberCheck(card, nextCard)) {
                        scoreCounter++;
                        System.out.println("CORRECT");
                    } else {
                        System.out.println("WRONG");
                    }
                    break;
                }
                System.out.println("Only Higher or Lower are accepted, Try Again.");
            }
            // shuffle deck
            Collections.shuffle(deck);
            // remove these cards from the deck
            prevCard = nextCard;
            deck.remove(card);
            deck.remove(nextCard);
        }
    }

    public static boolean cardNumberCheck(Card card, Card nextCard) {
        return nextCard.getCardValue() > card.getCardValue();
    }
}
