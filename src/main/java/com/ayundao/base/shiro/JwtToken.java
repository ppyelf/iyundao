package com.ayundao.base.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @ClassName: JwtToken
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/16 16:10
 * @Description: JwtToken
 * @Version: V1.0
 */
public class JwtToken implements AuthenticationToken {

    private static final long serialVersionUID = 1900286977895826147L;

    private String account;

    private String password;

    private boolean rememberMe;

    /**
     * Token
     */
    private String token;

    public JwtToken(String account, String password, boolean rememberMe, String token) {
        this.token = token;
        this.account = account;
        this.password = password;
        this.rememberMe = rememberMe;
    }

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return this.account;
    }

    @Override
    public Object getCredentials() {
        return this.password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
}
