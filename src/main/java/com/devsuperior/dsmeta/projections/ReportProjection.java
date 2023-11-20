package com.devsuperior.dsmeta.projections;

import java.time.LocalDate;

public interface ReportProjection {
    Long getId();
    LocalDate getDate();
    double getAmount();
    String getSellerName();
}
