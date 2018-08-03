package ro.msg.edu.jbugs.usermanagement.business.dto;

import ro.msg.edu.jbugs.usermanagement.persistance.entity.User;
import ro.msg.edu.jbugs.usermanagement.business.utils.Encryptor;

public class UserDTOHelper {


    public static UserDTO fromEntity(User user) {

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setUsername(user.getUsername());

        return userDTO;
    }

    public static User toEntity(UserDTO dto) {

        User user = new User();

        user.setId(dto.getId());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword(Encryptor.encrypt(dto.getPassword()));
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setUsername(dto.getUsername());

        return user;

    }
}
