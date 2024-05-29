import React, {useEffect, useState} from 'react';
import {Dropdown, DropdownItem} from 'react-bootstrap';

export function DynamicDropdown({data, valueKey, onSelectedOptionsChange, dropdownname, bxIcon, onAddingNew}) {

    const [selectedOptions, setSelectedOptions] = useState([]);
    const [availableOptions, setAvailableOptions] = useState(data);

    useEffect(() => {
        if (selectedOptions.length === 0) {
            setAvailableOptions(data)
        } else {
            setAvailableOptions(data);
            selectedOptions.forEach(selected => (
                handleChange(selected)
            ))

        }

    }, [data]);

    const handleChange = (selectedValue) => {
        const selectedObject = data.find(item => item[valueKey] === selectedValue);

        if (selectedObject) {
            setSelectedOptions(prevSelectedOptions => [...prevSelectedOptions, selectedObject]);
            onSelectedOptionsChange([...selectedOptions, selectedObject]);
            setAvailableOptions((prevOptions) => prevOptions.filter((option) => option[valueKey] !== selectedValue));
        }
    };

    const handleNewElement = () => {
        onAddingNew()
    }

    const handleRemove = (removeValue) => {
        const newSelectedOptions = selectedOptions.filter(item => item[valueKey] !== removeValue);
        setSelectedOptions(newSelectedOptions);
        onSelectedOptionsChange(newSelectedOptions);
        const removedOption = data.find((item) => item[valueKey] === removeValue);
        if (removedOption) {
            setAvailableOptions([...availableOptions, removedOption]);
        }
    };

    return (<>
        <div className="d-flex my-2">
            <div>
                <div className="d-flex justify-content-start">
                    <div className="dropdown">
                        <button
                            className="btn btn-sage-light rounded-2 text-start btn-fixed-size fw-semibold dropdown-toggle"
                            type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false">
                            <i className={bxIcon}></i> {dropdownname}
                        </button>
                        <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton">
                            {availableOptions.map((item, index) => (
                                <li key={index}>
                                    <button className="dropdown-item" type={"button"} onClick={() => handleChange(item[valueKey])}>
                                        {item[valueKey]}
                                    </button>
                                </li>
                            ))}
                            <li className="text-center">
                                <button className="dropdown-item" type={"button"} onClick={() => handleNewElement()}>
                                    <i className='bx bx-plus bx-xs'></i>
                                </button>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>

            <div>
                {selectedOptions.map((item, index) => (
                    <button
                        key={index}
                        className="btn btn-sage-light mx-2"
                        type="button"
                        onClick={() => handleRemove(item[valueKey])}
                    >
                        {item[valueKey]}
                    </button>
                ))}
            </div>
        </div>
    </>);
}

export function DynamicSingleValueChoiceDropdown({
                                                     data,
                                                     valueKey,
                                                     onSelectedOptionsChange,
                                                     dropdownname,
                                                     bxIcon,
                                                     onAddingNew
                                                 }) {
    const [selectedOption, setSelectedOption] = useState(null);

    useEffect(() => {
        if (selectedOption != null) {
            handleChange(selectedOption)
        }

    }, [data]);


    const handleChange = (selectedValue) => {
        const selectedObject = data.find(item => item[valueKey].toString() === selectedValue.toString());

        if (selectedObject) {
            setSelectedOption(selectedObject);
            onSelectedOptionsChange(selectedObject);
        }
    };
    const handleNewElement = () => {
        onAddingNew()
    }

    const handleRemove = () => {
        setSelectedOption(null);
        onSelectedOptionsChange(null);
    };

    return (<>
        <div className="d-flex my-2">
            <div>
                <div className="d-flex justify-content-start">
                    <div className="dropdown">
                        <button className="btn btn-sage-light rounded-2 text-start btn-fixed-size dropdown-toggle"
                                type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false">
                            <i className={bxIcon}></i> {dropdownname}
                        </button>
                        <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton">
                            {data.map((item, index) => (
                                <li key={index}>
                                    <button className="dropdown-item" type={"button"} onClick={() => handleChange(item[valueKey])}>
                                        {item[valueKey]}
                                    </button>
                                </li>
                            ))}
                            <li className="text-center">
                                <button className="dropdown-item" type={"button"} onClick={() => handleNewElement()}>
                                    <i className='bx bx-plus bx-xs'></i>
                                </button>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div className="col-10 d-flex justify-content-start">
                {selectedOption && (
                    <button
                        className="btn btn-sage-light mx-2 fw-semibold"
                        type="button"
                        onClick={handleRemove}
                    >
                        {selectedOption[valueKey]}
                    </button>
                )}
            </div>
        </div>

    </>);
}


export function DynamicDropdown2({data, valueKey, onSelectedOptionsChange, dropdownname, bxIcon, preselected}) {

    const [selectedOptions, setSelectedOptions] = useState(preselected);
    const [availableOptions, setAvailableOptions] = useState([]);

    useEffect(() => {
        setInitialAvailableOptions();
    }, [data, preselected]);
    const setInitialAvailableOptions = () => {
        let avOpt;
        if (valueKey) {
            avOpt = data.filter(item => !preselected.some(selItem => selItem[valueKey] === item[valueKey]));
        } else {
            avOpt = data.filter(item => !preselected.includes(item));
        }
        setAvailableOptions(avOpt);
    };


    const handleChange = (selectedValue) => {
        const selectedObject = data.find(item => item[valueKey].toString() === selectedValue);

        if (selectedObject) {
            setSelectedOptions(prevSelectedOptions => [...prevSelectedOptions, selectedObject]);
            onSelectedOptionsChange([...selectedOptions, selectedObject]);
            setAvailableOptions((prevOptions) => prevOptions.filter((option) => option[valueKey] !== selectedValue));
        }
    };

    const handleRemove = (removeValue) => {
        const newSelectedOptions = selectedOptions.filter(item => item[valueKey] !== removeValue);
        setSelectedOptions(newSelectedOptions);
        onSelectedOptionsChange(newSelectedOptions);
        const removedOption = data.find((item) => item[valueKey] === removeValue);
        if (removedOption) {
            setAvailableOptions([...availableOptions, removedOption]);
        }
    };

    return (<>
            <div className="d-flex my-2">
                <div>
                    <div className="d-flex justify-content-start">
                        <div className="dropdown">
                            <button className="btn btn-sage-light rounded-2 text-start btn-fixed-size dropdown-toggle" type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false">
                                <i className={bxIcon}></i> {dropdownname}
                            </button>
                            <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                {availableOptions.map((item, index) => (
                                    <li key={index}>
                                        <button className="dropdown-item" type={"button"} onClick={() => handleChange(item[valueKey])}>
                                            {item[valueKey]}
                                        </button>
                                    </li>
                                ))}
                            </ul>
                        </div>
                    </div>
                </div>

                <div>
                    {selectedOptions.map((item, index) => (
                        <button
                            key={index}
                            className="btn btn-sage-light mx-2"
                            type="button"
                            onClick={() => handleRemove(item[valueKey])}
                        >
                            {item[valueKey]}
                        </button>
                    ))}
                </div>
            </div>

        </>
    );
}

export function DynamicSingleValueChoiceDropdown2({
                                                      data,
                                                      valueKey,
                                                      onSelectedOptionsChange,
                                                      dropdownname,
                                                      bxIcon,
                                                      preselected
                                                  }) {
    const [selectedOption, setSelectedOption] = useState(preselected);


    const handleChange = (selectedValue) => {
        const selectedObject = data.find(item => item[valueKey].toString() === selectedValue.toString());

        if (selectedObject) {
            setSelectedOption(selectedObject);
            onSelectedOptionsChange(selectedObject);
        }
    };

    const handleRemove = () => {
        setSelectedOption(null);
        onSelectedOptionsChange(null);
    };

    return (<>
        <div className="d-flex my-2">
            <div>
                <div className="d-flex justify-content-start">
                    <div className="dropdown">
                        <button className="btn btn-sage-light rounded-2 text-start btn-fixed-size dropdown-toggle"
                                type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false">
                            <i className={bxIcon}></i> {dropdownname}
                        </button>
                        <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton">
                            {data.map((item, index) => (
                                <li key={index}>
                                    <button className="dropdown-item" type={"button"} onClick={() => handleChange(item[valueKey])}>
                                        {item[valueKey]}
                                    </button>
                                </li>
                            ))}
                        </ul>
                    </div>
                </div>
            </div>
            <div className="col-10 d-flex justify-content-start">
                {selectedOption && (
                    <button
                        className="btn btn-sage-light mx-2"
                        type="button"
                        onClick={handleRemove}
                    >
                        {selectedOption[valueKey]}
                    </button>
                )}
            </div>
        </div>

    </>);
}


