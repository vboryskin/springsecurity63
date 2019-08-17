package com.alexcompany.springsecurity62.validator;

import com.alexcompany.springsecurity62.dto.UserDTO;
import com.alexcompany.springsecurity62.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserValidator implements ConstraintValidator<RegistrationValid, UserDTO> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(RegistrationValid constraintAnnotation) {

    }

    @Override
    public boolean isValid(UserDTO userDTO, ConstraintValidatorContext constraintValidatorContext) {
        if(userDTO.getEmail().isEmpty() ||
        userDTO.getUsername().isEmpty() ||
        userDTO.getPassword().isEmpty()){
            userDTO.setInvalidEmail(userDTO.getEmail().isEmpty()?"Email cannot be empty!":"");
            userDTO.setInvalidUsername(userDTO.getUsername().isEmpty()?"Username cannot be empty!":"");
            userDTO.setInvalidPassword(userDTO.getPassword().isEmpty()?"Password cannot be empty!":"");
            return false;
        }
        if(!userDTO.getPassword().equals(userDTO.getRepeatPassword())){
            userDTO.setPasswordsDoNotMatch("Passwords do not match!");
            return false;
        }
        if(userRepository.existsUserByUsernameOrEmail(userDTO.getUsername(), userDTO.getEmail())){
            userDTO.setAlreadyExistsError("User with this username or email already exists!");
            return false;
        }
        return true;
    }
}
