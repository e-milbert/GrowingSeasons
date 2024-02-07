import {Button, Card, Col, Container, Row} from "react-bootstrap";
import {OneLineFormC} from "../formHelpers/OneLineFormC";
import {useState} from "react";
import {updatePostWeatherSettings} from "../../constants/apiConstants";

export function Setting() {

    const defaultPref = {
        id: 1,
        longitude: 52.51,
        latitude: 13.37,

    }


    const [weatherPreference, setWeatherPreference] = useState(defaultPref);
    const [loadingWeatherUpdate, setLoadingWeatherUpdate] = useState(false);

    const updateWeather = async () => {


        if (weatherPreference.latitude <= 90 && weatherPreference.latitude >= -90 && weatherPreference.longitude <= 180 && weatherPreference.longitude >= -180) {
            setLoadingWeatherUpdate(true);

            try {
                const settings = JSON.stringify(weatherPreference);

                const response = await fetch(updatePostWeatherSettings, {
                    method: "POST",
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: settings
                });

                if (response.ok) {
                    console.log("preferences updated")
                } else if (response.status === 503) {
                    console.log("service is not available, weather data cannot be retrieved from third party")
                } else if (response.status === 400) {
                    console.log("something is wrong with the request url sent to the weather data provider. check logs")
                }
            } catch (error) {
                console.log(error);
            }

            setLoadingWeatherUpdate(false);

        }
    }
    const handleLatitude = (value) => {

        setWeatherPreference({...weatherPreference, latitude: value})


    }
    const handleLongitude = (value) => {
        setWeatherPreference({...weatherPreference, longitude: value})
    }


//TODO settings for weather api: longitude, latitude, timezone(should be dropdown)

    return (
        <>

            <Row>
                <Col>
                    <Container className={"bg-container d-flex justify-content-center p-5 rounded-3"}>
                        <Card className={"bg-transparent m-2 p-2 border-0"}>
                            <Card.Title className={"text-white"}>weather settings</Card.Title>
                            <Card.Body>
                                <Row className={"d-flex justify-content-evenly"}>
                                    <Col>
                                        <OneLineFormC handleInputChangeFunction={handleLatitude}
                                                      idName={"lat"}
                                                      labelText={"Latitude"} placeholderText={""}/>

                                    </Col>
                                    <Col>
                                        <OneLineFormC handleInputChangeFunction={handleLongitude}
                                                      idName={"long"}
                                                      labelText={"Longitude"} placeholderText={""}/>
                                    </Col>
                                    <Col>
                                        {!loadingWeatherUpdate ?
                                            <Button
                                                className={"custom-button text-black p-2"} type={"button"}
                                                onClick={updateWeather}>submit</Button> :
                                            <i className='bx bxs-florist text-white bx-tada bx-md'/>
                                        }
                                    </Col>
                                </Row>
                                {weatherPreference.latitude > 90 || weatherPreference.latitude < -90 || weatherPreference.longitude > 180 || weatherPreference.longitude < -180 ?
                                    <Row>
                                        <Col>
                                            <div className={"ms-5 fs-6 fw-normal text-warning-emphasis"}>latitude is
                                                from -180 to 180and longitude is from -90 to 90
                                            </div>
                                        </Col>
                                    </Row> : null
                                }
                            </Card.Body>

                        </Card>
                    </Container>
                </Col>
            </Row>
        </>
    )

}

