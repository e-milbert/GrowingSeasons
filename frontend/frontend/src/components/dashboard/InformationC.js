import React, {useEffect, useState} from "react";

import {forecastWeek, soilTemp, weatherCurrent} from "../../constants/apiConstants";
import {days, months} from "../../constants/helper";
import {LoadingAnimation} from "../shared/LoadingAnimation";
import {Container} from "react-bootstrap";
import sunnycloud from "../../images/sunnycloud.jpg"


export function InformationC() {

    const [date, setDate] = useState('');

    const [currentWeather, setCurrentWeather] = useState({});
    const [soilTemps, setSoilTemps] = useState({});
    const [forecast, setForecast] = useState([]);

    const [isLoading, setIsLoading] = useState(true);


    function setCurrentDate() {
        let date = new Date();
        let weekday = days[date.getDay()];

        let day = date.getDate();
        let dayAsStr;

        let month = months[date.getMonth()]

        switch (date.getDate()) {
            case 1:
                dayAsStr = day.toString() + 'st'
                break;
            case 21:
                dayAsStr = day.toString() + 'st'
                break;
            case 31:
                dayAsStr = day.toString() + 'st'
                break;
            case 2:
                dayAsStr = day.toString() + 'nd'
                break;
            case 3:
                dayAsStr = day.toString() + 'rd'
                break;
            case 22:
                dayAsStr = day.toString() + 'nd'
                break;
            case 23:
                dayAsStr = day.toString() + 'rd'
                break;
            default:
                dayAsStr = day.toString() + 'th'
        }

        setDate(weekday + ', ' + dayAsStr + ' of ' + month);
    }

    useEffect(() => {
        setCurrentDate();

    }, []);
    useEffect(() => {
        const fetchPromises = [
            fetch(weatherCurrent)
                .then(response => response.json())
                .then(data => {
                    setCurrentWeather(data);

                }),
            fetch(soilTemp)
                .then(response => response.json())
                .then(data => {
                    setSoilTemps(data);

                }),
            fetch(forecastWeek)
                .then(response => response.json())
                .then(data => {
                    setForecast(data)
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

    const forecastDateWithoutYear = (dateString) => {
        const parts = dateString.split("-")
        return (
            `${parts[1]}-${parts[2]}`
        )
    }


    return (
        <>
            {isLoading ? (
                <div className="container d-flex justify-content-center">
                    <LoadingAnimation/>
                </div>
            ) : (

                <div className={"rounded-3 pt-4 d-none d-md-block"}>

                    <div id="weatherForecast" className="carousel carousel-dark slide"
                         data-bs-ride="carousel">
                        <div className="carousel-indicators">
                            <button type="button" data-bs-target="#weatherForecast" data-bs-slide-to="0"
                                    className="active" aria-current="true" aria-label="current weather"></button>
                            <button type="button" data-bs-target="#weatherForecast" data-bs-slide-to="1"
                                    aria-label="soil temperatures"></button>
                            <button type="button" data-bs-target="#weatherForecast" data-bs-slide-to="2"
                                    aria-label="forecast"></button>
                        </div>
                        <div className="carousel-inner">

                            <img src={sunnycloud} className="d-block w-100" alt="sun and clouds in the sky"/>

                            <div className="carousel-item active " data-bs-interval="8000">
                                <div className="carousel-caption ">

                                    <h4>{date} {currentWeather["time"]}</h4>

                                    <table className="table text-black table-bg-transp-blur ">
                                        <tbody className="text-start fw-semibold">
                                        <tr>
                                            <td><i className='bx bxs-thermometer bx-sm'/></td>
                                            <td>{currentWeather["temperature_2m"]}</td>

                                            <td><i className={"bx bx-cloud-drizzle bx-sm"}/></td>
                                            <td>{currentWeather["rain"]}</td>
                                        </tr>
                                        <tr>
                                            <td><i className={"bx bx-cloud bx-sm"}/></td>
                                            <td>{currentWeather["cloud_cover"]}</td>

                                            <td><i className={"bx bx-cloud-rain bx-sm"}/></td>
                                            <td>{currentWeather["showers"]}</td>
                                        </tr>
                                        <tr>
                                            <td><i className={"bx bx-wind bx-sm"}/></td>
                                            <td>{currentWeather["wind_speed_10m"]} - {currentWeather["wind_gusts_10m"]}</td>

                                            <td><i className={"bx bx-cloud-snow bx-sm"}/></td>
                                            <td>{currentWeather["snowfall"]}</td>
                                        </tr>

                                        </tbody>
                                    </table>
                                </div>
                            </div>

                            <div className="carousel-item" data-bs-interval="8000">
                                <div className="carousel-caption d-none d-md-block">

                                    <h4 className={"text-center"}>soil conditions this week</h4>
                                    <table className="table text-black table-bg-transp-blur ">
                                        <thead>
                                        <tr>
                                            <th scope={"col"} colSpan={1} className={"text-start"}>
                                            </th>
                                            <th scope={"col"} colSpan={1} className={"text-start"}>
                                                <i className='bx bxs-thermometer bx-sm'/>
                                            </th>
                                            <th scope={"col"} colSpan={1} className={"text-start"}>
                                                <i className='bx bx-droplet bx-sm'></i>
                                            </th>
                                        </tr>
                                        </thead>

                                        <tbody className="text-start fw-semibold ">
                                        <tr>
                                            <td>surface:</td>
                                            <td>{soilTemps["tempAt0cmMin"]} to {soilTemps["tempAt0cmMax"]}</td>
                                            <td>
                                                {soilTemps["moistureTo1cm"]}%
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>around 6cm:</td>
                                            <td>{soilTemps["tempAt6cmMin"]} to {soilTemps["tempAt6cmMax"]}</td>
                                            <td>
                                                {soilTemps["moisture3To9cm"]}%
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>

                            <div className="carousel-item" data-bs-interval="8000">
                                <div className="carousel-caption d-none d-md-block">

                                    <h4>forecast</h4>
                                    <table className="table table-sm text-black table-bg-transp-blur ">
                                        <thead>
                                        <tr>
                                            <th scope={"col"}>

                                            </th>
                                            <th scope={"col"} className="text-start">
                                                <i className='bx bxs-thermometer bx-sm'/>
                                            </th>
                                            <th scope={"col"} className="text-start">
                                                <i className='bx bx-sun bx-sm'/>
                                            </th>
                                            <th scope={"col"} className="text-start">
                                                <i className={"bx bx-cloud-drizzle bx-sm"}/>
                                            </th>
                                            <th scope={"col"} className="text-start">
                                                <i className={"bx bx-wind bx-sm"}/>
                                            </th>
                                            <th scope={"col"} className="text-start">
                                                uv
                                            </th>
                                        </tr>
                                        </thead>

                                        <tbody className="text-start fw-semibold ">
                                        {forecast.map((item, index) => (
                                            <tr key={index}>
                                                <td>{forecastDateWithoutYear(item.date)}</td>
                                                <td>{item.tempMax}° | {item.tempMin}°</td>
                                                <td>{item.sunshine} hrs</td>
                                                <td>{(item.rain + item.shower).toFixed(1)} mm</td>
                                                <td>{item.wind} km/h</td>
                                                <td>{item.uv}</td>
                                            </tr>

                                        ))}
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <button className="carousel-control-prev" type="button" data-bs-target="#weatherForecast"
                                data-bs-slide="prev">
                            <span className="carousel-control-prev-icon" aria-hidden="true"></span>
                            <span className="visually-hidden">Previous</span>
                        </button>
                        <button className="carousel-control-next" type="button" data-bs-target="#weatherForecast"
                                data-bs-slide="next">
                            <span className="carousel-control-next-icon" aria-hidden="true"></span>
                            <span className="visually-hidden">Next</span>
                        </button>
                    </div>
                </div>
            )}
        </>
    )


}