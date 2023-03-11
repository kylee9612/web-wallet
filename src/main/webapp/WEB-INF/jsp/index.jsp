<%@include file="../include/header.jsp" %>
<div id="root"></div>

<div id="wrap">
    <div id="left-body" class="body_content">
        <h2>From</h2>
        <label for="from_public">Public Key
            <input type="text" id="from_public" class="in_body" placeholder="publicKey"/>
        </label>

        <label for="from_private">Private Key
            <input type="text" id="from_private" class="in_body" placeholder="privateKey"/>
        </label>

        <label for="from_address">Classic Address
            <input type="text" id="from_address" class="in_body" placeholder="classicAddress"/>
        </label>

        <label for="from_tag">Destination Tag
            <input type="text" id="from_tag" class="in_body" placeholder="Destination Tag"/>
        </label>

        <label for="from_balance">balance
            <input type="text" id="from_balance" class="in_body" readonly="readonly">
        </label>
        <label for="from_valid">
            <button id="from_valid_button" onclick="from_valid()" class="in_body">Check Valid</button>
            <input type="text" id="from_valid" class="in_small" readonly="readonly">
        </label>
        <label for="amount">Sending Amount
            <input type="text" id="amount" class="small">
            Transaction Fee<input type="text" id="fee" class="small" readonly="readonly" placeholder="Fee">
        </label>

        <label>
            <button id="generate" onclick="generate()" class="in_body">Generate Wallet</button>
            <button id="send" onclick="send()" class="in_body">Send</button>
        </label>
    </div>
    <div id="right-body" class="body_content">
        <h2>To</h2>
        <label for="to_address">Classic Address
            <input type="text" id="to_address" class="in_body" placeholder="classicAddress"/>
        </label>
        <label for="to_tag">Destination Tag
            <input type="text" id="to_tag" class="in_body" placeholder="Destination Tag"/>
        </label>
        <label for="to_balance">balance
            <input type="text" id="to_balance" class="in_body" readonly="readonly">
        </label>
        <label for="to_valid">
            <button id="to_valid_button" onclick="to_valid()" class="in_body">Check Valid</button>
            <input type="text" id="to_valid" class="in_small" readonly="readonly"/>
        </label>
        <h2>User</h2>
        <label for="user_tag">Destination
            <input type="text" id="user_tag" class="in_body" readonly>
        </label>
        <label for="user_idx">Index
            <input type="text" id="user_idx" class="in_body" readonly>
        </label>
        <button id="user_generate" onclick="user_generate()" class="in_body">User Generate</button>
    </div>
</div>
<%@include file="../include/footer.jsp" %>