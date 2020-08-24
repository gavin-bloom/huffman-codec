//By Gavin Bloom
//For use with my include Huff.java
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import edu.princeton.cs.algs4.BinaryIn;
import edu.princeton.cs.algs4.BinaryOut;
import java.util.*;

public class Puff implements PuffInterface {

  private SymbolTableC symbolTable;
  private FileIO io;
  private int N; //number of symbols gotten from document symbol table
  private int words = 0; //counter for the total amount of characters that should
                        //be in the output

  public Puff(String fname) {
    this.io = new FileIOC();
    this.symbolTable = new SymbolTableC();
    BinaryIn inputFile = this.io.openBinaryInputFile(fname);
    if (inputFile.readInt(16) != 3008){ //checks if file is signed
      //inputFile.close();
      System.out.println("Not valid file");
      return;
    }
    this.N = inputFile.readInt(); //gets number of chars from input
    int i;
    //reads and fills the puff symbol table
    for(i=0; i < this.N; i++) {
      char nextChar = (char) inputFile.readChar(8);
      int newFreq = inputFile.readInt(32);
      this.symbolTable.addChar(nextChar);
      this.symbolTable.setFrequency(nextChar, newFreq);
      words = words + newFreq; //increases max "word" count
    }
    PriorityQueue HuffPQ = new PriorityQueue<HuffTreeC>();
    String symbolString =
      this.symbolTable.toString();
    int j;
    //fills the minPQ with the nodes
    for(j = 0; j <symbolString.length(); j++) {
      char newChar = symbolString.charAt(j);
      int frequency = this.symbolTable.getFrequency(newChar);
      HuffPQ.add(new HuffTreeC(newChar, frequency, null, null));
    }
    //collapses the minPQ to one huff tree
    while(HuffPQ.size() > 1) {
      HuffTreeC t1 = (HuffTreeC) HuffPQ.poll();
      HuffTreeC t2 = (HuffTreeC) HuffPQ.poll();
      HuffPQ.add(new HuffTreeC('\0', -1, t1, t2));
    }
    //writes the output file
    HuffTreeC myTree = (HuffTreeC) HuffPQ.poll();
    FileWriter outputFile = this.io.openOutputFile();
    int path;
    int k = 0;
    try {
      //till max count of characters had been printed
      while(words > k) {
        //walks down tree till reaching a leaf/symbol node
        HuffTreeC pathTree = myTree;
        while(pathTree.symbol == '\0') {
          path = inputFile.readInt(1);
          if(path == 1) {
            pathTree = pathTree.right;
          }
          else if(path == 0) {
            pathTree = pathTree.left;
          }
        }
        outputFile.write("" + pathTree.symbol);
        k++;
      }
      //inputFile.close();
      outputFile.close();
    }
    catch (IOException e){System.out.println("stop");}
  }

  public SymbolTableC getSymbolTable() {return this.symbolTable;}

  public static void main(String[] args){
    Puff test = new Puff(args[0]);
  }
}
