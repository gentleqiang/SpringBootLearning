package com.gentleman.server.request;



import com.gentleman.server.annotation.SexAnnotation;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author 一粒尘埃
 * @date 2020/11/3/14:55
 */
public class UserRequest {

    private Integer id;

    private String userName;

    private String posName;

    private Integer age;

    private String mobile;

    private String profile;

    private String email;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPosName() {
        return posName;
    }

    public void setPosName(String posName) {
        this.posName = posName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserRequest{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", posName='" + posName + '\'' +
                ", age=" + age +
                ", mobile='" + mobile + '\'' +
                ", profile='" + profile + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
