import React from "react";
import {Col, Container, Row} from "react-bootstrap";
import {monthWeeks} from "../../constants/helper";

export function SingleMonthTimeLine({timelinePlants, actionKey, monthFilter}) {

    const filteredTl = filter(timelinePlants, actionKey)

    function plantObj(name, weekarray) {
        return {plant: name, time: weekarray}
    }

    function filter(allTimeLines, actionKey) {

        return allTimeLines.filter(plant => {
            let tl = plant[actionKey];
            return tl.some(time => monthWeeks[monthFilter].includes(time));
        }).map(plant => plantObj(plant.name, plant[actionKey]));
    }


    return (
        <>

            <div className="row mt-2 mb-3">
                <h5>{actionKey} in {monthFilter}</h5>
            </div>


            <Container className="scrollable-SingleMonthTl scrollbar-custom">
                <Row className="py-1">

                    {filteredTl.map((plant, index) => (

                        <Row key={index} className="my-3 ">
                            <Col>

                                {plant.plant}

                            </Col>
                            <Col>

                                <Row className="my-2">
                                    {[...Array(4)].map((_, idx) => (

                                        <Col key={idx}>
                                            {plant.time && monthWeeks[monthFilter] && plant.time.includes(monthWeeks[monthFilter][idx])
                                                ? <div className="dot bg-light opacity-75"></div>
                                                : <div className="dot bg-light opacity-25"></div>
                                            }

                                        </Col>


                                    ))}
                                </Row>

                            </Col>
                            <div className="p-2 border-bottom opacity-25"/>
                        </Row>


                    ))}

                </Row>
            </Container>
        </>
    )
}