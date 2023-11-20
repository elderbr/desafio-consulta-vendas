package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.ReportProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT new com.devsuperior.dsmeta.dto.SummaryDTO(obj.seller.name, SUM(obj.amount))"
            + " FROM Sale obj " +
            "WHERE obj.date BETWEEN :minDate AND :maxDate " +
            "GROUP BY obj.seller.name " +
            "ORDER BY SUM(obj.amount)"
    )
    List<SummaryDTO> searchSummary(LocalDate minDate, LocalDate maxDate);

    @Query(value = "SELECT new com.devsuperior.dsmeta.dto.SummaryDTO(obj.seller.name, SUM(obj.amount))"
            + " FROM Sale obj " +
            "WHERE obj.date BETWEEN :minDate AND :maxDate " +
            "GROUP BY obj.seller.name " +
            "ORDER BY SUM(obj.amount)",
            countQuery = "SELECT new com.devsuperior.dsmeta.dto.SummaryDTO(obj.seller.name, SUM(obj.amount))"
                    + " FROM Sale obj " +
                    "WHERE obj.date BETWEEN :minDate AND :maxDate " +
                    "GROUP BY obj.seller.name " +
                    "ORDER BY SUM(obj.amount)"
    )
    Page<SummaryDTO> searchSummaryPage(Pageable pageable, LocalDate minDate, LocalDate maxDate);

    @Query(nativeQuery = true, value =
            "SELECT sales.id AS id, sales.date AS date, sales.amount AS amount, seller.name AS sellerName " +
                    "FROM tb_sales sales " +
                    "INNER JOIN tb_seller seller ON seller.id = sales.seller_id " +
                    "WHERE LOWER(seller.name) LIKE LOWER(CONCAT('%', :name,'%')) " +
                    "AND sales.date BETWEEN :minDate AND :maxDate " +
                    "ORDER BY sales.date"
    )
    List<ReportProjection> searchReportNative(String name, LocalDate minDate, LocalDate maxDate);

    @Query(value =
            "SELECT obj FROM Sale obj JOIN FETCH obj.seller " +
                    "WHERE UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :name, '%')) " +
                    "AND obj.date BETWEEN :minDate AND :maxDate " +
                    "ORDER BY obj.date",
            countQuery = "SELECT COUNT(obj) FROM Sale obj JOIN obj.seller " +
                    "WHERE UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :name, '%')) " +
                    "AND obj.date BETWEEN :minDate AND :maxDate"
    )
    Page<Sale> searchReportPage(Pageable pageable, String name, LocalDate minDate, LocalDate maxDate);


}
