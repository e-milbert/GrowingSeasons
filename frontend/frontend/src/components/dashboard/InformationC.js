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

                            <table className="table text-black table-bg-transp-blur ">
                                <thead>
                                <tr>
                                    <th colSpan="2"><h4>{date} {currentWeather["time"]}</h4></th>
                                </tr>
                                </thead>
                                <tbody className="text-start fw-semibold">
                                <tr>
                                    <td>Temperature:</td>
                                    <td>{currentWeather["temperature_2m"]}</td>
                                </tr>
                                <tr>
                                    <td>Rain:</td>
                                    <td>{currentWeather["rain"]}</td>
                                </tr>
                                <tr>
                                    <td>Showers:</td>
                                    <td>{currentWeather["showers"]}</td>
                                </tr>
                                <tr>
                                    <td>Snow:</td>
                                    <td>{currentWeather["snowfall"]}</td>
                                </tr>
                                <tr>
                                    <td>Clouds:</td>
                                    <td>{currentWeather["cloud_cover"]}</td>
                                </tr>
                                <tr>
                                    <td>Wind:</td>
                                    <td>~ {currentWeather["wind_speed_10m"]} - {currentWeather["wind_gusts_10m"]}</td>
                                </tr>
                                </tbody>
                            </table>
                        </Carousel.Caption>
                    </Carousel.Item>

                    <Carousel.Item interval={8000}>
                        <CarouselWeatherBack/>
                        <Carousel.Caption>

                            <table className="table text-black table-bg-transp-blur ">
                                <thead>
                                <tr>
                                    <th colSpan="3"><h4>soil conditions this week</h4></th>
                                </tr>
                                <tr className="text-start">
                                    <th>
                                    </th>
                                    <th>
                                        temperature
                                    </th>
                                    <th>
                                        avg. moisture
                                    </th>
                                </tr>
                                </thead>
                                <tbody className="text-start fw-semibold ">
                                <tr>
                                    <td>surface:</td>
                                    <td>{soilTemps["tempAt0cmMin"]} up
                                        to {soilTemps["tempAt0cmMax"]}</td>
                                    <td>
                                        {soilTemps["moistureTo1cm"]}%
                                    </td>
                                </tr>
                                <tr>
                                    <td>around 6cm:</td>
                                    <td>{soilTemps["tempAt6cmMin"]} up
                                        to {soilTemps["tempAt6cmMax"]}</td>
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
                            <table className="table table-sm text-black table-bg-transp-blur ">
                                <thead>
                                <tr>
                                    <th colSpan="6"><h4>forecast</h4></th>
                                </tr>
                                <tr className="text-start">
                                    <th>
                                        date
                                    </th>
                                    <th>
                                        temperatures
                                    </th>
                                    <th>
                                        sun
                                    </th>
                                    <th>
                                        rain
                                    </th>
                                    <th>
                                        wind
                                    </th>
                                    <th>
                                        uv
                                    </th>
                                </tr>
                                </thead>
                                <tbody className="text-start fw-semibold ">
                                {forecast.map((item, index) => (
                                    <tr key={index}>
                                        <td>{item.date}</td>
                                        <td>{item.tempMax}° / {item.tempMin}°</td>
                                        <td>{item.sunshine} hrs</td>
                                        <td>{item.rain + item.shower} mm</td>
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