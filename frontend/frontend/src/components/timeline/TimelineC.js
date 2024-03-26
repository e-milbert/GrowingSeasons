import {Col, Row} from "react-bootstrap";
import React from "react";

export function TimelineCompParent({plants, months}) {
    return (
        <Row>

            <Row className="text-center"><h3>timeline</h3></Row>
            <Row id="legend" className=" my-1">

                <Col className="col-3 p-2">


                    <div className="row dot bg-planting mb-3 mx-3">
                        <div className="mx-1">planting</div>
                    </div>
                </Col>
                <Col className="col-3 p-2">
                    <div className="row dot bg-harvesting mx-3">
                        <div className="mx-1">harvesting</div>
                    </div>


                </Col>

                <Col className="col-3 p-2 text-center">


                    <div className="row dot bg-flowering mb-3 mx-3">
                        <div className="mx-1">flowering</div>
                    </div>
                </Col>
                <Col className="col-3 p-2">

                    <div className="row dot bg-sowing mx-3">
                        <div className="mx-1">sowing</div>
                    </div>


                </Col>
            </Row>


            <Row className={"g-0"}>


                <Col className="col-2 col-border">
                    <div className="col-header ">
                        <Col>name</Col>
                    </div>

                </Col>


                <Col className="col-10">

                    <Row className="year-grid g-0">

                        {months.map((month) => (
                            <Col key={month} className="col-border">

                                <div className="col-header ">{month.toUpperCase()}</div>
                            </Col>))}
                    </Row>
                </Col>
            </Row>

            <Row className={"g-0"}>

                {plants.map(item =>
                    <React.Fragment key={item.id}>

                        <Col className="col-2 col-border">
                            <div key={item.id + '-' + item.name} className="pt-3 overflow-auto">
                                {item.name}
                            </div>
                            <div key={item.id + '-' + item.officialName} className="overflow-auto">
                               {item.officialName}
                            </div>
                        </Col>


                        <Col className="col-10">
                            <Row className="year-grid g-0">
                                {months.map((month, index) => (
                                    <Col key={month + '-' + index} className="col-border pt-2 col-border-top">
                                        <TimelineComponent plant={item} MONTHS={months} month={month}/>
                                    </Col>
                                ))}
                            </Row>
                        </Col>
                    </React.Fragment>
                )}
            </Row>
        </Row>

    )

}


export function TimelineComponent({plant, MONTHS, month}) {

    // Calculate the subcolumn range for each month
    const startSubcol = MONTHS.indexOf(month) * 4 + 1;
    const endSubcol = startSubcol + 3;

    const shouldShowDot = (eventArray, subcolNumber) => {
        return eventArray.includes(subcolNumber);
    };


    const renderInnerColumns = () => (
        <>
            {[startSubcol, startSubcol + 1, startSubcol + 2, endSubcol].map(subcolNumber => {
                return (
                    <Col key={subcolNumber}>
                        {/* Sowing */}

                        <div key={plant.id + 'sowing'} className="mb-2">
                            {shouldShowDot(plant.sowing, subcolNumber)
                                ? (<div className="dot bg-sowing"></div>)
                                : (<div className="dot invisible"></div>)
                            }
                        </div>

                        {/* Harvest */}

                        <div key={plant.id + 'harvest'} className="mb-2">
                            {shouldShowDot(plant.harvest, subcolNumber)
                                ? (<div className="dot bg-harvesting"></div>)
                                : (<div className="dot invisible"></div>)
                            }
                        </div>

                        {/* Planting */}

                        <div key={plant.id + 'planting'} className="mb-2">
                            {shouldShowDot(plant.planting, subcolNumber)
                                ? (<div className="dot bg-planting"></div>)
                                : (<div className="dot invisible"></div>)
                            }
                        </div>

                        {/* Bloom */}

                        <div key={plant.id + 'bloom'} className="mb-2">
                            {shouldShowDot(plant.bloom, subcolNumber)
                                ? (<div className="dot bg-flowering"></div>)
                                : (<div className="dot invisible"></div>)
                            }
                        </div>


                    </Col>
                );
            })}
        </>
    );

    return (
        <div key={plant.id + startSubcol} className="mon-grid">
            {renderInnerColumns()}
        </div>
    );
}
