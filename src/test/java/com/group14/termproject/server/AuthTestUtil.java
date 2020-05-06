package com.group14.termproject.server;

import com.group14.termproject.server.model.User;
import com.group14.termproject.server.model.UserDTO;

public interface AuthTestUtil {

    /**
     * @return a mock unregistered UserDTO object.
     */
    UserDTO generateUnregisteredUserDTO();

    /**
     * @return a mock registered UserDTO object.
     */
    UserDTO generateRegisteredUserDTO();

    /**
     * @return a mock registered User object.
     */
    User generateRegisteredUser();
}
