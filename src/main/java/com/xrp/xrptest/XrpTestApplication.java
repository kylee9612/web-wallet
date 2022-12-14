package com.xrp.xrptest;

import  com.xrp.service.XrpClientService;
import com.xrp.service.XrpWalletService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.xrpl.xrpl4j.model.transactions.Address;
import org.xrpl.xrpl4j.wallet.Wallet;

@SpringBootApplication
public class XrpTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(XrpTestApplication.class, args);
		//ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		//XRPTest xrpTest = new XRPTest();
		//xrpTest.xrpClientService.checkServer();
		XrpClientService xrpClientService = new XrpClientService();
		XrpWalletService xrpWalletService = new XrpWalletService();
		xrpClientService.checkServer();
		xrpClientService.getInfo();
		String pubKey = "EDCB72D7DFFDDEA267F256A9FFAD3D356C66201E9BF14BBEBD7E7C8691396AEADA";
		String priKey = "EDDEC5B09C3C0CBD2E80CAC17C7C850E35B094E3D3DF31E03E23F89E0ECA5A4F76";
		Wallet testWallet = xrpWalletService.getTestWallet(pubKey,priKey);
		xrpWalletService.walletInfo(testWallet);
		Address add = Address.builder().value(pubKey).build();
		xrpClientService.checkBalance(testWallet.classicAddress());
		//Address address = Address.builder().value(pubKey).build();
//		Address address1 = Address.of(pubKey);
//		xrpClientService.checkBalance(address1);
//		xrpClientService.fundFaucet();
	}
}
