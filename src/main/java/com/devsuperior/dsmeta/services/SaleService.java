package com.devsuperior.dsmeta.services;

import com.devsuperior.dsmeta.dto.ReportDTO;
import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SummaryDTO;
import com.devsuperior.dsmeta.dto.exceptions.DateValidationException;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class SaleService {

    @Autowired
    private SaleRepository repository;

    private DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private LocalDate min;
    private LocalDate max;

    @Transactional(readOnly = true)
    public SaleMinDTO findById(Long id) {
        Optional<Sale> result = repository.findById(id);
        Sale entity = result.get();
        return new SaleMinDTO(entity);
    }

    @Transactional(readOnly = true)
    public Page<SummaryDTO> findSummary(Pageable pageable, String minDate, String maxDate) {
        validDate(minDate, maxDate);
        return repository.searchSummaryPage(pageable, min, max);
    }

    @Transactional(readOnly = true)
    public Page<ReportDTO> findReport(Pageable pageable, String name, String minDate, String maxDate) {
        validDate(minDate, maxDate);
        Page<Sale> page = repository.searchReportPage(pageable, name, min, max);
        return page.map(x -> new ReportDTO(x));
    }

    private void validDate(String minDate, String maxDate) {
        try {
            if (!minDate.isBlank()) {
                // Tenta fazer o parse da String para LocalDate usando o formatter
                min = LocalDate.parse(minDate, df);
            } else {
                min = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault()).minusYears(1L);
            }
        } catch (Exception e) {
            throw new DateValidationException("A data minima está na formato errado espera-se ano-mês-dia (yyyy-MM-dd)"); // Se houver exceção, a data não é válida
        }

        try {
            if (!maxDate.isBlank()) {
                // Tenta fazer o parse da String para LocalDate usando o formatter
                max = LocalDate.parse(maxDate, df);
            } else {
                max = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
            }
        } catch (Exception e) {
            throw new DateValidationException("A data máxima está na formato errado espera-se ano-mês-dia (yyyy-MM-dd)"); // Se houver exceção, a data não é válida
        }

        if (minDate.isBlank() && !maxDate.isBlank()) {
            min = LocalDate.parse(maxDate).minusYears(1L);
        }
    }
}
