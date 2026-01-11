import javax.print.DocFlavor;

// Different displays/sreeens of the game to be rendered, excludes some screens
// as those require game state information that increases complexity if split into this class.
public class Screen {

    public static void clearScreen() {
        // ANSI escape code for clearing screen and cursoring to top left of terminal
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Home/Menu Screen, central point to the rest of the game's functionality
    public static void menuScreen() {
        System.out.println(Game.GREEN + Game.BOLD + "HIGHER OR LOWER" + Game.RESET);
        System.out.println();
        System.out.println("Press P and Enter to Play");
        System.out.println("Press Q and Enter to Quit");
        System.out.println("Press R and Enter for How to Play");
        System.out.println("Press C and Enter for Controls and Help");
        System.out.println("Press S and Enter for Game Stats");
        System.out.println();
        System.out.flush();
    }

    // Shows the user controls for navigation the CLI and playing the game
    public static void controlsScreen() {
        System.out.println(Game.YELLOW + Game.BOLD + "CONTROLS" + Game.RESET);
        System.out.println("Game Controls: H for Higher, L for Lower");
        System.out.println();
        System.out.println("Game Stats shows highest all time score and current score");
        System.out.println();
        System.out.println("To Exit Controls, How to Play or Game Stats Screens: Press any Key and Enter");
        System.out.println();
        System.out.println("Can only quit application when on Home Screen or Playing");
        System.out.flush();
    }

    // Shows the user how the game actually works
    public static void rulesScreen() {
        System.out.println(Game.YELLOW + Game.BOLD + "RULES" + Game.RESET);
        System.out.println("You must guess whether the next card in the deck will be higher or lower than your current card.");
        System.out.println("1 point for a correct guess.");
        System.out.println("-1 point for 3 consecutive incorrect guesses");
        System.out.println();
        System.out.println("Card's are ranked by their numerical values.");
        System.out.println("Jack's have value 11.");
        System.out.println("Queen's have value 12.");
        System.out.println("King's have value 13");
        System.out.println("Ace's have value 14");
        System.out.println("Jokers have value 15");
        System.out.println();
        System.out.println("Round ends after current deck is empty (all 54 cards are used)");
        System.out.flush();
    }

    // Called when the deck is empty (all 54 cards have been used), effectively this round is over
    // Displays the users score for this round, how to start a new game or go back to main menu
    public static void gameOverScreen(int score) {
        clearScreen();
        System.out.println(Game.YELLOW + Game.BOLD + "GAME OVER" + Game.RESET);
        System.out.println();
        System.out.println("Score was: " + score);
        System.out.println("Press N for New Game, B for Main Menu, Q to Quit");
        System.out.println();
    }
}
