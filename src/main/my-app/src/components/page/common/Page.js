import React from "react";
import Deposit from "../coin/Deposit";

function Page({coin}) {
    return (
        <div id="wrap">
            <div className="page-bg"></div>

            <div className="animation-wrapper">
                <div className="particle particle-1"></div>
                <div className="particle particle-2"></div>
                <div className="particle particle-3"></div>
                <div className="particle particle-4"></div>
            </div>
            <Deposit coin={coin}/>
        </div>
    )
}

export default Page