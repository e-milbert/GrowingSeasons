package plant.planner.plantplanner.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import plant.planner.plantplanner.dto.SoilDto;
import plant.planner.plantplanner.entity.Plant;
import plant.planner.plantplanner.entity.Soil;
import plant.planner.plantplanner.helpers.AttributesMapper;
import plant.planner.plantplanner.helpers.AttributesMapperImpl;
import plant.planner.plantplanner.repository.SoilRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SoilServiceImplTest {
    private final AttributesMapper mapper = new AttributesMapperImpl();
    Soil soil1;
    Soil soil2;
    Soil soil3;
    SoilDto soilDto1;
    SoilDto soilDto2;
    SoilDto soilDto3;
    List<Plant> p1;
    List<Plant> p2;
    List<Plant> p3;
    List<Soil> all;
    @InjectMocks
    private SoilServiceImpl service;
    @Mock
    private SoilRepository repository;

    @BeforeEach
    public void setUp() {
        service = new SoilServiceImpl(repository, mapper);

        p1 = new ArrayList<>();
        p2 = Arrays.asList(new Plant(), new Plant());
        p3 = Arrays.asList(new Plant(), new Plant(), new Plant());

        soil1 = new Soil();
        soil1.setId(1);
        soil1.setType("type1");
        soil1.setPlants(p1);

        soil2 = new Soil();
        soil2.setId(2);
        soil2.setType("type2");
        soil2.setPlants(p2);

        soil3 = new Soil();
        soil3.setId(3);
        soil3.setType("type3");
        soil3.setPlants(p3);

        all = Arrays.asList(soil1, soil2, soil3);

        soilDto1 = mapper.toSoilDto(soil1);
        soilDto2 = mapper.toSoilDto(soil2);
        soilDto3 = mapper.toSoilDto(soil3);
    }

    @Test
    public void shouldReturnSoil() {
        when(repository.findById(2)).thenReturn(Optional.of(soil2));

        SoilDto result = service.getSoil(2);

        assertEquals(soilDto2.getId(), result.getId());

    }

    @Test
    public void shouldAddNewSoil() {

        SoilDto dto = new SoilDto();
        dto.setType("new");

        when(repository.save(any(Soil.class))).thenAnswer(new Answer<Soil>() {
            @Override
            public Soil answer(InvocationOnMock invocation) throws Throwable {
                Soil res = invocation.getArgument(0);
                if (res.getId() == 0) {
                    res.setId(13);
                }
                return res;
            }
        });


        SoilDto result = service.addNewSoil(dto);

        assertEquals(result.getId(), 13);
        assertEquals(result.getType(), dto.getType());

    }

    @Test
    public void shouldThrowWhenPassedExistingDto() {

        assertThrows(IllegalArgumentException.class, () -> {
            service.addNewSoil(soilDto3);
        });
    }

    @Test
    public void shouldUpdateSoil() {
        soilDto2.setType("other");

        when(repository.save(any(Soil.class))).thenReturn(mapper.toSoil(soilDto2));

        SoilDto updated = service.updateSoil(soilDto2);

        assertEquals(updated.getType(), "other");

    }

    @Test
    public void shouldThrowWhenPassedNewDto() {
        SoilDto newDto = new SoilDto();
        newDto.setType("typex");
        assertThrows(IllegalArgumentException.class, () -> {
            service.updateSoil(newDto);
        });
    }


    @Test
    public void shouldReturnListOfAllSoilsAsDto() {


        when(repository.findAll()).thenReturn(all);

        List<SoilDto> result = service.allSoils();

        assertEquals(result.get(0).getType(), all.get(0).getType());
        assertEquals(result.size(), all.size());

    }

    @Test
    public void shouldDeleteWhenPassedDto() {

        boolean isDeleted = service.deleteSoil(soilDto1);
        assertTrue(isDeleted);

    }

}