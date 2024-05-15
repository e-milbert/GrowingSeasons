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

            onChange(newTimeLine);
            return newTimeLine;
        });
    };

    const getButtonStyle = (subcolNumber) => {
        return timeLine.includes(subcolNumber) ? "m-0 p-0 selection-button selection-button-chosen" : "m-0 p-0 selection-button selection-button-notchosen";
    };

    return (
        <div className="container container-bg-secondary rounded-3 p-3 mb-2">
            <div className="row flex-fill">
                <div className="col col-1 col-border">
                    <div className="col-header  mt-3">
                        {actionName}
                    </div>
                </div>

                <div className="col col-11">
                    <div className="row year-grid">
                        {monthsShort.map((month, monthIndex) => {
                            const startSubcol = monthIndex * 4 + 1;
                            const endSubcol = startSubcol + 3;

                            return (

                                <div key={month} className="col col-border">
                                    <div className="col-header mb-0">{month.toUpperCase()}</div>
                                    <div className={"row"}>
                                        {[startSubcol, startSubcol + 1, startSubcol + 2, endSubcol].map(subcolNumber => (
                                            <div key={subcolNumber} className="col my-1 col-border-top mx-0 p-0">
                                                <button
                                                    className={getButtonStyle(subcolNumber)}
                                                    onClick={() => addActionToTimeLine(subcolNumber)}

                                                    type="button">

                                                </button>
                                            </div>
                                        ))}
                                    </div>
                                </div>
                            );
                        })}
                    </div>
                </div>
            </div>
        </div>
    );
}

//TODO need to fix duplication;

export function QuarterMonthTimeLinePickerEdit({actionName, onChange, preselected}) {
    const [timeLine, setTimeLine] = useState(preselected);


    const addActionToTimeLine = (subcolNumber) => {
        setTimeLine(prevTimeLine => {
            const newTimeLine = prevTimeLine.includes(subcolNumber)
                ? prevTimeLine.filter(item => item !== subcolNumber)
                : [...prevTimeLine, subcolNumber];

            onChange(newTimeLine);
            return newTimeLine;
        });
    };

    const getButtonStyle = (subcolNumber) => {
        return timeLine.includes(subcolNumber) ? "m-0 p-0 selection-button selection-button-chosen" : "m-0 p-0 selection-button selection-button-notchosen";
    };

    return (
        <div className="container container-bg-secondary rounded-3 p-3 mb-2">
            <div className="row flex-fill">
                <div className="colcol-1 col-border">
                    <div className="col-header  mt-3">
                        {actionName}
                    </div>
                </div>

                <div className="col col-11">
                    <div className="row year-grid">
                        {monthsShort.map((month, monthIndex) => {
                            const startSubcol = monthIndex * 4 + 1;
                            const endSubcol = startSubcol + 3;

                            return (

                                <div key={month} className="colcol-border">
                                    <div className="col-header mb-0">{month.toUpperCase()}</div>
                                    <div className={"row"}>
                                        {[startSubcol, startSubcol + 1, startSubcol + 2, endSubcol].map(subcolNumber => (
                                            <div key={subcolNumber} className="col my-1 col-border-top mx-0 p-0">
                                                <button
                                                    className={getButtonStyle(subcolNumber)}
                                                    onClick={() => addActionToTimeLine(subcolNumber)}

                                                    type="button">

                                                </button>
                                            </div>
                                        ))}
                                    </div>
                                </div>
                            );
                        })}
                    </div>
                </div>
            </div>
        </div>
    );
}
