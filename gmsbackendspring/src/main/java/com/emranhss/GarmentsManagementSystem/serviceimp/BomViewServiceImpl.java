package com.emranhss.GarmentsManagementSystem.serviceimp;

import com.emranhss.GarmentsManagementSystem.dto.mapper.BomViewMapper;
import com.emranhss.GarmentsManagementSystem.dto.request.BomViewRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.BomViewResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.BomStyle;
import com.emranhss.GarmentsManagementSystem.entity.BomView;
import com.emranhss.GarmentsManagementSystem.repository.BomStyleRepository;
import com.emranhss.GarmentsManagementSystem.repository.BomViewRepository;
import com.emranhss.GarmentsManagementSystem.service.BomViewService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BomViewServiceImpl implements BomViewService {
    private final BomViewRepository bomViewRepository;
    private final BomStyleRepository bomStyleRepository;

    @Override
    public BomViewResponseDto create(BomViewRequestDto request) {
        BomStyle style =
                bomStyleRepository.findById(request.getStyleId())
                        .orElseThrow(() ->
                                new RuntimeException("Style Not Found"));

        BomView bomView =
                BomViewMapper.toEntity(request);

        bomView.setStyle(style);

        BigDecimal totalCost =
                request.getQuantity()
                        .multiply(request.getUnitPrice());

        bomView.setTotalCost(totalCost);

        BomView savedBomView =
                bomViewRepository.save(bomView);

        return BomViewMapper.toDto(savedBomView);
    }

    @Override
    public BomViewResponseDto update(Long id, BomViewRequestDto request) {
        BomView bomView =
                bomViewRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("BOM View Not Found"));

        BomStyle style =
                bomStyleRepository.findById(request.getStyleId())
                        .orElseThrow(() ->
                                new RuntimeException("Style Not Found"));

        bomView.setSerial(request.getSerial());
        bomView.setMaterialName(request.getMaterialName());
        bomView.setUnit(request.getUnit());
        bomView.setBaseFabric(request.getBaseFabric());
        bomView.setQuantity(request.getQuantity());
        bomView.setUnitPrice(request.getUnitPrice());

        bomView.setStyle(style);

        BigDecimal totalCost =
                request.getQuantity()
                        .multiply(request.getUnitPrice());

        bomView.setTotalCost(totalCost);

        BomView updatedBomView =
                bomViewRepository.save(bomView);

        return BomViewMapper.toDto(updatedBomView);
    }

    @Override
    public BomViewResponseDto getById(Long id) {
        BomView bomView =
                bomViewRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("BOM View Not Found"));

        return BomViewMapper.toDto(bomView);
    }

    @Override
    public List<BomViewResponseDto> getAll() {
        return bomViewRepository.findAll()
                .stream()
                .map(BomViewMapper::toDto)
                .toList();

    }

    @Override
    public List<BomViewResponseDto> getByStyle(Long styleId) {
        return bomViewRepository.findByStyleId(styleId)
                .stream()
                .map(BomViewMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        BomView bomView =
                bomViewRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("BOM View Not Found"));

        bomViewRepository.delete(bomView);

    }
}
