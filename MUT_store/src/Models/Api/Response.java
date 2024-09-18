package Models.Api;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Classe que representa a resposta da API.
 * 
 * @author Aderito
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    private int statusCode;
    private int error_code;
    private String msg;
    private String token;
    private Object data;

    public Response() {
    }

    public Response(int statusCode,int error_code, String msg) {
        this.statusCode = statusCode;
        this.error_code = error_code;
        this.msg = msg;
    }

    public Response(int statusCode, int error_code, String msg, String token) {
        this.statusCode = statusCode;
        this.error_code = error_code;
        this.msg = msg;
        this.token = token;
    }

    public Response(int statusCode, int error_code, String msg, String token, Object data) {
        this.statusCode = statusCode;
        this.error_code = error_code;
        this.msg = msg;
        this.token = token;
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
