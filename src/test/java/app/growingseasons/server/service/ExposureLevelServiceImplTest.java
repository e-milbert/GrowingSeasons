package app.growingseasons.server.service;

import app.growingseasons.server.dto.ExposureLevelDto;
import app.growingseasons.server.entity.ExposureLevel;
import app.growingseasons.server.entity.Plant;
import app.growingseasons.server.helpers.AttributesMapper;
import app.growingseasons.server.helpers.AttributesMapperImpl;
import app.growingseasons.server.repository.ExposureLevelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExposureLevelServiceImplTest {
    private final AttributesMapper mapper = new AttributesMapperImpl();
    ExposureLevel level1;
    ExposureLevel level2;
    ExposureLevel level3;
    List<ExposureLevel> levels;
    ExposureLevelDto level1Dto;
    ExposureLevelDto level2Dto;
    ExposureLevelDto level3Dto;
    List<Plant> p1;
    List<Plant> p2;
    List<Plant> p3;
    @InjectMocks
    private ExposureLevelServiceImpl exposureLevelService;
    @Mock
    private ExposureLevelRepository exposureLevelRepository;

    @BeforeEach
    public void setUp() {
        exposureLevelService = new ExposureLevelServiceImpl(exposureLevelRepository, mapper);

        p1 = new ArrayList<>();
        p2 = Arrays.asList(new Plant(), new Plant());
        p3 = Arrays.asList(new Plant(), new Plant(), new Plant());

        level1 = new ExposureLevel();
        level1.setId(1);
        level1.setType("type1");
        level1.setPlants(p1);

        level2 = new ExposureLevel();
        level2.setId(2);
        level2.setType("type2");
        level2.setPlants(p2);

        level3 = new ExposureLevel();
        level3.setId(3);
        level3.setType("type3");
        level3.setPlants(p3);
        levels = Arrays.asList(level1, level2, level3);
        level1Dto = mapper.toExposureDto(level1);
        level2Dto = mapper.toExposureDto(level2);
        level3Dto = mapper.toExposureDto(level3);

    }

    @Test
    public void shouldReturnLevelDto() {
        when(exposureLevelRepository.findById(3)).thenReturn(Optional.of(level3));

        ExposureLevelDto result = exposureLevelService.getExposureLevel(3);

        assertEquals(result.getType(), level3.getType());
    }

    @Test
    public void shouldAddNewLevel() {
        when(exposureLevelRepository.save(any(ExposureLevel.class))).thenAnswer(new Answer<ExposureLevel>() {
            @Override
            public ExposureLevel answer(InvocationOnMock invocation) throws Throwable {
                ExposureLevel level = invocation.getArgument(0);
                if (level.getId() == 0) {
                    level.setId(13);
                }
                return level;
            }
        });

        ExposureLevelDto newLevel = new ExposureLevelDto();
        newLevel.setType("type13");

        ExposureLevelDto result = exposureLevelService.addNewExposureLevel(newLevel);

        assertEquals(result.getType(), "type13");

    }

    @Test
    public void shouldThrowIllegalWhenExistingPassed() {
        assertThrows(IllegalArgumentException.class, () -> {
            exposureLevelService.addNewExposureLevel(level1Dto);
        });
    }

    @Test
    public void shouldUpdateWhenDtoIsPassed() {

        level1Dto.setType("another");
        level1.setType(level1Dto.getType());

        when(exposureLevelRepository.save(any(ExposureLevel.class))).thenReturn(level1);

        ExposureLevelDto result = exposureLevelService.updateExposureLevel(level1Dto);

        assertEquals(result.getType(), "another");
        assertEquals(level1.getPlants().size(), 0);

    }

    @Test
    public void shouldThrowIllegalWhenNewDtoPassed() {
        assertThrows(IllegalArgumentException.class, () -> {
            exposureLevelService.updateExposureLevel(new ExposureLevelDto());
        });
    }

    @Test
    public void shouldReturnAllLevelsAsDto() {
        when(exposureLevelRepository.findAll()).thenReturn(levels);

        List<ExposureLevelDto> dtos = exposureLevelService.allExposureLevels();
        assertEquals(dtos.size(), levels.size());
        assertEquals(dtos.get(0).getId(), levels.get(0).getId());

    }

    @Test
    public void shouldDeleteWhenPassedDto() {

        boolean isDeleted = exposureLevelService.deleteExposureLevel(level2Dto);

        assertTrue(isDeleted);

    }
}