public class CharArrays {
  public static void main(String args[]) {
    char[][]a = {{'1','2','3'},{'4','5','6'},{'7','8','9'}};
    print(a);
    change(a, '0', 1, 2);
    print(a);
    ch2(a);
    print(a);
  }
  
  public static void change(char[][]a, char c, int i, int j){
    a[i][j] = c;
  }
  
  public static void ch2(char[][]a) {
    for(int i=0; i<a.length; i++) {
      for (int j=0; j<a[0].length; j++){
        if((int)a[i][j]%2==0)
          a[i][j] = '*';
        if(a[i][j]=='5')
          a[i][j]='A';
      }
    }
 
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  public static void print(char[]a){
    for(int i=0; i<a.length; i++) {
      System.out.print(a[i]+"\t");
    }
    System.out.println();
  }
  public static void print(char[][]a){
    for(int i=0; i<a.length; i++) {
      print(a[i]);
    }
    System.out.println();
  }
}