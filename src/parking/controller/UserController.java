package parking.controller;

import parking.controller.exception.AccessDeniedException;
import parking.model.bl.Logger;
import parking.model.bl.UserBl;
import parking.model.entity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserController {

    public static Map<String, String> save(String name, String family, String userName, String password, String nationalCode) {
        Map<String, String> result = new HashMap<>();
        try {
            if (Validator.checkName(name, 30) && Validator.checkName(family, 30)
                    && Validator.checkUserNameAndPassword(userName, 30) && Validator.checkUserNameAndPassword(password, 30)
                    && Validator.checkUserNameAndPassword(nationalCode, 30)) {
                User user =
                        User
                                .builder()
                                .name(name)
                                .family(family)
                                .userName(userName)
                                .password(password)
                                .nationalCode(nationalCode)
                                .status(true)
                                .deleted(false)
                                .build();
                UserBl.save(user);
                Logger.info("USER-SAVE", user.getUserName(), BaseController.user.getId());
                result.put("status", "true");
                result.put("message", user.toString() + " Saved");
            } else {
                Logger.error("USER-SAVE-ERROR", "INVALID DATA !", BaseController.user.getId());
                result.put("status", "false");
                result.put("message", "Invalid Data !");
            }
        } catch (Exception e) {
            Logger.error("USER-SAVE-ERROR", e.getMessage(), BaseController.user.getId());
            result.put("status", "false");
            result.put("message", e.getMessage());
        }
        return result;
    }

    public static Map<String, String> edit(Long id, String name, String family, String userName, String password, String nationalCode, boolean status, boolean deleted) {
        Map<String, String> result = new HashMap<>();
        try {
            if (Validator.checkName(name, 30) && Validator.checkName(family, 30)
                    && Validator.checkUserNameAndPassword(userName, 30) && Validator.checkUserNameAndPassword(password, 30)
                    && Validator.checkUserNameAndPassword(nationalCode, 30)) {
                User user =
                        User
                                .builder()
                                .id(id)
                                .name(name)
                                .family(family)
                                .userName(userName)
                                .password(password)
                                .nationalCode(nationalCode)
                                .status(status)
                                .deleted(deleted)
                                .build();

                UserBl.edit(user);
                Logger.info("USER-EDIT", user.getUserName(), BaseController.user.getId());
                result.put("status", "true");
                result.put("message", user.toString() + " Edited");
            } else {
                Logger.error("USER-EDIT-ERROR", "INVALID DATA !", BaseController.user.getId());
                result.put("status", "false");
                result.put("message", "Invalid Data !");
            }
        } catch (Exception e) {
            Logger.error("USER-EDIT-ERROR", e.getMessage(), BaseController.user.getId());
            result.put("status", "false");
            result.put("message", e.getMessage());
        }
        return result;
    }

    public static Map<String, String> remove(Long id) {
        Map<String, String> result = new HashMap<>();
        try {
            User user = UserBl.findById(id);
            UserBl.remove(id);
            Logger.info("USER-REMOVE", user.getUserName(), BaseController.user.getId());
            result.put("status", "true");
            result.put("message", user.toString() + " Removed");
        } catch (Exception e) {
            Logger.error("USER-REMOVE-ERROR", e.getMessage(), BaseController.user.getId());
            result.put("status", "false");
            result.put("message", e.getMessage());
        }
        return result;
    }

    public static Map<String, List<User>> findAll() {
        Map<String, List<User>> result = new HashMap<>();
        try {
            List<User> userList = UserBl.findAll();
            Logger.info("USER-FIND", "ALL", BaseController.user.getId());
            result.put("ok", userList);
        } catch (Exception e) {
            Logger.error("USER-FIND-ERROR", e.getMessage(), BaseController.user.getId());
            result.put(e.getMessage(), null);
        }
        return result;
    }

    public static Map<String, List<User>> findById(Long id) {
        Map<String, List<User>> result = new HashMap<>();
        try {
            User user = UserBl.findById(id);
            List<User> userList = new ArrayList<>();
            userList.add(user);
            Logger.info("USER-FIND-BY ID", user.getUserName(), BaseController.user.getId());
            result.put("ok", userList);
        } catch (Exception e) {
            Logger.error("USER-FIND-ERROR", e.getMessage(), BaseController.user.getId());
            result.put(e.getMessage(), null);
        }
        return result;
    }

    public static Map<String, List<User>> findByUserName(String userName) {
        Map<String, List<User>> result = new HashMap<>();
        try {
            User user = UserBl.findByUserName(userName);
            List<User> userList = new ArrayList<>();
            userList.add(user);
            Logger.info("USER-FIND-BY USERNAME", user.getUserName(), BaseController.user.getId());
            result.put("ok", userList);
        } catch (Exception e) {
            Logger.error("USER-FIND-ERROR", e.getMessage(), BaseController.user.getId());
            result.put(e.getMessage(), null);
        }
        return result;
    }

    public static Map<String, List<User>> findByName(String name) {
        Map<String, List<User>> result = new HashMap<>();
        try {
            List<User> userList = UserBl.findByName(name);
            Logger.info("USER-FIND", "BY NAME", BaseController.user.getId());
            result.put("ok", userList);
        } catch (Exception e) {
            Logger.error("USER-FIND-ERROR", e.getMessage(), BaseController.user.getId());
            result.put(e.getMessage(), null);
        }
        return result;
    }

    public static Map<String, List<User>> findByFamily(String family) {
        Map<String, List<User>> result = new HashMap<>();
        try {
            List<User> userList = UserBl.findByFamily(family);
            Logger.info("USER-FIND", "BY FAMILY", BaseController.user.getId());
            result.put("ok", userList);
        } catch (Exception e) {
            Logger.error("USER-FIND-ERROR", e.getMessage(), BaseController.user.getId());
            result.put(e.getMessage(), null);
        }
        return result;
    }

    public static User login(String userName, String password){
        try {
            User user = UserBl.findByUserNameAndPassword(userName,password);
            if (user != null){
                Logger.info("LOGIN", userName, UserBl.findByUserName(userName).getId());
                return user;
            } else {
                throw new AccessDeniedException();
            }
        }catch (Exception e){
            Logger.error("LOGIN-ERROR", userName + "/" + password, 1L);
        }
        return null;
    }

    public static Map<String, List<User>> findByStatus(boolean status) {
        Map<String, List<User>> result = new HashMap<>();
        try {
            List<User> userList = UserBl.findByStatus(status);
            Logger.info("USER-FIND", "BY STATUS", BaseController.user.getId());
            result.put("ok", userList);
        } catch (Exception e) {
            Logger.error("USER-FIND-ERROR", e.getMessage(), BaseController.user.getId());
            result.put(e.getMessage(), null);
        }
        return result;
    }

}
