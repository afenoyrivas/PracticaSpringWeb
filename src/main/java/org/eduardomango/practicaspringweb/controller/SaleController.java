package org.eduardomango.practicaspringweb.controller;

import org.eduardomango.practicaspringweb.model.dto.SaleRequestDto;
import org.eduardomango.practicaspringweb.model.dto.SaleResponseDto;
import org.eduardomango.practicaspringweb.model.services.SaleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SaleController {
    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping
    public ResponseEntity<List<SaleResponseDto>> getAll(){
        List<SaleResponseDto> sales = saleService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(sales);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleResponseDto> getById(@PathVariable long id){
        SaleResponseDto saleResponse = saleService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(saleResponse);
    }

    @PostMapping
    public ResponseEntity<SaleResponseDto> createSale(@RequestBody SaleRequestDto saleRequest){
        SaleResponseDto saleResponse = saleService.save(saleRequest);
        return ResponseEntity.status(HttpStatus.OK).body(saleResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleResponseDto> updateSale(@PathVariable long id, @RequestBody SaleRequestDto saleRequest){
        SaleResponseDto saleResponse = saleService.update(id, saleRequest);
        return ResponseEntity.status(HttpStatus.OK).body(saleResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SaleResponseDto> delete(@PathVariable long id){
        saleService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
