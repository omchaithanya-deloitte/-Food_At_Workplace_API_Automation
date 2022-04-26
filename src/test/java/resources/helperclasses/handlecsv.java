package resources.helperclasses;

import java.io.*;

public class handlecsv {

    /*This method is to write data to a csv file
     *@param file_path is the first parameter in writeData
     */
    public static void writeData(String file_path,String email,String password)
    {
        // first create file object for file placed at location
        // specified by filepath
        File file = new File(file_path);
        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file,true);

            StringBuilder sb = new StringBuilder();
            sb.append(email);
            sb.append(',');
            sb.append(password);
            sb.append('\n');
            outputfile.write(sb.toString());

            // closing writer connection
            outputfile.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String readFromLast(String filePath) throws IOException {
        String last="", line;
        try
        {
            File file = new File(filePath);
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null)
            {
                last = line;
            }
            return last;
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found! check the correct file path.");
        }
        return null;

    }

    public static void writeToken(String file_path,String token)
    {
        // first create file object for file placed at location
        // specified by filepath
        File file = new File(file_path);
        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file,true);
            StringBuilder sb = new StringBuilder();
            sb.append(token);
            sb.append('\n');
            outputfile.write(sb.toString());
            // closing writer connection
            outputfile.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
