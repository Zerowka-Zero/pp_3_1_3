package ru.ryazan.pp_3_1_3.exceprion;

public class UsernameAlreadyExistsException extends Exception {
    public UsernameAlreadyExistsException(String nameError) {
        super(nameError);
    }
}
