package com.emranhss.GarmentsManagementSystem.serviceimp;

import com.emranhss.GarmentsManagementSystem.dto.mapper.ProductionLineMapper;
import com.emranhss.GarmentsManagementSystem.dto.request.ProductionLineRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.ProductionLineResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.ProductionLine;
import com.emranhss.GarmentsManagementSystem.repository.ProductionLineRepository;
import com.emranhss.GarmentsManagementSystem.service.ProductionLineService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductionLineServiceImpl implements ProductionLineService {

    private final ProductionLineRepository productionLineRepository;

    @Override
    public ProductionLineResponseDto create(ProductionLineRequestDto request) {
        ProductionLine line = ProductionLineMapper.toEntity(request);
        ProductionLine saved = productionLineRepository.save(line);
        return ProductionLineMapper.toDto(saved);
    }

    @Override
    public ProductionLineResponseDto update(Long id, ProductionLineRequestDto request) {
        ProductionLine line = productionLineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Production Line Not Found"));

        // ডাটাবেজ থেকে পাওয়া অবজেক্টে নতুন ভ্যালু সেট করা হচ্ছে
        line.setLineId(request.getLineId());
        line.setCapacityPerDay(request.getCapacityPerDay());
        line.setSupervisor(request.getSupervisor());

        ProductionLine updated = productionLineRepository.save(line);
        return ProductionLineMapper.toDto(updated);
    }

    @Override
    public ProductionLineResponseDto getById(Long id) {
        ProductionLine line = productionLineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Production Line Not Found"));
        return ProductionLineMapper.toDto(line);
    }

    @Override
    public List<ProductionLineResponseDto> getAll() {
        return productionLineRepository.findAll()
                .stream()
                .map(ProductionLineMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
// ডিলিট করার আগে চেক করে নেওয়া ভালো যে এই আইডি আসলেই এক্সিস্ট করে কিনা
        if (!productionLineRepository.existsById(id)) {
            throw new RuntimeException("Production Line Not Found");
        }
        productionLineRepository.deleteById(id);
    }
}
