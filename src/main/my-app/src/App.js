import React from 'react';
import "./App.css"
import Header from "./components/page/common/header";
import {useState} from "react";
import Deposit from "./components/page/xrp/deposit";
import axios from "axios";

function App() {
    const [Coin, setCoin] = useState("XRP");
    const coinList = ['BTC', 'ETH', 'XRP']
    const [coinData, setCoinData] = useState({});


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
        axios.get("/api/v2/"+Coin.toLowerCase()+"/generate")
            .then((response) => {
                setCoinData(response.data)
            })
            .catch((error) => {
                console.log(error)
            })
            .finally(()=>{
                console.log(coinData)
            })
    }

    return (
        <div id={"inner_root"}>
            <Header onChangeCoin={changeCoin}/>
            <Deposit coin={Coin}
                     generate={generate}
                     data={coinData}
            />
        </div>
    );
}

export default App;