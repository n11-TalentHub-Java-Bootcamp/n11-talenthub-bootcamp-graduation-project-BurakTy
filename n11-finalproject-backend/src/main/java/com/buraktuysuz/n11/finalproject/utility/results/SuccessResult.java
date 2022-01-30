package com.buraktuysuz.n11.finalproject.utility.results;

public class SuccessResult extends Result{
		public SuccessResult() {
			super(true);
		} 
		
		public SuccessResult(String message) {
			super(true,message);
		} 
}
