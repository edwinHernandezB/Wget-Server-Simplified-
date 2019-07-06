import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Wget {

    public static void main(String[] args) {

        try{
            String line = null;
            FileReader file = new FileReader("urls");
            BufferedReader br = new BufferedReader(file);

            while((line = br.readLine()) != null) {
                System.out.println(line);
            }

        }catch (IOException ex){

            System.out.println("Cannot read file...");
        }
    }
}
