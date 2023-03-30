package com.gsc.cathelp.service;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.gsc.cathelp.po.Adopt;
import com.gsc.cathelp.util.OrderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class PayService {


    @Value("${alipay.returnUrl}")
    private String returnUrl;


    public Object pay (Adopt adopt,String sn) throws Exception {
        AlipayTradePagePayResponse response = Factory.Payment
                .Page()
                .pay(adopt.getCat().getName(), sn, "100", returnUrl
                );
        return response.body;
    }

    public Object oldPay (Adopt adopt) throws Exception {
        AlipayTradePagePayResponse response = Factory.Payment
                .Page()
                .pay(adopt.getCat().getName(), adopt.getPaySn(), "100", returnUrl
                );
        return response.body;
    }
}
