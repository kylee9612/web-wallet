package com.axia.xrp.util;

import com.axia.xrp.service.XrpClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class XRPTest {
    @Autowired
    @Qualifier("xrpClientService")
    public XrpClientService xrpClientService;
}
