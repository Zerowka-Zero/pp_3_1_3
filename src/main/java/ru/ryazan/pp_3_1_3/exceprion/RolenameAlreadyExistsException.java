package ru.ryazan.pp_3_1_3.exceprion;

public class RolenameAlreadyExistsException extends Exception {
    public RolenameAlreadyExistsException(String nameError) {
        super(nameError);
    }
}
