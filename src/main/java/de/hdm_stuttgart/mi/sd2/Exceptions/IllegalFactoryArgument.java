package de.hdm_stuttgart.mi.sd2.Exceptions;

public class IllegalFactoryArgument extends Exception{
    public IllegalFactoryArgument() {

    }
    public IllegalFactoryArgument( String message ) {
        super(message);
    }
}
