package com.tutorial.racksemanalservice.controller;

import com.tutorial.racksemanalservice.entity.RackSemanalEntity;
import com.tutorial.racksemanalservice.service.RackSemanalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rack")
public class RackSemanalController {
    @Autowired
    RackSemanalService rackSemanalService;

    @GetMapping("/")
    public ResponseEntity<List<RackSemanalEntity>> listRack() {
        List<RackSemanalEntity> rack = rackSemanalService.getRack();
        return ResponseEntity.ok(rack);
    }


}
