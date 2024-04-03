import {Col, Container, Row} from "react-bootstrap";
import {useEffect, useState} from "react";
import {InformationC} from "./InformationC";
import {timelinesGetByPlant} from "../../constants/apiConstants";
import {SingleMonthTimeLine} from "../formHelpers/SingleMonthTimeLine";
import {months} from "../../constants/helper";
import {LoadingAnimation} from "../shared/LoadingAnimation";

export function Dashboard() {


    const [allTimes, setAllTimes] = useState([]);

    const [isLoading, setIsLoading] = useState(true)

    useEffect(() => {
        const fetchPromises = [
            fetch(timelinesGetByPlant)
                .then(response => response.json())
                .then(data => setAllTimes(data))
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

    const date = new Date();
    const month = date.getMonth();
    const monName = getMonthName(month);

    function getMonthName(number) {
        return months[number];
    }

    return (
        <>
            {isLoading ? (
                <div className="row min-vh-100 d-flex justify-content-center align-items-center">
                    <div className="col d-flex justify-content-center align-items-center">
                        <LoadingAnimation/>
                    </div>

                </div>
            ) : (
                <>



                    <div className="container container-style pb-5">
                        <InformationC/>
                        <div className="row g-5 mt-3">

                            <div className={"col col-md-12 col-lg-6 col-sm-12"}>

                                <div className="container border border-1 border-sage-light rounded-3 p-3">
                                    <SingleMonthTimeLine timelinePlants={allTimes} monthFilter={monName}
                                                         actionKey={"sowing"}/>
                                </div>
                            </div>

                            <div className={"col col-md-12 col-lg-6 col-sm-12"}>

                                <div className="container  border border-1 border-sage-light rounded-3 p-3">
                                    <SingleMonthTimeLine timelinePlants={allTimes} monthFilter={monName}
                                                         actionKey={"planting"}/>
                                </div>

                            </div>



                            <div className={"col col-md-12 col-lg-6 col-sm-12"}>

                                <div className="container  border border-1 border-sage-light rounded-3 p-3">
                                    <SingleMonthTimeLine timelinePlants={allTimes} monthFilter={monName}
                                                         actionKey={"harvest"}/>
                                </div>

                            </div>

                            <div className={"col col-md-12 col-lg-6 col-sm-12"}>

                                <div className="container  border border-1 border-sage-light rounded-3 p-3">
                                    <SingleMonthTimeLine timelinePlants={allTimes} monthFilter={monName}
                                                         actionKey={"bloom"}/>
                                </div>

                            </div>

                        </div>

                    </div>
                </>
            )}
        </>
    )
}
