package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.ReportProjection;

import java.time.LocalDate;

public class ReportDTO {

    private Long id;
    private LocalDate date;
    private double amount;
    private String sellerName;

    public ReportDTO(Long id, LocalDate date, double amount, String sellerName) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.sellerName = sellerName;
    }

    public ReportDTO(Sale entity) {
        id = entity.getId();
        date = entity.getDate();
        amount = entity.getAmount();
        sellerName = entity.getSeller().getName();
    }

    public ReportDTO(ReportProjection entity) {
        id = entity.getId();
        date = entity.getDate();
        amount = entity.getAmount();
        sellerName = entity.getSellerName();
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public String getSellerName() {
        return sellerName;
    }

    @Override
    public String toString() {
        return "ReportDTO{" +
                "id=" + id +
                ", date=" + date +
                ", amount=" + amount +
                ", sellerName='" + sellerName + '\'' +
                '}';
    }
}
