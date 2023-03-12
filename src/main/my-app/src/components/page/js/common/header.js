import React from "react"
import "../../../css/common/header.css"

function Header() {
    return (
        <div id="nav">
            <div id="logo">
                <a href={"/"} className={"logo_text"} c>
                    Rallet
                </a>
            </div>
            <div className={"rightTop"}>
                <div className="dropdown">
                    <button className="dropbtn">Coin List</button>
                    <div className="dropdown-content">
                        <a href="#"><img
                            src={'https://s2.coinmarketcap.com/static/img/coins/64x64/1.png'}/><span>BTC</span></a>
                        <a href="#"><img src={'https://s2.coinmarketcap.com/static/img/coins/64x64/1027.png'}/>ETH</a>
                        <a href="#"><img src={'https://s2.coinmarketcap.com/static/img/coins/64x64/52.png'}/>XRP</a>
                    </div>
                </div>
                <div className={"login"}>
                    <button className={"loginBtn"}>Login</button>
                </div>
            </div>
        </div>)
}

export default Header