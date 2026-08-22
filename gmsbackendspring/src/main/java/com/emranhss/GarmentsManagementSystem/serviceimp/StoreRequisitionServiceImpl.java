package com.emranhss.GarmentsManagementSystem.serviceimp;

import com.emranhss.GarmentsManagementSystem.dto.mapper.StoreRequisitionMapper;
import com.emranhss.GarmentsManagementSystem.dto.request.StoreRequisitionItemRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.request.StoreRequisitionRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.StoreRequisitionResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.Item;
import com.emranhss.GarmentsManagementSystem.entity.StoreRequisition;
import com.emranhss.GarmentsManagementSystem.entity.StoreRequisitionItem;
import com.emranhss.GarmentsManagementSystem.enums.StoreRequisitionStatus;
import com.emranhss.GarmentsManagementSystem.repository.ItemRepository;
import com.emranhss.GarmentsManagementSystem.repository.PurchaseOrderRepository;
import com.emranhss.GarmentsManagementSystem.repository.StoreRequisitionRepository;
import com.emranhss.GarmentsManagementSystem.service.StoreRequisitionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreRequisitionServiceImpl implements StoreRequisitionService {

    private final StoreRequisitionRepository storeRequisitionRepository;
    private final ItemRepository itemRepository;

    private final PurchaseOrderRepository purchaseOrderRepository;


    @Override
    public StoreRequisitionResponseDto create(StoreRequisitionRequestDto request) {
        StoreRequisition requisition =
                new StoreRequisition();

        // PR Number Generate
        String prNo = "PR-" + System.currentTimeMillis();

        requisition.setPrNo(prNo);

        requisition.setRequisitionDate(
                request.getRequisitionDate());

        requisition.setRequestedBy(
                request.getRequestedBy());

        requisition.setDepartment(
                request.getDepartment());

        requisition.setRemarks(
                request.getRemarks());

        requisition.setStatus(
                StoreRequisitionStatus.PENDING);

        // Item List
        for (StoreRequisitionItemRequestDto itemDto
                : request.getItems()) {

            Item item = itemRepository.findById(
                            itemDto.getItemId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Item Not Found"));

            StoreRequisitionItem requisitionItem =
                    new StoreRequisitionItem();

            requisitionItem.setStoreRequisition(
                    requisition);

            requisitionItem.setItem(item);

            requisitionItem.setQuantity(
                    itemDto.getQuantity());



            requisition.getItems()
                    .add(requisitionItem);
        }

        StoreRequisition saved =
                storeRequisitionRepository
                        .save(requisition);

        return StoreRequisitionMapper
                .toDto(saved);
    }

    @Override
    public StoreRequisitionResponseDto update(Long id, StoreRequisitionRequestDto request) {
        StoreRequisition requisition =
                storeRequisitionRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Purchase Requisition Not Found"));

        requisition.setRequisitionDate(
                request.getRequisitionDate());

        requisition.setRequestedBy(
                request.getRequestedBy());

        requisition.setDepartment(
                request.getDepartment());

        requisition.setRemarks(
                request.getRemarks());

        // Remove Previous Items
        requisition.getItems().clear();

        // Add New Items
        for (StoreRequisitionItemRequestDto itemDto
                : request.getItems()) {

            Item item = itemRepository.findById(
                            itemDto.getItemId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Item Not Found"));

            StoreRequisitionItem requisitionItem =
                    new StoreRequisitionItem();

            requisitionItem.setStoreRequisition(
                    requisition);

            requisitionItem.setItem(item);

            requisitionItem.setQuantity(
                    itemDto.getQuantity());



            requisition.getItems().add(
                    requisitionItem);
        }

        StoreRequisition updated =
                storeRequisitionRepository.save(
                        requisition);

        return StoreRequisitionMapper.toDto(
                updated);
    }

    @Override
    public StoreRequisitionResponseDto getById(Long id) {
        StoreRequisition requisition =
                storeRequisitionRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Purchase Requisition Not Found"));

        return StoreRequisitionMapper.toDto(
                requisition);
    }

    @Override
    public List<StoreRequisitionResponseDto> getAll() {
        return storeRequisitionRepository.findAll()
                .stream()
                .map(StoreRequisitionMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        StoreRequisition requisition =
                storeRequisitionRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Purchase Requisition Not Found"));

        storeRequisitionRepository.delete(
                requisition);
    }

    @Override
    public List<StoreRequisitionResponseDto> getPendingRequisitions() {
        return storeRequisitionRepository
                .findByStatus(StoreRequisitionStatus.PENDING)
                .stream()
                .map(StoreRequisitionMapper::toDto)
                .toList();
    }

    @Override
    public StoreRequisitionResponseDto approve(Long id) {
        StoreRequisition requisition =
                storeRequisitionRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Store Requisition Not Found"));

        requisition.setStatus(
                StoreRequisitionStatus.APPROVED
        );

        StoreRequisition saved =
                storeRequisitionRepository.save(requisition);

        return StoreRequisitionMapper.toDto(saved);
    }

    @Override
    public StoreRequisitionResponseDto reject(Long id) {
        StoreRequisition requisition =
                storeRequisitionRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Store Requisition Not Found"));

        requisition.setStatus(
                StoreRequisitionStatus.REJECTED
        );

        StoreRequisition saved =
                storeRequisitionRepository.save(requisition);

        return StoreRequisitionMapper.toDto(saved);
    }

    @Override
    public List<StoreRequisitionResponseDto> getApprovedRequisitions() {
        return storeRequisitionRepository
                .findByStatus(StoreRequisitionStatus.APPROVED)
                .stream()
                .filter(requisition ->
                        !purchaseOrderRepository.existsByStoreRequisitionId(
                                requisition.getId()))
                .map(StoreRequisitionMapper::toDto)
                .toList();
    }
}
