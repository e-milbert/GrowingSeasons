import React, {useEffect, useState} from "react";
import {Col, Container, Dropdown, DropdownItem, Row, Table} from "react-bootstrap";
import {OneLineFormC} from "../formHelpers/OneLineFormC";
import {plantPlainGetAll} from "../../constants/apiConstants";
import {ModalHoldingComponentC} from "./ModalHoldingComponentC";
import {tableHeaders} from "../../constants/helper";
import {LoadingAnimation} from "../shared/LoadingAnimation";
import {useNavigate} from "react-router-dom";

export function DatabaseParent() {
    const [reload, setReload] = useState(true);
    const [data, setData] = useState([]);
    const [isLoading, setIsLoading] = useState(true);

    const nav = useNavigate();

    useEffect(() => {
        if (reload) {
            nav('/entries');
        }
    }, [reload])

    useEffect(() => {

        const fetchPromises = [
            fetch(plantPlainGetAll)
                .then(response => response.json())
                .then(data => setData(data))
        ];

        Promise.all(fetchPromises)
            .then(() => {
                setIsLoading(false); // Set loading to false when all fetches are done
                setReload(false)
            })
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
                setReload(false)
                setIsLoading(false); // Set loading to false on error as well
            });

    }, []);

    function refreshData() {
        setIsLoading(true);
        fetch(plantPlainGetAll)
            .then(response => response.json())
            .then(data => {
                setData(data);
                setIsLoading(false);
            })
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
                setIsLoading(false);
            });
    }

    return (
        <>
            {isLoading ? (

                <Row className="min-vh-100 d-flex justify-content-center align-items-center">
                    <Col className="d-flex justify-content-center align-items-center">
                        <LoadingAnimation/>
                    </Col>
                </Row>
            ) : (
                <DatabaseC headerMapping={tableHeaders} listOfData={data} reload={refreshData}/>


            )}
        </>
    )

}


export function DatabaseC({listOfData, headerMapping, reload}) {
    const listOfHeads = Object.values(headerMapping);
    const [tableData, setTableData] = useState(listOfData);

    const keys = Object.keys(headerMapping);
    const uniqueIdentifierKey = keys[0];


    function handleSearch(value) {
        const isNumeric = !isNaN(value) && !isNaN(parseFloat(value));

        // Check if the input has at least 1 character for numbers, or 3 characters for other types
        const minLength = isNumeric ? 1 : 3;

        // Check if the input has at least the required number of characters
        if (value.length >= minLength) {
            const searchList = listOfData.filter((element) => {
                const valArray = Object.values(element);
                // Check if any value includes the partial input
                return valArray.some((val) =>
                    val.toString().toLowerCase().includes(value.toLowerCase())
                );
            });

            setTableData(searchList);
        } else {
            // If the input has less than 3 characters, reset the table data to the original list
            setTableData(listOfData);
        }
    }

    function handleSorting(displayValue) {

        const selectedHeadKey = Object.keys(headerMapping).find(key => headerMapping[key] === displayValue);

        // Check if the selected header is valid
        if (selectedHeadKey && selectedHeadKey !== "sort by") {
            const sortedData = [...listOfData].sort((a, b) => {
                let aValue = a[selectedHeadKey];
                let bValue = b[selectedHeadKey];

                // Check if the value is an array, and sort by the first element if it is
                if (Array.isArray(aValue) && aValue.length > 0) {
                    aValue = aValue[0];
                }
                if (Array.isArray(bValue) && bValue.length > 0) {
                    bValue = bValue[0];
                }

                // Check for empty or undefined values
                const aIsEmpty = aValue === '' || aValue === undefined || (Array.isArray(aValue) && aValue.length === 0);
                const bIsEmpty = bValue === '' || bValue === undefined || (Array.isArray(bValue) && bValue.length === 0);

                if (aIsEmpty && bIsEmpty) {
                    return 0; // Both are empty or undefined, considered equal
                } else if (aIsEmpty) {
                    return 1; // a is empty/undefined, sort b before a
                } else if (bIsEmpty) {
                    return -1; // b is empty/undefined, sort a before b
                }

                // Adjust the comparison based on the data type
                if (typeof aValue === "string" && typeof bValue === "string") {
                    return aValue.localeCompare(bValue);
                } else if (typeof aValue === "number" && typeof bValue === "number") {
                    return aValue - bValue;
                } else {
                    return 0; // No sorting in case of other
                }
            });

            setTableData(sortedData);
        }
    }


    return (
        <Container className="bg-container rounded-3 p-4">
            <Row className="m-2 d-flex">
                <Col>
                    <div className="d-flex my-2">
                        <div>
                            <div className="d-flex justify-content-start">
                                <Dropdown className="btn custom-button rounded-2  text-center crowbar">
                                    <div className="">
                                        <Dropdown.Toggle as="div" className="cursor-pointer">
                                            sort by:
                                        </Dropdown.Toggle>
                                    </div>
                                    <Dropdown.Menu>
                                        {listOfHeads.map((head, index) => (
                                            <DropdownItem key={index} onClick={() => handleSorting(head)}>
                                                {head}
                                            </DropdownItem>))}
                                    </Dropdown.Menu>
                                </Dropdown>
                            </div>
                        </div>
                    </div>
                </Col>
                <Col>
                    <OneLineFormC
                        labelText="search:"
                        placeholderText=""
                        idName="search"
                        handleInputChangeFunction={handleSearch}

                    />
                </Col>
            </Row>
            <Row className="d-flex">
                <Table className="custom-table" hover striped bordered>
                    <thead>
                    <tr>
                        {listOfHeads.map((head) => (
                            <th className="custom-table fw-medium" key={head}>
                                {head}
                            </th>
                        ))}
                        <th className="custom-table"></th>
                    </tr>
                    </thead>
                    <tbody>
                    {tableData.map((rowData) => (
                        <tr key={`row-${rowData[uniqueIdentifierKey]}`}>
                            {keys.map((key) => (
                                <td
                                    className="custom-table fw-normal"
                                    key={`cell-${rowData[uniqueIdentifierKey]}-${key}`}>

                                    {Array.isArray(rowData[key]) ? rowData[key].join(", ") : rowData[key]}
                                </td>
                            ))}
                            <td>
                                <ModalHoldingComponentC id={rowData[uniqueIdentifierKey]} data={rowData}
                                                        reload={reload}/>
                            </td>
                        </tr>

                    ))}
                    </tbody>
                </Table>
            </Row>
        </Container>
    );
}
