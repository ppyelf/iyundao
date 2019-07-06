package com.ayundao.base;

import javax.persistence.Embeddable;

/**
 * @ClassName: BaseComponent
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/3 11:20
 * @Description: 基类 - 组件
 * @Version: V1.0
 */
@Embeddable
public class BaseComponent {

    private final static long serialVersionUID = -129830213810920838L;

    /**
     * ID
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    private BaseComponent() {
    }

    public BaseComponent(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
