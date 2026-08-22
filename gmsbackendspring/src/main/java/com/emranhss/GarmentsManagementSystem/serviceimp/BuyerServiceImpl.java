package com.emranhss.GarmentsManagementSystem.serviceimp;

import com.emranhss.GarmentsManagementSystem.dto.mapper.BuyerMapper;
import com.emranhss.GarmentsManagementSystem.dto.request.BuyerContactRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.request.BuyerRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.BuyerResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.Buyer;
import com.emranhss.GarmentsManagementSystem.entity.BuyerContact;
import com.emranhss.GarmentsManagementSystem.repository.BuyerRepository;
import com.emranhss.GarmentsManagementSystem.service.BuyerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BuyerServiceImpl implements BuyerService {

    private final BuyerRepository buyerRepository;

    @Override
    public BuyerResponseDto create(BuyerRequestDto request) {
        Buyer buyer = BuyerMapper.toEntity(request);

        buyer.setActive(true);

        if (request.getContacts() != null) {

            List<BuyerContact> contacts =
                    request.getContacts()
                            .stream()
                            .map(this::buildContact)
                            .toList();

            contacts.forEach(contact ->
                    contact.setBuyer(buyer));

            buyer.setContacts(contacts);
        }

        Buyer savedBuyer =
                buyerRepository.save(buyer);

        return BuyerMapper.toDto(savedBuyer);
    }

    @Override
    public BuyerResponseDto update(Long id, BuyerRequestDto request) {

        Buyer buyer =
                buyerRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Buyer Not Found"));

        buyer.setBuyerCode(request.getBuyerCode());
        buyer.setBuyerName(request.getBuyerName());
        buyer.setCountry(request.getCountry());
        buyer.setAddress(request.getAddress());
        buyer.setWebsite(request.getWebsite());
        buyer.setCurrency(request.getCurrency());
        buyer.setPaymentTerms(request.getPaymentTerms());

        buyer.getContacts().clear();

        if (request.getContacts() != null) {

            List<BuyerContact> contacts =
                    request.getContacts()
                            .stream()
                            .map(this::buildContact)
                            .toList();

            contacts.forEach(contact ->
                    contact.setBuyer(buyer));

            buyer.getContacts().addAll(contacts);
        }

        Buyer updatedBuyer =
                buyerRepository.save(buyer);

        return BuyerMapper.toDto(updatedBuyer);
    }

    @Override
    public BuyerResponseDto getById(Long id) {

        Buyer buyer=buyerRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("ZBuyer Not Found"));
        return BuyerMapper.toDto(buyer);
    }

    @Override
    public List<BuyerResponseDto> getAll() {
        return buyerRepository.findAll()
                .stream().map(BuyerMapper::toDto).toList();
    }

    @Override
    public void delete(Long id) {
    Buyer buyer=buyerRepository.findById(id).orElseThrow(()->
            new RuntimeException("Buyer not found With this id"));
    buyerRepository.delete(buyer);
    }

    private BuyerContact buildContact(BuyerContactRequestDto dto){
        BuyerContact contact = new BuyerContact();
        contact.setContactName(dto.getContactName());
        contact.setDesignation(dto.getDesignation());
        contact.setEmail(dto.getEmail());
        contact.setPhone(dto.getPhone());
        return contact;
    }
}
