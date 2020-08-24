//By Gavin Bloom
import java.util.NoSuchElementException;

public class SymbolTableC implements SymbolTable {

  public Node first; //pointer to first node
  private int N; //number of elements in the table
  private String symbolString = "";

  //nodes to hold symbol, frequency, and a variable bit string path
  private class Node {
    Node next;
    char symbol;
    int frequency;
    VariableBitString path;

    private Node(char symbol) {
      this.next = null;
      this.symbol = symbol;
      this.frequency = 1;
      this.path = null;
    }

  }

  public SymbolTableC(){
    N = 0;
    first = null;
  }

  public void addChar(char symbol){
    if(isEmpty()) { //sets first
      Node newFirst = new Node(symbol);
      first = newFirst;
      N = 1;
      this.symbolString = this.symbolString + Character.toString(symbol);
    }
    else { //searches table for symbol and either adds it if it does not exist
      // in the table or increases its frequency if it already does
      Node insertNode = first;
      while(true) {
        if(insertNode.symbol == symbol){
          insertNode.frequency++;
          return;
        }
        if(insertNode.next == null) {
          insertNode.next = new Node(symbol);
          N++;
          this.symbolString = this.symbolString + Character.toString(symbol);
          return;
        }
        insertNode = insertNode.next;
      }
    }
  }

  //finds symbol in table and returns its frequency
  public int getFrequency(char symbol) {
    if(isEmpty()){
      throw new ArrayIndexOutOfBoundsException("No symbols in table");
    }
    else {
      Node symbolNode = first;
      while(true) {
        if(symbolNode.symbol == symbol) {
          return symbolNode.frequency;
        }
        else if(symbolNode.next == null) {
          throw new NoSuchElementException("Symbol is not in the table");
        }
        else {
          symbolNode = symbolNode.next;
        }
      }
    }
  }

  public void setFrequency(char symbol, int frequency) {
    if(isEmpty()){
      throw new ArrayIndexOutOfBoundsException("No symbols in table");
    }
    else {
      Node symbolNode = first;
      while(true) {
        if(symbolNode.symbol == symbol) {
          symbolNode.frequency = frequency;
          return;
        }
        else if(symbolNode.next == null) {
          throw new NoSuchElementException("Symbol is not in the table");
        }
        else {
          symbolNode = symbolNode.next;
        }
      }
    }
  }

  //assigns a variable bit string path to a symbol in the table

  public void setPath(char symbol, VariableBitString path) {
    if(isEmpty()){
      throw new ArrayIndexOutOfBoundsException("No symbols in table");
    }
    else {
      Node symbolNode = first;
      while(true) {
        if(symbolNode.symbol == symbol) {
          symbolNode.path = path;
          break;
        }
        else if(symbolNode.next == null) {
          throw new NoSuchElementException("Symbol is not in the table");
        }
        else {
          symbolNode = symbolNode.next;
        }
      }
    }
  }

  //returns VariableBitString path of a symbol
  public VariableBitString getPath(char symbol) {
    if(isEmpty()){
      throw new ArrayIndexOutOfBoundsException("No symbols in table");
    }
    else {
      Node symbolNode = first;
      while(true) {
        if(symbolNode.symbol == symbol) {
          return symbolNode.path;
        }
        else if(symbolNode.next == null) {
          throw new NoSuchElementException("Symbol is not in the table");
        }
        else {
          symbolNode = symbolNode.next;
        }
      }
    }
  }


  /*
  public String toString(Node node) {
    if (first == null) {return "";}
    StringBuilder builder = new StringBuilder();
    if (node.next != null) {
      builder.append(toString(node.next));
    }
    return builder.append(Character.toString(node.symbol)).toString();
    }
  */

  //changed toString to maintain order of symbols
  public String toString() {return this.symbolString;}

  public boolean isEmpty() {
    return(N == 0);
  }

  public int size() {
    return N;
  }

  //unit testing
  public static void main(String[] args) {
    SymbolTableC mySymbolTable = new SymbolTableC();
    mySymbolTable.addChar('a');
    mySymbolTable.addChar('b');
    mySymbolTable.addChar('c');
    mySymbolTable.addChar('b');
    mySymbolTable.addChar('b');
    mySymbolTable.addChar('a');
    mySymbolTable.addChar('c');
    mySymbolTable.addChar('a');
    mySymbolTable.addChar('d');
    mySymbolTable.addChar('b');
    System.out.format("" + mySymbolTable.getFrequency('a'));
    System.out.format("" + mySymbolTable.getFrequency('b'));
    System.out.format("" + mySymbolTable.getFrequency('c'));
    System.out.format("" + mySymbolTable.getFrequency('d'));
    System.out.format(mySymbolTable.toString());
    //System.out.format(mySymbolTable.toString(mySymbolTable.first));
  }
}
