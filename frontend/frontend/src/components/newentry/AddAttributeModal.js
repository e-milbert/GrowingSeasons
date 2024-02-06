import React, {useState} from "react";
import {Modal} from "react-bootstrap";
import {LoadingAnimation} from "../shared/LoadingAnimation";
import {OneLineFormC} from "../formHelpers/OneLineFormC";


export function AddAttributeModal({setIsShowing, typeName, isShowing, setDataCallback, validationType}) {

    const [Show, setShow] = useState(isShowing);
    const [isLoading, setIsLoading] = useState(false);
    const [inputValue, setInputValue] = useState("");
    const [isAddDisabled, setIsAddDisabled] = useState(true)


    const updateValue = (value) => {
        if (validationType.toString().toLowerCase() === "string") {
            if (value.toString().trim().length >= 3) {
                setInputValue(value);
                setIsAddDisabled(false);
            } else {
                setInputValue("");
                setIsAddDisabled(true);
            }
        } else if (validationType.toString().toLowerCase() === "number") {

            if (!isNaN(value)) {
                setInputValue(value);
                setIsAddDisabled(false);
            } else {
                setInputValue("");
                setIsAddDisabled(true);

            }

        }
    }

    const handleSubmit = () => {

        setDataCallback(inputValue);

    }


    function handleHide(boolValue) {
        setShow(boolValue);
        return (
            setIsShowing(boolValue)
        )
    }

    return (
        <>
            <Modal
                size={"lg"}

                show={Show}
                onHide={() => handleHide(false)}

                dialogClassName="modal-custom-bg"
            >

                <Modal.Header closeButton>

                </Modal.Header>

                <Modal.Body>

                    <>

                        {isLoading ? (
                            <div className={"text-center"}>
                                <LoadingAnimation/>
                            </div>


                        ) : (
                            <>

                                <OneLineFormC idName={`${typeName}add`} labelText={`new ${typeName}`}
                                              placeholderText={""} handleInputChangeFunction={updateValue}
                                              focus={true}/>
                                <div className="text-center mt-4 mb-3">
                                    <button className="custom-button rounded-3" disabled={isAddDisabled} type={"submit"}
                                            onClick={handleSubmit}>add
                                    </button>
                                </div>

                            </>
                        )}

                    </>

                </Modal.Body>

            </Modal>
        </>
    )


}