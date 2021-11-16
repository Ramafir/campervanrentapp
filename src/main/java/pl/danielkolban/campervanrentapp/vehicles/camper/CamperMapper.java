package pl.danielkolban.campervanrentapp.vehicles.camper;

import org.springframework.stereotype.Service;
import pl.danielkolban.campervanrentapp.vehicles.category.Category;
import pl.danielkolban.campervanrentapp.vehicles.category.CategoryRepository;

import java.util.Optional;

@Service
public class CamperMapper {

    private CategoryRepository categoryRepository;

    public CamperMapper(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    CamperDto toDto(Camper camper) {
        CamperDto dto = new CamperDto();
        dto.setId(camper.getId());
        dto.setBrand(camper.getBrand());
        dto.setModel(camper.getModel());
        dto.setDescription(camper.getDescription());
        dto.setImgUrl(camper.getImgUrl());
        dto.setSerialNumber(camper.getSerialNumber());
        if(camper.getCategory() != null)
            dto.setCategory(camper.getCategory().getName());
        return dto;
    }

    Camper toEntity(CamperDto camper) {
        Camper entity = new Camper();
        entity.setId(camper.getId());
        entity.setBrand(camper.getBrand());
        entity.setModel(camper.getModel());
        entity.setDescription(camper.getDescription());
        entity.setImgUrl(camper.getImgUrl());
        Optional<Category> category = categoryRepository.findByName(camper.getCategory());
        category.ifPresent(entity::setCategory);
        entity.setSerialNumber(camper.getSerialNumber());
        return entity;
    }
}
