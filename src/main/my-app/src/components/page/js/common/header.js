import React from "react"
import "../../../css/common/header.css"

function Header() {
    return (<div id="nav">
        <nav id='navigation'>
            <ul id="coin">
                <li>BTC</li>
                <li>ETH</li>
                <li>XRP</li>
            </ul>
        </nav>
        <a href={"/main.do"} id={"logo"}>
            ReWallet
        </a>
    </div>)
}

export default Header