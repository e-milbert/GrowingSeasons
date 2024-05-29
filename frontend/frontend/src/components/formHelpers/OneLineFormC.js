import {useState} from "react";

export function OneLineFormC({labelText, placeholderText, idName, handleInputChangeFunction}) {

    const [inputValue, setInputValue] = useState('');

    const onChange = (event) => {
        setInputValue(event.target.value);
        handleInputChangeFunction && handleInputChangeFunction(event.target.value);

    }


    return (
        <>
            <div className="mb-2 ">
                <label htmlFor={idName} className={"form-label mb-0"}>{labelText}</label>
                <input
                    className="form-control"
                    type="text"
                    placeholder={placeholderText}
                    aria-label={labelText}
                    id={idName}
                    value={inputValue}
                    onChange={onChange}
                />
            </div>
        </>
    )


}