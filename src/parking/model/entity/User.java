package parking.model.entity;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder

public class User {
    private Long id;
    private String name;
    private String family;
    private String userName;
    private String password;
    private String nationalCode;
    private boolean status;
    private boolean deleted;

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}


