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

            <div className="row">
                <div className="col">
                    <div className="container bg-container d-flex justify-content-center p-5 rounded-3">
                        <div className="card bg-transparent m-2 p-2 border-0">
                            <h5 className="card-title">Weather Settings</h5>
                            <div className="card-body">
                                <div className="row d-flex align-items-center">
                                    <div className="col">
                                        <OneLineFormC
                                            handleInputChangeFunction={handleLatitude}
                                            idName={"lat"}
                                            labelText={"Latitude"}
                                            placeholderText={""}
                                        />
                                    </div>
                                    <div className="col">
                                        <OneLineFormC
                                            handleInputChangeFunction={handleLongitude}
                                            idName={"long"}
                                            labelText={"Longitude"}
                                            placeholderText={""}
                                        />
                                    </div>
                                    <div className="col">
                                        {!loadingWeatherUpdate ? (
                                            <button
                                                className="btn btn-sage-light text-black p-2"
                                                type="button"
                                                onClick={updateWeather}
                                            >
                                                Submit
                                            </button>
                                        ) : (
                                            <i className='bx bxs-florist text-white bx-tada bx-md'></i>
                                        )}
                                    </div>
                                </div>
                                {weatherPreference.latitude > 90 || weatherPreference.latitude < -90 || weatherPreference.longitude > 180 || weatherPreference.longitude < -180 ? (
                                    <div className="row">
                                        <div className="col">
                                            <div className="ms-5 fs-6 fw-normal text-warning-emphasis">
                                                Latitude is from -90 to 90 and longitude is from -180 to 180
                                            </div>
                                        </div>
                                    </div>
                                ) : null}
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </>
    )

}

