import java.util.*;
import java.net.*;
import java.util.regex.Pattern;
import java.awt.image.BufferedImage;

public class htmlJava {
    public static void main(String[] args) {
        String content = null;
        //Pattern p = Pattern.compile(".*\\\"(.*)\\\".*");
        //BufferedImage image = null; 
        try {
            URLConnection connection =  new URL("https://www.artweb.com").openConnection();
            Scanner scanner = new Scanner(connection.getInputStream());
            scanner.useDelimiter("\\Z");
            content = scanner.next();
            scanner.close();
        }catch ( Exception ex ) {
            ex.printStackTrace();
        }
        String[] arr = content.split("\"");
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].endsWith("jpg")) {
                System.out.println(arr[i]);
            }
        }
    }   
}