package com.scraper.web.repository;

import com.scraper.web.entity.Laptop;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LaptopRepository extends JpaRepository<Laptop, Long> {

    Optional<Laptop> findByCode(String code);

}
