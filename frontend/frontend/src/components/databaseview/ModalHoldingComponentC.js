import React, {useEffect, useState} from "react";
import {plantDeletePlant, plantGetSingleById} from "../../constants/apiConstants";
import {EditPlantC} from "./EditPlantC";
import {LoadingAnimation} from "../shared/LoadingAnimation";


export function ModalHoldingComponentC({data, reload}) {
    const [needReload, setNeedReload] = useState(false)
    const [wasManipulated, setWasManipulated] = useState(false)
    const [Show, setShow] = useState(false);
    const [formData, setFormData] = useState({});
    const [isLoading, setIsLoading] = useState(true);
    const [isDeleted, setIsDeleted] = useState(false);


    useEffect(() => {
        const fetchPromises = [
            fetch(plantGetSingleById.replace("{id}", data.id))
                .then(response => response.json())
                .then(data => setFormData(data))
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


    function handleHide(boolValue) {
        setShow(boolValue);
    }

    useEffect(() => {
        if (isDeleted) {
            handleHide(false)

        }
    }, [isDeleted]);

    const deleteSingleEntry = () => {

        let id = formData.id;
        fetch(plantDeletePlant.replace("{id}", id), {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json"
            },
        })
            .then((response) => {
                if (!response.ok) {
                    console.log("error")
                }
                return response.json();
            })
            .then((data) => {
                setIsDeleted(data)
                reload();
            })
            .catch((error) => {
                console.error('Error:', error);
            });

    }


    return (
        <>

            <button
                className="btn btn-mini bg-transparent fs-5 m-0 p-0"
                type="button"
                data-bs-toggle="modal"
                data-bs-target="#editModal">
                <i className='bx bx-pencil'></i>
            </button>


            <div className="modal fade" tabIndex="-1" aria-labelledby="planteditor" id={"editModal"} aria-hidden="true">

                <div className={"modal-dialog modal-fullscreen modal-dialog-scrollable "}>

                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title" id="planteditor">
                                Edit
                            </h5>

                            <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"/>

                        </div>


                        <div className="modal-body">

                            <>

                                {isLoading ? (
                                    <div className="row min-vh-100 d-flex justify-content-center align-items-center">
                                        <div className=" col d-flex justify-content-center align-items-center">
                                            <LoadingAnimation/>
                                        </div>

                                    </div>
                                ) : (
                                    <>
                                        <div className="text-end">
                                            <button className="btn btn-sage-light rounded-3"
                                                    onClick={deleteSingleEntry}>delete
                                            </button>
                                        </div>
                                        <EditPlantC plant={formData} afterSubmit={setShow} reload={reload}/>
                                    </>
                                )}

                            </>

                        </div>
                    </div>

                </div>
            </div>
        </>

    )


}