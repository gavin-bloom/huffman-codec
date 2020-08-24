
public interface HuffTree extends Comparable<HuffTreeC> {

  int getWeight();
  int compareTo(HuffTreeC other);
  String toString();
}
