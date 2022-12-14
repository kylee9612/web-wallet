package com.xrp.common;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface DicKey {

    /*
     * 코드값
     */
    // WalletDaemon Check action type
    public static final String CODE_check_db = "DB";		// DB
    public static final String CODE_check_node = "NODE";	// NODE
    public static final String CODE_check_daemon = "DAEMON";		// DAEMON
    public static final String CODE_check_all = "ALL";		// ALL


    // FDS type
    public static final String CODE_fraud_time = "TIME";		// 시간조건:TIME
    public static final String CODE_fraud_amount = "AMOUNT";	// 금액조건:AMOUNT
    public static final String CODE_fraud_count = "COUNT";		// 횟수조건:COUNT

    // SYSVARS type
    public static final String CODE_sysvars_fee = "FEE";			// 수수료
    public static final String CODE_sysvars_func_flag = "FUNC_FLAG";	//  기능 정지/재개 (Y,N)
    public static final String CODE_sysvars_noti_flag = "NOTI_FLAG";// 알림 정지/재개 (Y,N)
    public static final String CODE_fsysvars_confirmations = "CONFIRMATIONS";	// 컨펌수

    // 자산반영상태
    public static final String CODE_complete_previous = "P";	// 진행상태(P-자산반영전)
    public static final String CODE_complete_finish = "F";		// 진행상태(F-자산반영)
    public static final String CODE_complete_in = "I";			// 진행상태(I-입고)			- 정산처리를 위해 필요 (입고) : 입금정산
    public static final String CODE_complete_out = "O";			// 진행상태(O-출고)			- 정산처리를 위해 필요 (출고) : 출금정산 : 아직 이경우는 없음.>>> 있다면 횡령
    public static final String CODE_complete_error = "E";		// 진행상태(E-예외처리)
    public static final String CODE_complete_charges = "C";		// 진행상태(C-잔돈처리)			- 정산처리를 위해 필요 (잔돈) : 출금정산
    public static final String CODE_complete_set = "S";			// 진행상태(S-리플 (어카운트셋팅)) - 정산처리를 위해 필요 (어카운트셋팅) : 출금정산

    // 입츨금그분 : exc_type
    public static final String CODE_send = "W"; 			// W(withdraw) : 출금
    public static final String CODE_receive = "D"; 			// D(deposit): 입금
    public static final String CODE_send_charge = "WC";		// WC(withdraw): 충전요청
    public static final String CODE_send_fee = "WF";		// WF: 토큰의 거래수수료 출금 (ERC20)
    public static final String CODE_receive_charge = "DC";	// DC(deposit): 충전입금
    public static final String CODE_receive_fee = "DF";		// DF(fee): ERC20의 거래수수료 입금

    // 요청상태구분 : exc_state
    public static final String CODE_exc_state_air = "AIR";	// AIR : 에어드랍요청
    public static final String CODE_exc_state_req = "REQ";	// REQ : 요청
    public static final String CODE_exc_state_rej = "REJ";	// REJ :  리젝
    public static final String CODE_exc_state_err = "ERR";	// ERR :  에러
    public static final String CODE_exc_state_ok = "OK";	// OK : 지불완료
    public static final String CODE_exc_state_ing = "ING";	// ING : Node송금처리전, 진행중
    public static final String CODE_exc_state_fee = "FEE";	// FEE : 어드민에서 먼저 송금하고 유저지갑에서 어드민지갑으로의 대기상태
    public static final String CODE_exc_state_can = "CAN";	// CAN : 취소

    // 디폴트 컨펌수
    public static final int CODE_default_confirm = 6;
    public static final int CODE_default_blocks = 25;
    public static final int CODE_create_wallet_cnt = 30;

    public static final BigDecimal DATA_minusAmount =  new BigDecimal(-1);
    public static final BigDecimal DATA_zeroAmount =  new BigDecimal(0);
    public static final BigInteger DATA_1 =  new BigInteger("1");

    // 이상증후 확인용 컬럼명
    public static final String ABNORMAL_IDX = "idx";
    public static final String ABNORMAL_REG_DATES = "reg_dates";
    public static final String ABNORMAL_SEPARATOR = ",";
    public static final long ABNORMAL_CHECK_INTERVAL = 60 * 1000;

    //RIPPLE date 연산용 상수
    public static final String DATE_MILLENNIUM = "946684800";
    public static final BigInteger DEFAULT_FEE = new BigInteger("45");

    //ERC20 파트 상수
    public static final String FUNCTION_TRANSFER = "0xa9059cbb";
    public static final String FUNCTION_sendMultiSig = "0x39125215";

    /*********************************************************
     *               Core Functions
     **********************************************************/


    // 1. 지갑주소 생성
    public static final String WAS_NEW_ADDRESS		= "/newAddress";

    // 2. 지갑주소 유효성 체크
    public static final String WAS_VALID_ADDRESS	= "/isValidateAddress";

    // 3-1. 코인 송금 요청 (REQ)
    public static final String WAS_SEND_COIN_REQUEST	= "/sendCoinRequest";

    // 3-2. 코인 송금 취소 (CAN)
    public static final String WAS_SEND_COIN_CANCEL	= "/sendCoinCancel";

    // 3-3. 코인 송금 진행 (ING)
    public static final String WAS_SEND_COIN		= "/sendCoin";

    // 3-4. 이더리움 출금 재전송
    public static final String WAS_RE_SEND_URL		= "/reSendCoin";

    // 3-5. 코인 송금 진행 (Request없이 바로 송금)
    public static final String WAS_SEND_TO_ADDRESS		= "/sendToAddress";

    // 3-6. 송금 코인 OK (OK)
    public static final String WAS_SEND_COIN_OK	= "/sendCoinOK";

    // 4-1. 보유 코인 (User_id)
    public static final String WAS_BALANCE_OF		= "/balanceOf";

    // 4-2. 보유 코인 (Address)
    public static final String WAS_BALANCE_OF_ADDRESS	= "/balanceOfAddress";

    // 4-3. 보유 코인 (Node Address)
    public static final String WAS_BALANCE_OF_ADDRESS_BY_NODE	= "/balanceOfAddressByNode";

    // 5. 어드민지갑 충전
    public static final String WAS_CHARGE_ADMIN	= "/chargeAdminWallet";

    // 6. 트렌젝션 리스트
    public static final String WAS_LIST_TRANSACTIONS	= "/listTransactions";

    // 7. 입출금 리스트
    public static final String WAS_LIST_EXCHANGES	= "/listExchanges";

    // 8. Asset의 TrustLine
    public static final String WAS_MAKE_TRUSTLINE	= "/makeTrustLine";

    // 9. ETH 수수료 충전
    public static final String WAS_SEND_ETH_FEE	= "/sendEthFee";

    // 10. 오입금 토큰 출금
    public static final String WAS_SEND_ERC_WRONG_DEPOSIT	= "/sendErcWrongDeposit";

    // 11. 외부에서 생성된 지갑주소 Import
    public static final String WAS_IMPORT_WALLET	= "/importWallet";

    // 12. 지갑주소 활성화 (XRP)
    public static final String WAS_ACCOUNT_SET	= "/accountSet";

    // 13. 어카운트ID Resource정보 (EOS)
    public static final String WAS_ACCOUNT_RESOURCE	= "/accountResource";

    // 14. 유저 ERC20토큰 발란스 리스트
    public static final String WAS_LIST_USER_BALANCE	= "/listUserBalance";

    // 15. 이더리움 블록넘버 다시 읽어오기
    public static final String WAS_RELOAD_BLOCK_NUMBER	= "/reloadBlockNumber";

    // 16. 거래소쪽 재 통지
    public static final String WAS_RE_NOTIFICATION	= "/reNotification";

    // 17. 비트코인계열 estimateSmartFee
    public static final String WAS_ESTIMATE_SMARTFEE	= "/estimateSmartFee";

    // 18. 수동 입금 처리
    public static final String WAS_MANUAL_ACCOUNT_DEPOSIT		= "/manualAccountDeposit";


    /*********************************************************
     *               Admin Functions
     **********************************************************/
    // DB 및 노드 작동, 데몬 작동여부
    public static final String WAS_CHECK_WALLET_DAEMON	= "/checkWalletDaemon";

    // 지갑 노드 변경 (Active, Slave)
    public static final String WAS_SET_NODE_IP		= "/setNodeIP";
    public static final String WAS_GET_NODE_IP		= "/getNodeIP";

    // cryptoAsset의 송금에 필요한 fee를 보고/설정하는 API
    public static final String WAS_SET_FEE			= "/setFee";
    public static final String WAS_GET_FEE			= "/getFee";

    public static final String WAS_SET_SYS_VARS= "/setSysVars";
    public static final String WAS_GET_SYS_VARS= "/getSysVars";

    public static final String WAS_EMERGENCY_HOLD	= "/emergencyHold";

    public static final String WAS_SET_BLACK_LIST	= "/setBlackList";
    public static final String WAS_GET_BLACK_LIST	= "/getBlackList";
    public static final String WAS_SET_WHITE_LIST	= "/setWhiteList";
    public static final String WAS_GET_WHITE_LIST	= "/getWhiteList";

    public static final String WAS_SET_FDS	= "/setFds";
    public static final String WAS_GET_FDS	= "/getFds";

    /*********************************************************
     *               Inside Functions
     **********************************************************/
    public static final String WAS_INSIDE_IMPORT_ADDRESS	= "/importAddress";



    /*********************************************************
     *               Cold Wallet Functions
     **********************************************************/

    // 1. 지갑주소 생성
    public static final String API_NEW_ADDRESS		= "/api_newAddress";

    // 2. 트렌젝션 생성
    public static final String API_CREATE_TX		= "/api_createTx";

    // 3. 트렌젝션 싸인
    public static final String API_SIGN_TX		= "/api_signTx";

    // 4. 트렌젝션 보내기
    public static final String API_SEND_TX		= "/api_sendTx";


    /*
     * JSON Param Name
     */
    public static final String PARAM_address = "address";
    public static final String PARAM_privkey = "privkey";
    public static final String PARAM_txid = "txid";
    public static final String PARAM_vout = "vout";
    public static final String PARAM_account = "account";
    public static final String PARAM_scriptPubKey = "scriptPubKey";
    public static final String PARAM_amount = "amount";
    public static final String PARAM_confirmations = "confirmations";
    public static final String PARAM_toAddress = "toAddress";
    public static final String PARAM_fee = "fee";
    public static final String PARAM_category = "category";
    public static final String PARAM_label = "label";
    public static final String PARAM_tx_state = "tx_state";
    public static final String PARAM_time = "time";
    public static final String PARAM_timereceived = "timereceived";

    // DB컬럼
    public static final String DB_address = "address";
    public static final String DB_mb_address = "mb_address";
    public static final String DB_other_address = "other_address";
    public static final String DB_other_mb_idx = "other_mb_idx";
    public static final String DB_amount = "amount";
    public static final String DB_coin_type = "coin_type";
    public static final String DB_category = "category";
    public static final String DB_apply_state = "apply_state";
    public static final String DB_confirmations_out = "w_confirmations";
    public static final String DB_blocks = "blocks";
    public static final String DB_mb_idx = "mb_idx";
    public static final String DB_exc_type = "exc_type";
    public static final String DB_confirmations = "confirmations";
    public static final String DB_inoutside_type = "inoutside_type";
    public static final String DB_exc_state = "exc_state";
    public static final String DB_exc_state_msg = "exc_state_msg";
    public static final String DB_request_amount = "request_amount";
    public static final String DB_fee = "fee";
    public static final String DB_to_mb_idx = "to_mb_idx";
    public static final String DB_from_address = "from_address";
    public static final String DB_from_mb_idx = "from_mb_idx";
    public static final String DB_exc_total_fee = "exc_total_fee";
    public static final String DB_exc_real_fee = "exc_real_fee";
    public static final String DB_exc_margin_fee = "exc_margin_fee";
    public static final String DB_adm_idx = "adm_idx";
    public static final String DB_exc_certify_type = "exc_certify_type";
    public static final String DB_exc_certify_no = "exc_certify_no";
    public static final String DB_txid = "txid";
    public static final String DB_account = "account";
    public static final String DB_mb_id = "mb_id";
    public static final String DB_memo = "memo";
    public static final String DB_label = "label";
    public static final String DB_exc_request_ip = "exc_request_ip";
    public static final String DB_complete_date = "complete_date";
    public static final String DB_app_date = "app_date";
    public static final String DB_reg_date = "reg_date";
    public static final String DB_mod_date = "mod_date";

    public static final String DB_act_type = "act_type";
    public static final String DB_balance = "balance";
    public static final String DB_exchange_can = "exchange_can";
    public static final String DB_exchange_ing = "exchange_ing";
    public static final String DB_trade_can = "trade_can";
    public static final String DB_total_in = "total_in";
    public static final String DB_total_out = "total_out";
    public static final String DB_se_type = "se_type";
    public static final String DB_avg_price = "avg_buy_coin_price";
    public static final String DB_type = "type";
    public static final String DB_account_seq = "account_seq";
    public static final String DB_global_seq = "global_seq";
    public static final String DB_block = "block";
    public static final String DB_time = "time";
    public static final String DB_to_address = "to_address";

    public static final String DB_vout = "vout";
    public static final String DB_idx = "idx";

    public static final String DB_account_set = "account_set";

    // summary_contract(라 적고 입출금,매매 서머리임.) DB 컬럼
    public static final String DB_con_coin_seq 		= "coin_seq";
    public static final String DB_con_ord_no 		= "ord_no";
    public static final String DB_con_mb_idx 		= "mb_idx";
    public static final String DB_con_trd_type 		= "trd_type";
    public static final String DB_con_trd_state 		= "trd_state";
    public static final String DB_con_sc_type 		= "sc_type";
    public static final String DB_con_price 			= "price";
    public static final String DB_con_qty 			= "qty";
    public static final String DB_con_residual_qty 	= "residual_qty";
    public static final String DB_con_fee_type 		= "fee_type";
    public static final String DB_con_fee 			= "fee";
    public static final String DB_con_avg_coin_price = "avg_coin_price";
    public static final String DB_con_contract_date 	= "contract_date";
    public static final String DB_con_reg_date 		= "reg_date";

    // 차후 추가될 내용
    public static final String DB_con_real_fee 		= "real_fee";
    public static final String DB_con_margin_fee 	= "margin_fee";
    public static final String DB_con_other_address 	= "other_address";
    public static final String DB_con_memo 			= "memo";

    // 암호화 옵션 내용
    public static final String SECURITY_SEED		= "seed";
    public static final String SECURITY_KSIGN		= "ksign";
    public static final String SECURITY_UPSIGN		= "upsign";

    // ERC20 지갑주소 옵션 내용
    public static final String WALLET_ERC20_ADDR_ETH	= "ETH";	// 이더주소그대로
    public static final String WALLET_ERC20_ADDR_ERC20	= "ERC20";  // 새로운 이더주소

    //거래소 알림 옵션
    public static final String NOTI_TYPE_CALL_URL		= "url";
    public static final String NOTI_TYPE_SEND_KAFKA		= "kafka";

    public static final String NOTI_PARAM_COIN_TYPE		= "coin_type";
    public static final String NOTI_PARAM_INOUT_TYPE	= "inoutside_type";
    public static final String NOTI_PARAM_SEQ			= "seq";
    public static final String NOTI_PARAM_TYPE			= "type";
    public static final String NOTI_PARAM_EXC_TYPE		= "exc_type";
    public static final String NOTI_PARAM_STATE			= "exc_state";
    public static final String NOTI_PARAM_TXID			= "txid";
    public static final String NOTI_PARAM_VOUT			= "vout";
    public static final String NOTI_PARAM_AMOUNT		= "amount";
    public static final String NOTI_PARAM_TO_ADDRESS	= "toaddress";
    public static final String NOTI_PARAM_FROM_ADDRESS	= "fromaddress";
    public static final String NOTI_PARAM_CONFIRM		= "confirmation";
    public static final String NOTI_PARAM_DATETIME		= "datetime";
    public static final String NOTI_PARAM_USER_ID		= "user_id";

    public static final String NOTI_DEPOSIT				= "deposit";
    public static final String NOTI_WITHDRAW			= "withdraw";
    public static final String NOTI_ERROR				= "error";
    public static final String NOTI_BLOCK				= "block";
    public static final String NOTI_CONFIRMATIONS		= "confirmations";
    public static final String NOTI_CHARGE				= "charge";
    public static final String NOTI_CHARGE_F			= "charge_f";

    public static final String NOTI_INSIDE				= "inside";
    public static final String NOTI_OUTSIDE				= "outside";

    public static final String KST_DEFAULT_FEE			= "0.01";

    // 어드민 지갑주소
    public static final String ADMIN_WALLET_USER_ID			= "admin.wallet.user_id";
    public static final String ADMIN_XLM_WALLET_USER_IDS	= "admin.xlm.wallet.user_ids";
    public static final String ADMIN_XRP_WALLET_USER_IDS	= "admin.xrp.wallet.user_ids";
    public static final String ADMIN_EOS_WALLET_USER_IDS	= "admin.eos.wallet.user_ids";
    public static final String ADMIN_ETH_WALLET_USER_IDS	= "admin.eth.wallet.user_ids";

}
