import React from 'react';
import "./components/css/index.css"
import Header from "./components/page/js/common/header";
import {useState} from "react";
import Deposit from "./components/page/js/xrp/deposit";

function App() {
    const [Coin, setCoin] = useState("");

    const changeCoin = (coinTo)=>{
        setCoin(coinTo)
    }

    return (
        <div id={"inner_root"}>
            <Header onChangeCoin={changeCoin}/>
            <Deposit coin={Coin}/>
            {/*<Footer/>*/}
        </div>
    );
}

export default App;