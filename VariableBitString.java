
public class VariableBitString implements VariableBitStringInterface {
  //adt to hold binary paths as integers with a given length.
  private int bitString;
  private int bitsUsed;

  public VariableBitString(int bitString, int bitsUsed) {
    this.bitString = bitString;
    this.bitsUsed = bitsUsed;
  }

  public int getBitString() {return this.bitString;}

  public int getBitsUsed() {return this.bitsUsed;}
}
