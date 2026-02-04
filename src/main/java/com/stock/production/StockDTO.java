package com.stock.production;

import java.math.BigDecimal;

public class StockDTO {

    public String productCode;
    public String productName;
    public BigDecimal unitValue;

    public int stock;
    public int required;

    public StockDTO(
            String productCode,
            String productName,
            BigDecimal unitValue,
            int stock,
            int required
    ) {
        this.productCode = productCode;
        this.productName = productName;
        this.unitValue = unitValue;
        this.stock = stock;
        this.required = required;
    }
}
