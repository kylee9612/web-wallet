import React, {useRef, useState} from "react"
import axios from "axios";
import "./deposit.css"
import Swal from "sweetalert2";

function Deposit({coin}) {
    const [coinData, setCoinData] = useState({});
    const [qrCode, setQrCode] = useState("");

    const qrRef = useRef();
    const genRef = useRef();

    const generate = () => {
        axios.get("/api/v2/" + coin.toLowerCase() + "/generate")
            .then((response) => {
                setCoinData(response.data)
                setQrCode("data:image/png;base64," + response.data.qr_code)
                qrRef.current.style.display = ""
                genRef.current.style.display = "none"
                Swal.fire({
                    "html":
                        "<div class='alert-div'>" +
                        "<span>private key</span>" +
                        "<input type='text' id='from_private' class='alert-input' value='" + response.data.private_key + "'/>" +
                        "<br>" +
                        "<span>public key</span>" +
                        "<input type='text' id='from_public' class='alert-input' value='" + response.data.public_key + "'/>" +
                        "</div>",
                    "showConfirmButton" : "OK"
                })
            })
            .catch((error) => {
                console.log(error)
            })
    }

    const imgError = (event) => {
        event.target.style.display = 'none'
    }

    return (
        <>
            <div id="left-body" className="body_content">
                <div className={"content-top"}>
                    <h2>{coin}</h2>
                </div>
                <div className={"qr_code"}>
                    <img ref={qrRef} src={qrCode} width={"300px"} height={"300px"} alt={'qr_code'}
                         onError={imgError}/>
                    <button ref={genRef} id="generate" onClick={generate} className="in_body">Generate Wallet</button>
                </div>

                <label htmlFor="from_address">Classic Address
                    <input type="text" id="from_address" className="in_body" placeholder="Address"
                           value={coinData.address} readOnly/>
                </label>

                <label htmlFor="from_tag">Destination Tag
                    <input type="text" id="from_tag" className="in_body" placeholder="Destination Tag"
                           value={coinData.destination} readOnly/>
                </label>

                <label htmlFor="from_balance">balance
                    <input type="text" id="from_balance" className="in_body" readOnly/>
                </label>
                <button id="generate" onClick={generate} className="in_body">Send Asset</button>
            </div>
        </>
    )
}

export default Deposit;