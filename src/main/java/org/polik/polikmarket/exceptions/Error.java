package org.polik.polikmarket.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Polik on 6/10/2022
 */
@Data
@AllArgsConstructor
public class Error {
    private int code;
    private String message;
}
