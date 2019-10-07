import java.util.*;

/**
 * TicTacToe - The class performs an ASCII graphic tic-tac-toe game with dynamic gameboard size against an AI player.
 *
 * @author Kalle Lindevall
 * @version 2018.1.01
 */
class TicTacToe {

    // Variables used for gameboard size management and comparison.
    public static int gameboardWidth = NewGame();
    public static int gameboardHeight = BoardHeight();
    // The gameboard size is automatically adjusted from the two values received .above
    public static char [][] Gameboard = new char[gameboardHeight][gameboardWidth];
    // Variables used for marking the gameboard and determining wins.
    public static int markRequired = 0;
    public static char markEmpty = '-';
    public static char markPlayer = 'X';
    public static char markCPU = 'O';
    public static int markRow = 0;
    public static int markCol = 0;
    // Variables that end the game/keep it going.
    public static boolean hasWon = false;
    public static boolean isDraw = false;

    /**
     * Starts the game and keeps it running until a winner is found or the gameboard is full. Later announces the winner.
     * @param args the command line arguments
     */
    public static void main(String [] args) {


        // NOTE: The code is filled with line breaks (System.out.println(); with nothing to print), for the sake of appeal and readability of the output.


        MarkAmount(gameboardWidth, gameboardHeight);
        PrintNewGameboard();

        System.out.println();
        System.out.println("Let's start the game!");
        System.out.println();
        // A delay (of 1000 milliseconds = 1 second) to help with the pacing and the readability of the output. Also found later in code with different values.
        Wait(1000);
        // Loops the game via PlayGame() until a win or a tie isn't found.
        // Upon finding a victory/tie, ends the game and the end result is printed out.
        do {
            // Contains the player and computer turns + victory checking.
            PlayGame();
            // CheckWins() sets a char value for the variable "win".
            char win = CheckWins();
            // When the "win" variable is set as the mark of one of the players, the game ends.
            if (win != markEmpty) {
                hasWon = true;
                // If "win" receives a mark of the human player, the game ends with an announcement of player victory.
                if (win == markPlayer) {
                    System.out.println();
                    System.out.println("+--------------------------------------------------+");
                    System.out.println("|           The player has won the game!           |");
                    System.out.println("+--------------------------------------------------+");
                // If "win" receives a mark of the computer player, the game ends with an announcement of computer victory.
                } else if (win == markCPU) {
                    System.out.println();
                    System.out.println("+--------------------------------------------------+");
                    System.out.println("|     The computer has (somehow) won the game!     |");
                    System.out.println("+--------------------------------------------------+");
                }
            }
            // If "isDraw" is true and no wins have been found, the game ends with an announcement of a tie.
            if (isDraw && !hasWon) {
                System.out.println();
                System.out.println("+--------------------------------------------------+");
                System.out.println("|              The game ends in a tie!             |");
                System.out.println("+--------------------------------------------------+");
            }
        } while (!isDraw && !hasWon);
    }

    /**
     * Prints out the rules for the game and then starts the adjustments for the gameboard size, starting by asking an input for the width of the gameboard.
     * @return width for the gameboard
     */
    public static int NewGame() { 

        System.out.println();
        System.out.println("+--------------------------------------------------+");
        System.out.println("|          Let's play a game: TIC-TAC-TOE          |");
        System.out.println("+--------------------------------------------------+");
        // A delay of 1000 milliseconds = 1 second.
        Wait(1000);
        System.out.println();
        System.out.println("RULES:");
        // A delay of 1000 milliseconds = 1 second.
        Wait(1000);
        System.out.println();
        System.out.println("The player, X, takes turns against the computer, O, marking the spaces on a gameboard.");
        System.out.println("The size of the gameboard is input by the user, and the minimum size for it is 3x3.");
        System.out.println("The first player to put user defined amount of marks in a horizontal, vertical, or diagonal line wins the game.");
        System.out.println();
        System.out.println();
        // A delay of 6000 millisecond = 6 seconds.
        Wait(6000);
        // Notifies the player, that gameboards larger or equal to 10x10 are usually played with 5 marks to win.
        System.out.println("NOTE:");
        // A delay of 1000 milliseconds = 1 second.
        Wait(1000);
        System.out.println();
        System.out.println("If the size of the gameboard is 10x10 or larger, it is recommended");
        System.out.println("to set the amount of marks required to win to be at least 5.");
        System.out.println();
        // A delay of 3000 milliseconds = 3 seconds.
        Wait(3000);
        System.out.println();
        System.out.println("Let's start by adjusting the gameboard size.");
        System.out.println();
        // A delay of 1500 milliseconds = 1.5 seconds.


        // NOTE: After this, later occurrences of Wait() appear without additional commentation. The functionality of the method should be clear by now.

        
        Wait(1500);
        System.out.println("First, input the width for the gameboard:");
        // Note: the minimum size for the gameboard is 3x3.
        System.out.println("(Minimum: 3)");
        // The value for the gameboard witdth is sent as parameters to, and received from, a method in the Validate class.
        // (Methods in the Validate class validate user input as whole numbers.)
        gameboardWidth = Validate.numBoard(gameboardWidth);
        // A value for gameboardWidth is returned. Later the size for the gameboard is automatically determined from the size values received.
        return gameboardWidth;
    }

    /**
     * Asks user input for the height of the gameboard.
     * @return height for the gameboard
     */
    public static int BoardHeight() {

        System.out.println("Then the height for the gameboard:");
        // Note: the minimum size for the gameboard is 3x3.
        System.out.println("(Minimum: 3)");
        // The value for the gameboard height is sent as paremeters to, and received from, a method in the Validate class.
        // (Methods in the Validate class validate user input as whole numbers.)
        gameboardHeight = Validate.numBoard(gameboardHeight);
        // A value for gameboardHeight is returned. Later the size for the gameboard is automatically determined from the size values received.
        return gameboardHeight;
    }

    /**
     * Asks user input for the amount of marks required to win the game.
     * @param gameboardWidth value of the width of the gameboard, that is compared to the value of the height
     * @param gameboardHeight value of the height of the gameboard, that is compared to the value of the width
     * @return the amount of marks required to win the game
     */
    public static int MarkAmount(int gameboardWidth, int gameboardHeight) {
        // The value of the shorter side of the gameboard
        int shorter = 0;
        // Compares the values of the width and height of the gameboard, determining which value is smaller.
        if (gameboardWidth < gameboardHeight) {
            shorter = gameboardWidth;
        } else {
            shorter = gameboardHeight;
        }        
        System.out.println("Now input the amount of marks required to win:");
        // If user has set the size for gameboard as 10x10 or larger, the output suggests the value for the marks required to win to be at least 5.
        // Minimum value is 3, as the minimum width/height is also 3.
        if (gameboardWidth >= 10 && gameboardHeight >= 10) {
            System.out.println("(Minimum: 3 / Maximum: " + shorter + " / Recommended: 5+)");
        } else {
        System.out.println("(Minimum: 3 / Maximum: " + shorter + ")");
        }
        // The value for the marks required to win is sent as parameters to, and received from, a method in the Validate class.
        // (Methods in the Validate class validate user input as whole numbers.)
        //
        // Also sends the values of the gameboard width and height as parameters for the means of comparison,
        // this way the value for the marks required to win can't be larger than the width or the height.
        markRequired = Validate.numMark(markRequired, gameboardWidth, gameboardHeight);
        // A value for the marks required to win is returned.
        return markRequired;
    }

    /**
     * First fills the gameboard with '-' (markEmpty), then prints out the board.
     */
    public static void PrintNewGameboard() {

        // Fills the gameboard 2D array with '-'.
        for (int x = 0; x < gameboardHeight; x++) {    
            for (int y = 0; y < gameboardWidth; y++) {  
                    Gameboard[x][y] = markEmpty;
            }
        }
        System.out.println();
        System.out.println("HERE'S THE GAMEBOARD:");
        System.out.println();
        Wait(500);
        // Prints out the gameboard 2D array.
        for (int x = 0; x < Gameboard.length; x++) {
            System.out.print(" ");
            for (int y = 0; y < Gameboard[x].length; y++) {
                System.out.print(Gameboard[x][y] + " ");
            }
            System.out.println();
        }  
        // Additional output to remind the player of the size of the gameboard and the amount of marks required to win the game.
        System.out.println();
        System.out.println("WIDTH: " + gameboardWidth + "  |  HEIGHT: " + gameboardHeight + "  |  MARKS TO WIN: " + markRequired);
        Wait(2000);
    }

    /**
     * Runs the methods for the player turn and the computer turn. Checks for wins after each turn.
     */
    public static void PlayGame() {
        System.out.println();
        System.out.println("+-------------------------+");
        System.out.println("|       PLAYER TURN       |");
        System.out.println("+-------------------------+");
        MovePlayer();
        CheckWins();
        Wait(1000);
        // As the human player is the first to play, if the player makes the winning move or the gameboard fills up, no more computer turns are played out.
        if (!FullBoard() && !hasWon) {
            System.out.println();
            System.out.println("+-------------------------+");
            System.out.println("|      COMPUTER TURN      |");
            System.out.println("+-------------------------+");
            Wait(500);
            MoveCPU();
            CheckWins();
            Wait(1000);
        }
    }

    /**
     * The human player's turn. Asks the input for the coordinates for the player mark 'X'.
     */
    public static void MovePlayer() {
        // Later used as an identifier which player's turn it was.
        int player = 0;
        System.out.println();
        System.out.print("Select the row for your mark ( 1 - " + gameboardHeight + " ): ");
        // The row coordinate value for the player's mark is sent as parameters to, and received from, a method in the Validate class.
        // (Methods in the Validate class validate user input as whole numbers.)
        markRow = Validate.numPlay(markRow, gameboardHeight);

        System.out.print("Select the column for your mark ( 1 - " + gameboardWidth + " ): ");
        // The column coordinate value for the player's mark is sent as parameters to, and received from, a method in the Validate class.
        // (Methods in the Validate class validate user input as whole numbers.)
        markCol = Validate.numPlay(markCol, gameboardWidth);
        // Starts PrintUpdatedGameboard() with the player identifier and the coordinates for the human player's mark as parameters.
        PrintUpdatedGameboard(player, markRow, markCol);
        // Row and column coordinates are reset, ready for the next turn.
        markRow = 0;
        markCol = 0;
    }

    /**
     * The computer player's turn. Randomizes the values of the coordinates for the computer player mark 'O'.
     */
    public static void MoveCPU() {
        // Later used as an identifier which player's turn it was.
        int player = 1;
        // The coordinates for the computer player's mark are generated from Math.random,
        // then multiplied with either the gameboards width (column coordinate) or the height (row coordinate).
        int markCol = (int) (Math.random() * gameboardWidth +1);
        int markRow = (int) (Math.random() * gameboardHeight +1);
        // Starts PrintUpdatedGameboard() with the player identifier and the coordinates for the computer player's mark as parameters.
        PrintUpdatedGameboard(player, markRow, markCol);
        // Row and column coordinates are reset, ready for the next turn.
        markRow = 0;
        markCol = 0;
    }

    /**
     * Prints the gameboard with the human and the computer player's marks updated in it.
     * @param whichPlayer used as an identifier to determine which player's mark to put on the board
     * @param markRow height/row coordinate for the player's mark, used to put the mark in the correct coordinates
     * @param markCol width/column coordinate for the player's mark, used to put the mark in the correct coordinates
     */
    public static void PrintUpdatedGameboard(int whichPlayer, int markRow, int markCol) {

        System.out.println();
        // If it was the human player's turn.
        if (whichPlayer == 0) {
            // A labelled loop (making it easier to just jump out of the loop), 
            // that goes through the gameboard with for-loops to find the coordinates the player input.
            boardloop:
            for (int x = 0; x < gameboardHeight; x++) {
                for (int y = 0; y < gameboardWidth; y++) {
                    // If there's already a player's mark in the coordinates, proceeds to ask for new values.
                    // (For so long, that the player inputs valid coordinates.)
                    if (Gameboard[markRow-1][markCol-1] == markPlayer || Gameboard[markRow-1][markCol-1] == markCPU) {
                        do {
                            // The values are again reset.
                            markRow = 0;
                            markCol = 0;
                            System.out.println("ERROR: There is already a mark in that position, input new coordinates.");
                            System.out.print("Select a new row for your mark ( 1 - " + gameboardHeight + " ): ");
                            // Again using a method in the Validate class to check for a whole number.
                            markRow = Validate.numPlay(markRow, gameboardHeight);
                            // Again using a method in the Validate class to check for a whole number.
                            System.out.print("Select a new column for your mark ( 1 - " + gameboardWidth + " ): ");
                            markCol = Validate.numPlay(markCol, gameboardWidth);
                                
                        } while (Gameboard[markRow-1][markCol-1] == markPlayer || Gameboard[markRow-1][markCol-1] == markCPU);
                    } else {
                    // If correct coordinate values are input, player's mark is placed on the specified coordinates.
                    Gameboard[markRow-1][markCol-1] = markPlayer;
                    // Breaks the loop.
                    break boardloop;
                    }
                }
            }
        // The computer player's turn.
        } else {
            // An identical loop as the human player loop, to check through the gameboard array.
            // (The only thing that differs, is the contents inside the if-else and the mark to place.)
            boardloop:
            for (int x = 0; x < gameboardHeight; x++) {
                for (int y = 0; y < gameboardWidth; y++) {
                    if (Gameboard[markRow-1][markCol-1] == markPlayer || Gameboard[markRow-1][markCol-1] == markCPU) {
                        System.out.println("The computer had a glitch and tried to place their mark on an occupied spot.");
                        System.out.println();
                        Wait(1500);
                        do {
                            markRow = 0;
                            markCol = 0;
                            System.out.println("Now generating new coordinates...");
                            Wait(250);
                            // Generates new random coordinates (within the gameboard's boundaries) for the computer player's mark.
                            markCol = (int) (Math.random() * gameboardWidth +1);
                            markRow = (int) (Math.random() * gameboardHeight +1);

                        } while (Gameboard[markRow-1][markCol-1] == markPlayer || Gameboard[markRow-1][markCol-1] == markCPU);
                    } else {
                    System.out.println();
                    Gameboard[markRow-1][markCol-1] = markCPU;
                    break boardloop;
                    }
                }
            }
        }
        // Prints the updated gameboard, with the current player's mark placed in it.
        for (int x = 0; x < Gameboard.length; x++) {
            System.out.print(" ");
            for (int y = 0; y < Gameboard[x].length; y++) {
                System.out.print(Gameboard[x][y] + " ");
            }
            System.out.println();
        }
        // Notifies where the user/computer placed their makrs.
        // If it was the human player's turn:
        if (whichPlayer == 0) {
            System.out.println();
            System.out.println("You placed your mark on " + "ROW: " + markRow + ", " + "COLUMN: " + markCol);
        // If it was the computer player's turn:
        } else {
            System.out.println();
            System.out.println("The computer placed their mark on " + "ROW: " + markRow + ", " + "COLUMN: " + markCol);
        }
    }

    /**
     * Checks if the marks placed by the user/computer form a horizontal, vertical or a diagonal line,
     * containing an equal amount of that player's marks to the user defined amount of marks required to win the game.
     *
     * The loops do this by first going the gameboard through with for-loops and then utilising if-else to check,
     * if the current mark is a mark placed by either of the palyers. If it is, the markCount increases with +1 until
     * an empty mark is checked or the required amount of marks is achieved. markCount is reset when it comes across a '-'.
     *
     * @return either a mark of a player (that also determines the winner), or an empty mark if no winner is found
     */
    public static char CheckWins() {
        // Keeps track of the amount of marks found in a horizontal, vertical or a diagonal line.
        int markCount = 0;
        // The last mark that was checked by the loop.
        char markLast = markEmpty; 
        // Goes through the gameboard and checks for HORIZONTAL WINS.
        for (int x = 0; x < gameboardHeight; x++) {
            for (int y = 0; y < gameboardWidth; y++) {
                // If the last mark checked was something else than a '-'.
                if (Gameboard[x][y] != markEmpty) {
                    // If the last mark checked also the same player's mark, markCount goes up by 1.
                    if (Gameboard[x][y] == markLast) {
                        markCount++;
                    } else {
                        // Mark count is kept at one, if there was just a singular player mark.
                        markCount = 1;
                        markLast  = Gameboard[x][y];
                    }
                    // If the amounts of subsequent marks equal the marks required to win,
                    // changes the boolean value of hasWon to true (thus ending the game)
                    // and sending the latest mark checked to main() and determining the winner.
                    if (markCount == markRequired) {
                        hasWon = true;
                        return markLast;
                    }
                } else {
                    // No wins found, resets the counter and the last mark checked to an empty one, ready for the next turn.
                    markLast = markEmpty;
                    markCount = 0;
                }
            }
            // No wins found, resets the counter and the last mark checked to an empty one, ready for the next turn.
            markLast = markEmpty;
            markCount = 0;
        }

        // Does the same thing as the loop before this, but checks for VERTICAL WINS instead.
        // x and y for-loops have swapped places, otherwise it's and identical loop with identical functions.
        for (int y = 0; y < gameboardWidth; y++) {
            for (int x = 0; x < gameboardHeight; x++) {
                // If the last mark checked was something else than a '-'.
                if (Gameboard[x][y] != markEmpty) {
                    // If the last mark checked also the same player's mark, markCount goes up by 1.
                    if (Gameboard[x][y] == markLast) {
                        markCount++;
                    } else {
                        // Mark count is kept at one, if there was just a singular player mark.
                        markCount = 1;
                        markLast  = Gameboard[x][y];
                    }
                    // If the amounts of subsequent marks equal the marks required to win,
                    // changes the boolean value of hasWon to true (thus ending the game)
                    // and sending the latest mark checked to main() and determining the winner.
                    if (markCount == markRequired) {
                        hasWon = true;
                        return markLast;
                    }
                } else {
                    // No wins found, resets the counter and the last mark checked to an empty one, ready for the next turn.
                    markLast = markEmpty;
                    markCount = 0;
                }
            }
            // No wins found, resets the counter and the last mark checked to an empty one, ready for the next turn.
            markLast = markEmpty;
            markCount = 0;
        }
        // Goes through the gameboard and checks for DIAGONAL WINS (from top-left to bottom-right / top-right to bottom-left).
        for (int x = 0; x < gameboardHeight; x++) {
            for (int y = 0; y < gameboardWidth; y++) {
                // If the last mark checked was something else than a '-'.
                if (Gameboard[x][y] != markEmpty) {
                    // If the last mark checked also the same player's mark, markCount goes up by 1.
                    if (Gameboard[x][y] == markLast) {
                        markCount++;
                    } else {
                        // Mark count is kept at one, if there was just a singular player mark.
                        // Diagonal checks are a bit trickier, so this is important...
                        markCount = 1;
                        markLast  = Gameboard[x][y];
                        // This for-loop checks for top-left to bottom-right wins, by everytime adding the integer z to the width (y) & height (x)
                        // (for as long as z is smaller than marks required to win, height+z smaller than max height, and width+z smaller than max width).
                        for (int z = 1; z < markRequired && x+z < gameboardHeight && y+z < gameboardWidth; z++) {
                            // If the mark found on the new coordinates equals to the last mark checked, markCount goes up by 1.
                            if (Gameboard[x+z][y+z] == markLast) {
                                markCount++;
                                // If the amounts of subsequent marks equal the marks required to win,
                                // changes the boolean value of hasWon to true (thus ending the game)
                                // and sending the latest mark checked to main() and determining the winner.
                                if (markCount == markRequired) {
                                    hasWon = true;
                                    return markLast;
                                }
                            } else {
                                break;
                            }
                        }
                        // Does the same thing as the previous loop that checked from top-left bottom-right,
                        // this one checks from top-right to bottom-left by everytime adding the integer z to the height (x) and substracting z from the width (y).
                        // (for as long as z is smaller than marks required to win, height+z smaller than max height, and width-z smaller than max width).
                        for (int z = 1; z < markRequired && x+z < gameboardHeight && y-z < gameboardWidth; z++) {
                            // An additional check, that the width is equal to or greater than the marks required to win (-1),
                            // this to keep the loop from checking top-right to bottom-left victories while the mark checked is on coordinate,
                            // from where a top-right to bottom-left victory is impossible to achieve (e.g. 5 marks to win, 'X' was checked on column 3).
                            if (y >= markRequired-1) {
                                // If the mark found on the new coordinates equals to the last mark checked, markCount goes up by 1.
                                if (Gameboard[x+z][y-z] == markLast) {
                                    markCount++;
                                    // If the amounts of subsequent marks equal the marks required to win,
                                    // changes the boolean value of hasWon to true (thus ending the game)
                                    // and sending the latest mark checked to main() and determining the winner
                                    if (markCount == markRequired) {
                                        hasWon = true;
                                        return markLast;
                                    }
                                } else {
                                    break;
                                }
                            }
                        }
                        // No wins found, resets the counter and the last mark checked to an empty one, ready for the next turn.
                        markLast = markEmpty;
                        markCount = 0;
                    }
                }
            }
        }
        // If no wins are found, returns an empty mark from the board.
        return markEmpty;
    }

    /**
     * Checks if the board is full or not.
     * @return is the gameboard full and no more turns are possible, equaling in a draw.
     */
    public static boolean FullBoard() {
        // By default, the value to return is "true".
        isDraw = true;
        // Goes through the board with for-loops, checking if empty marks are found.
        for (int x = 0; x < gameboardHeight; x++) {
            for (int y = 0; y < gameboardWidth; y++) {
                if (Gameboard[x][y] == markEmpty) {
                    // Empty marks found, changes the value of isDraw to "false".
                    isDraw = false;
                }
            }
        }
        // Returns the value of isDraw to main().
        return isDraw;
    }

    /**
     * A nifty little "extra" method, used to add small delays in the code. 
     * The reason for this was already explained in the beginning of the code (line 43),
     * but it's mainly to help with the user experience, by easing the pacing of the output a little bit.
     * @param time the amount of milliseconds to wait
     */
    public static void Wait (int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

}

/**
 * Validate - The class processes user input, depending on the situation, through different methods to validate it to an appropriately sized integer.
 *
 * @author Kalle Lindevall
 * @version 2018.1
*/
class Validate {

    /**
     * This loop checks the user input for the size of the gameboard.
     * @param numToCheck either the value for the height or the width of the gameboard
     * @return an integer equal to or bigger than 3, for either gameboardHeight or gameboardWidth
     */
    public static int numBoard(int numToCheck) {
        Scanner input = new Scanner(System.in);
        // While the number input by the user is under 3, a decimal number or not a number at all.
        while (numToCheck < 3) {
            // Checks if the value input by the user is an integer.
            try {
                numToCheck = Integer.parseInt(input.nextLine().trim());
                System.out.println();
            } catch (NumberFormatException e) {
                System.out.println();
                System.out.println("ERROR: Only (whole) numbers are valid.");
                System.out.println();
                continue;
            }
            // If the number input by the user is an integer, but too small, proceeds to ask for a different value.
            if (numToCheck < 3) {
                System.out.println("ERROR: Please give a valid number.");
                System.out.println();
            }
        }
        // Returns the validated integer as a value for either gameboardHeight or gameboardWidth, depending on what method was this method called from.
        return numToCheck;
    }

    /**
     * This loop checks the user input for the marks required to win the game.
     * @param numToCheck the user input value for the marks required to win
     * @param numWidth value of the width of the gameboard, that is compared to the value of the height
     * @param numHeight value of the height of the gameboard, that is compared to the value of the width
     * @return an integer that is 3 or bigger and equal or smaller than the shorter side of the gameboard
     */
    public static int numMark(int numToCheck, int numWidth, int numHeight) {
        Scanner input = new Scanner(System.in);

        // The value of the shorter side of the gameboard
        int shorter = 0;

        // Compares the values of the width and height of the gameboard, determining which is smaller.
        if (numWidth < numHeight) {
            shorter = numWidth;
        } else {
            shorter = numHeight;
        }        
        // While the number input by the user is under 3, bigger than the shorter side of the gameboard, a decimal number or not a number at all.
        while (numToCheck < 3 || numToCheck > shorter) {
            // Checks if the value input by the user is an integer.
            try {
                numToCheck = Integer.parseInt(input.nextLine().trim());
                System.out.println();
            } catch (NumberFormatException e) {
                System.out.println();
                System.out.println("ERROR: Only (whole) numbers are valid.");
                System.out.println();
                continue;
            }
            // If the number input by the user is an integer, but too small or big, proceeds to ask for a different value.
            if (numToCheck < 3 || numToCheck > shorter) {
                System.out.println("ERROR: Please give a valid number.");
                System.out.println();
            }
        }
        // Returns the validated integer as a value for the marks required to win the game.
        return numToCheck;
    }

    /**
     * This loop checks the user input values for the coordinates for the human player's mark.
     * @param numToCheck the user input value for either the column or the row coordinate for their mark
     * @param compareBoard value of the width/height of the gameboard, that is compared to the column/row coordinate input by the user
     * @return an integer that is 3 or bigger and equal or smaller than the shorter side of the gameboard
     */
    public static int numPlay(int numToCheck, int compareBoard) {
        Scanner input = new Scanner(System.in);
        // While the number input by the user is under 0 or smaller, or bigger than the corresponding size of the gameboard (width/column or height/row)
        while (numToCheck <= 0 || numToCheck > compareBoard) {
            // Checks if the value input by the user is an integer.
            try {
                numToCheck = Integer.parseInt(input.nextLine().trim());
                System.out.println();
            } catch (NumberFormatException e) {
                System.out.println();
                System.out.println("ERROR: Only (whole) numbers are valid.");
                System.out.println();;
                continue;
            }
            // If the number input by the user is an integer, but too small or big, proceeds to ask for a different value.
            if (numToCheck <= 0 || numToCheck > compareBoard) {
                System.out.println("ERROR: Please give a valid number.");
                System.out.println();
            }
        }
        // Returns the validated integer as a value for the row/column coordinate for the human player's mark.
        return numToCheck;
    }
}