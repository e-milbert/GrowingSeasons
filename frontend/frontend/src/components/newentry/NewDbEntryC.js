import React, {useEffect, useState} from "react";

import {Button, Col, Container, Row} from "react-bootstrap";
import Form from "react-bootstrap/Form";
import {OneLineFormC} from "../formHelpers/OneLineFormC";
import {DynamicDropdown, DynamicSingleValueChoiceDropdown} from "../formHelpers/McDropdownC";
import {
    colorsGetAllColors,
    exposureGetAllExposures,
    germinationGetAllGerminations,
    growthGetAllGrowth,
    plantPostPlant,
    soilGetAllSoils,
    timelinePutUpdate
} from "../../constants/apiConstants";
import {QuarterMonthTimeLinePicker2} from "../formHelpers/QuarterMonthTimeLinePicker";
import {LoadingAnimation} from "../shared/LoadingAnimation";


export function NewDbEntryC() {


    const [commonName, setCommonName] = useState();
    const [officialName, setOfficialName] = useState();
    const [isEdible, setIsEdible] = useState();
    const [isPoisonous, setIsPoisonous] = useState();
    const [isOrnamental, setIsOrnamental] = useState();

    const [allcolors, setAllColors] = useState([]);
    const [chosenColors, setChosenColors] = useState([]);

    const [allExposures, setAllExposures] = useState([]);
    const [chosenExposures, setChosenExposures] = useState([]);

    const [allSoils, setAllSoils] = useState([]);
    const [chosenSoils, setChosenSoils] = useState([]);

    const [allSizes, setSizes] = useState([]);

    const [heightSize, setHeightSize] = useState({});
    const [widthSize, setWidthSize] = useState({});

    const [allGermTemp, setAllGermTemp] = useState([]);

    const [sowingTimes, setSowingTimes] = useState([]);
    const [plantingTimes, setPlantingTimes] = useState([]);
    const [harvestingTimes, setHarvestingTimes] = useState([]);
    const [bloomingTimes, setBloomingTimes] = useState([]);

    const [isLoading, setIsLoading] = useState(true);

    const [minGerm, setMinGerm] = useState()
    const [maxGerm, setMaxGerm] = useState()

    const germination = {
        minGerminationTemp: minGerm,
        maxGerminationTemp: maxGerm
    }

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
            fetch(germinationGetAllGerminations)
                .then(response => response.json())
                .then(data => setAllGermTemp(data))

        ];

        Promise.all(fetchPromises)
            .then(() => {
                setIsLoading(false);
            })
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
                setIsLoading(false);
            });
    }, []);

    const handleEdible = (event) => {
        setIsEdible(event.target.checked);
    }
    const handleOrnamental = (event) => {
        setIsOrnamental(event.target.checked);
    }
    const handlePoisonous = (event) => {
        setIsPoisonous(event.target.checked);
    }

    const handleSubmit = async () => {

        console.log("expo " + chosenExposures)

        try {

            const plantw = JSON.stringify({
                "commonName": commonName,
                "publicName": officialName,

                "edible": isEdible,
                "ornamental": isOrnamental,
                "poisonous": isPoisonous,

                "colors": chosenColors,
                "exposureLevels": chosenExposures,
                "soilTypes": chosenSoils,

                "maxHeight": heightSize,
                "maxWidth": widthSize,
                "germinationTempRange": germination
            });

            const response = await fetch(plantPostPlant, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: plantw
            });

            const data = await response.json();
            console.log('Success:', data);

            const plantId = data.id;

            let sow = JSON.stringify({
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

            document.location.reload();

        } catch (error) {
            console.error('Error:', error);
        }
    };


    return (
        <>
            {isLoading ? (
                <Row className="min-vh-100 d-flex justify-content-center align-items-center">
                    <Col className="d-flex justify-content-center align-items-center">
                        <LoadingAnimation/>
                    </Col>

                </Row>
            ) : (
                <>
                    <Container className="bg-container rounded-3 text-center px-1 py-5">
                        <Row className="text-center">
                            <h3>new database entry</h3>
                        </Row>

                        <Form className="p-4">
                            <div className="mb-4">
                                <Row>
                                    <Col>
                                        <OneLineFormC
                                            idName="name1"
                                            labelText="Common Name"
                                            placeholderText=""
                                            handleInputChangeFunction={setCommonName}/>
                                    </Col>
                                    <Col>
                                        <OneLineFormC
                                            idName="name2"
                                            labelText="Official Name"
                                            placeholderText=""
                                            handleInputChangeFunction={setOfficialName}/>
                                    </Col>
                                </Row>
                            </div>

                            <div className="mb-4">
                                <div className="d-flex justify-content-start">
                                    <div className="px-3 my-2">
                                        <Form.Check
                                            type="checkbox"
                                            label="Edible"
                                            checked={isEdible || false}
                                            onChange={handleEdible}
                                            name="checkboxEdible"

                                        />
                                    </div>
                                    <div className="px-3 my-2">
                                        <Form.Check
                                            type="checkbox"
                                            label="Ornamental"
                                            checked={isOrnamental || false}
                                            onChange={handleOrnamental}
                                            name="checkboxOrnamental"

                                        /></div>
                                    <div className="px-3 my-2">
                                        <Form.Check
                                            type="checkbox"
                                            label="Poisonous"
                                            checked={isPoisonous || false}
                                            onChange={handlePoisonous}
                                            name="checkboxPoisonous"

                                        /></div>
                                </div>
                            </div>

                            <div className="mb-4">

                                <DynamicDropdown bxIcon="bx bxs-florist mx-3"
                                                 key="color"
                                                 dropdownname="color"
                                                 data={allcolors}
                                                 valueKey="name"
                                                 onSelectedOptionsChange={setChosenColors}/>

                                <DynamicDropdown bxIcon="bx bx-sun mx-3"
                                                 key="expo"
                                                 dropdownname="exposure"
                                                 data={allExposures}
                                                 valueKey="type"
                                                 onSelectedOptionsChange={setChosenExposures}/>

                                <DynamicDropdown bxIcon="bx bx-vertical-bottom mx-3"
                                                 key="soil"
                                                 dropdownname="soil"
                                                 data={allSoils}
                                                 valueKey="type"
                                                 onSelectedOptionsChange={setChosenSoils}/>
                            </div>

                            <div className="mb-4">

                                <DynamicSingleValueChoiceDropdown bxIcon="bx bx-up-arrow-alt mx-3"
                                                                  data={allSizes} valueKey="size"
                                                                  onSelectedOptionsChange={setHeightSize}
                                                                  dropdownname="height"/>


                                <DynamicSingleValueChoiceDropdown bxIcon="bx bx-right-arrow-alt mx-3"
                                                                  data={allSizes}
                                                                  valueKey="size"
                                                                  onSelectedOptionsChange={setWidthSize}
                                                                  dropdownname="width"/>
                            </div>
                            <div className="mb-4">
                                <Row>
                                    <h5 className="text-start m-3">germination temperature</h5>
                                    <Col>
                                        <OneLineFormC idName="germmin" handleInputChangeFunction={setMinGerm}
                                                      labelText="min temperature" placeholderText=""/>
                                    </Col>
                                    <Col>
                                        <OneLineFormC idName="germmax" handleInputChangeFunction={setMaxGerm}
                                                      labelText="max temperature" placeholderText=""/>
                                    </Col>
                                </Row>
                            </div>


                            <div className="mb-4">
                                <QuarterMonthTimeLinePicker2 actionName="sow" onChange={setSowingTimes}/>
                                <QuarterMonthTimeLinePicker2 actionName="plant" onChange={setPlantingTimes}/>
                                <QuarterMonthTimeLinePicker2 actionName="harvest" onChange={setHarvestingTimes}/>
                                <QuarterMonthTimeLinePicker2 actionName="bloom" onChange={setBloomingTimes}/>
                            </div>

                        </Form>
                        <Button className="mb-2 custom-button text-black" onClick={handleSubmit}>save</Button>
                    </Container>
                </>
            )}


        </>
    )

}