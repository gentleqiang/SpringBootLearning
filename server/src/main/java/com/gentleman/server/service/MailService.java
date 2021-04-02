package com.gentleman.server.service;

import com.gentleman.model.entity.Appendix;
import com.gentleman.server.controller.MailSendController;
import com.mysql.jdbc.MiniAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import sun.print.ServiceDialog;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;

/**
 * @author 一粒尘埃
 * @date 2020/12/28/17:40
 */
@Service
public class MailService {

    private static final Logger log = LoggerFactory.getLogger(MailService.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Environment environment;

    /**
     * 发送简单文本
     * @param subject
     * @param content
     * @param tos
     */
    public void sendSimpleEmail(final String subject,final String content,final String[] tos)throws Exception{
        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        simpleMessage.setFrom(environment.getProperty("mail.send.from"));
        simpleMessage.setTo(tos);
        simpleMessage.setSubject(subject);
        simpleMessage.setText(content);
        javaMailSender.send(simpleMessage);
        log.info("发送简单文件成功");
    }

    /**
     * 发送简单文本带附件的邮件
     * @param subject
     * @param content
     * @param tos
     */
    public void sendAttachmentEmail(String subject, String content, String[] tos) throws Exception {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true,"utf-8");
        mimeMessageHelper.setFrom(environment.getProperty("mail.send.from"));
        mimeMessageHelper.setTo(tos);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(content);
        mimeMessageHelper.addAttachment(environment.getProperty("mail.send.attachment.one.name"),
                new File(environment.getProperty("mail.send.attachment.root.url")));

        javaMailSender.send(mimeMessage);
        log.info("发送简单文本带附件的邮件");
    }

    /**
     *
     * @param subject
     * @param content
     * @param tos
     * @param appendices
     * @throws Exception
     */
    public void sendUploadMail(String subject, String content, String[] tos, List<Appendix> appendices)throws Exception{
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true,"utf-8");
        mimeMessageHelper.setFrom(environment.getProperty("mail.send.from"));
        mimeMessageHelper.setTo(tos);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(content);
        for (Appendix appendix : appendices) {
            try { mimeMessageHelper.addAttachment(appendix.getName(),new File(environment.getProperty("spring.location.root")+appendix.getLocation()));

            }catch (Exception e){
                e.getStackTrace();
            }
        }
        javaMailSender.send(mimeMessage);
    }
}
