package com.emranhss.GarmentsManagementSystem.serviceimp;

import com.emranhss.GarmentsManagementSystem.dto.mapper.ItemMapper;
import com.emranhss.GarmentsManagementSystem.dto.request.ItemRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.ItemResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.Item;
import com.emranhss.GarmentsManagementSystem.repository.ItemRepository;
import com.emranhss.GarmentsManagementSystem.service.ItemService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;


    @Override
    public ItemResponseDto create(ItemRequestDto request) {
        Item item = ItemMapper.toEntity(request);

        item.setActive(true);

        Item saveItem = itemRepository.save(item);

return ItemMapper.toDto(item);



    }

    @Override
    public ItemResponseDto update(Long id, ItemRequestDto request) {
       Item item = itemRepository.findById(id).orElseThrow(() -> new RuntimeException("Item not found by id") );
       item.setItemName(request.getItemName());
       item.setCategory(request.getCategory());
       item.setUnit(request.getUnit());

       Item updateItem = itemRepository.save(item);
      return ItemMapper.toDto(updateItem);
    }

    @Override
    public ItemResponseDto getById(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(()-> new RuntimeException("Item not found"));

       return ItemMapper.toDto(item);

    }

    @Override
    public List<ItemResponseDto> getAll() {
       return itemRepository.findAll().stream().map(ItemMapper::toDto).toList();
    }

    @Override
    public void delete(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(()-> new RuntimeException("Item not found"));

         itemRepository.deleteById(id);

    }
}
