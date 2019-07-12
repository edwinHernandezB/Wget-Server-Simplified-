import java.io.*;
import java.net.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Wget {

    public static void URLDownload(FileReader file){

        try{
            URL url;
            String line;

            // Create a BufferedReader object that read a complete line(FileReader read byte by byte)
            BufferedReader br = new BufferedReader(file);

            while((line = br.readLine()) != null)
            {

                System.out.println(line);
                url = new URL(line); //Create URL to open connection to the source in network
                URLConnection urlConnection = url.openConnection();//Connects a remote object that references a URL
                HttpURLConnection connection; //Create a URL extends connection with specifics features of HTTP

                //Compare instance of a specific class(this case of HttpURLConnection)
                if(urlConnection instanceof HttpURLConnection)
                {
                    connection = (HttpURLConnection) urlConnection;//Convert urlConnection in HttpURLConnection
                }
                else
                {
                    System.out.println("Please enter an HTTP URL.");
                    return;
                }

                /*Create InputStream reader from a connection using getInputStream, then create a Buffered Reader to
                read each URL to the file*/
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String urlString = "";
                String current;
                String fileDownload = url.getPath();//Get path/file name

                //Get the filename from the last Index of char '/' to the end (filename.extension)
                String fileName = fileDownload.substring(fileDownload.lastIndexOf('/')+1);
                //fileName = fileName.replaceFirst("[.][^.]+$", ""); //delete extension file

                System.out.println("Name: " + fileName);

                //Once obtained the file name, I get the extension file from the last index of '.' to the end
                String extension = fileDownload.substring(fileDownload.indexOf(".")+1);

                System.out.println("Extension: " + extension);

                FileOutputStream output;//Create a specific file output to save in disk the resource of the URL

                //Create new file output
                if ("".equals(extension))//if doesn't have extension means that it is only a URL like www.google.es without path
                {
                    output = new FileOutputStream("source.html");
                }
                else
                {
                    output = new FileOutputStream(fileName);
                }

                //Read the content of a path/file from the Buffered Reader that contains all the stream data file
                while((current = in.readLine()) != null)
                {
                    urlString += current + "\n";
                }

                /*if extension is a type image, save the image using javax.imageio and java.awt.image.BufferedImage
                else I use the urlString.getBytes to convert all the data string in byte array and finally write in the output file*/
                if ("png".equals(extension) || "jpg".equals(extension))
                {
                    BufferedImage image = null;
                    File outputFile = new File(fileName); //create file output
                    image = ImageIO.read(url); // read the image content from the URL
                    ImageIO.write(image,extension,outputFile); //write the data in a file with a name,extension and output file name
                }
                else
                {
                    byte b[] = urlString.getBytes();//convert string into byte array
                    output.write(b);
                }

            }
            file.close();//close the file that contains the URL's

        }
        catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public static void main(String[] args) {

        try{
            FileReader file = new FileReader("urls"); //Create a file reader object
            URLDownload(file);
        }
        catch (IOException ex)
        {
            System.out.println(ex);
        }
    }
}
