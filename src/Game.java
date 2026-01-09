    import java.io.*;
    import java.util.Collections;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Scanner;

    public class Game {
        private List<Card> deck;
        private final Scanner scanner;
        private int score;
        private GameMode mode;
        private boolean gameRunning;
        private final File gameStats = new File("../gamestats.txt");

        public enum GameMode {
            START_SCREEN, PLAYING, RULES, CONTROLS, STATS;
        }

        public Game() throws IOException {
            this.deck = Deck.createDeck();
            this.scanner = new Scanner(System.in);
            this.gameRunning = true;
            this.mode = GameMode.START_SCREEN;
            this.score = 0;

            if (!gameStats.exists()) {
                boolean f = gameStats.createNewFile();
                try (FileWriter fileWriter = new FileWriter(gameStats)){
                    fileWriter.write("Highest score is: 0");
                }
                catch (Exception e) {e.printStackTrace();}
            }
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
                                //printCurrentCard(card);
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
                            break;

                        case "Q":
                            gameRunning = false;
                            break;

                        case "N":
                            card = null;
                            score = 0;
                            this.deck = Deck.createDeck();
                            Collections.shuffle(this.deck);
                            break;
                        default:
                            System.out.println("Invalid input, try again");
                            System.out.flush();
                            break;
                    }
                }
                else {
                    mode = GameMode.START_SCREEN;
                }

            }
        }

        private Card playCard(Card currCard, boolean higherGuessed) {
            Card nextCard = pickCard();

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
            Collections.shuffle(this.deck);
            if (score > fileMaxScore()) saveScore(score);
            System.out.println("Press Enter to continue: ");
            for (;;) {
                String continueInput = scanner.nextLine().trim().toUpperCase();
                if (continueInput.equals("N")) {
                    mode = GameMode.START_SCREEN;
                    nextCard = null;
                    break;
                } else if (continueInput.equals("B")) {
                    mode = GameMode.START_SCREEN;
                    break;
                } else if (continueInput.equals("Q")) {
                    gameRunning = false;
                    break;
                } else if (continueInput.isEmpty()) {
                    break;
                }
                clearScreen();
                System.out.println("Try again: ");
                System.out.println("Press Enter to continue: ");
            }
            return nextCard;
        }

        private Card pickCard() {
            if (this.deck.isEmpty()) {
                this.deck = Deck.createDeck();
                Collections.shuffle(this.deck);
            }
            return Deck.chooseRandomCard(this.deck);
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
                System.out.println("Press B and Enter to pause current game");
                System.out.println("Press N to abandon current game and create new game");
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
            System.out.println("Highest score is: " + fileMaxScore());
            System.out.println("Score currently is: " + score);
        }

        private void saveScore(int currScore) {
            try (FileWriter fileWriter = new FileWriter(gameStats, false)){
                fileWriter.write("Highest score is: " + currScore);
                fileWriter.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private int fileMaxScore() {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(gameStats))){
                String line = bufferedReader.readLine();
                String[] splitLine = line.split(":");
                return Integer.parseInt(splitLine[1].trim());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void drawDeckSize() {
            String deckSize = String.valueOf(this.deck.size() - 1);
            System.out.println("\t\t\tDeck Size: " + deckSize);
        }
    }
