package com.elecxa.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FiltersDTO {

	private double min;
	private double max;
	private List<String> brands;
	private double rating;
}
