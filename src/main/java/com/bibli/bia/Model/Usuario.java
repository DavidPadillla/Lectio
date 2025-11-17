package com.bibli.bia.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Document(collection = "usuarios")
@Data
@NoArgsConstructor
public class Usuario {

    @Id
    private String id; // En MongoDB el id es normalmente un ObjectId (String)

    private String username;
    private String password;
    private Set<String> roles;

    public Usuario(String id, String username, String password, Set<String> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}
