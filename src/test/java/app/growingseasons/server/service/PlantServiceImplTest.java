package app.growingseasons.server.service;

import app.growingseasons.server.dto.*;
import app.growingseasons.server.entity.Plant;
import app.growingseasons.server.helpers.AttributesMapper;
import app.growingseasons.server.helpers.AttributesMapperImpl;
import app.growingseasons.server.helpers.PlantMapper;
import app.growingseasons.server.helpers.PlantMapperImpl;
import app.growingseasons.server.repository.PlantRepository;
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
class PlantServiceImplTest {

    private final PlantMapper mapper = new PlantMapperImpl();
    private final AttributesMapper attributesMapper = new AttributesMapperImpl();
    @InjectMocks
    private PlantServiceImpl plantService;
    @Mock
    private PlantRepository plantRepository;

    private List<ColorDto> colors;
    private List<SoilDto> soils;
    private List<ExposureLevelDto> exposureLevels;
    private SizeDto maxHeight;
    private SizeDto maxWidth;
    private GerminationTempDto germinationTemp;


    @BeforeEach
    public void setUp() {
        plantService = new PlantServiceImpl(plantRepository, mapper, attributesMapper);

        ColorDto color1 = new ColorDto(1, "Red");
        ColorDto color2 = new ColorDto(2, "Green");

        colors = Arrays.asList(color1, color2);

        SoilDto soil1 = new SoilDto(1, "Loamy");
        SoilDto soil2 = new SoilDto(2, "Sandy");

        soils = Arrays.asList(soil1, soil2);

        ExposureLevelDto exposure1 = new ExposureLevelDto(1, "Full Sun");
        ExposureLevelDto exposure2 = new ExposureLevelDto(2, "Partial Shade");

        exposureLevels = Arrays.asList(exposure1, exposure2);

        maxHeight = new SizeDto(1, 150); // 150 cm
        maxWidth = new SizeDto(2, 100); // 100 cm


        germinationTemp = new GerminationTempDto(1, 5, 20); // Min 5°C, Max 20°C
    }

    @Test
    public void shouldReturnTrueWhenExisting() {
        when(plantRepository.existsById(123L)).thenReturn(true);

        boolean result = plantService.plantExists(123L);

        assertTrue(result);

    }

    @Test
    public void testMapper() {
        PlantDto dto = new PlantDto(73647L, "common", "official", true, true, false, colors, maxHeight, maxWidth,
                exposureLevels, soils, germinationTemp);

        Plant plant = mapper.PlantDtoToPlant(dto, attributesMapper);

        assertEquals(dto.getId(), plant.getId());
        assertEquals(dto.getCommonName(), plant.getCommonName());
        assertEquals(dto.getColors().get(0).getId(), plant.getColors().get(0).getId());
        assertEquals(dto.getMaxHeight().getSize(), plant.getMaxHeight().getSize());
        assertEquals(dto.getSoilTypes().get(0).getType(), plant.getSoilTypes().get(0).getType());
        assertEquals(dto.getGerminationTempRange().getMaxGerminationTemp(), plant.getGerminationTempRange().getMaxGerminationTemp());


    }

    @Test
    public void shouldReturnPlantDtoWhenProvidedWithId() {
        PlantDto dto = new PlantDto(73647L, "common", "official", true, true, false, colors, maxHeight, maxWidth,
                exposureLevels, soils, germinationTemp);

        Plant plant = mapper.PlantDtoToPlant(dto, attributesMapper);

        when(plantRepository.findById(73647L)).thenReturn(Optional.of(plant));

        var res = plantService.getPlantDto(73647L);

        assertSame(res.getClass().getSimpleName(), PlantDto.class.getSimpleName());

    }

    @Test
    public void shouldReturnAllPlantsAsDtos() {
        List<Plant> plants = new ArrayList<>();
        long leftLimit = 1L;
        long rightLimit = 10000L;
        for (int i = 0; i < 5; i++) {
            long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));

            plants.add(mapper.PlantDtoToPlant((new PlantDto(generatedLong, "common", "official", true, true, false, colors, maxHeight, maxWidth,
                    exposureLevels, soils, germinationTemp)), attributesMapper));
        }
        when(plantRepository.findAll()).thenReturn(plants);

        List<PlantDto> plantDtos = plantService.allPlantDtos();

        assertEquals(5, plantDtos.size());


    }

    @Test
    public void shouldReturnAllPlants() {
        List<Plant> plants = new ArrayList<>();
        long leftLimit = 1L;
        long rightLimit = 10000L;
        for (int i = 0; i < 5; i++) {
            long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));

            plants.add(mapper.PlantDtoToPlant((new PlantDto(generatedLong, "common", "official", true, true, false, colors, maxHeight, maxWidth,
                    exposureLevels, soils, germinationTemp)), attributesMapper));
        }
        when(plantRepository.findAll()).thenReturn(plants);

        List<Plant> result = plantService.getAllPlants();

        assertSame(plants.get(0), result.get(0));
    }

    @Test
    public void shouldRemovePlantWhenProvidedWithDto() {

        PlantDto dto = new PlantDto(73647L, "common", "official", true, true, false, colors, maxHeight, maxWidth,
                exposureLevels, soils, germinationTemp);

        boolean isDeleted = plantService.deletePlant(dto);

        assertTrue(isDeleted);

    }

    @Test
    public void shouldUpdateEntityAccToDtoPassed() {
        PlantDto dto = new PlantDto(73647L, "common", "official", true, true, false, colors, maxHeight, maxWidth,
                exposureLevels, soils, germinationTemp);
        Plant plant = mapper.PlantDtoToPlant(dto, attributesMapper);

        PlantDto updated = dto;
        updated.setCommonName("newname");

        when(plantRepository.findById(73647L)).thenReturn(Optional.of(plant));

        PlantDto changed = plantService.updatePlant(updated);

        assertEquals(changed.getCommonName(), "newname");
        assertNotNull(plant.getBusinessKey());


    }

    @Test
    public void shouldAddNewPlant() {
        PlantDto newDto = new PlantDto("common", "official", true, true, false, colors, maxHeight, maxWidth,
                exposureLevels, soils, germinationTemp);

        when(plantRepository.existsByBusinessKey(any())).thenReturn(false);
        when(plantRepository.save(any(Plant.class))).thenAnswer(new Answer<Plant>() {
            @Override
            public Plant answer(InvocationOnMock invocation) throws Throwable {
                Plant plant = invocation.getArgument(0);
                if (plant.getId() == 0) {
                    plant.setId(47865L);
                }
                return plant;
            }
        });
        PlantDto addedPlant = plantService.addNewPlant(newDto);

        assertTrue(newDto.getId() != addedPlant.getId());

    }
}