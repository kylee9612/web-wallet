import React, {useState} from 'react';
import "./App.css"
import Header from "./components/page/common/Header";
import Page from "./components/page/common/Page";

function App() {
    const [coin, setCoin] = useState("XRP");
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

    return (
        <div id={"inner_root"}>
            <Header onChangeCoin={changeCoin}/>
            <Page coin={coin}/>
        </div>
    );
}

export default App;