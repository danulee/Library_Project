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
}