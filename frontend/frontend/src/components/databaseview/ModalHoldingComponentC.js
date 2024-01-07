import React, {useEffect, useState} from "react";
import {Col, Modal, Row} from "react-bootstrap";
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
                className="edit-button"
                type="button"
                onClick={() => setShow(true)}>
                <i className='bx bx-pencil edit-pen-color'></i>
            </button>

            <Modal
                scrollable={true}
                size="lg"
                show={Show}
                onHide={() => handleHide(false)}
                fullscreen={true}
                aria-labelledby="planteditor"
                dialogClassName="modal-custom-bg">

                <Modal.Header closeButton>
                    <Modal.Title id="planteditor">

                    Edit


                    </Modal.Title>

                </Modal.Header>

                <Modal.Body>

                    <>

                        {isLoading ? (
                            <Row className="min-vh-100 d-flex justify-content-center align-items-center">
                                <Col className="d-flex justify-content-center align-items-center">
                                    <LoadingAnimation/>
                                </Col>

                            </Row>
                        ) : (
                            <>
                                <div className="text-end">
                                    <button className="custom-button rounded-3" onClick={deleteSingleEntry}>delete
                                    </button>
                                </div>
                                <EditPlantC plant={formData} afterSubmit={setShow} reload={reload}/>
                            </>
                        )}

                    </>

                </Modal.Body>

            </Modal>
        </>
    )


}