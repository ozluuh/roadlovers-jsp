package com.roadlovers.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Manufacturer {

	private Long id;

	private String description;
}
