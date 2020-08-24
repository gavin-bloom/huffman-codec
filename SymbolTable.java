//By Gavin Bloom
import java.util.NoSuchElementException;

public interface SymbolTable {

void addChar(char symbol);
int getFrequency(char symbol);
void setFrequency(char symbol, int frequency);
//void setPath(char symbol, VariableBitString path);
//VariableBitString getPath(char symbol);
String toString();
boolean isEmpty();
int size();

}
