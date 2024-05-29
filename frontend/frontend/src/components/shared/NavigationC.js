import React, {useEffect, useState} from "react";
import {BrowserRouter, Route, Routes} from "react-router-dom";


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
                    <nav className={"navbar navbar-expand-lg"} >
                        <div className={"container-fluid"}>
                            <a href="/" className={"navbar-brand"}><i
                                className='bx bx-lg bxs-leaf align-middle bx-tada-hover text-sage-light'></i>
                            </a>
                            <button className="navbar-toggler" type="button" data-bs-toggle="collapse"
                                    data-bs-target="#navbarMain" aria-controls="navbarMain"
                                    aria-expanded="false" aria-label="Toggle navigation">
                                <span className="navbar-toggler-icon"></span>
                            </button>
                            <div className={"collapse navbar-collapse"} id="navbarMain">
                                <ul className="me-auto navbar-nav mb-2 mb-lg-0 fs-5">
                                    <li className={"nav-item"}>
                                        <a className="nav-link fw-bold" aria-current={"page"}
                                           href="/timeline">Timeline</a>
                                    </li>
                                    <li className={"nav-item"}>
                                        <a className="nav-link fw-bold" href="/entries">Database</a>
                                    </li>
                                    <li className={"nav-item"}>
                                        <a className="nav-link fw-bold" href="/addtodatabase">
                                            New Entry</a>
                                    </li>
                                    <li className={"nav-item"}>
                                        <a className="nav-link " href="/settings"><i
                                            className='bx bxs-cog bx-sm bx-spin-hover align-middle '/></a>
                                    </li>
                                </ul>
                                <button type={"button"} className="bg-transparent text-muted border-0 text-end"
                                        onClick={() => setWasShutdown(true)}><i
                                    className='bx bx-power-off bx-md'/></button>
                            </div>
                        </div>
                    </nav>

                    <div
                        className="pt-5 scrollbar-custom">
                        <Routes>
                            <Route path="/" element={<Dashboard/>}/>
                            <Route path="/timeline" element={<TimeLineTable/>}/>
                            <Route path="/entries" element={<DatabaseParent/>}/>
                            <Route path="/addtodatabase" element={<NewDbEntryC/>}/>
                            <Route path="/settings" element={<Setting/>}/>
                        </Routes>
                    </div>

                </BrowserRouter>
            )
            }
        </>
    )
}
