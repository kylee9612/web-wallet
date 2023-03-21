import React from "react"
import "../../../css/common/header.css"

function Header(props) {
    const onClickCoin = (coin) =>{
        console.log(coin)
        return props.onChangeCoin(coin)
    }

    return (
        <div id="nav">
            <div id="logo">
                <a href={"/"} className={"logo_text"}>
                    Rallet
                </a>
            </div>
            <div className={"rightTop"}>
                <div className="dropdown">
                    <button className="dropbtn">Coin List</button>
                    <div className="dropdown-content">
                        <li onClick={onClickCoin('BTC')}><img src={'https://s2.coinmarketcap.com/static/img/coins/64x64/1.png'}/><span>BTC</span></li>
                        <li onClick={onClickCoin('ETH')}><img src={'https://s2.coinmarketcap.com/static/img/coins/64x64/1027.png'}/>ETH</li>
                        <li onClick={onClickCoin('XRP')}><img src={'https://s2.coinmarketcap.com/static/img/coins/64x64/52.png'}/>XRP</li>
                    </div>
                </div>
                <div className={"login"}>
                    <button className={"loginBtn"}>Login</button>
                </div>
            </div>
        </div>)
}

export default Header