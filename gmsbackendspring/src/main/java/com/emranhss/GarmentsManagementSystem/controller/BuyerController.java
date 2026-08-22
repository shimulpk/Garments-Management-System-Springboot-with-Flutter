package com.emranhss.GarmentsManagementSystem.controller;

import com.emranhss.GarmentsManagementSystem.dto.request.BuyerRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.BuyerResponseDto;
import com.emranhss.GarmentsManagementSystem.service.BuyerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buyers")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','MERCHANDISER','CUTTING_MANAGER')")
public class BuyerController {

    private final BuyerService buyerService;

    @PostMapping
    public ResponseEntity<BuyerResponseDto> create(@RequestBody BuyerRequestDto request){
        return ResponseEntity.ok(buyerService.create(request));
    }


    @PutMapping("/{id}")
    public ResponseEntity<BuyerResponseDto> update(@PathVariable Long id , @RequestBody BuyerRequestDto request){
        return ResponseEntity.ok(buyerService.update(id,request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BuyerResponseDto> getById(@PathVariable Long id){
       return ResponseEntity.ok(buyerService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<BuyerResponseDto>> getAll(){
        return ResponseEntity.ok(buyerService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        buyerService.delete(id);
       return ResponseEntity.ok("Delete Successfully");
    }
}
