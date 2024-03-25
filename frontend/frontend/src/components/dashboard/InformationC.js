import React, {useEffect, useState} from "react";
import Carousel from 'react-bootstrap/Carousel';

import {forecastWeek, soilTemp, weatherCurrent} from "../../constants/apiConstants";
import {CarouselWeatherBack} from "../formHelpers/CarouselWeatherBack";
import {days, months} from "../../constants/helper";
import {LoadingAnimation} from "../shared/LoadingAnimation";
import {Container} from "react-bootstrap";


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
                <Container className="d-flex justify-content-center">
                    <LoadingAnimation/>
                </Container>
            ) : (


                <Carousel fade data-bs-theme="dark">

                    <Carousel.Item interval={8000}>
                        <CarouselWeatherBack/>
                        <Carousel.Caption>
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
                        </Carousel.Caption>
                    </Carousel.Item>

                    <Carousel.Item interval={8000}>
                        <CarouselWeatherBack/>
                        <Carousel.Caption>
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


                        </Carousel.Caption>
                    </Carousel.Item>
                    <Carousel.Item interval={8000}>
                        <CarouselWeatherBack/>
                        <Carousel.Caption>
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
                        </Carousel.Caption>
                    </Carousel.Item>
                </Carousel>


            )}
        </>
    )


}