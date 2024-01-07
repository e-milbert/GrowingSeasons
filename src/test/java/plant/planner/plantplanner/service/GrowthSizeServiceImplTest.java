package plant.planner.plantplanner.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import plant.planner.plantplanner.dto.SizeDto;
import plant.planner.plantplanner.entity.GrowthSize;
import plant.planner.plantplanner.helpers.AttributesMapper;
import plant.planner.plantplanner.helpers.AttributesMapperImpl;
import plant.planner.plantplanner.repository.GrowthSizeRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GrowthSizeServiceImplTest {
    private final AttributesMapper mapper = new AttributesMapperImpl();
    GrowthSize growthSize1;
    GrowthSize growthSize2;
    GrowthSize growthSize3;
    List<GrowthSize> all;
    SizeDto sizeDto1;
    SizeDto sizeDto2;
    SizeDto sizeDto3;

    @InjectMocks
    private GrowthSizeServiceImpl service;
    @Mock
    private GrowthSizeRepository repository;

    @BeforeEach
    public void setUp() {
        service = new GrowthSizeServiceImpl(repository, mapper);

        growthSize1 = new GrowthSize();
        growthSize1.setId(1);
        growthSize1.setSize(10);

        growthSize2 = new GrowthSize();
        growthSize2.setId(2);
        growthSize2.setSize(20);

        growthSize3 = new GrowthSize();
        growthSize3.setId(3);
        growthSize3.setSize(30);

        all = Arrays.asList(growthSize1, growthSize2, growthSize3);

        sizeDto1 = mapper.toSizeDto(growthSize1);
        sizeDto2 = mapper.toSizeDto(growthSize2);
        sizeDto3 = mapper.toSizeDto(growthSize3);
    }

    @Test
    public void shouldReturnSize() {
        when(repository.findById(2)).thenReturn(Optional.of(growthSize2));

        SizeDto result = service.getGrowthSize(2);

        assertEquals(growthSize2.getId(), result.getId());

    }

    @Test
    public void shouldAddNewSize() {

        SizeDto dto = new SizeDto();
        dto.setSize(14);

        when(repository.save(any(GrowthSize.class))).thenAnswer(new Answer<GrowthSize>() {
            @Override
            public GrowthSize answer(InvocationOnMock invocation) throws Throwable {
                GrowthSize result = invocation.getArgument(0);
                if (result.getId() == 0) {
                    result.setId(13);
                }
                return result;
            }
        });


        SizeDto result = service.addNewGrowthSize(dto);

        assertEquals(result.getId(), 13);
        assertEquals(result.getSize(), dto.getSize());

    }

    @Test
    public void shouldThrowWhenPassedExistingDto() {

        assertThrows(IllegalArgumentException.class, () -> {
            service.addNewGrowthSize(sizeDto3);
        });
    }

    @Test
    public void shouldUpdateSize() {
        sizeDto2.setSize(40);

        when(repository.save(any(GrowthSize.class))).thenReturn(mapper.toGrowthSize(sizeDto2));

        SizeDto updated = service.updateGrowthSize(sizeDto2);

        assertEquals(updated.getSize(), 40);

    }

    @Test
    public void shouldThrowWhenpassedNew() {

        assertThrows(IllegalArgumentException.class, () -> {
            service.updateGrowthSize(new SizeDto());
        });
    }


    @Test
    public void shouldReturnListOfAllGerminationTempsAsDto() {


        when(repository.findAll()).thenReturn(all);

        List<SizeDto> result = service.allGrowthSizes();

        assertEquals(result.get(0).getSize(), all.get(0).getSize());
        assertEquals(result.size(), all.size());

    }

    @Test
    public void shouldDeleteWhenPassedDto() {

        boolean isDeleted = service.deleteGrowthSize(sizeDto1);
        assertTrue(isDeleted);

    }

}