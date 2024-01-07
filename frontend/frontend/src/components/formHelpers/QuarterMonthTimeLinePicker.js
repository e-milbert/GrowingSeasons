import React, {useState} from "react";
import {Button, Col, Container, Row} from "react-bootstrap";
import {monthsShort} from "../../constants/helper";


export function QuarterMonthTimeLinePicker2({actionName, onChange}) {
    const [timeLine, setTimeLine] = useState([]);

    const addActionToTimeLine = (subcolNumber) => {
        setTimeLine(prevTimeLine => {
            const newTimeLine = prevTimeLine.includes(subcolNumber)
                ? prevTimeLine.filter(item => item !== subcolNumber)
                : [...prevTimeLine, subcolNumber];

            onChange(newTimeLine);  // Now using the updated timeline
            return newTimeLine;
        });
    };

    const getButtonStyle = (subcolNumber) => {
        return timeLine.includes(subcolNumber) ? "m-0 p-0 selection-button selection-button-chosen" : "m-0 p-0 selection-button selection-button-notchosen";
    };

    return (
        <Container className="container-bg-secondary rounded-3 p-3 mb-2">
            <Row className="flex-fill">
                <Col className="col-1 col-border">
                    <div className="col-header  mt-3">
                        {actionName}
                    </div>
                </Col>

                <Col className="col-11">
                    <Row className="year-grid">
                        {monthsShort.map((month, monthIndex) => {
                            const startSubcol = monthIndex * 4 + 1;
                            const endSubcol = startSubcol + 3;

                            return (

                                <Col key={month} className="col-border">
                                    <div className="col-header mb-0">{month.toUpperCase()}</div>
                                    <Row>
                                        {[startSubcol, startSubcol + 1, startSubcol + 2, endSubcol].map(subcolNumber => (
                                            <Col key={subcolNumber} className="my-1 col-border-top mx-0 p-0">
                                                <Button
                                                    className={getButtonStyle(subcolNumber)}
                                                    onClick={() => addActionToTimeLine(subcolNumber)}

                                                    type="button">

                                                </Button>
                                            </Col>
                                        ))}
                                    </Row>
                                </Col>
                            );
                        })}
                    </Row>
                </Col>
            </Row>
        </Container>
    );
}


export function QuarterMonthTimeLinePickerEdit({actionName, onChange, preselected}) {
    const [timeLine, setTimeLine] = useState(preselected);


    const addActionToTimeLine = (subcolNumber) => {
        setTimeLine(prevTimeLine => {
            const newTimeLine = prevTimeLine.includes(subcolNumber)
                ? prevTimeLine.filter(item => item !== subcolNumber)
                : [...prevTimeLine, subcolNumber];

            onChange(newTimeLine);  // Now using the updated timeline
            return newTimeLine;
        });
    };

    const getButtonStyle = (subcolNumber) => {
        return timeLine.includes(subcolNumber) ? "m-0 p-0 selection-button selection-button-chosen" : "m-0 p-0 selection-button selection-button-notchosen";
    };

    return (
        <Container className="container-bg-secondary rounded-3 p-3 mb-2">
            <Row className="flex-fill">
                <Col className="col-1 col-border">
                    <div className="col-header  mt-3">
                        {actionName}
                    </div>
                </Col>

                <Col className="col-11">
                    <Row className="year-grid">
                        {monthsShort.map((month, monthIndex) => {
                            const startSubcol = monthIndex * 4 + 1;
                            const endSubcol = startSubcol + 3;

                            return (

                                <Col key={month} className="col-border">
                                    <div className="col-header mb-0">{month.toUpperCase()}</div>
                                    <Row>
                                        {[startSubcol, startSubcol + 1, startSubcol + 2, endSubcol].map(subcolNumber => (
                                            <Col key={subcolNumber} className="my-1 col-border-top mx-0 p-0">
                                                <Button
                                                    className={getButtonStyle(subcolNumber)}
                                                    onClick={() => addActionToTimeLine(subcolNumber)}

                                                    type="button">

                                                </Button>
                                            </Col>
                                        ))}
                                    </Row>
                                </Col>
                            );
                        })}
                    </Row>
                </Col>
            </Row>
        </Container>
    );
}
