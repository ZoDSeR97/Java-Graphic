import java.util.StringTokenizer;

public class strMani {
    public static void main(String[] args) {
        String content = "hello there jello";
        StringTokenizer tok = new StringTokenizer(content);
        while(tok.hasMoreTokens()) {
            String temp = tok.nextToken();
            if (temp.endsWith("lo")){
                System.out.println(temp);
            }
        }
    }
}
