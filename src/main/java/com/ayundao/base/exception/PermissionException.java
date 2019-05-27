package com.ayundao.base.exception;

/**
 * @ClassName: PermissionException
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/21 5:24
 * @Description: 权限异常
 * @Version: V1.0
 */
public class PermissionException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public PermissionException() {
        super();
    }

    public PermissionException(String message) {
        super(message);
    }

    public PermissionException(Throwable cause) {
        super(cause);
    }

    public PermissionException(String message, Throwable cause) {
        super(message, cause);
    }


}
