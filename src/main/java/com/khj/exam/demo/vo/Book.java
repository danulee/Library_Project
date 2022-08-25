package com.khj.exam.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
	private int id;
	private String regDate;
	private String title;
	private String writer;
	private String publisher;
	private int rental;
	private String rentalDate;
	private String returnDate;
	private String userId;
	
	public String getForPrintType1RegDate() {
		return regDate.substring(2, 16).replace(" ", "<br>");
	}
	public String getForPrintType1RentalDate() {
		return rentalDate.substring(2, 10);
	}
	public String getForPrintType1ReturnDate() {
		return returnDate.substring(2, 10);
	}
}