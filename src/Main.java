import java.io.FileNotFoundException;
import java.util.*;
import java.util.spi.AbstractResourceBundleProvider;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Game game = new Game();
        game.run();
    }
}
