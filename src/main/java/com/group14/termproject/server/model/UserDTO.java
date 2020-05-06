package com.group14.termproject.server.model;

import com.group14.termproject.server.annotations.ValidPassword;
import lombok.Getter;
import lombok.Setter;

public class UserDTO {
    @Getter
    @Setter
    private String username;
    @ValidPassword
    @Getter
    @Setter
    private String password;

}
