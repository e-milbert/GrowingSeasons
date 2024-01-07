package plant.planner.plantplanner.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import plant.planner.plantplanner.dto.GerminationTempDto;
import plant.planner.plantplanner.entity.GerminationTemp;
import plant.planner.plantplanner.entity.Plant;
import plant.planner.plantplanner.helpers.AttributesMapper;
import plant.planner.plantplanner.helpers.AttributesMapperImpl;
import plant.planner.plantplanner.repository.GerminationTempRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GerminationTempServiceImplTest {


    private final AttributesMapper attributesMapper = new AttributesMapperImpl();
    GerminationTemp germination1;
    GerminationTemp germination2;
    GerminationTemp germination3;
    List<GerminationTemp> all;
    GerminationTempDto germinationDto1;
    GerminationTempDto germinationDto2;
    GerminationTempDto germinationDto3;
    Plant p1;
    Plant p2;
    @InjectMocks
    private GerminationTempServiceImpl germinationTempService;
    @Mock
    private GerminationTempRepository germinationTempRepository;

    @BeforeEach
    public void setUp() {
        germinationTempService = new GerminationTempServiceImpl(germinationTempRepository, attributesMapper);
        p1 = new Plant();
        p2 = new Plant();

        germination1 = new GerminationTemp();
        germination1.setId(1L);
        germination1.setMaxGerminationTemp(100);
        germination1.setMinGerminationTemp(10);
        germination1.setPlant(p1);

        germination2 = new GerminationTemp();
        germination2.setId(2L);
        germination2.setMaxGerminationTemp(200);
        germination2.setMinGerminationTemp(20);
        germination2.setPlant(p2);

        germination3 = new GerminationTemp();
        germination3.setId(3L);
        germination3.setMaxGerminationTemp(300);
        germination3.setMinGerminationTemp(30);

        all = Arrays.asList(germination1, germination2, germination3);

        germinationDto1 = attributesMapper.toGerminationTempDto(germination1);
        germinationDto2 = attributesMapper.toGerminationTempDto(germination2);
        germinationDto3 = attributesMapper.toGerminationTempDto(germination3);

    }

    @Test
    public void shouldReturnGerminationTemp() {
        when(germinationTempRepository.findById(2L)).thenReturn(Optional.of(germination2));

        GerminationTempDto result = germinationTempService.getGerminationTemp(2L);

        assertEquals(germination2.getId(), result.getId());

    }

    @Test
    public void shouldAddNewGerminationTemp() {

        GerminationTempDto dto = new GerminationTempDto();
        dto.setMaxGerminationTemp(14);

        when(germinationTempRepository.save(any(GerminationTemp.class))).thenAnswer(new Answer<GerminationTemp>() {
            @Override
            public GerminationTemp answer(InvocationOnMock invocation) throws Throwable {
                GerminationTemp germinationTemp = invocation.getArgument(0);
                if (germinationTemp.getId() == 0) {
                    germinationTemp.setId(13);
                }
                return germinationTemp;
            }
        });


        GerminationTempDto result = germinationTempService.addNewGerminationTemp(dto);

        assertEquals(result.getId(), 13);
        assertEquals(result.getMaxGerminationTemp(), dto.getMaxGerminationTemp());

    }

    @Test
    public void shouldThrowWhenPassedExistingDto() {

        assertThrows(IllegalArgumentException.class, () -> {
            germinationTempService.addNewGerminationTemp(germinationDto3);
        });
    }

    @Test
    public void shouldUpdateGerminationTemp() {
        germinationDto2.setMinGerminationTemp(40);

        when(germinationTempRepository.save(any(GerminationTemp.class))).thenReturn(attributesMapper.toGerminationTemp(germinationDto2));

        GerminationTempDto updated = germinationTempService.updateGerminationTemp(germinationDto2);

        assertEquals(updated.getMinGerminationTemp(), 40);

    }

    @Test
    public void shouldThrowWhenInvalidTemps() {
        germinationDto2.setMinGerminationTemp(germinationDto2.getMaxGerminationTemp() + 500);

        assertThrows(IllegalArgumentException.class, () -> {
            germinationTempService.updateGerminationTemp(germinationDto2);
        });
    }


    @Test
    public void shouldReturnListOfAllGerminationTempsAsDto() {


        when(germinationTempRepository.findAll()).thenReturn(all);

        List<GerminationTempDto> result = germinationTempService.allGerminationTemps();

        assertEquals(result.get(0).getMinGerminationTemp(), all.get(0).getMinGerminationTemp());
        assertEquals(result.size(), all.size());

    }

    @Test
    public void shouldDeleteWhenPassedDto() {

        boolean isDeleted = germinationTempService.deleteGerminationTemp(germinationDto3);
        assertTrue(isDeleted);

    }


}

