import React from "react";
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

            <div className="g-1 text-center mb-3">
                <h5>{actionKey} in {monthFilter}</h5>
            </div>


            <div className="container scrollable-SingleMonthTl scrollbar-custom">
                <div className="row g-1">

                    {filteredTl.map((plant, index) => (

                        <div key={index} className="row g-1">
                            <div className={"col g-0"}>

                                {plant.plant}

                            </div>
                            <div className={"col g-0"}>

                                <div className="row">
                                    {[...Array(4)].map((_, idx) => (

                                        <div key={idx} className={"col pt-2"}>
                                            {plant.time && monthWeeks[monthFilter] && plant.time.includes(monthWeeks[monthFilter][idx])
                                                ? <div className="dot bg-light opacity-75"></div>
                                                : <div className="dot bg-light opacity-25"></div>
                                            }

                                        </div>


                                    ))}
                                </div>

                            </div>
                            <div className="p-1 mb-1 border-bottom border-sage-light"/>
                        </div>

                    ))}

                </div>
            </div>
        </>
    )
}