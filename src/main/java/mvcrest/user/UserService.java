package mvcrest.user;

import java.io.IOException;
import java.util.List;

/**
 * Servisni sloj se bavi svom "biznis logikom"
 */
public class UserService {

    public List<User> getUsers() throws IOException {
        return UserRepository.getUsers();
    }

    public int getNumberOfReservations(int id) throws IOException {
        return UserRepository.getNumberOfReservations(id);
    }

    public User getUserById(Integer id) throws IOException {
        return UserRepository.getUserById(id);
    }

    public User addUser(User user) throws IOException {
        return UserRepository.addUser(user);
    }

    public User findUser(String username, String password) throws IOException {
        return UserRepository.findUser(username, password);
    }

    public User findUserByUsername(String username) throws IOException {
        return UserRepository.findUserByUsername(username);
    }

    public User deleteUserById(Integer id) throws IOException {
        return UserRepository.deleteUserById(id);
    }

}
