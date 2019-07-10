package de.hdm_stuttgart.mi.sd2.Exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IllegalFactoryArgument extends Exception{

    private static Logger log = LogManager.getLogger(IllegalFactoryArgument.class);

    public IllegalFactoryArgument( String message ) {
        super(message);
        log.error("A fatal error has occurred!");
    }
}
