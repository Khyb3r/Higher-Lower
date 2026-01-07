    import java.io.File;
    import java.io.FileNotFoundException;
    import java.io.FileWriter;
    import java.io.IOException;
    import java.util.Collections;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Scanner;

    public class Game {
        private List<Card> deck;
        private Scanner scanner;
        private int score;
        private GameMode mode;
        private boolean gameRunning;
        /*private static File gameStats = new File("gamestats.txt");
        private static FileWriter fileWriter;
        //private Scanner fileScanner = new Scanner(gameStats);
        static {;
            try {
                fileWriter = new FileWriter(gameStats.getPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }*/

        public enum GameMode {
            START_SCREEN, PLAYING, RULES, CONTROLS, STATS;
        }

        public Game() throws FileNotFoundException {
            this.deck = Deck.createDeck();
            this.scanner = new Scanner(System.in);
            this.gameRunning = true;
            this.mode = GameMode.START_SCREEN;
            this.score = 0;
        }

        public void run() {
            Card card = null;
            while (gameRunning) {
                clearScreen();
                if (mode == GameMode.START_SCREEN) {
                    menuScreen();
                } else if (mode == GameMode.PLAYING) {
                    gameScreen(card);
                } else if (mode == GameMode.CONTROLS) {
                    controlsScreen();
                } else if (mode == GameMode.RULES) {
                    rulesScreen();
                } else if (mode == GameMode.STATS) {
                    statsScreen();
                }

                String input = scanner.nextLine().trim().toUpperCase();
                if (mode == GameMode.START_SCREEN) {
                    switch (input) {
                        case "P":
                            mode = GameMode.PLAYING;
                            break;
                        case "R":
                            mode = GameMode.RULES;
                            break;
                        case "C":
                            mode = GameMode.CONTROLS;
                            break;
                        case "S":
                            mode = GameMode.STATS;
                            break;
                        case "Q":
                            gameRunning = false;
                            break;
                        default:
                            System.out.println("Invalid input, try again");
                            break;
                    }
                } else if (mode == GameMode.PLAYING) {
                    switch (input) {
                        case "START":
                            // game start
                            if (card == null) {
                                card = Deck.chooseRandomCard(deck);
                                printCurrentCard(card);
                            }
                            break;

                        case "H":
                            card = playCard(card, true);
                            break;

                        case "L":
                            card = playCard(card, false);
                            break;

                        case "B":
                            mode = GameMode.START_SCREEN;
                            card = null;
                            break;

                        case "Q":
                            gameRunning = false;
                            break;
                        default:
                            System.out.println("Invalid input, try again");
                            break;
                    }
                }
                else {
                    mode = GameMode.START_SCREEN;
                }

            }
        }

        private Card playCard(Card currCard, boolean higherGuessed) {
            Card nextCard = Deck.chooseRandomCard(deck);
            //printCurrentCard(currCard);

            boolean isHigher = Deck.cardNumberCheck(currCard, nextCard);
            boolean correct = (isHigher && higherGuessed) || (!isHigher && !higherGuessed);

            if (correct) {
                score++;
                System.out.println("CORRECT");
            }
            else {
                System.out.println("WRONG");
            }
            // shuffle deck
            Collections.shuffle(deck);
            System.out.print("Press Enter to continue: ");
            scanner.nextLine();
            return nextCard;

        }

        private void printCurrentCard(Card card) {
            if (card.getSuitType() == Suit.JOKER) {
                System.out.print("Card is: " + card.getJokerType() + " " + card.getSuitType());
            }
            else {
                System.out.println("Card is: " + card.getRankType() + " of " + card.getSuitType());
            }
            System.out.println();
        }

        private void clearScreen() {
            // ANSI escape code for clearing screen and cursoring to top left of terminal
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }

        private void menuScreen() {
            System.out.println("HIGHER OR LOWER");
            System.out.println("Press R and Enter for How to Play");
            System.out.println("Press C and Enter for Controls");
            System.out.println("Press S for Game Stats");
            System.out.println("Press P and Enter to Play");
            System.out.println("Press Q and Enter to Quit");
            System.out.flush();
        }
        private void gameScreen(Card card) {
            if (card == null) {
                System.out.println("Type Start and Press Enter to Begin");
                System.out.println("Press B and Enter to go back to Main Menu");
                System.out.println("Press Q to Quit");
            }
            else  {
                printCurrentCard(card);
                System.out.println("Higher or Lower? : ");
            }
            System.out.flush();
        }

        private void controlsScreen() {
            System.out.println("H for Higher");
            System.out.println("L for Lower");
            System.out.flush();
        }
        private void rulesScreen() {
            // drop points per 3 consecutive incorrect guesses
            System.out.println("You must guess whether the next card in the deck will be higher or lower than your current card");
            System.out.println("You will lose points for 3 consecutive incorrect guesses");
            System.out.println("Card's are ranked by their numerical values.");
            System.out.println("Jack's have value 11.");
            System.out.println("Queen's have value 12.");
            System.out.println("King's have value 13");
            System.out.println("Ace's have value 14");
            System.out.println("Jokers have value 15");
            System.out.flush();
        }
        private void statsScreen() {
            // display stats of the game
        }
    }
