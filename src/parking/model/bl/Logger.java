package parking.model.bl;


import parking.model.da.LogDa;
import parking.model.entity.Log;
import parking.model.entity.User;

import java.time.LocalDateTime;

public class Logger {

    public static void info(String action, String data, Long userId){
        try(LogDa logDa = new LogDa()) {
            Log log =
                    Log
                            .builder()
                            .action(action)
                            .data(data)
                            .user(UserBl.findById(userId))
                            .logTimeStamp(LocalDateTime.now())
                            .build();
            logDa.save(log);
            System.out.printf("%s [%s]:[%s] - By userId %s %n", LocalDateTime.now(), action, data, userId);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void error(String action, String data, Long userId){
        try(LogDa logDa = new LogDa()) {
            Log log =
                    Log
                            .builder()
                            .action(action)
                            .data(data)
                            .user(UserBl.findById(userId))
                            .logTimeStamp(LocalDateTime.now())
                            .build();
            logDa.save(log);
            System.err.printf("%s [%s]:[%s] - By userId %s %n", LocalDateTime.now(), action, data, userId);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
