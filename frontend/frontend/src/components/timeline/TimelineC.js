import React from "react";

export function TimelineCompParent({plants, months}) {
    return (
        <div className={"sm-scroll"}>

            <div className="row text-center"><h3>timeline</h3></div>
            <div id="legend" className="row my-1">

                <div className="col col-3 p-2">


                    <div className="row dot bg-planting mb-3 mx-3">
                        <div className="mx-1">planting</div>
                    </div>
                </div>
                <div className="col col-3 p-2">
                    <div className="row dot bg-harvesting mx-3">
                        <div className="mx-1">harvesting</div>
                    </div>


                </div>

                <div className="col col-3 p-2 text-center">


                    <div className="row dot bg-flowering mb-3 mx-3">
                        <div className="mx-1">flowering</div>
                    </div>
                </div>
                <div className="col col-3 p-2">

                    <div className="row dot bg-sowing mx-3">
                        <div className="mx-1">sowing</div>
                    </div>


                </div>
            </div>


                <div className={"row g-0"}>


                    <div className="col col-sm-2 col-border">
                        <div className="col-header ">
                            <div className={"col"}>name</div>
                        </div>

                    </div>


                    <div className="col col-sm-10">

                        <div className="row year-grid g-0">

                            {months.map((month) => (
                                <div key={month} className=" col col-border">

                                    <div className="text-sage-light col-header">{month.toUpperCase()}</div>
                                </div>))}
                        </div>
                    </div>
                </div>

                <div className={"row g-0"}>

                    {plants.map(item =>
                        <React.Fragment key={item.id}>

                            <div className="col col-sm-2 col-border text-start">
                                <div key={item.id + '-' + item.name} className="pt-3 text-break">
                                    {item.name}
                                </div>
                                <div key={item.id + '-' + item.officialName} className="text-break">
                                    {item.officialName}
                                </div>
                            </div>


                            <div className="col col-sm-10">
                                <div className="row year-grid g-0 ">
                                    {months.map((month, index) => (
                                        <div key={month + '-' + index} className="col col-border pt-2 col-border-top">
                                            <TimelineComponent plant={item} MONTHS={months} month={month}/>
                                        </div>
                                    ))}
                                </div>
                            </div>
                        </React.Fragment>
                    )}
                </div>
            </div>


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
                    <div key={subcolNumber} className={"col"}>
                        {/* Sowing */}

                        <div key={plant.id + 'sowing'} className="mb-2 row d-flex justify-content-center">
                            {shouldShowDot(plant.sowing, subcolNumber)
                                ? (<div className="dot bg-sowing"></div>)
                                : (<div className="dot invisible"></div>)
                            }
                        </div>

                        {/* Harvest */}

                        <div key={plant.id + 'harvest'} className="mb-2  row d-flex justify-content-center">
                            {shouldShowDot(plant.harvest, subcolNumber)
                                ? (<div className="dot bg-harvesting"></div>)
                                : (<div className="dot invisible"></div>)
                            }
                        </div>

                        {/* Planting */}

                        <div key={plant.id + 'planting'} className="mb-2  row d-flex justify-content-center">
                            {shouldShowDot(plant.planting, subcolNumber)
                                ? (<div className="dot bg-planting"></div>)
                                : (<div className="dot invisible"></div>)
                            }
                        </div>

                        {/* Bloom */}

                        <div key={plant.id + 'bloom'} className="mb-2  row d-flex justify-content-center">
                            {shouldShowDot(plant.bloom, subcolNumber)
                                ? (<div className="dot bg-flowering"></div>)
                                : (<div className="dot invisible"></div>)
                            }
                        </div>


                    </div>
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
