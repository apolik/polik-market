package org.polik.polikmarket.exceptions;

/**
 * Created by Polik on 6/12/2022
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException() {
    }
}
