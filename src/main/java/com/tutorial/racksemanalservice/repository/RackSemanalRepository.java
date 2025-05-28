package com.tutorial.racksemanalservice.repository;
import com.tutorial.racksemanalservice.entity.RackSemanalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RackSemanalRepository extends JpaRepository<RackSemanalEntity, Long> {

    public RackSemanalEntity findByRut(String rut);
    List<RackSemanalEntity> findByName(String category);

    @Query(value = "SELECT * FROM client WHERE client.rut = :rut", nativeQuery = true)
    RackSemanalEntity findByRutNativeQuery(@Param("rut") String rut);
}

