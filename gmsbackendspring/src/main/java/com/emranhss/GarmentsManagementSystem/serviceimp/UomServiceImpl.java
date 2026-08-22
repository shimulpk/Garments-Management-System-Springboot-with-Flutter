package com.emranhss.GarmentsManagementSystem.serviceimp;

import com.emranhss.GarmentsManagementSystem.dto.mapper.UomMapper;
import com.emranhss.GarmentsManagementSystem.dto.request.UomRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.UomResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.Uom;
import com.emranhss.GarmentsManagementSystem.repository.UomRepository;
import com.emranhss.GarmentsManagementSystem.service.UomService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UomServiceImpl implements UomService {

    private final UomRepository uomRepository;

    @Override
    public UomResponseDto create(UomRequestDto request) {
        Uom uom = UomMapper.toEntity(request);

        uom.setTotalBaseFabric(
                calculateTotalBaseFabric(request)
        );

        Uom savedUom =
                uomRepository.save(uom);

        return UomMapper.toDto(savedUom);
    }

    @Override
    public UomResponseDto update(Long id, UomRequestDto request) {
        Uom uom =
                uomRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("UOM Not Found"));

        uom.setProductName(request.getProductName());
        uom.setSize(request.getSize());
        uom.setBody(request.getBody());
        uom.setSleeve(request.getSleeve());
        uom.setPocket(request.getPocket());
        uom.setWastage(request.getWastage());
        uom.setShrinkage(request.getShrinkage());

        uom.setTotalBaseFabric(
                calculateTotalBaseFabric(request)
        );

        Uom updatedUom =
                uomRepository.save(uom);

        return UomMapper.toDto(updatedUom);
    }

    @Override
    public UomResponseDto getById(Long id) {
        Uom uom =
                uomRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("UOM Not Found"));

        return UomMapper.toDto(uom);
    }

    @Override
    public List<UomResponseDto> getAll() {
        return uomRepository.findAll()
                .stream()
                .map(UomMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        Uom uom =
                uomRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("UOM Not Found"));

        uomRepository.delete(uom);
    }

    private BigDecimal calculateTotalBaseFabric(
            UomRequestDto request) {

        BigDecimal body =
                request.getBody() == null
                        ? BigDecimal.ZERO
                        : request.getBody();

        BigDecimal sleeve =
                request.getSleeve() == null
                        ? BigDecimal.ZERO
                        : request.getSleeve();

        BigDecimal pocket =
                request.getPocket() == null
                        ? BigDecimal.ZERO
                        : request.getPocket();

        BigDecimal wastage =
                request.getWastage() == null
                        ? BigDecimal.ZERO
                        : request.getWastage();

        BigDecimal shrinkage =
                request.getShrinkage() == null
                        ? BigDecimal.ZERO
                        : request.getShrinkage();

        BigDecimal baseFabric =
                body.add(sleeve).add(pocket);

        BigDecimal wastageFactor =
                BigDecimal.ONE.add(
                        wastage.divide(
                                BigDecimal.valueOf(100))
                );

        BigDecimal shrinkageFactor =
                BigDecimal.ONE.add(
                        shrinkage.divide(
                                BigDecimal.valueOf(100))
                );

        return baseFabric
                .multiply(wastageFactor)
                .multiply(shrinkageFactor);
    }
}
