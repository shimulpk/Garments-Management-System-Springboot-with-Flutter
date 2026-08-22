package com.emranhss.GarmentsManagementSystem.serviceimp;

import com.emranhss.GarmentsManagementSystem.dto.mapper.MaterialIssueMapper;
import com.emranhss.GarmentsManagementSystem.dto.request.MaterialIssueItemRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.request.MaterialIssueRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.MaterialIssueResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.Item;
import com.emranhss.GarmentsManagementSystem.entity.MaterialIssue;
import com.emranhss.GarmentsManagementSystem.entity.MaterialIssueItem;
import com.emranhss.GarmentsManagementSystem.entity.Stock;
import com.emranhss.GarmentsManagementSystem.enums.MaterialIssueStatus;
import com.emranhss.GarmentsManagementSystem.repository.ItemRepository;
import com.emranhss.GarmentsManagementSystem.repository.MaterialIssueRepository;
import com.emranhss.GarmentsManagementSystem.repository.StockRepository;
import com.emranhss.GarmentsManagementSystem.service.MaterialIssueService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MaterialIssueServiceImpl implements MaterialIssueService {

    private final MaterialIssueRepository materialIssueRepository;
    private final ItemRepository itemRepository;
    private final StockRepository stockRepository;


    @Override
    public MaterialIssueResponseDto create(MaterialIssueRequestDto requestDto) {
        MaterialIssue materialIssue = new MaterialIssue();

        materialIssue.setIssueNo(generateIssueNo());
        materialIssue.setIssueDate(requestDto.getIssueDate());
        materialIssue.setDepartment(requestDto.getDepartment());
        materialIssue.setRequestedBy(requestDto.getRequestedBy());
        materialIssue.setRemarks(requestDto.getRemarks());
        materialIssue.setStatus(MaterialIssueStatus.ISSUED);

        materialIssue.setItems(
                buildMaterialIssueItems(
                        materialIssue,
                        requestDto.getItems()
                )
        );

        MaterialIssue saved =
                materialIssueRepository.save(materialIssue);

        return MaterialIssueMapper.toDto(saved);

    }

    private List<MaterialIssueItem> buildMaterialIssueItems(
            MaterialIssue materialIssue,
            List<MaterialIssueItemRequestDto> requestItems) {

        List<MaterialIssueItem> items = new ArrayList<>();

        for (MaterialIssueItemRequestDto requestItem : requestItems) {

            Item item = itemRepository.findById(requestItem.getItemId())
                    .orElseThrow(() ->
                            new RuntimeException("Item not found."));

            Stock stock = stockRepository.findByItemId((item).getId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Stock not found for item : "
                                            + item.getItemName()));

            if ((stock).getAvailableQuantity() < requestItem.getQuantity()) {

                throw new RuntimeException(
                        "Insufficient stock for item : "
                                + item.getItemName());

            }

            stock.setAvailableQuantity(
                    stock.getAvailableQuantity() - requestItem.getQuantity());

            stockRepository.save(stock);

            MaterialIssueItem issueItem = new MaterialIssueItem();

            issueItem.setMaterialIssue(materialIssue);
            issueItem.setItem(item);
            issueItem.setQuantity(requestItem.getQuantity());

            items.add(issueItem);

        }

        return items;

    }

    // ============================================
    // Generate Issue Number
    // ============================================

    private String generateIssueNo() {

        Optional<MaterialIssue> lastIssue =
                materialIssueRepository.findTopByOrderByIdDesc();

        if (lastIssue.isEmpty()) {

            return "MI-00001";

        }

        String lastIssueNo = lastIssue.get().getIssueNo();

        int nextNumber = Integer.parseInt(lastIssueNo.substring(3)) + 1;

        return String.format("MI-%05d", nextNumber);

    }




    @Override
    public List<MaterialIssueResponseDto> getAll() {
        return materialIssueRepository.findAll()
                .stream()
                .map(MaterialIssueMapper::toDto)
                .toList();
    }

    @Override
    public MaterialIssueResponseDto getById(Long id) {
        MaterialIssue materialIssue =
                materialIssueRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Material Issue not found."));

        return MaterialIssueMapper.toDto(materialIssue);
    }
}
