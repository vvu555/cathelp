package com.gsc.cathelp.web.user;


import com.gsc.cathelp.dao.CatAdoptRepository;
import com.gsc.cathelp.po.Adopt;
import com.gsc.cathelp.po.Cat;
import com.gsc.cathelp.service.CatAdoptService;
import com.gsc.cathelp.service.PayService;
import com.gsc.cathelp.util.OrderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class PaymentController {

    private Logger log = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PayService payservice;
    @Autowired
    private CatAdoptService catAdoptService;
    @Autowired
    private CatAdoptRepository catAdoptRepository;

    @GetMapping("/getpayment")
    public String getPay(){
        return "user/userPayment";
    }

    @ResponseBody
    @GetMapping(value = "/payment/{id}" , produces = {"text/html;charset=UTF-8"})
    public Object pay (@PathVariable Long id, Model model) throws Exception {
        Adopt a = catAdoptService.getAdoptId(id);
        if(a.getPaySn() == null){
            String sn = OrderUtils.getOrderNo();
            model.addAttribute(a);
            catAdoptRepository.updateSn(id,sn);
            return payservice.pay(a,sn);
        }
        else{
            model.addAttribute(a);
            return payservice.oldPay(a);
        }
    }

    @RequestMapping("/pat_notify")
    @ResponseBody
    public String payNotify(HttpServletRequest request){
        log.info("******成功进入支付宝异步通知接口******");
        String sn = request.getParameter("out_trade_no");
        catAdoptRepository.updateNotifySn(sn);
        return "success";
    }
}
