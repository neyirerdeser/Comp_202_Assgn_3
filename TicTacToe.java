// NEYIR ERDESER
// 260736004

/* INSIDE : these descriptions are also inside respective methods, this is just an overview because there are so many methods.
 * A. createBoard           : takes an integer and returns an empty square game board with given dimension
 * B. displayBoard          : takes a 2D array of chars and prints them as a tictactoe board
 *  -->helper : printBorder : takes an integer (dimension of the board) prints the border between rows
 * C. writeOnBoard          : takes the board, the character that will be written and the x and y coordinates, modifies the board with the new character added to the given coordinates, throws exceptions if the given coordinates aren't valid (either out of bounds or already in use)
 * D. getUserMove           : takes the board, asks the user to enter coordinates. writes x on the given coordinates using writeOnBoard. keeps asking until valid coordinates are given
 * E. checkForObviousMove   : takes the board ,looks at it to see if theres a move the AI is suppose to do in order to win immediately or at least avoid immidiate loss. if there is one executes the move returns true, if not returns false
 *  -->helpers
 *  1.getColumn             : takes the board and an integer telling which column is wanted. returns a new array with only the contents of that column
 *  2.getRow                : takes the board and an integer telling which row is wanted. returns a new array with only the contents of that row 
 *  3.getDiagonal           : takes the board and an integer (either 0 or 1, 0 for the diagonal from top left to bottom right, 1 for the other one.) returns a new array with only the contents of that diagonal 
 *  4.findSpace             : takes one line of the board (1D array of characters returned by one of the three get methods), assumes there will be only one empty space, since it will be used with another method that ensures theres ONLY one empty space (isAIWinning), returns an integer indicating where the empty space is later to be used as one of the coordinates (x coordinate, if the given line was a column and y coordinate, if the given lie was a row)
 *  5.countSpace            : takes one line of the board (1D array of characters returned by one of the three get methods), and counts how many empty spaces there is, returns the integer
 *  6.isAIWinning           : takes one line of the board (1D array of characters returned by one of the three get methods), and determines if the AI is winning for that line (if theres exactly one empty space and the rest of the characters are 'x's), return true if it is
 *  7.isPlayerWinning       : takes one line of the board (1D array of characters returned by one of the three get methods), and determines if the player is winning for that line (if theres exactly one empty space and the rest of the characters are 'o's) return true if they are
 * F. getAIMove             : takes the board, if it yet full; first checks if theres an obvious move to be executed, if none performs a random move.
 *  -->helper : boardFull   : takes the board, checks if theres any empty space left. returns true if theres none left (if its full)
 * G. checkForWinner        : takes the board, looks at it to determine the winner / if there's a winner, then returns the winner (x for the AI, o for the player or empty char if none)
 *  -->helper : lineMatch   : takes one line of the board (1D array of characters returned by one of the get methods), checks it to see if they're all the same character. returns true if they do
 * H. play                  : starts the game asking user for their name, welcoming them, asking them what dimension they want for the board and creating the board, flipping a coin to determine who starts first (0 for user 1 for the AI). Then, it plays the game until theres winner or theres no possible moves left. Ends the game by telling its over and declaring the winner (or a tie)
 * main method              : only to call the play method, so the game can actually be played
 
 * note: the helper methods aren't designed to be error-proof. If given unexpected values some might cause problems but I the reason I'm not making them secure is that I am be the only one who can ever give the input those methods and I know when and how to use them. So within users usage of the program, they wont cause any problems - unless the code is changed                                                                                  */

import java.util.Scanner;
import java.util.Random;
import java.util.InputMismatchException;
public class TicTacToe {
  // A.
  public static char[][] createBoard(int n) {
    // takes an integer and returns an empty square game board with given dimension
    char[][]board = new char[n][n];
    for(int i=0; i<n; i++) {
      for(int j=0; j<n; j++) {
        board[i][j] = ' ';
      }
    }
    return board;
  }
  // B.
  public static void displayBoard(char[][]layout) {
    // takes a 2D array of chars and prints them as a tictactoe board
    for(int i=0; i<layout.length; i++) {
      printBorder(layout.length); // +-+-... line before each row
      for(int j=0; j<layout[0].length; j++) {
        System.out.print("|"+layout[i][j]); // stipes to the left of all entries
      }
      System.out.println("|"); // stipe to the right of te last entry, closing the row.
    }
    printBorder(layout.length); // another +-+... line after every row is printed to close the board
  }
  // B - helper
  public static void printBorder(int n) {
    // takes an integer (dimension of the board) prints the border between rows
    for(int i=0; i<2*n+1; i++) { // total length will be different than the board dimensions since there are stipes (|) in between entries
      if(i%2==0)
        System.out.print("+"); // starting from 0 every other character should be a +
      else
        System.out.print("-"); // startig from 1 every other character should be a -
    }
    System.out.println();
  }
  // C.
  public static void writeOnBoard(char[][]board, char input, int x, int y) {
    // takes the board, the character that will be written and the x and y coordinates, 
    // modifies the board with the new character added to the given coordinates, 
    // throws exceptions if the given coordinates aren't valid (either out of bounds or already in use)
    int n = board.length;
    if(x<0||x>=n||y<0||y>=n) // checks if the coordinates are out of the board dimensions
      throw new IllegalArgumentException("given coordinates are outside of the board");
    else if(board[x][y]!=' ') // checks if the spot in given coordinates is already filled
      throw new IllegalArgumentException("the box with given coordinates is already in use");
    else // if everything is okay changes the spot in given coordinates with desired input.
      board[x][y] = input;
  }
  // D.
  public static void getUserMove(char[][]board) {
    // takes the board, asks the user to enter coordinates. writes x on the given coordinates using writeOnBoard. 
    // keeps asking until valid coordinates are given
    Scanner sc = new Scanner(System.in);
    boolean valid = false;
    while(!valid) { // will loop as long as the coordinates are not valid to keep asking for new ones.
      try {
        System.out.println("Give coordinates");
        int x = sc.nextInt();
        int y = sc.nextInt();
        writeOnBoard(board,'x',x,y);
        // if the try block can reach this line the while condition it means the coordinates are valid and the loop can now be exited
        valid = true;
      } catch (IllegalArgumentException e) {
        System.out.println(e); // this lets the program print the expections thrown in the writeOnBoard method, so theres an explanation why the coordinates arent valid
        System.out.println("coordinates arent valid");
      }
    }
    sc.close();
  }
  // E.
  public static boolean checkForObviousMove(char[][]board) {
    // takes the board ,looks at it to see if theres a move the AI is suppose to do in order to win immediately or at least avoid immidiate loss.
    // if there is one executes the move returns true, if not returns false
    boolean check = false;
    // first checking if there's a move that would let AI win immediately
    // it will check for each diagonal, row and column
    // and immediatly if it finds a line where the AI is winning it will fill the empty space on that line with an o
    // to win the game.
    
    // starting with diagonals
    // for them, i wont use my findSpace method
    // instead ill search the line manually
    // since its already guaranteed that theres only one empty space via the isAIWinning method
    if(isAIWinning(getDiagonal(board, 0))) {
      check = true;
      for(int i=0; i<board.length; i++) {
        if(board[i][i]==' ')
          board[i][i]='o';
      }
    }
    else if(isAIWinning(getDiagonal(board, 1))) {
      check = true;
      for(int i=0; i<board.length; i++) {
        if(board[i][board.length-1-i]==' ')
          board[i][board.length-1-i]='o';
      }
    }
    else {
      for(int i=0; i<board.length; i++) {
        
        if(isAIWinning(getRow(board, i))) {
          check = true;
          writeOnBoard(board,'o',i,findSpace(getRow(board, i)));
        }       
        else if(isAIWinning(getColumn(board, i))) {
          check = true;
          writeOnBoard(board,'o',findSpace(getColumn(board, i)),i);
        }      
      }
    }
    if (check==false) { 
      // if check was never modified to be true it means there was no move found to win the game
      // now looking for moves to block the user from winning
      
      // it will check for each diagonal, row and column
      // and immediatly if it finds a line where the player is winning it will fill the empty space on that line with an o
      // to prevent the player from winning the game.
      if(isPlayerWinning(getDiagonal(board, 0))) {
        check = true;
        for(int i=0; i<board.length; i++) {
          if(board[i][i]==' ')
            board[i][i]='o';
        }
      }
      else if(isPlayerWinning(getDiagonal(board, 1))) {
        check = true;
        for(int i=0; i<board.length; i++) {
          if(board[i][board.length-1-i]==' ')
            board[i][board.length-1-i]='o';
        }
      }
      else {
        for(int i=0; i<board.length; i++) {
          
          if(isPlayerWinning(getRow(board, i))) {
            check = true;
            writeOnBoard(board,'o',i,findSpace(getRow(board, i)));
          }       
          else if(isPlayerWinning(getColumn(board, i))) {
            check = true;
            writeOnBoard(board,'o',findSpace(getColumn(board, i)),i);          
          }      
        }
      }
    }
    // if by now the check is still false the method will simply return false
    // if it was modified to be true at some point, it means that it also executed the move and will return true.
    return check;
  }
  // E - helpers
  // since ill be the one using these methods i'm not making them error-proof
  // i know what they need as input and ill make sure they get those inputs in the program
  // they wont be changed by the user at any point.
  
  public static char[] getColumn(char[][]board, int n) {
    // takes the board and an integer telling which column is wanted. returns a new array with only the contents of that column
    char[]col = new char[board.length];
    for(int i=0; i<col.length; i++) {
      col[i] = board[i][n]; // making a new array with the elements of the desired column
    }
    return col;
  }
  public static char[] getRow(char[][]board, int n) {
    // takes the board and an integer telling which row is wanted. returns a new array with only the contents of that row 
    char[]row = new char[board[0].length];
    for(int i=0; i<row.length; i++) {
      row[i] = board[n][i]; // making a new array with the elements of the desired row
    }
    return row;
  }
  public static char[] getDiagonal(char[][]board, int n) {
    // takes the board and an integer (either 0 or 1, 0 for the diagonal from top left to bottom right, 1 for the other one.) 
    // returns a new array with only the contents of that diagonal 
    int size = board.length;
    char[]diag = new char[size];
    // n = 0 for left to right diagonal
    // n = 1 for right to left diagonal
    for(int i=0; i<size; i++) {
      if (n==0) 
        diag[i] = board[i][i]; // making a new array with the elements of diagonal 0
      else if (n==1) 
        diag[i] = board[i][size-1-i];  // making a new array with the elements of diagonal 1
    }   
    return diag;
  }
  
  public static int findSpace(char[]line) {
    // takes one line of the board (1D array of characters returned by one of the three get methods), 
    // assumes there will be only one empty space, since it will only be used with another method that ensures theres ONLY one empty space (isAIWinning), 
    // returns an integer indicating where the empty space is later to be used as one of the coordinates
    // (x coordinate, if the given line was a column and y coordinate, if the given lie was a row)
    for(int i=0; i<line.length; i++) {
      if(line[i]==' ')
        return i;
    }
    return 1000; // for error control only, wont be reached
  }
  public static int countSpace(char[]line) {
    // takes one line of the board (1D array of characters returned by one of the three get methods), 
    // and counts how many empty spaces there is, returns the integer
    int count = 0;
    for(int i=0; i<line.length; i++) {
      if(line[i]==' ')
        count++;
    }
    return count;
  }
  public static boolean isAIWinning(char[]line) {
    // takes one line of the board (1D array of characters returned by one of the three get methods),
    // and determines if the AI is winning for that line 
    // (if theres exactly one empty space and the rest of the characters are 'x's), 
    // return true if it is
    if(countSpace(line)==1) {
      // assuring there exactly one space on the given line
      // meaning the rest of the elements are suppose to be o in order to win
      // so in total all the element in the line must either be o or space character
      for(int i=0; i<line.length; i++) {
        if(line[i]=='x') // checking if there are any elements that would jeopardize AI's winning
          return false;
      }
      return true;
    }
    return false; // if there are more than 1 space on the line, no one can be winning. if there are non; either no winners or game ends, theres a seperate method to check that (checkForWinner).
  }
  public static boolean isPlayerWinning(char[]line) {
    // takes one line of the board (1D array of characters returned by one of the three get methods), 
    // and determines if the player is winning for that line 
    // (if theres exactly one empty space and the rest of the characters are 'o's) return true if they are
    // same idea as isAIWinning
    // only this time we want only x's and one space character
    if(countSpace(line)==1) {
      for(int i=0; i<line.length; i++) {
        if(line[i]=='o')
          return false;
      }
      return true;
    }
    return false;
  }
  // F.
  public static void getAIMove(char[][]board) {
    // takes the board, if it yet full; first checks if theres an obvious move to be executed, if none performs a random move.
    // if theres an obvious move, calling the method to check the condition will execute the move then return true
    boolean valid = false;
    System.out.println("The AI made its move:");
    // i put this print in this method and not in play, so if there isnt any more moves, it wont print this and not play.
    if(!checkForObviousMove(board)) { // only if there isnt an obvious move it'll perform a random move
      while(!valid&&!boardFull(board)) {
        // will try to find a valid move
        // wont try if the board is already full
        try {
          Random rg = new Random();
          // producing integers for the coordinates between 0 and the dimension (excluded)
          // so there wont be any problem that they're out of the boundaries of the board
          // but they might point to a slot which is already filled
          // if that happens it will cathc the error and come back here to try again.
          int x = rg.nextInt(board.length);
          int y = rg.nextInt(board.length);
          writeOnBoard(board, 'o',x, y);
          valid = true;
          // if this is reached, it means the move was valid, just like in the getUserMove method
        } catch(IllegalArgumentException e) {  
          
        }
      }
    }
  }
  // F. - helper
  public static boolean boardFull(char[][]board) {
    // takes the board, checks if theres any empty space left. returns true if theres none left (if its full)
    for(int i=0; i<board.length; i++){
      for(int j=0; j<board.length; j++){
        if(board[i][j]==' ')
          // if there's any slot on the board that is still empty it will return false
          return false;
      }
    }
    return true;
  }
  // G.
  public static char checkForWinner(char[][]board) {
    // takes the board, looks at it to determine the winner / if there's a winner, 
    // then returns the winner (x for the AI, o for the player or empty char if none)
    char winner = ' ';
    // if by the end of the code no winner is found it will b e a tie and the method will return an empty character to symbolize that
    for(int i=0; i<board.length; i++) {
      // it checks all lines (rows, colums and diagonals) on the board
      // if all the slots on theat line are the same it checks the first entry
      // that entry will determine the winner.
      if(lineMatch(getRow(board, i))) {
        char line[] = getRow(board, i);
        if(line[0]=='x')
          winner = 'x';
        else if(line[0]=='o')
          winner = 'o';
      } else if (lineMatch(getColumn(board, i))) {
        char line[] = getColumn(board, i);
        if(line[0]=='x')
          winner = 'x';
        else if(line[0]=='o')
          winner = 'o';
      } else if (lineMatch(getDiagonal(board, 0))) {
        char line[] = getDiagonal(board, 0);
        if(line[0]=='x')
          winner = 'x';
        else if(line[0]=='o')
          winner = 'o';
      } else if (lineMatch(getDiagonal(board, 1))) {
        char line[] = getDiagonal(board, 1);
        if(line[0]=='x')
          winner = 'x';
        else if(line[0]=='o')
          winner = 'o';
      }
    }
    return winner;
  }
  // G. - helper
  public static boolean lineMatch(char[]line){
    // takes one line of the board (1D array of characters returned by one of the get methods), 
    // checks it to see if they're all the same character. returns true if they do
    for(int i=0; i<line.length-1; i++) {
      if(line[i]!=line[i+1])
        return false;
    }
    return true;
  }
  // H.
  public static void play() {
    // starts the game asking user for their name, welcoming them, 
    // asking them what dimension they want for the board and creating the board, 
    // flipping a coin to determine who starts first (0 for user 1 for the AI). 
    // Then, it plays the game until theres winner or theres no possible moves left.
    // Ends the game by telling its over and declaring the winner (or a tie)
    Scanner sc = new Scanner(System.in);
    // the beginning of the game
    // takes neccessary inputs : name for the player and dimension for the board
    System.out.println("Please enter your name:");
    String name = sc.nextLine();
    System.out.println("Welcome, "+name+"! Are you ready to play?");
    System.out.println("Please chose the dimension of your board:");
    int boardSize=0; // will be changed in the while loop
    // the loops works exactly the same way as the ones in getUserMove and getAIMove
    boolean valid = false;
    while (!valid) {
      try {
        boardSize = sc.nextInt(); 
        valid = true;
      } catch (InputMismatchException e) {
        System.out.println("Please enter an integer instead");
        sc.nextLine();
      }
    }
    char[][]board = createBoard(boardSize);
    // coin flip - determine who plays first
    Random rg = new Random();
    int coin = rg.nextInt(2);
    System.out.println("Flipping coin to see who starts first....\n(Heads for you and tails for the AI)");
    System.out.print("The result of the coin toss is: ");
    // if the AI is starting this will run first then it will continue with the players turn
    // if the player is starting this part will be skipped and it will go on with the players turn
    if(coin==1) {
      System.out.println("Tails!\nThe AI has the first move");
      getAIMove(board);
      displayBoard(board);
    } 
    else if (coin==0) 
      System.out.println("Heads!\nYou get the first move");
    char winner = checkForWinner(board);
    // game loop
    while(winner == ' '&&!boardFull(board)) {
      // the game will run until theres a winner or the board is full
      System.out.println("Please enter your move:");
      getUserMove(board);
      winner = checkForWinner(board);
      displayBoard(board); 
      if (checkForWinner(board)!=' ')
        break;
      // secndary check in between turns inca theres a winner or the board is full
      getAIMove(board);
      winner = checkForWinner(board);
      displayBoard(board);
    }
    sc.close();
    // after the game loop exits it tells that its over
    // and declares the winner or that i t is a tie
    System.out.println("GAME OVER!");
    if(winner=='x')
      System.out.println("You won");
    else if(winner=='o')
      System.out.println("You lost");    
    else
      System.out.println("It's a tie");
  } 
  public static void main(String args[]) {
    play();
  }
}