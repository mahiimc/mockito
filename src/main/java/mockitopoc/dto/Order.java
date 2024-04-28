package mockitopoc.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Order {

	private Long id;
	private String productCode;
	private BigDecimal price;
	private int quantity;
}
