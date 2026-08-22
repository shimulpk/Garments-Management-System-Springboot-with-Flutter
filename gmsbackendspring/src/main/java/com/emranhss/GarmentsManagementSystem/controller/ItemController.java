package com.emranhss.GarmentsManagementSystem.controller;

import com.emranhss.GarmentsManagementSystem.dto.request.ItemRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.ItemResponseDto;
import com.emranhss.GarmentsManagementSystem.repository.ItemRepository;
import com.emranhss.GarmentsManagementSystem.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','STORE_MANAGER')")
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemResponseDto> create(@RequestBody ItemRequestDto dto){

        return ResponseEntity.ok(itemService.create(dto));
    }

    @PutMapping("/{id}")

    public ResponseEntity<ItemResponseDto> update(@RequestBody ItemRequestDto dto, @PathVariable Long id){

        return ResponseEntity.ok(itemService.update(id , dto));
    }

    @GetMapping("/{id}")

    public ResponseEntity<ItemResponseDto> getById(@PathVariable Long id){

        return ResponseEntity.ok(itemService.getById(id));
    }

@GetMapping

    public ResponseEntity<List<ItemResponseDto>> getAll() {

return ResponseEntity.ok(itemService.getAll());
}

@DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){

        itemService.delete(id);
        return ResponseEntity.ok("Delete Successfully");
}


}
