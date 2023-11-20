package com.devsuperior.dsmeta;

import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DsmetaApplication {

    @Autowired
    private SaleRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(DsmetaApplication.class, args);
    }

}
