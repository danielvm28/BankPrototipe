package model;

import java.time.LocalDate;

public class Activity {
	private String description;
	private LocalDate date;
	private double quantity;
	private String quantityStr;
	private ActivityType type;
	
	public Activity(String description, LocalDate date, double quantity, ActivityType type) {
		this.description = description;
		this.date = date;
		this.quantity = quantity;
		this.type = type;
		setQuantityStr("$" + String.valueOf(quantity));
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public double getQuantity() {
		return quantity;
	}
	
	public void setQuantitiy(double quantity) {
		this.quantity = quantity;
	}
	
	public ActivityType getType() {
		return type;
	}
	
	public void setType(ActivityType type) {
		this.type = type;
	}

	public String getQuantityStr() {
		return quantityStr;
	}

	public void setQuantityStr(String quantityStr) {
		this.quantityStr = quantityStr;
	}
	
	
}
