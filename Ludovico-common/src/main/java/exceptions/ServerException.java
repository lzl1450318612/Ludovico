package exceptions;

import enums.ExceptionEnum;

/**
 * @author lizilin
 */
public class ServerException extends RuntimeException {

    private ExceptionEnum exceptionEnum;

    private Object data;


    public ServerException(ExceptionEnum exceptionEnum) {
        this.exceptionEnum = exceptionEnum;
    }


    public ServerException(ExceptionEnum exceptionEnum, Object data) {
        this.exceptionEnum = exceptionEnum;
        this.data = data;
    }

    @Override
    public String toString() {
        return "ServiceException(exceptionEnum=" + this.exceptionEnum + ", data=" + this.data + ")";
    }
}
