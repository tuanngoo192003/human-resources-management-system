package com.lab.lib.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T>  {
	
	private boolean status;
	private Object message;
	
	@JsonIgnoreProperties
    @JsonInclude(JsonInclude.Include.NON_NULL)
	private T data;
	
	public static final String SUCCESS = "Success";
	public static final String FAILED = "Failed";
	
	public ApiResponse(boolean status, String message) {
		this.status = status;
		this.message = message;
	}
	
	public ApiResponse(boolean status, T data) {
        this.status = status;
        if (this.status) {
            this.message = SUCCESS;
        } else {
            this.message = FAILED;
        }
        this.data = data;
    }
	
	public ApiResponse(boolean status) {
		this.status = status;
		if (status) {
			this.message = SUCCESS;
		} else {
			this.message = FAILED;
		}
	}
}
