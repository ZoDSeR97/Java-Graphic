import java.util.*;

public class draft{
    
    public static void main (String []args){
        String[] array = {"a", "b", "c", "d", "e"};
        String[] strs = {"abc", "def", "ghi", "jkl", "mno"};
        List<String> list = new ArrayList<String>();
        list.addAll(Arrays.asList(array));
        list.addAll(Arrays.asList(strs));
        System.out.println(list.get(0));
    }
}
