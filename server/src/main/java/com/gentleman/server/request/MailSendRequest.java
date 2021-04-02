package com.gentleman.server.request;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @author 一粒尘埃
 * @date 2020/12/28/16:02
 */
public class MailSendRequest implements Serializable  {

    @NotBlank
    private String tos;

    @NotBlank
    private String subject;

    @NotBlank
    private String content;

    public MailSendRequest() {

    }

    public MailSendRequest(String tos, String subject, String content) {
        this.tos = tos;
        this.subject = subject;
        this.content = content;
    }

    public String getTos() {
        return tos;
    }

    public void setTos(String tos) {
        this.tos = tos;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "MailSendRequest{" +
                "tos='" + tos + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
