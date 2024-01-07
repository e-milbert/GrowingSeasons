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
                <Row className="min-vh-100 d-flex justify-content-center align-items-center">
                    <Col className="d-flex justify-content-center align-items-center">
                        <LoadingAnimation/>
                    </Col>

                </Row>
            ) : (
                <>
                    <InformationC/>


                    <Container className="bg-container rounded-3">

                        <Row className="col mb-4 pt-3">

                            <Col className="col-6">

                                <Container className="bg-transparent text-center ">
                                    <SingleMonthTimeLine timelinePlants={allTimes} monthFilter={monName}
                                                         actionKey={"sowing"}/>
                                </Container>
                            </Col>

                            <Col className="col-6">

                                <Container className="bg-transparent text-center">
                                    <SingleMonthTimeLine timelinePlants={allTimes} monthFilter={monName}
                                                         actionKey={"planting"}/>
                                </Container>

                            </Col>

                        </Row>

                        <Row className="col">

                            <Col className="col-6">

                                <Container className="bg-transparent text-center">
                                    <SingleMonthTimeLine timelinePlants={allTimes} monthFilter={monName}
                                                         actionKey={"harvest"}/>
                                </Container>

                            </Col>

                            <Col className="col-6">

                                <Container className="bg-transparent text-center">
                                    <SingleMonthTimeLine timelinePlants={allTimes} monthFilter={monName}
                                                         actionKey={"bloom"}/>
                                </Container>

                            </Col>

                        </Row>

                    </Container>
                </>
            )}
        </>
    )
}
