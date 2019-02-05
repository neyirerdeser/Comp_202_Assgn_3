public class Winner {
  public static char checkWinner(char[][]board) { // defected
    char winner = ' '; // a for error control  
    for(int i=0; i<board.length-1; i++) {
      // check rows
      char[]line = getRow(board,i);
      // checking if they're all the same
      if(line[i]==line[i+1]) {
        if(line[0]=='x')
          winner = 'x';
        else if(line[0]=='o')
          winner = 'o';
      } else {
        // check columns
        line = getColumn(board,i);
        if(line[i]==line[i+1]) {
          if(line[0]=='x')
            winner = 'x';
          else if(line[0]=='o')
            winner = 'o';         
        } else {
          // check diagonal 0
          line = getDiagonal(board,0);
          if(line[i]==line[i+1]) {
            if(line[0]=='x')
              winner = 'x';
            else if(line[0]=='o')
              winner = 'o';         
          } else {
            // check diagonal 1
            if(line[i]==line[i+1]) {
              if(line[0]=='x')
                winner = 'x';
              else if(line[0]=='o')
                winner = 'o';  
            }
          }
        } 
      }
    }
    return winner;
  }
  public static char checkForWinner(char[][]board) { // works !!!!
    char winner = ' ';
    for(int i=0; i<board.length; i++) {
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
  public static char[] getColumn(char[][]board, int n) {
    char[]col = new char[board.length];
    for(int i=0; i<col.length; i++) {
      col[i] = board[i][n];
    }
    return col;
  }
  public static char[] getRow(char[][]board, int n) {
    char[]row = new char[board[0].length];
    for(int i=0; i<row.length; i++) {
      row[i] = board[n][i];
    }
    return row;
  }
  public static char[] getDiagonal(char[][]board, int n) {
    int size = board.length;
    char[]diag = new char[size];
    // n = 0 for left to right diagonal
    // n = 1 for right to left diagonal
    for(int i=0; i<size; i++) {
      if (n==0) 
        diag[i] = board[i][i];     
      else if (n==1) 
        diag[i] = board[i][size-1-i];
    }   
    return diag;
  }
  
  public static boolean lineMatch(char[]line){
    for(int i=0; i<line.length-1; i++) {
      if(line[i]!=line[i+1])
        return false;
    }
    return true;
  }
  //=============================================================================
  public static void main (String args[]) {
    char[][]a = {{'o','o','x'},
                 {'x','o','x'},
                 {'o','x','o'}};
    display(a);
    char[]diag0 = getDiagonal(a,0);
    print(diag0);
    int i=0;
    boolean match = diag0[i]==diag0[i+1];
    System.out.println(match);
    //System.out.println(checkWinner(a));
    System.out.println(checkForWinner(a));
  }
  //=============================================================================
  
  // print methods
  public static void display(char[][]layout) {
    for(int i=0; i<layout.length; i++) {
      printBorder(layout.length);
      for(int j=0; j<layout[0].length; j++) {
        System.out.print("|"+layout[i][j]);
      }
      System.out.println("|");
    }
    printBorder(layout.length);
  }
  public static void printBorder(int n) {
    for(int i=0; i<2*n+1; i++) {
      if(i%2==0)
        System.out.print("+");
      else
        System.out.print("-");
    }
    System.out.println();
  }
  public static void print(char[]a){
    for(int i=0; i<a.length; i++) {
      System.out.print(a[i]+"\t");
    } System.out.println();
  }
}