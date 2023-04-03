import React ,{useState} from "react"
import $ from "jquery";
import axios from "axios";

function from_valid() {
    let publicKey = $("#from_public").val();
    let privateKey = $("#from_private").val();
    let classicAddress = $("#from_address").val();
    let tag = $("#from_tag").val();
    let type = ""
    let url = ""
    if (publicKey === "" && classicAddress !== "" && tag !== "") {
        type = "GET"
        url = "/api/v2/xrp/balance?address=" + classicAddress + "&tag=" + tag
        $.ajax({
            type: type,
            url: url,
            beforeSend: function () {
                $("#from_balance").val("");
                $("#from_valid").val("");
            },
            success: function (data) {
                $("#from_balance").val(data);
                $("#from_valid").val("valid");
            },
            error: function (error) {
                alert("Not Valid Address")
                $("#from_balance").val("");
                $("#from_valid").val("false")
            }
        })
    } else if (publicKey !== "") {
        let data = {
            "publicKey": publicKey,
            "privateKey": privateKey
        }
        type = "POST"
        url = "/api/v2/xrp/wallet"

        console.log(data)
        $.ajax({
            type: type,
            url: url,
            contentType: "application/json",
            dataType: "JSON",
            data: JSON.stringify(data),
            beforeSend: function () {
                $("#from_balance").val("");
                $("#from_valid").val("");
            },
            success: function (data) {
                console.log(data)
                $("#from_address").val(data.classicAddress);
                $("#from_tag").val(data.tag);
                $("#from_balance").val(data.balance);
                $("#from_valid").val("valid");
                $("#fee").val("0.0000" + data.fee.value)
            },
            error: function (error) {
                console.log("Not Valid Keys")
                $("#from_address").val("");
                $("#from_tag").val("");
                $("#from_balance").val("");
                $("#from_valid").val("false")
            }
        })
    } else {
        alert("Public Key or Classic Address and tag Not Entered")
    }
}

function Deposit(prop) {
    const generate = () => {
        prop.generate()
    }

    return (
        <div id="wrap">
            <div className="page-bg"></div>

            <div className="animation-wrapper">
                <div className="particle particle-1"></div>
                <div className="particle particle-2"></div>
                <div className="particle particle-3"></div>
                <div className="particle particle-4"></div>
            </div>
            <div id="left-body" className="body_content">
                <h2>{prop.coin}</h2>
                <img id={"qr_code"} src={"data:image/png;base64,"+prop.data.qr_code} width={"200px"} height={"200px"}/>
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
                    <button id="from_valid_button" onClick={from_valid} className="in_body">Check Valid</button>
                    <input type="text" id="from_valid" className="in_small" readOnly="readonly"/>
                </label>
                <label>
                    <button id="generate" onClick={generate} className="in_body">Generate Wallet</button>
                </label>
            </div>
        </div>
    )
}

export default Deposit;