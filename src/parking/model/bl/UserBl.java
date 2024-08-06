package parking.model.bl;

import parking.controller.exception.AccessDeniedException;
import parking.controller.exception.DuplicateException;
import parking.controller.exception.NoContentException;
import parking.model.da.UserDa;
import parking.model.entity.User;

import java.util.List;

public class UserBl {

    public static User save(User user) throws Exception{
        try(UserDa userDa = new UserDa()) {
            if (userDa.findByUserName(user.getUserName()) == null){
                return userDa.save(user);
            }
            throw new DuplicateException("Duplicate username !");
        }
    }

    public static User edit(User user) throws Exception{
        try(UserDa userDa = new UserDa()) {
            if (userDa.findById(user.getId()) != null){
                return userDa.edit(user);
            }
            throw new NoContentException("Invalid user ID !");
        }
    }

    public static User remove(Long id) throws Exception{
        try(UserDa userDa = new UserDa()) {
            User user = userDa.findById(id);
            if (user != null){
                userDa.remove(id);
                return user;
            }
            throw new NoContentException("Invalid user ID !");
        }
    }

    public static List<User> findAll() throws Exception{
        try(UserDa userDa = new UserDa()) {
            List<User> userList = userDa.findAll();
            if (!userList.isEmpty()){
                return userList;
            }
            throw new NoContentException("There is no user !");
        }
    }

    public static User findById(Long id) throws Exception{
        try(UserDa userDa = new UserDa()) {
            User user = userDa.findById(id);
            if (user != null){
                return user;
            }
            throw new NoContentException("Invalid user ID !");
        }
    }

    public static User findByUserName(String userName) throws Exception{
        try(UserDa userDa = new UserDa()) {
            User user = userDa.findByUserName(userName);
            if (user != null){
                return user;
            }
            throw new NoContentException("Invalid username !");
        }
    }

    public static List<User> findByName(String name) throws Exception{
        try(UserDa userDa = new UserDa()) {
            List<User> userList = userDa.findByName(name);
            if (!userList.isEmpty()){
                return userList;
            }
            throw new NoContentException("There is no user !");
        }
    }

    public static List<User> findByFamily(String family) throws Exception{
        try(UserDa userDa = new UserDa()) {
            List<User> userList = userDa.findByFamily(family);
            if (!userList.isEmpty()){
                return userList;
            }
            throw new NoContentException("There is no user !");
        }
    }

    public static User findByUserNameAndPassword(String userName, String password) throws Exception{
        try(UserDa userDa = new UserDa()) {
            User user = userDa.findByUserNameAndPassword(userName, password);
            if (user != null){
                return user;
            }
            throw new AccessDeniedException("Access Denied !");
        }
    }

    public static List<User> findByStatus(boolean status) throws Exception{
        try(UserDa userDa = new UserDa()) {
            List<User> userList = userDa.findByStatus(status);
            if (!userList.isEmpty()){
                return userList;
            }
            throw new NoContentException("There is no user !");
        }
    }

}
