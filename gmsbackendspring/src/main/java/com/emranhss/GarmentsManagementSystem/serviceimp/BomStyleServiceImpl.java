package com.emranhss.GarmentsManagementSystem.serviceimp;

import com.emranhss.GarmentsManagementSystem.dto.mapper.BomStyleMapper;
import com.emranhss.GarmentsManagementSystem.dto.request.BomStyleRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.BomStyleResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.BomStyle;
import com.emranhss.GarmentsManagementSystem.entity.Buyer;
import com.emranhss.GarmentsManagementSystem.repository.BomStyleRepository;
import com.emranhss.GarmentsManagementSystem.repository.BuyerRepository;
import com.emranhss.GarmentsManagementSystem.service.BomStyleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BomStyleServiceImpl implements BomStyleService {
    private final BomStyleRepository bomStyleRepository;
    private final BuyerRepository buyerRepository;

    @Override
    public BomStyleResponseDto create(BomStyleRequestDto request) {
        BomStyle style = BomStyleMapper.toEntity(request);

        Buyer buyer = buyerRepository.findById(request.getBuyerId())
                .orElseThrow(() ->
                        new RuntimeException("Buyer Not Found"));

        style.setBuyer(buyer);

        style.setActive(true);

        BomStyle savedStyle = bomStyleRepository.save(style);

        return BomStyleMapper.toDto(savedStyle);

    }


    @Override
    public BomStyleResponseDto update(Long id, BomStyleRequestDto request) {
        BomStyle style =
                bomStyleRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Style Not Found"));

        Buyer buyer = buyerRepository.findById(request.getBuyerId())
                .orElseThrow(() ->
                        new RuntimeException("Buyer Not Found"));

        style.setBuyer(buyer);
        style.setStyleCode(request.getStyleCode());
        style.setStyleName(request.getStyleName());
        style.setStyleType(request.getStyleType());
        style.setDescription(request.getDescription());
        style.setApprovalStatus(request.getApprovalStatus());
        style.setSizeSet(request.getSizeSet());

        BomStyle updatedStyle = bomStyleRepository.save(style);

        return BomStyleMapper.toDto(updatedStyle);
    }

    @Override
    public BomStyleResponseDto getById(Long id) {
        BomStyle style =
                bomStyleRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Style Not Found"));

        return BomStyleMapper.toDto(style);
    }

    @Override
    public List<BomStyleResponseDto> getAll() {
        return bomStyleRepository.findAll()
                .stream()
                .map(BomStyleMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        BomStyle style =
                bomStyleRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Style Not Found"));

        bomStyleRepository.delete(style);
    }

    @Override
    public List<BomStyleResponseDto> getByBuyer(Long buyerId) {
        return bomStyleRepository.findByBuyerId(buyerId)
                .stream()
                .map(BomStyleMapper::toDto)
                .toList();

    }
}
