package com.example.ecommerce.dto.response;

public class CommonResponse {

	private int status;
	private String message;
	private Object body;
	

    public CommonResponse(int status, String message, Object body) {
        this.status = status;
        this.message = message;
        this.body = body;
    }

    public CommonResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.body = null;
    }

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

}
