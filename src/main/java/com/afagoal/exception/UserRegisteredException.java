package com.afagoal.exception;

import org.springframework.core.NestedRuntimeException;

/**
 * Created by BaoCai on 18/6/28.
 * Description:
 */
public class UserRegisteredException extends NestedRuntimeException {
    public UserRegisteredException(String msg) {
        super(msg);
    }

    public UserRegisteredException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
