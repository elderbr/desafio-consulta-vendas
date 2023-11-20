package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.SummaryMinProjetion;

public class SummaryDTO {
    private String sellerName;
    private double total;

    public SummaryDTO(String sellerName, double total) {
        this.sellerName = sellerName;
        this.total = total;
    }

    public SummaryDTO(Sale entity) {
        sellerName = entity.getSeller().getName();
        total = entity.getAmount();
    }

    public SummaryDTO(SummaryMinProjetion entity) {
        sellerName = entity.getName();
        total = entity.getAmount();
    }

    public String getSellerName() {
        return sellerName;
    }

    public double getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "SummaryDTO{" +
                "sellerName='" + sellerName + '\'' +
                ", total=" + total +
                '}';
    }
}
