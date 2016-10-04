package com.flp.ems.domain;

public class DateFormat {
	private int day;
	private int month;
	private int year;
	private String date_format = "";
	private String date;
	// private boolean formatDDMMYYYY = false;

	DateFormat(int month, int year) {
		this.date_format = "MMYYYY";
		this.month = month;
		this.year = year;
		this.date = String.valueOf(month) + "/" + String.valueOf(year);

	}

	DateFormat(int day, int month, int year) {
		this.date_format = "DDMMYYYY";
		this.day = day;
		this.month = month;
		this.year = year;
		this.date = String.valueOf(day) + "/" + String.valueOf(month) + "/" + String.valueOf(year);
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDate_format() {
		return date_format;
	}

	public void setDate_format(String date_format) {
		this.date_format = date_format;
	}

	void setDay(int day) {
		this.day = day;
	}

	int getDay() {
		return day;
	}

	void setMonth(int month) {
		this.month = month;
	}

	int getMonth() {
		return month;
	}

	void setYear(int year) {
		this.year = year;
	}

	int getYear() {
		return year;
	}
}
