# Higher or Lower
This is a CLI game based on the "Higher or Lower" card game, built using Java.

## File Structure

- All source code is located within the "src" folder.
- The bash script "run.sh" can be used to run the game from terminal.

## Setup

This application requires JDK version 22.0.1 or newer.  
Older versions may work however to avoid any versioning issues please use the version stated above or newer.

## Running the Application

- Load the project in an IDE like Intellij and run from there.
- Use the bash script "run.sh" by navigating to the "Higher-Lower" folder and running ./run.sh in your terminal.
    - You may need to set execute permissions before running the script by entering "chmod +x run.sh" in your terminal.
    - The bash script will compile and launch the application automatically in the terminal.

## Project Features
This project meets all the necessary requirements stated, it correctly:
 - Models a standard 52 card deck
 - Implements some mechanism for shuffling the deck
 - Creates rule(s) for the game
 - Builds a simple CLI based UI to play it

As well as this, other extensions have also been developed:
 - Added 2 Jokers to the deck
 - Scoring system with a twist (point loss for 3 incorrect consecutive guesses)
 - Persistent handling of scoring across game sessions (gamestats.txt)
 - ANSI terminal colours used to improve UI.

#### Rationale behind certain design decisions
The idea was to split up each component of the game into its own class/enum representation to keep everything modular.
Therefore, Card, Suit, Rank and Deck classes/enums were established. The easiest way to manage the numeric value for comparison 
between cards was by attaching a value to each enum value per Rank. Suits were also split in a standard manner into the 4 types
within a deck, with an added joker enum value. To accommodate for Red/Black jokers an inner enum within the Suits Enum was created
therefore meaning the Card class has 2 Constructors one for non Joker type cards and one for Joker types. In terms of the Game,
the Screen class was created to separate Game logic from the methods that "render" what mode/display is currently active. Due to 
some of these displays being stateful (requiring game logic) these screen methods were kept in the Game class to avoid moving things
like file I/O related objects, therefore allowing for simpler error handling.

#### Refer to the comments within the code for further reasoning behind how/why things work

## Project Improvements
- Improve logging and error case handling
- Cache score results as a variable to avoid overhead of even needing to reopen and read the score from the file on each check
- Refactor parts of input handling code as there are some duplications (functions could be created for these)

#### ANSI colours/escape codes are used in this application, ensure your terminal supports them.