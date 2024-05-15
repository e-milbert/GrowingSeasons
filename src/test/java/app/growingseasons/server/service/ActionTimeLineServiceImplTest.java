package app.growingseasons.server.service;

import app.growingseasons.server.dto.ActionTimeLineAggregate;
import app.growingseasons.server.entity.*;
import app.growingseasons.server.helpers.ActionTimeLineMapper;
import app.growingseasons.server.helpers.ActionTimeLineMapperImpl;
import app.growingseasons.server.helpers.MapperDynHelper;
import app.growingseasons.server.repository.FloweringRepository;
import app.growingseasons.server.repository.HarvestingRepository;
import app.growingseasons.server.repository.PlantingRepository;
import app.growingseasons.server.repository.SowingRepository;
import app.growingseasons.server.service.interfaces.PlantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActionTimeLineServiceImplTest {

    private final ActionTimeLineMapper mapper = new ActionTimeLineMapperImpl();
    @InjectMocks
    private ActionTimeLineServiceImpl atsImpl;
    @InjectMocks
    private MapperDynHelper mapHelp;
    @Mock
    private PlantService plantS;

    @Mock
    private SowingRepository sowingR;
    @Mock
    private PlantingRepository plantingR;
    @Mock
    private FloweringRepository floweringR;
    @Mock
    private HarvestingRepository harvestingR;


    private SowingDates sow1, sow2;
    private HarvestingDates harv1, harv2;
    private PlantingDates plan1, plan2;
    private FloweringDates fl2;

    private Plant plant1, plant2;


    @BeforeEach
    public void setUp() {
        mapHelp = new MapperDynHelper(mapper, plantS);
        atsImpl = new ActionTimeLineServiceImpl(sowingR, plantingR, floweringR, harvestingR, mapper, mapHelp);

        plant1 = new Plant();
        plant1.setId(99L);
        plant2 = new Plant();
        plant2.setId(88L);


        sow1 = new SowingDates();
        sow1.setId(12L);
        sow1.setTimeLineAsJson("{1:[1,2,3,4], 2:[1,2]}");
        sow1.setPlant(plant1);

        sow2 = new SowingDates();
        sow2.setId(13L);
        sow2.setTimeLineAsJson("{4:[1,2,3,4], 5:[4]}");
        sow2.setPlant(plant2);

        harv1 = new HarvestingDates();
        harv1.setId(22L);
        harv1.setTimeLineAsJson("{1:[1,2,3,4], 2:[1,2]}");
        harv1.setPlant(plant1);

        harv2 = new HarvestingDates();
        harv2.setId(23L);
        harv2.setTimeLineAsJson("{4:[1,2,3,4], 5:[4]}");
        harv2.setPlant(plant2);

        plan1 = new PlantingDates();
        plan1.setId(32L);
        plan1.setTimeLineAsJson("{1:[1,2,3,4], 2:[1,2]}");
        plan1.setPlant(plant1);

        plan2 = new PlantingDates();
        plan2.setId(33L);
        plan2.setTimeLineAsJson("{4:[1,2,3,4], 5:[4]}");
        plan2.setPlant(plant2);


        fl2 = new FloweringDates();
        fl2.setId(43L);
        fl2.setTimeLineAsJson("{4:[1,2,3,4], 5:[4]}");
        fl2.setPlant(plant2);

    }

    @Test
    public void mapperTest() {

        ActionTimeLineAggregate exp = new ActionTimeLineAggregate() {
            {
                setActionId(12L);
                setPlantId(99L);
                setActionType("sowingdates");
                setJsonTimeline("{1:[1,2,3,4], 2:[1,2]}");
            }
        };

        ActionTimeLineAggregate act = mapper.toActionTimeLineAggregate(99L, sow1);

        assertEquals(exp.getPlantId(), act.getPlantId());
        assertEquals(exp.getJsonTimeline(), act.getJsonTimeline());
        assertEquals(exp.getActionType(), act.getActionType());


    }

    @Test
    public void shouldProvideListOfAggregateWhenPassedPlantId() {

        ArrayList<ActionTimeLineAggregate> exp = new ArrayList<>();
        exp.add(new ActionTimeLineAggregate() {{
            setActionId(12L);
            setPlantId(99L);
            setActionType("sowingdates");
            setJsonTimeline("{1:[1,2,3,4], 2:[1,2]}");
        }});
        exp.add(new ActionTimeLineAggregate() {{
            setActionId(22L);
            setPlantId(99L);
            setActionType("harvestingdates");
            setJsonTimeline("{1:[1,2,3,4], 2:[1,2]}");
        }});
        exp.add(new ActionTimeLineAggregate() {{
            setActionId(32L);
            setPlantId(99L);
            setActionType("plantingdates");
            setJsonTimeline("{1:[1,2,3,4], 2:[1,2]}");
        }});

        when(sowingR.findByPlantId(99L)).thenReturn(Optional.of(sow1));
        when(plantingR.findByPlantId(99L)).thenReturn(Optional.of(plan1));
        when(harvestingR.findByPlantId(99L)).thenReturn(Optional.of(harv1));


        ArrayList<ActionTimeLineAggregate> act = atsImpl.getTimelinesOfPlant(99L);

        assertEquals(exp.get(0).getActionId(), act.get(0).getActionId());
        assertEquals(exp.get(0).getActionType(), act.get(0).getActionType());
        assertEquals(exp.get(0).getJsonTimeline(), act.get(0).getJsonTimeline());
        assertEquals(exp.get(0).getPlantId(), act.get(0).getPlantId());
        assertEquals(3, act.size());

    }

    @Test
    public void shouldAddEntityToDbWhenDtoProvided() {

        SowingDates expected = new SowingDates();
        expected.setId(888L);
        expected.setPlant(plant2);
        expected.setTimeLineAsJson("{4:[1,2,3,4], 5:[4]}");

        ActionTimeLineAggregate expDto = mapper.toActionTimeLineAggregate(plant2.getId(), expected);

        when(sowingR.save(any(SowingDates.class))).thenReturn(expected);

        ActionTimeLineAggregate atla = new ActionTimeLineAggregate(88L, "sowing", "{4:[1,2,3,4], 5:[4]}");

        ActionTimeLineAggregate dto = atsImpl.AddNewDates(atla);

        assertEquals(expDto.getActionId(), dto.getActionId());

    }

    @Test
    public void shouldRemoveEntityWhenProvidedWithDto() {

        ActionTimeLineAggregate dto = mapper.toActionTimeLineAggregate(harv1.getPlant().getId(), harv1);

        boolean isDeleted = atsImpl.DeleteDates(dto);

        assertTrue(isDeleted);
    }

    @Test
    public void shouldUpdateJsonTimeline() {

        ActionTimeLineAggregate changed = new ActionTimeLineAggregate();
        changed.setActionId(sow1.getId());
        changed.setPlantId(sow1.getPlant().getId());
        changed.setActionType("sowingdates");
        changed.setJsonTimeline("{9:[1,2,3,4], 11:[1,2]}");

        SowingDates newDates = sow1;
        newDates.setTimeLineAsJson(changed.getJsonTimeline());

        when(sowingR.save(any(SowingDates.class))).thenReturn(newDates);
        when(sowingR.findByPlantId(99L)).thenReturn(Optional.of(sow1));

        var updated = atsImpl.updateDates(changed);

        assertEquals(changed.getJsonTimeline(), updated.getJsonTimeline());

    }

}