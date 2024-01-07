import Form from 'react-bootstrap/Form';
import InputGroup from 'react-bootstrap/InputGroup';
import {useState} from "react";

export function OneLineFormC({labelText, placeholderText, idName, handleInputChangeFunction}) {

    const [inputValue, setInputValue] = useState('');

    const onChange = (event) => { //event handler
        setInputValue(event.target.value);  //set the new value first
        handleInputChangeFunction && handleInputChangeFunction(event.target.value);

    }


    return (
        <>
            <InputGroup className="mb-2">
                <InputGroup.Text className="input-grp-label fw-normal" id={idName}>{labelText}</InputGroup.Text>
                <Form.Control
                    className="input-grp-text"
                    placeholder={placeholderText}
                    aria-label={labelText}
                    aria-describedby={idName}
                    value={inputValue}
                    onChange={onChange}
                />
            </InputGroup>
        </>
    )
}