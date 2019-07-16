package com.ayundao.base.shiro;

import org.apache.shiro.session.Session;

import java.util.Date;

/**
 * @ClassName: SessionInMemory
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/12 14:24
 * @Description: shiro本地缓存
 * @Version: V1.0
 */
public class SessionInMemory {

    private Session session;
    private Date createTime;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
