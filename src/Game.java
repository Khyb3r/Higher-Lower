    import java.io.*;
    import java.util.Collections;
    import java.util.List;
    import java.util.Scanner;

    public class Game {
        private List<Card> deck;
        private final Scanner scanner;
        private int score;
        private int wrongGuessesRow;
        private GameMode mode;
        private boolean gameRunning;

        // creates file for storing high score in directory above src
        private final File gameStats = new File("../gamestats.txt");

        // Standard ANSI escape codes for colours etc.
        public static final String RED = "\033[91m";
        public static final String CYAN_BLUE = "\033[96m";
        public static final String GREEN = "\033[92m";
        public static final String YELLOW = "\033[93m";
        public static final String RESET = "\033[0m";
        public static final String BOLD = "\033[1m";

        // Different states game can be in (used for rendering specific screen)
        public enum GameMode {
            START_SCREEN, PLAYING, RULES, CONTROLS, STATS, GAME_OVER
        }

        // file handling causes IOException
        public Game() throws IOException {
            this.deck = Deck.createDeck();
            this.scanner = new Scanner(System.in);
            this.gameRunning = true;
            this.mode = GameMode.START_SCREEN;
            this.score = 0;
            this.wrongGuessesRow = 0;

            // create file if it doesn't exist and set with dummy/initial value
            if (!gameStats.exists()) {
                boolean f = gameStats.createNewFile();
                try (FileWriter fileWriter = new FileWriter(gameStats)){
                    fileWriter.write("Highest score is: 0");
                }
                catch (Exception e) {e.printStackTrace();}
            }
        }

        // Starts the Game
        public void run() {
            Card card = null;
            // Main Game Loop
            while (gameRunning) {
                Screen.clearScreen();
                // Render different screen depending on current mode
                if (mode == GameMode.START_SCREEN) {
                    Screen.menuScreen();
                } else if (mode == GameMode.PLAYING) {
                    gameScreen(card);
                } else if (mode == GameMode.CONTROLS) {
                    Screen.controlsScreen();
                } else if (mode == GameMode.RULES) {
                    Screen.rulesScreen();
                } else if (mode == GameMode.STATS) {
                    statsScreen();
                } else if (mode == GameMode.GAME_OVER) {
                    Screen.gameOverScreen(this.score);
                }

                // User inputs differ depending on what screen they are on only for
                // start_screen/Home, Playing screen and Game Over Screen
                String input = scanner.nextLine().trim().toUpperCase();

                // Navigate to other screens other than home/start
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
                            System.out.flush();
                            break;
                    }
                }
                // Input for Playing mode
                else if (mode == GameMode.PLAYING) {
                    switch (input) {
                        // game start
                        case "START":
                            // if first run/new game card is null
                            // so shuffle the deck and choose the card from the top
                            if (card == null) {
                                Collections.shuffle(deck);
                                card = Deck.chooseCard(deck);
                            }
                            break;
                        case "H":
                            // User guessed higher -> update card with next card
                            if (card != null) card = playCard(card, true);
                            break;

                        case "L":
                            // User guessed lower -> update card with next card
                            if (card != null) card = playCard(card, false);
                            break;

                        case "B":
                            // Go back to home/start screen
                            mode = GameMode.START_SCREEN;
                            break;

                        case "Q":
                            // quit application -> ends game loop
                            gameRunning = false;
                            break;

                        case "N":
                            // new game, reset state of game (card, deck, score, reshuffle)
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
                // input when game/current round is over (no cards remaining in the deck)
                else if (mode == GameMode.GAME_OVER) {
                    switch (input) {
                        case "N":
                            // reset all game state (score, create a new deck and reshuffle)
                            // set the mode back to playing and begins new game/round
                            score = 0;
                            this.deck = Deck.createDeck();
                            Collections.shuffle(this.deck);
                            mode = GameMode.PLAYING;
                            break;

                        // back to home screen
                        case "B":
                            // reset all game state (score, create a new deck and reshuffle)
                            // set mode to start screen
                            score = 0;
                            this.deck = Deck.createDeck();
                            Collections.shuffle(this.deck);
                            mode = GameMode.START_SCREEN;
                            break;


                        case "Q":
                            // quit entire game
                            gameRunning = false;
                            break;
                        default:
                            System.out.println("Invalid input, try again");
                            System.out.flush();
                            break;
                    }
                }
                // any other screen any input returns you to the home screen
                // (those screens are essentially read only)
                else {
                    mode = GameMode.START_SCREEN;
                }

            }
        }

        // guess whether the next card is higher/lower than the current card
        // updates score and returns the next card back to main game loop so it is stored
        private Card playCard(Card currCard, boolean higherGuessed) {
            Card nextCard = pickCard();

            // Check whether card was correctly guesses higher/lower
            boolean isHigher = Deck.cardNumberCheck(currCard, nextCard);
            boolean correct = (isHigher && higherGuessed) || (!isHigher && !higherGuessed);
            System.out.println();


            if (correct) {
                score++;
                // reset as consecutive wrong guesses is broken
                wrongGuessesRow = 0;
                System.out.println(GREEN + "CORRECT" + RESET);
            }
            else {
                wrongGuessesRow++;
                System.out.println(RED + "WRONG" + RESET);
            }

            // 3 consecutive wrong guesses -> deduct a point
            if (wrongGuessesRow == 3) {
                score--;
                wrongGuessesRow = 0;
            }

            // update file storing max score only if current score is higher
            // avoids overhead of writing even if score wasn't higher
            if (score > fileMaxScore()) saveScore(score);

            // end of this current round (no more cards left in deck)
            if (this.deck.isEmpty()) {
                mode = GameMode.GAME_OVER;
                return null;
            }

            // User input on whether to continue, quit, go back, start new game
            // keep asking for input until one of the following inputted
            System.out.println("Press Enter to continue: ");
            while (true) {
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
                Screen.clearScreen();
                System.out.println("Try again: ");
                System.out.println("Press Enter to continue: ");
            }
            return nextCard;
        }

        private Card pickCard() {
            if (this.deck.isEmpty()) {
                return null;
            }
            return Deck.chooseCard(this.deck);
        }

        // Displays the current card to the user, joker cards are displayed slightly differently
        // also displays current deck size to user, so they know how much of the current round is left
        private void printCurrentCard(Card card) {
            if (card.getSuitType() == Suit.JOKER) {
                System.out.print("Card is: " + BOLD + CYAN_BLUE +  card.getJokerType() + " " + card.getSuitType() + RESET);
            }
            else {
                System.out.print("Card is: " + BOLD + CYAN_BLUE + card.getRankType() + " of " + card.getSuitType() + RESET);
            }
            System.out.println("\t\t\t\tDeck Size: " + YELLOW + this.deck.size() + RESET);
            System.out.println();
        }

        // Displays before actually beginning the Game
        // This display is not in the Screen class as it requires some stateful information of the game
        private void gameScreen(Card card) {
            if (card == null) {
                System.out.println(CYAN_BLUE + BOLD + "PLAY" + RESET);
                System.out.println("Type Start and Enter to Begin");
                System.out.println("Press B and Enter to pause current game");
                System.out.println("Press N and Enter to abandon current game and create new game");
                System.out.println("Press Q and Enter to Quit");
            }
            else  {
                printCurrentCard(card);
                System.out.println("Higher or Lower? ");
            }
            System.out.println();
            System.out.flush();
        }

        // Displays the current score and highest score which it reads from gamestats.txt.
        // This display is not in the Screen class as it requires some stateful information of the game
        private void statsScreen() {
            System.out.println(YELLOW + BOLD + "GAME STATS" + RESET);
            System.out.println("Highest score is: " + GREEN + fileMaxScore() + RESET);
            System.out.println("Score currently is: " + CYAN_BLUE + score + RESET);
            System.out.println();
        }

        // Overwrites the high score in gamestats.txt
        private void saveScore(int currScore) {
            try (FileWriter fileWriter = new FileWriter(gameStats, false)){
                fileWriter.write("Highest score is: " + currScore);
                fileWriter.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Extracts the score from the gamestats.txt file and returns it
        private int fileMaxScore() {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(gameStats))){
                String line = bufferedReader.readLine();
                String[] splitLine = line.split(":");
                return Integer.parseInt(splitLine[1].trim());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
