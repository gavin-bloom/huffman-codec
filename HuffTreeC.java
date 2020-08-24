//By Gavin Bloom
public class HuffTreeC implements HuffTree {

  //these are the huff tree nodes
  HuffTreeC left;
  HuffTreeC right;
  int weight;
  char symbol;

  public HuffTreeC(char symbol, int weight, HuffTreeC left, HuffTreeC right) {
    if(symbol != '\0') { //creates leaf node if a symbol is given
      this.symbol = symbol;
      this.weight = weight;
      this.left = null;
      this.right = null;
    }
    else { //if no symbol given creates an interior node
      this.symbol = '\0';
      this.left = left;
      this.right = right;
      this.weight = this.left.getWeight() + this.right.getWeight();
    }
  }

  public int getWeight() {
    return this.weight;
  }

  //ties go to the calling object, giving it a lower priority in the minPQ
  public int compareTo(HuffTreeC other) {
    if(this.weight >= other.weight) return 1;
    else return -1;
  }

  public String toString() {
    StringBuilder builder = new StringBuilder();
    if (this.left != null) {
      builder.append(this.left.toString());
    }
    if (this.right != null) {
      builder.append(this.right.toString());
    }
    return builder.append(Character.toString(this.symbol)).toString();
  }

  //unit testing
  public static void main(String[] args) {
    HuffTreeC a = new HuffTreeC('a', 2, null, null);
    HuffTreeC b = new HuffTreeC('b', 1, null, null);
    HuffTreeC c = new HuffTreeC('c', 3, null, null);
    HuffTreeC ab = new HuffTreeC('\0', -1, b, a);
    HuffTreeC abc = new HuffTreeC('\0', -1, c, ab);
    System.out.format(abc.toString());
    System.out.format("" + a.getWeight());
    System.out.format("" + ab.getWeight());
    System.out.format("" + abc.getWeight());
  }

}
