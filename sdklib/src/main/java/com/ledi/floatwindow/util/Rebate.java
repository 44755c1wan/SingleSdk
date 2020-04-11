package com.ledi.floatwindow.util;

public class Rebate {
	public int min=0, max=0, rebate=0;
	public Rebate(int pMin, int pMax, int pRebate) {
		min = pMin;
		max = pMax;
		rebate = pRebate;
	}
	public Rebate() {
		// TODO Auto-generated constructor stub
	}

	public int getRebate(int money) {
		int i = (money >= min && max==0) ? rebate
				: (money >= min && money <= max) ? rebate : -1;
		return i;
	}
	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public int getRebate() {
		return rebate;
	}
	public void setRebate(int rebate) {
		this.rebate = rebate;
	}
	
	
}
