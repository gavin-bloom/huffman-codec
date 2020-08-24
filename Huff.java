//By Gavin Bloom
//For use with my included Puff.java
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import edu.princeton.cs.algs4.BinaryIn;
import edu.princeton.cs.algs4.BinaryOut;
import java.util.*;

public class Huff implements HuffInterface {

  private SymbolTableC symbolTable;
  private FileIO io;
  public static boolean DEBUG = false;

  public Huff(String fname) {
    this.io = new FileIOC();
    this.symbolTable = new SymbolTableC();
    FileReader inputFile = this.io.openInputFile(fname);
    int i;
    //creates symbol table from file with frequencys
    try {
      while((i=inputFile.read()) != -1) {
        char symbol = (char) i;
        this.symbolTable.addChar(symbol);
      }
      inputFile.close();
    }
    catch (IOException e){System.out.println("stop");}

    //creates minPQ for huff tree nodes
    PriorityQueue HuffPQ = new PriorityQueue<HuffTreeC>();
    String symbolString =
      this.symbolTable.toString();
      //this.symbolTable.toString(this.symbolTable.first);
    int j;
    //fills the minPQ with the nodes
    for(j = 0; j <symbolString.length(); j++) {
      char newChar = symbolString.charAt(j);
      int frequency = this.symbolTable.getFrequency(newChar);
      HuffPQ.add(new HuffTreeC(newChar, frequency, null, null));
    }
    //collapses nodes in the minPQ to one huff tree
    while(HuffPQ.size() > 1) {
      HuffTreeC t1 = (HuffTreeC) HuffPQ.poll();
      HuffTreeC t2 = (HuffTreeC) HuffPQ.poll();
      HuffPQ.add(new HuffTreeC('\0', -1, t1, t2));
    }
    //adds the variable bit string paths to respective nodes in symbol table
    HuffTreeC myTree = (HuffTreeC) HuffPQ.poll();
    stringWalk(myTree, "", 0);

    BinaryOut outputFile = this.io.openBinaryOutputFile();
    outputFile.write(3008, 16); //signs the output file
    outputFile.write(this.symbolTable.size(), 32); //writes size of symbol table

    //writes symbol table and frequencys into the output file
    int k;
    for (k = 0; k <symbolString.length(); k++) {
      char newChar =symbolString.charAt(k);
     outputFile.write(newChar, 8);
     outputFile.write(this.symbolTable.getFrequency(newChar), 32);
   }

   //writes binary paths of symbols into the output file
   int m;
   FileReader inputFile2nd = this.io.openInputFile(fname);
   try{
     while ((m = inputFile2nd.read()) != -1){
       outputFile.write(this.symbolTable.getPath((char) m).getBitString(),
       this.symbolTable.getPath((char) m).getBitsUsed());
     }
     inputFile2nd.close();
   }
   catch (IOException e){System.out.println("stop");}
   outputFile.close();
  }

  //iterates down thru huff tree to create binary paths to leaf nodes
  //adds the variable bit string to symbol table
  private void stringWalk(HuffTreeC tree, String bi, int length) {
    if (tree.left != null){
      stringWalk(tree.left, bi + "0", (length+1));
    }
    if (tree.right != null){
      stringWalk(tree.right, bi + "1", (length+1));
    }
    if (tree.symbol != '\0'){
      VariableBitString temp =
        new VariableBitString(Integer.parseInt(bi, 2), length);
      this.symbolTable.setPath(((char) tree.symbol), temp);
    }
  }

  public SymbolTableC getSymbolTable() { //returns symbol table created from file
    return this.symbolTable;
  }

  public static void main(String[] args) {
    Huff myHuff = new Huff(args[0]);
    /*
    SymbolTableC st = myHuff.getSymbolTable();
    System.out.format("" + st.size());
    System.out.format("" + st.toString());
    */

    //System.out.format("" + st.toString(st.first));
    //unit testing for miss.txt
    /*
    System.out.format(" ");
    System.out.format("m" + st.getPath('m').getBitString());
    System.out.format("" + st.getPath('m').getBitsUsed());
    System.out.format("i" + st.getPath('i').getBitString());
    System.out.format("" + st.getPath('i').getBitsUsed());
    System.out.format("s" + st.getPath('s').getBitString());
    System.out.format("" + st.getPath('s').getBitsUsed());
    System.out.format("p" + st.getPath('p').getBitString());
    System.out.format("" + st.getPath('p').getBitsUsed());
    */
  }

}
