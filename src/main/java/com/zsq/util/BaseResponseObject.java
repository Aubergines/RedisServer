package com.zsq.util;

import java.util.HashMap;
import java.util.Map;

public class BaseResponseObject {

	private Boolean responseStatus;
	private String responseCode;
	private String responseMessage;
	private Long responsePk = 0L;

	private Map responseData;

	public Map getResponseData() {
		return responseData;
	}

	public void setResponseData(Map responseData) {
		this.responseData = responseData;
	}

	public Long getResponsePk() {
		return responsePk;
	}

	public void setResponsePk(Long responsePk) {
		this.responsePk = responsePk;
	}

	public BaseResponseObject() {

	}

	public BaseResponseObject(Boolean responseStatus, String responseCode,
                              String responseMessage) {
		this.responseStatus = responseStatus;
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
		if (responseData == null) {
			responseData = new HashMap();
		}
	}

	public Boolean getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(Boolean responseStatus) {
		this.responseStatus = responseStatus;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

}
