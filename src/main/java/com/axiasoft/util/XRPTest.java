package com.axiasoft.util;

import com.axiasoft.controller.XRPController;
import com.axiasoft.service.XrpClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class XRPTest {
    @Autowired
    @Qualifier("xrpClientService")
    public XrpClientService xrpClientService;
}
