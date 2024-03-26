import React, {useEffect, useState} from "react";
import {timelinesGetByPlant} from "../../constants/apiConstants";
import {TimelineCompParent} from "./TimelineC";
import {monthsShort} from "../../constants/helper";
import {Col, Container, Row} from "react-bootstrap";
import {LoadingAnimation} from "../shared/LoadingAnimation";

export function TimeLineTable() {

    const [allPlantsTimes, setAllPlantsTimes] = useState([]);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        const fetchPromises = [
            fetch(timelinesGetByPlant)
                .then(response => response.json())
                .then(data => setAllPlantsTimes(data))
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
                <div className="row min-vh-100 d-flex justify-content-center align-items-center">
                    <div className="col d-flex justify-content-center align-items-center">
                        <LoadingAnimation/>
                    </div>
                </div>
            ) : (
                <div className="container container-style text-center py-3">
                    <TimelineCompParent months={monthsShort} plants={allPlantsTimes}/>
                </div>
            )}
        </>
    )


}