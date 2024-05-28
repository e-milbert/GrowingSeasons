import {OneLineFormC} from "../formHelpers/OneLineFormC";
import {DynamicDropdown2, DynamicSingleValueChoiceDropdown2} from "../formHelpers/McDropdownC";
import {QuarterMonthTimeLinePickerEdit} from "../formHelpers/QuarterMonthTimeLinePicker";
import React, {useEffect, useState} from "react";
import {
    colorsGetAllColors,
    exposureGetAllExposures,
    growthGetAllGrowth,
    plantPutPlant,
    soilGetAllSoils,
    timelinePutUpdate,
    timelinesForSinglePlant
} from "../../constants/apiConstants";
import {LoadingAnimation} from "../shared/LoadingAnimation";
import {useNavigate} from 'react-router-dom';

export function EditPlantC({plant, afterSubmit, reload}) {

    const [object, setObject] = useState(plant)
    const [commonName, setCommonName] = useState(plant.commonName)
    const [publicName, setPublicName] = useState(plant.publicName)
    const [edible, setEdible] = useState(plant.edible)
    const [ornamental, setOrnamental] = useState(plant.ornamental)
    const [poisonous, setPoisonous] = useState(plant.poisonous)
    /*fetch*/
    const [allColors, setAllColors] = useState([])
    const [chosenColors, setChosenColors] = useState(plant.colors);

    const [allExposures, setAllExposures] = useState([]);
    const [chosenExposures, setChosenExposures] = useState(plant.exposureLevels);

    const [allSoils, setAllSoils] = useState([]);
    const [chosenSoils, setChosenSoils] = useState(plant.soilTypes);

    const [allSizes, setSizes] = useState([]);
    const [heightSize, setHeightSize] = useState(plant.maxHeight);
    const [widthSize, setWidthSize] = useState(plant.maxWidth);

    const [allGermTemp, setAllGermTemp] = useState(plant.germinationTempRange || {
        minGerminationTemp: 0,
        maxGerminationTemp: 0
    });
    const [minGerm, setMinGerm] = useState(allGermTemp.minGerminationTemp)
    const [maxGerm, setMaxGerm] = useState(allGermTemp.maxGerminationTemp)

    const germination = {
        id: plant.germinationTempRange.id,
        minGerminationTemp: minGerm,
        maxGerminationTemp: maxGerm
    }
    /*fetch data*/
    const [sowingTimes, setSowingTimes] = useState();
    const [sowingId, setSowingId] = useState();
    const [plantingTimes, setPlantingTimes] = useState();
    const [plantingId, setPlantingId] = useState();
    const [harvestingTimes, setHarvestingTimes] = useState();
    const [harvestingId, setHarvestingId] = useState();
    const [bloomingTimes, setBloomingTimes] = useState();
    const [bloomingId, setBloomingId] = useState();

    const [isLoading, setIsLoading] = useState(true);
    const [isUpdated, setIsUpdated] = useState(false);

    useEffect(() => {
        const fetchPromises = [
            fetch(colorsGetAllColors)
                .then(response => response.json())
                .then(data => setAllColors(data)),
            fetch(exposureGetAllExposures)
                .then(response => response.json())
                .then(data => setAllExposures(data)),
            fetch(soilGetAllSoils)
                .then(response => response.json())
                .then(data => setAllSoils(data)),
            fetch(growthGetAllGrowth)
                .then(response => response.json())
                .then(data => setSizes(data)),
            fetch(timelinesForSinglePlant.replace("{plantid}", plant.id))
                .then(response => response.json())
                .then(data => {
                    if (data) {
                        setSowingTimes(data.sowing);
                        setHarvestingTimes(data.harvest);
                        setBloomingTimes(data.bloom);
                        setPlantingTimes(data.planting);
                        setBloomingId(data.bloomingId);
                        setSowingId(data.sowingId);
                        setPlantingId(data.plantingId);
                        setHarvestingId(data.harvestId);
                    }
                })

        ];

        Promise.all(fetchPromises)
            .then(() => {
                setIsLoading(false); // Set loading to false when all fetches are done
            })
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
                setIsLoading(false); // Set loading to false on error as well
            });
    }, []);

    useEffect(() => {
        if (plant.germinationTempRange) {
            setAllGermTemp(plant.germinationTempRange);
            setMinGerm(plant.germinationTempRange.minGerminationTemp);
            setMaxGerm(plant.germinationTempRange.maxGerminationTemp);
        }
    }, [plant.germinationTempRange]);

    useEffect(() => {
        if (plant.colors) {
            setChosenColors(plant.colors);
        }
        if (plant.exposureLevels) {
            setChosenExposures(plant.exposureLevels)
        }
        if (plant.soilTypes) {
            setChosenSoils(plant.soilTypes)
        }

    }, [plant]);

    const nav = useNavigate();


    const handleSubmit = async () => {

        try {

            const plantEdited = JSON.stringify({
                "id": object.id,
                "commonName": commonName,
                "publicName": publicName,

                "edible": edible,
                "ornamental": ornamental,
                "poisonous": poisonous,

                "colors": chosenColors,
                "exposureLevels": chosenExposures,
                "soilTypes": chosenSoils,

                "maxHeight": heightSize,
                "maxWidth": widthSize,
                "germinationTempRange": germination
            });

            const response = await fetch(plantPutPlant, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: plantEdited
            });

            const data = await response.json();
            console.log('Success:', data);

            const plantId = data.id;

            let sow = JSON.stringify({
                "actionId": sowingId,
                "plantId": plantId,
                "actionType": "sowing",
                "jsonTimeline": JSON.stringify(sowingTimes)
            });
            const sowResponse = await fetch(timelinePutUpdate, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: sow
            });
            const sowR = await sowResponse.json();
            console.log('Success:', sowR);


            let harvest = JSON.stringify({
                "actionId": harvestingId,
                "plantId": plantId,
                "actionType": "harvest",
                "jsonTimeline": JSON.stringify(harvestingTimes)
            });
            const harvestResponse = await fetch(timelinePutUpdate, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: harvest
            });
            const harvestR = await harvestResponse.json();
            console.log('Success:', harvestR);


            let plant = JSON.stringify({
                "actionId": plantingId,
                "plantId": plantId,
                "actionType": "planting",
                "jsonTimeline": JSON.stringify(plantingTimes)
            });
            const plantresponse = await fetch(timelinePutUpdate, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: plant
            });
            const plantr = await plantresponse.json();
            console.log('Success:', plantr);


            let bloom = JSON.stringify({
                "actionId": bloomingId,
                "plantId": plantId,
                "actionType": "flowering",
                "jsonTimeline": JSON.stringify(bloomingTimes)
            });
            const bloomresponse = await fetch(timelinePutUpdate, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: bloom
            });
            const bloomr = await bloomresponse.json();
            console.log('Success:', bloomr);
            setIsUpdated(true);
            reload();
            afterSubmit(false);


        } catch (error) {
            console.error('Error:', error);
        }
    };


    const handleEdible = (event) => {
        setEdible(event.target.checked);
    }
    const handleOrnamental = (event) => {
        setOrnamental(event.target.checked);
    }
    const handlePoisonous = (event) => {
        setPoisonous(event.target.checked);
    }
    const handleMinGermination = (value) => {
        if (value > maxGerm) {
            setMaxGerm(value)
        }
        setMinGerm(value);
    }
    const handleMaxGermination = (value) => {
        if (value < minGerm) {
            setMinGerm(value);
        }
        setMaxGerm(value);
    }


    return (
        <>
            {!isLoading ? (
                <div className="d-flex justify-content-center">
                    <LoadingAnimation/>
                </div>
            ) : (
                <form className="p-4">
                    <div className="row">
                        <div className="col-xl-6 d-flex flex-column">
                            <div className="mb-4">
                                <div className="row">
                                    <div className="col">
                                        <OneLineFormC
                                            idName="name1"
                                            labelText="Name"
                                            placeholderText={plant.commonName}
                                            handleInputChangeFunction={setCommonName}
                                        />
                                    </div>
                                    <div className="col">
                                        <OneLineFormC
                                            idName="name2"
                                            labelText="Official Name"
                                            placeholderText={publicName}
                                            handleInputChangeFunction={setPublicName}
                                        />
                                    </div>
                                </div>
                            </div>

                            <div className="mb-4">
                                <div className="d-flex justify-content-start">
                                    <div className="px-3 my-2">
                                        <input
                                            type="checkbox"
                                            id="checkboxEdible"
                                            checked={edible}
                                            onChange={handleEdible}
                                            name="checkboxEdible"
                                            className="form-check-input"
                                        />
                                        <label htmlFor="checkboxEdible"
                                               className="form-check-label fw-semibold">Edible</label>
                                    </div>
                                    <div className="px-3 my-2">
                                        <input
                                            type="checkbox"
                                            id="checkboxOrnamental"
                                            checked={ornamental}
                                            onChange={handleOrnamental}
                                            name="checkboxOrnamental"
                                            className="form-check-input"
                                        />
                                        <label htmlFor="checkboxOrnamental"
                                               className="form-check-label fw-semibold">Ornamental</label>
                                    </div>
                                    <div className="px-3 my-2">
                                        <input
                                            type="checkbox"
                                            id="checkboxPoisonous"
                                            checked={poisonous}
                                            onChange={handlePoisonous}
                                            name="checkboxPoisonous"
                                            className="form-check-input"
                                        />
                                        <label htmlFor="checkboxPoisonous"
                                               className="form-check-label fw-semibold">Poisonous</label>
                                    </div>
                                </div>
                            </div>

                            <div className="mb-4">
                                <DynamicDropdown2
                                    bxIcon="bx bxs-florist mx-3"
                                    key="color"
                                    dropdownname="color"
                                    data={allColors}
                                    valueKey="name"
                                    preselected={chosenColors}
                                    onSelectedOptionsChange={setChosenColors}
                                />

                                <DynamicDropdown2
                                    bxIcon="bx bx-sun mx-3"
                                    key="expo"
                                    dropdownname="exposure"
                                    data={allExposures}
                                    valueKey="type"
                                    preselected={chosenExposures}
                                    onSelectedOptionsChange={setChosenExposures}
                                />

                                <DynamicDropdown2
                                    bxIcon="bx bx-vertical-bottom mx-3"
                                    key="soil"
                                    dropdownname="soil"
                                    data={allSoils}
                                    valueKey="type"
                                    preselected={chosenSoils}
                                    onSelectedOptionsChange={setChosenSoils}
                                />
                            </div>

                            <div className="mb-4">
                                <DynamicSingleValueChoiceDropdown2
                                    bxIcon="bx bx-up-arrow-alt mx-3"
                                    data={allSizes}
                                    valueKey="size"
                                    preselected={heightSize}
                                    onSelectedOptionsChange={setHeightSize}
                                    dropdownname="height"
                                />

                                <DynamicSingleValueChoiceDropdown2
                                    bxIcon="bx bx-right-arrow-alt mx-3"
                                    data={allSizes}
                                    valueKey="size"
                                    preselected={widthSize}
                                    onSelectedOptionsChange={setWidthSize}
                                    dropdownname="width"
                                />
                            </div>

                            <div className="mb-4">
                                <div className="row">
                                    <h5 className="text-start m-3">Germination Temperature</h5>
                                    <div className="col">
                                        <OneLineFormC
                                            idName="germmin"
                                            handleInputChangeFunction={handleMinGermination}
                                            labelText="Min Temperature"
                                            placeholderText={minGerm}
                                        />
                                    </div>
                                    <div className="col">
                                        <OneLineFormC
                                            idName="germmax"
                                            handleInputChangeFunction={handleMaxGermination}
                                            labelText="Max Temperature"
                                            placeholderText={maxGerm}
                                        />
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className="col-xl-6 pt-5 d-flex flex-column">
                            <div className="mb-4">
                                <QuarterMonthTimeLinePickerEdit
                                    actionName="sow"
                                    preselected={sowingTimes}
                                    onChange={setSowingTimes}
                                />
                                <QuarterMonthTimeLinePickerEdit
                                    actionName="plant"
                                    preselected={plantingTimes}
                                    onChange={setPlantingTimes}
                                />
                                <QuarterMonthTimeLinePickerEdit
                                    actionName="harvest"
                                    preselected={harvestingTimes}
                                    onChange={setHarvestingTimes}
                                />
                                <QuarterMonthTimeLinePickerEdit
                                    actionName="bloom"
                                    preselected={bloomingTimes}
                                    onChange={setBloomingTimes}
                                />
                            </div>
                        </div>
                    </div>

                    <div className="row">
                        <div className="text-center">
                            <button
                                className="mb-2 btn-sage-light btn text-black"
                                data-bs-dismiss="modal"
                                data-bs-target="#editModal"
                                onClick={handleSubmit}>
                                Save
                            </button>
                        </div>
                    </div>

                </form>
            )}


        </>
    )


}