import React from "react"
import "../../../css/common/header.css"

function Header(props) {

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
                        <li onClick={props.onChangeCoin}><img alt={'BTC'} value={0} src={'https://s2.coinmarketcap.com/static/img/coins/64x64/1.png'}/><span>BTC</span></li>
                        <li onClick={props.onChangeCoin}><img alt={'ETH'} value={1} src={'https://s2.coinmarketcap.com/static/img/coins/64x64/1027.png'}/><span>ETH</span></li>
                        <li onClick={props.onChangeCoin}><img alt={'XRP'} value={2} src={'https://s2.coinmarketcap.com/static/img/coins/64x64/52.png'}/><span>XRP</span></li>
                    </div>
                </div>
                <div className={"login"}>
                    <button className={"loginBtn"}>Login</button>
                </div>
            </div>
        </div>)
}

export default Header