package plant.planner.plantplanner.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import plant.planner.plantplanner.dto.ColorDto;
import plant.planner.plantplanner.entity.Colors;
import plant.planner.plantplanner.entity.Plant;
import plant.planner.plantplanner.helpers.AttributesMapper;
import plant.planner.plantplanner.helpers.AttributesMapperImpl;
import plant.planner.plantplanner.repository.ColorsRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ColorsServiceImplTest {
    private final AttributesMapper attributesMapper = new AttributesMapperImpl();
    Colors color1;
    Colors color2;
    Colors color3;
    ColorDto colorDto1;
    ColorDto colorDto2;
    ColorDto colorDto3;
    List<Plant> p1;
    List<Plant> p2;
    List<Plant> p3;
    @InjectMocks
    private ColorsServiceImpl colorService;
    @Mock
    private ColorsRepository colorsRepository;

    @BeforeEach
    public void setUp() {
        colorService = new ColorsServiceImpl(colorsRepository, attributesMapper);
        p1 = new ArrayList<>();
        p2 = Arrays.asList(new Plant(), new Plant());
        p3 = Arrays.asList(new Plant(), new Plant(), new Plant());

        color1 = new Colors();
        color1.setId(1);
        color1.setName("red");
        color1.setPlants(p1);

        color2 = new Colors();
        color2.setId(2);
        color2.setName("blue");
        color2.setPlants(p2);

        color3 = new Colors();
        color3.setId(1);
        color3.setName("green");
        color3.setPlants(p3);

        colorDto1 = attributesMapper.toColorDto(color1);
        colorDto2 = attributesMapper.toColorDto(color2);
        colorDto3 = attributesMapper.toColorDto(color3);
    }

    @Test
    public void shouldReturnColor() {
        when(colorsRepository.findById(2)).thenReturn(Optional.of(color2));

        ColorDto result = colorService.getColor(2);

        assertEquals(colorDto2.getId(), result.getId());

    }

    @Test
    public void shouldAddNewColor() {

        ColorDto dto = new ColorDto();
        dto.setName("pink");

        when(colorsRepository.save(any(Colors.class))).thenAnswer(new Answer<Colors>() {
            @Override
            public Colors answer(InvocationOnMock invocation) throws Throwable {
                Colors color = invocation.getArgument(0);
                if (color.getId() == 0) {
                    color.setId(13);
                }
                return color;
            }
        });


        ColorDto result = colorService.addNewColor(dto);

        assertEquals(result.getId(), 13);
        assertEquals(result.getName(), dto.getName());

    }

    @Test
    public void shouldThrowWhenPassedExistingDto() {

        assertThrows(IllegalArgumentException.class, () -> {
            colorService.addNewColor(colorDto3);
        });
    }

    @Test
    public void shouldUpdateColor() {
        colorDto2.setName("black");

        when(colorsRepository.save(any(Colors.class))).thenReturn(attributesMapper.toColors(colorDto2));

        ColorDto updated = colorService.updateColor(colorDto2);

        assertEquals(updated.getName(), "black");

    }

    @Test
    public void shouldThrowWhenPassedNewDto() {
        ColorDto newDto = new ColorDto();
        newDto.setName("color");
        assertThrows(IllegalArgumentException.class, () -> {
            colorService.updateColor(newDto);
        });
    }


    @Test
    public void shouldReturnListOfAllColorsAsDto() {
        List<Colors> colors = Arrays.asList(color1, color2, color3);

        when(colorsRepository.findAll()).thenReturn(colors);

        List<ColorDto> result = colorService.allColors();

        assertEquals(result.get(0).getName(), colors.get(0).getName());
        assertEquals(result.size(), colors.size());

    }

    @Test
    public void shouldDeleteWhenPassedDto() {

        boolean isDeleted = colorService.deleteColor(colorDto1);
        assertTrue(isDeleted);

    }


}