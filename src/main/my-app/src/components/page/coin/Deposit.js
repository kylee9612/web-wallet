import React, {useState} from "react"
import axios from "axios";

function Deposit({coin}) {
    const [coinData, setCoinData] = useState({});

    const generate = () => {
        axios.get("/api/v2/" + coin.toLowerCase() + "/generate")
            .then((response) => {
                setCoinData(response.data)
            })
            .catch((error) => {
                console.log(error)
            })
            .finally(() => {
                console.log(coinData)
            })
    }

    return (
        <>
            <div id="left-body" className="body_content">
                <h2>{coin}</h2>
                <img id={"qr_code"} src={"data:image/png;base64,"+coinData.qr_code} width={"400px"} height={"400px"} alt={'qr_code'}/>
                {/*<img id={"qr_code"} src={"data:image/png;base64,"+prop.data.qr_code} width={"400px"} height={"400px"} alt={'qr_code'}/>*/}
                <label htmlFor="from_public">Public Key
                    <input type="text" id="from_public" className="in_body" placeholder="publicKey"/>
                </label>
                <label htmlFor="from_private">Private Key
                    <input type="text" id="from_private" className="in_body" placeholder="privateKey"/>
                </label>

                <label htmlFor="from_address">Classic Address
                    <input type="text" id="from_address" className="in_body" placeholder="classicAddress"/>
                </label>

                <label htmlFor="from_tag">Destination Tag
                    <input type="text" id="from_tag" className="in_body" placeholder="Destination Tag"/>
                </label>

                <label htmlFor="from_balance">balance
                    <input type="text" id="from_balance" className="in_body" readOnly="readonly"/>
                </label>
                <label htmlFor="from_valid">
                    <button id="from_valid_button" className="in_body">Check Valid</button>
                    <input type="text" id="from_valid" className="in_small" readOnly="readonly"/>
                </label>
                <label>
                    <button id="generate" onClick={generate} className="in_body">Generate Wallet</button>
                </label>
            </div>
        </>
    )
}

export default Deposit;