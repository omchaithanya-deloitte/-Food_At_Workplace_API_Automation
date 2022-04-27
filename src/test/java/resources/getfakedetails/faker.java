package resources.getfakedetails;
import com.github.javafaker.Faker;
import resources.helperclasses.Utils;
import resources.helperclasses.handlecsv;
import java.io.IOException;
public class faker {

    public static Faker faker = new Faker();
    public static String name;
    public static String password;
    public static String email;


    public static void generateFakeUserDetails(String filepath) throws IOException {
        name = faker.name().firstName();
        email = name + "@gmail.com";
        email = email.toLowerCase();
        password = name + randomNumber();
        handlecsv.writeData(Utils.getProperties(filepath),email,password);
    }

    /**This method is to generate random number*/
    public static int randomNumber() {
        int min = 0;
        int max = 1000;
        int randomNumber = (int) (Math.random() * (max - min + 1) + min);
        return randomNumber;
    }

}
