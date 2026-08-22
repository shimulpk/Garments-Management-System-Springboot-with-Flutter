package com.emranhss.GarmentsManagementSystem.serviceimp;

import com.emranhss.GarmentsManagementSystem.dto.mapper.StockMapper;
import com.emranhss.GarmentsManagementSystem.dto.response.StockResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.Stock;
import com.emranhss.GarmentsManagementSystem.repository.StockRepository;
import com.emranhss.GarmentsManagementSystem.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;


    @Override
    public List<StockResponseDto> getAll() {
        return stockRepository.findAll()
                .stream()
                .map(StockMapper::toDto)
                .toList();
    }

    @Override
    public StockResponseDto getById(Long id) {
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Stock not found with id: " + id));

        return StockMapper.toDto(stock);

    }

    @Override
    public List<StockResponseDto> getAvailableStocks() {
        return stockRepository
                .findByAvailableQuantityGreaterThan(0.0)
                .stream()
                .map(StockMapper::toDto)
                .toList();
    }
}
