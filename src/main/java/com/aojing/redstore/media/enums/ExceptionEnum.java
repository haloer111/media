package com.aojing.redstore.media.enums;

import lombok.Getter;

/**
 * 全局异常类枚举
 * 返回码说明:
 * 0[0-1] 0代表成功值,1代表失败值
 * 0[0-9] 代表异常的大类
 * 00[00-99] 代表异常的微服务项目
 * 000[000-999] 代表具体异常码
 * @author gexiao
 * @date 2018/12/5 9:57
 */
@Getter
public enum ExceptionEnum {
    SUCCESS(0001000, "成功"),

    PARAM_ERROR(1101001, "参数不正确"),

    PRODUCT_NOT_EXIST(1101002, "商品不存在"),

    PRODUCT_STOCK_ERROR(1101003, "商品库存不正确"),


    PRODUCT_STATUS_ERROR(1101004, "商品状态不正确"),

    LOGIN_FAIL(1101005, "登录失败, 登录信息不正确"),

    LOGOUT_SUCCESS(1101006, "登出成功"),

    UPLOAD_FAIL(1101006,"上传失败"),
    ;

    private Integer code;

    private String message;

    ExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
