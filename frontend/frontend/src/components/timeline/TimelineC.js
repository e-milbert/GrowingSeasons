import {Col, Row} from "react-bootstrap";
import React from "react";

export function TimelineCompParent({plants, months}) {
    return (
        <Row>

            <Row className="text-center"><h3>timeline</h3></Row>
            <Row id="legend" className="col-4 my-4">

                <Col className="col-3 mx-2 p-4">


                    <div className="row dot plant mb-3 mx-5">
                        <div className="mx-1">planting</div>
                    </div>
                    <div className="row dot harvest mx-5">
                        <div className="mx-1">harvesting</div>
                    </div>


                </Col>

                <Col className="col-3 mx-2 p-4 text-center">


                    <div className="row dot bloom mb-3 mx-5">
                        <div className="mx-1">flowering</div>
                    </div>

                    <div className="row dot sow mx-5">
                        <div className="mx-1">sowing</div>
                    </div>


                </Col>
            </Row>


            <Row>


                <Col className="col-1 col-border">
                    <div className="col-header ">
                        <Col>name</Col>
                    </div>

                </Col>


                <Col className="col-11">

                    <Row className="year-grid">

                        {months.map((month) => (
                            <Col key={month} className="col-border">

                                <div className="col-header ">{month.toUpperCase()}</div>
                            </Col>))}
                    </Row>
                </Col>
            </Row>

            <Row>

                {plants.map(item =>
                    <React.Fragment key={item.id}>

                        <Col className="col-1 col-border">
                            <div key={item.id + '-' + item.name} className="pt-3">
                                {item.name}
                            </div>
                            <div>
                                {item.officialName}
                            </div>
                        </Col>


                        <Col className="col-11">
                            <Row className="year-grid">
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
                                ? (<div className="dot sow"></div>)
                                : (<div className="dot invisible"></div>)
                            }
                        </div>

                        {/* Harvest */}

                        <div key={plant.id + 'harvest'} className="mb-2">
                            {shouldShowDot(plant.harvest, subcolNumber)
                                ? (<div className="dot harvest"></div>)
                                : (<div className="dot invisible"></div>)
                            }
                        </div>

                        {/* Planting */}

                        <div key={plant.id + 'planting'} className="mb-2">
                            {shouldShowDot(plant.planting, subcolNumber)
                                ? (<div className="dot plant"></div>)
                                : (<div className="dot invisible"></div>)
                            }
                        </div>

                        {/* Bloom */}

                        <div key={plant.id + 'bloom'} className="mb-2">
                            {shouldShowDot(plant.bloom, subcolNumber)
                                ? (<div className="dot bloom"></div>)
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
