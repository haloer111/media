package com.aojing.redstore.media.exception;

import com.aojing.redstore.media.enums.ExceptionEnum;
import lombok.Getter;


@Getter
public class RedStoreException extends RuntimeException{

    private Integer code;

    public RedStoreException(ExceptionEnum resultEnum) {
        super(resultEnum.getMessage());

        this.code = resultEnum.getCode();
    }

    public RedStoreException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
