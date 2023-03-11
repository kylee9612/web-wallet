import React from "react"
import $ from "jquery";
function generate() {
    $.ajax({
        type: "GET",
        url: "/api/v2/xrp/generate",
        success: function (data) {
            $("#from_public").val(data.publicKey);
            $("#from_private").val(data.privateKey);
            $("#from_address").val(data.classicAddress);
            $("#from_tag").val(data.tag);
        },
        error: function (error) {
            console.log(error)
            makeEmpty()
            alert("Generating Error")
        }
    })
}

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

function to_valid() {
    let classicAddress = $("#to_address").val();
    let tag = $("#to_tag").val();
    let type = ""
    let url = ""
    type = "GET"
    if (classicAddress !== null && tag !== null) {
        url = "/api/v2/xrp/balance?address=" + classicAddress + "&tag=" + tag
        $.ajax({
            type: type,
            url: url,
            beforeSend: function () {
                $("#to_balance").val("");
                $("#to_valid").val("");
            },
            success: function (data) {
                $("#to_balance").val(data);
                $("#to_valid").val("valid");
            },
            error: function (error) {
                console.log("Not Valid Address")
                $("#to_valid").val("false")
            }
        })
    } else if (classicAddress === null || tag === null) {
        alert("지갑 주소와 태그를 확인하세요")
    }
}

function user_generate() {
    $.ajax({
        type: "GET",
        url: "/api/v2/user/generate",
        success: function (data) {
            $("#user_idx").val(data.mb_idx)
            $("#user_tag").val(data.destination)
        }, error: function (error) {
            alert("Generating failed")
        }
    })
}

function send() {
    let from_ = $("#from_valid").val()
    let to_ = $("#to_valid").val()
    let amount = $("#amount").val()
    if (from_ !== "valid") {
        alert("Make From Account Valid First")
    } else if (to_ !== "valid") {
        alert("Make To Account Valid First")
    } else if (amount === "" || amount === null) {
        alert("Type the amount how much you want to Send")
    } else {
        let data = {
            "publicKey": $("#from_public").val(),
            "privateKey": $("#from_private").val(),
            "from_address" : $("#from_address").val(),
            "address": $("#to_address").val(),
            "to_tag" : $("#to_tag").val(),
            "amount": $("#amount").val()
        }
        $.ajax({
            type: "POST",
            url: "/api/v2/xrp/send",
            contentType: "application/json",
            dataType: "JSON",
            data: JSON.stringify(data),
            beforeSend: function () {
                $("#from_balance").val("")
                $("#from_valid").val("")
            },
            success: function (data) {
                $("#from_address").val(data.classicAddress)
                $("#from_xAddress").val(data.xAddress)
                $("#from_balance").val(data.balance)
                $("#to_balance").val(data.to_balance)
                $("#send").val("")
            },
            error: function (error) {
                console.log("Not Valid Keys")
            }
        })
    }
}

function makeEmpty() {
    $("#from_public").val();
    $("#from_private").val();
    $("#from_address").val();
    $("#from_xAddress").val();
    $("#from_balance").val();
}

function Body() {

    return (
        <div id="wrap">
            <div id="left-body" className="body_content">
                <h2>From</h2>
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
                <label htmlFor="amount">Sending Amount
                    <input type="text" id="amount" className="small"/>
                    Transaction Fee
                    <input type="text" id="fee" className="small" readOnly="readonly" placeholder="Fee"/>
                </label>
                <label>
                    <button id="generate" onClick={generate} className="in_body">Generate Wallet</button>
                    <button id="send" onClick={send} className="in_body">Send</button>
                </label>
            </div>
            <div id="right-body" className="body_content">
                <h2>To</h2>
                <label htmlFor="to_address">Classic Address
                    <input type="text" id="to_address" className="in_body" placeholder="classicAddress"/>
                </label>
                <label htmlFor="to_tag">Destination Tag
                    <input type="text" id="to_tag" className="in_body" placeholder="Destination Tag"/>
                </label>
                <label htmlFor="to_balance">balance
                    <input type="text" id="to_balance" className="in_body" readOnly="readonly"/>
                </label>
                <label htmlFor="to_valid">
                    <button id="to_valid_button" onClick={to_valid} className="in_body">Check Valid</button>
                    <input type="text" id="to_valid" className="in_small" readOnly="readonly"/>
                </label>
                <h2>User</h2>
                <label htmlFor="user_tag">Destination
                    <input type="text" id="user_tag" className="in_body" readOnly/>
                </label>
                <label htmlFor="user_idx">Index
                    <input type="text" id="user_idx" className="in_body" readOnly/>
                </label>
                <button id="user_generate" onClick={user_generate}
                        className="in_body">User Generate</button>
            </div>
        </div>
    )
}

export default Body;