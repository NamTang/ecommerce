package com.example.ecommerce.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.example.ecommerce.model.Code;
import com.example.ecommerce.model.Customer;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {

    @Autowired
    private CodeService codeService;
    @Autowired
    private MailSender mailSender;
    @Value("${fromAddress}")
    private String fromAddress;
    @Value("${websiteAddr}")
    private String websiteAddr;

    @Override
    public void sendActiveCode(Customer customer) {
        List<Code> codes = codeService.findByCodeTypeAndCustomer(0, customer);
        String msgBody = "Click or copy this link to the browser -> http://" + websiteAddr + "/rg/";
        msgBody = msgBody + codes.get(0).getCodeStr();

        sendMail(customer.getEmail(), "Active Your Account", msgBody);
    }

    @Override
    public void sendResetPasswordCode(Customer customer) {
        Code code = new Code();
        code.setCodeDate(new Date());
        code.setCodeType(1);
        code.setCustomer(customer);

        codeService.save(code);
        String msgBody = websiteAddr + "/rp/" + code.getCodeStr();

        sendMail(customer.getEmail(), "Reset Your Passowrd", msgBody);
    }

    private void sendMail(String toAddress, String subject, String msgBody) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(fromAddress);
        msg.setTo(toAddress);
        msg.setSubject(subject);
        msg.setText(msgBody);
        mailSender.send(msg);
    }
}
