package app.growingseasons.server.service;

import app.growingseasons.server.dto.SizeDto;
import app.growingseasons.server.entity.GrowthSize;
import app.growingseasons.server.helpers.AttributesMapper;
import app.growingseasons.server.repository.GrowthSizeRepository;
import app.growingseasons.server.service.interfaces.GrowthSizeService;
import org.springframework.stereotype.Service;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GrowthSizeServiceImpl implements GrowthSizeService {

    private final GrowthSizeRepository repository;
    private final AttributesMapper mapper;

    public GrowthSizeServiceImpl(GrowthSizeRepository repository, AttributesMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public SizeDto getGrowthSize(int id) {
        Optional<GrowthSize> opt = repository.findById(id);
        GrowthSize result = opt.orElse(null);
        if (result != null) {
            return mapper.toSizeDto(result);

        } else {
            return new SizeDto();
        }
    }

    @Override
    public SizeDto addNewGrowthSize(SizeDto newGrowthSize) {
        if (newGrowthSize.getId() == 0) {
            GrowthSize updated = repository.save(mapper.toGrowthSize(newGrowthSize));
            return mapper.toSizeDto(updated);
        } else {
            Logger.info("provided id was not 0");
            throw new IllegalArgumentException("must be without id, id must be 0");
        }
    }

    @Override
    public SizeDto updateGrowthSize(SizeDto updatedGrowthSize) {
        if (updatedGrowthSize.getId() != 0) {

            GrowthSize updated = repository.save(mapper.toGrowthSize(updatedGrowthSize));
            return mapper.toSizeDto(updated);
        } else {
            Logger.info("provided id was 0");

            throw new IllegalArgumentException("new item with id 0 was provided. item must have id > 0");
        }
    }

    @Override
    public List<SizeDto> allGrowthSizes() {
        List<GrowthSize> result = repository.findAll();
        List<SizeDto> dtos = new ArrayList<>();
        if (!result.isEmpty()) {
            for (var entry : result) {
                dtos.add(mapper.toSizeDto(entry));
            }
        }
        return dtos;
    }

    @Override
    public boolean deleteGrowthSize(SizeDto oldGrowthSize) {
        try {
            GrowthSize old = mapper.toGrowthSize(oldGrowthSize);
            repository.delete(old);
            return true;
        } catch (Exception ex) {
            Logger.info("delete unsuccessful");
            Logger.error(ex);
            return false;
        }
    }
}
