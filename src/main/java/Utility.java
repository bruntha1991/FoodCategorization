import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Created by bruntha on 9/7/15.
 */
 public class Utility {
    public static void printArrayList(ArrayList<String> strings) {
        for (int i = 0; i < strings.size(); i++) {
            System.out.println(strings.get(i));
        }
    }

    public static void printHashTable(Hashtable hashtable) {
        Enumeration e = hashtable.keys();
        while (e.hasMoreElements()){
            Object key=e.nextElement();
            Object value=hashtable.get(key);
            System.out.println(key+" : "+value);
        }
    }
}
