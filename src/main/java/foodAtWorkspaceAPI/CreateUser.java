package foodAtWorkspaceAPI;
import PojoClasses.User;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import io.restassured.specification.RequestSpecification;
public class CreateUser {
    public User sendRequest(String email, String password, boolean isAdmin, boolean admin){
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setIsAdmin(isAdmin);
        user.setAdmin(admin);
        return user;
    }
    public User sendRequest(String email){
        User user = new User();
        user.setEmail(email);
        return user;
    }

}
