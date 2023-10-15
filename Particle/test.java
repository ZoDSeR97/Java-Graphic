import java.io.File;

public class test {
    public static void main(String[] args) {
        File effectFolder = new File("./effect");
        File[] effectFiles = effectFolder.listFiles();
        for (int i = 0; i < effectFiles.length; i++){
            if (effectFiles[i].toString().contains(("smoke.png"))){
                System.out.println(effectFiles[i]);
            }
        }
    }
}
