package com.hexaware.assetmanagement.exception;



public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException(String error) {
		super(error);
	}
}