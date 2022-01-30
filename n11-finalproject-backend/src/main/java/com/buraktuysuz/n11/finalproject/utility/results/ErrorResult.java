package com.buraktuysuz.n11.finalproject.utility.results;

public class ErrorResult extends Result{
	public ErrorResult() {
		super(false);
	} 
	
	public ErrorResult(String message) {
		super(false,message);
	} 
}