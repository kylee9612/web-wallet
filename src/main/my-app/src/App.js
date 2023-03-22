import React from 'react';
import "./components/css/index.css"
import Header from "./components/page/js/common/header";
import {useState} from "react";
import Deposit from "./components/page/js/xrp/deposit";
import axios from "axios";

function App() {
    const [Coin, setCoin] = useState("XRP");
    const coinList = ['BTC', 'ETH', 'XRP']


    const changeCoin = (event) => {
        let value = ''
        if (event.target.tagName === 'SPAN')
            value = event.target.textContent
        else if (event.target.tagName === 'IMG')
            value = event.target.alt
        else
            value = coinList[event.target.value]
        setCoin(value)
    }

    const generate = () => {
        axios.get("/api/v2/xrp/generate")
            .then((response) => {
                console.log(response.data)
            })
            .catch((error) => {
                console.log(error)
            })
    }

    return (
        <div id={"inner_root"}>
            <Header onChangeCoin={changeCoin}/>
            <Deposit coin={Coin}
                     generate={generate}
            />
        </div>
    );
}

export default App;