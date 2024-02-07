import React, {useEffect, useState} from "react";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import {Button, Container, Nav, Navbar} from "react-bootstrap";

import {Dashboard} from "../dashboard/Dashboard";
import {DatabaseParent} from "../databaseview/DatabaseC";
import {TimeLineTable} from "../timeline/TimeLineTable";
import {NewDbEntryC} from "../newentry/NewDbEntryC";
import {shutdownInit} from "../../constants/apiConstants";
import {Setting} from "../Settings/Setting";


export function NavigationC() {

    const [wasShutdown, setWasShutdown] = useState(false);
    const [shuttingDown, setShowShutdown] = useState(false);
    useEffect(() => {
        if (wasShutdown) {
            setWasShutdown(false)
            fetch(shutdownInit)
            setShowShutdown(true)
        }
    }, [wasShutdown]);

    return (
        <>
            {shuttingDown ? (

                <div className="mt-5 pt-5 text-center">

                    <h1>shutting down</h1>
                    <h5>you may close this window.</h5>

                </div>

            ) : (
                <BrowserRouter>
                    <Navbar id="navbar-main" expand="lg" className="custom-header">
                        <Container className="mt-5">
                            <Navbar.Brand href="/" className="nav-font-style"><i
                                className='bx bx-lg bxs-leaf align-middle bx-tada-hover'></i></Navbar.Brand>
                            <Navbar.Toggle aria-controls="basic-navbar-nav"/>
                            <Navbar.Collapse id="basic-navbar-nav">
                                <Nav className="me-auto">
                                    <Nav.Link className="nav-font-style" href="/timeline">Timeline</Nav.Link>
                                    <Nav.Link className="nav-font-style" href="/entries">Database</Nav.Link>
                                    <Nav.Link className="nav-font-style" href="/addtodatabase">
                                        Entry Form</Nav.Link>
                                    <Nav.Link className="nav-font-style" href="/settings"><i className='bx bxs-cog bx-sm bx-spin-hover align-middle '/></Nav.Link>
                                </Nav>
                                <Button className="bg-transparent border-0 text-white text-end"
                                        onClick={() => setWasShutdown(true)}><i
                                    className='bx bx-power-off bx-md'/></Button>
                            </Navbar.Collapse>
                        </Container>
                    </Navbar>

                    <Container
                        className="mt-5 px-5 bg-transparent align-self-md-center scrollable-main scrollbar-custom ">
                        <Routes>
                            <Route path="/" element={<Dashboard/>}/>
                            <Route path="/timeline" element={<TimeLineTable/>}/>
                            <Route path="/entries" element={<DatabaseParent/>}/>
                            <Route path="/addtodatabase" element={<NewDbEntryC/>}/>
                            <Route path="/settings" element={<Setting/>}/>
                        </Routes>
                    </Container>

                </BrowserRouter>
            )
            }
        </>
    )
}
