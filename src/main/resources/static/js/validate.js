function generate() {
    $.ajax({
        type: "GET",
        url: "/api/v2/xrp/generate",
        success: function (data) {
            $("#from_public").val(data.publicKey);
            $("#from_private").val(data.privateKey);
            $("#from_address").val(data.classicAddress);
            $("#from_xAddress").val(data.xAddress);
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
    let xAddress = $("#from_xAddress").val();
    let type = ""
    let url = ""
    if (publicKey === "") {
        type = "GET"
        if (classicAddress === null)
            url = "/api/v2/xrp/balance?address=" + xAddress
        else if (xAddress !== null)
            url = "/api/v2/xrp/balance?address=" + classicAddress
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
    } else if (classicAddress !== null) {
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
                $("#from_xAddress").val(data.xAddress);
                $("#from_balance").val(data.balance);
                $("#from_valid").val("valid");
                $("#fee").val("0.0000" + data.fee.value)
            },
            error: function (error) {
                console.log("Not Valid Keys")
                $("#from_address").val("");
                $("#from_xAddress").val("");
                $("#from_balance").val("");
                $("#from_valid").val("false")
            }
        })
    } else {
        alert("Public Key or Classic Address Entered")
    }
}

function to_valid() {
    let classicAddress = $("#to_address").val();
    let xAddress = $("#to_xAddress").val();
    let type = ""
    let url = ""
    type = "GET"
    if (classicAddress === null)
        url = "/api/v2/xrp/balance?address=" + xAddress
    else
        url = "/api/v2/xrp/balance?address=" + classicAddress
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
            "address": $("#to_address").val(),
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