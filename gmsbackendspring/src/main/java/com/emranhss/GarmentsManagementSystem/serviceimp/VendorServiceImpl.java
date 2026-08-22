package com.emranhss.GarmentsManagementSystem.serviceimp;

import com.emranhss.GarmentsManagementSystem.dto.mapper.VendorMapper;
import com.emranhss.GarmentsManagementSystem.dto.request.VendorRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.VendorResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.Vendor;
import com.emranhss.GarmentsManagementSystem.repository.VendorRepository;
import com.emranhss.GarmentsManagementSystem.service.VendorService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VendorServiceImpl implements VendorService {
    private final VendorRepository vendorRepository;


    @Override
    public VendorResponseDto create(VendorRequestDto request) {
        Vendor vendor =
                VendorMapper.toEntity(request);

        vendor.setActive(true);

        Vendor savedVendor =
                vendorRepository.save(vendor);

        return VendorMapper.toDto(savedVendor);
    }

    @Override
    public VendorResponseDto update(Long id, VendorRequestDto request) {
        Vendor vendor =
                vendorRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Vendor Not Found"));

        vendor.setCompanyName(
                request.getCompanyName());

        vendor.setContactPerson(
                request.getContactPerson());

        vendor.setPhone(
                request.getPhone());

        vendor.setAddress(
                request.getAddress());

        Vendor updatedVendor =
                vendorRepository.save(vendor);

        return VendorMapper.toDto(updatedVendor);
    }

    @Override
    public VendorResponseDto getById(Long id) {
        Vendor vendor =
                vendorRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Vendor Not Found"));

        return VendorMapper.toDto(vendor);
    }

    @Override
    public List<VendorResponseDto> getAll() {
        return vendorRepository.findAll()
                .stream()
                .map(VendorMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        Vendor vendor =
                vendorRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Vendor Not Found"));

        vendorRepository.delete(vendor);
    }
}
