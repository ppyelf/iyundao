//package com.ayundao.entity;
//
//import com.ayundao.base.BaseEntity;
//
//import javax.persistence.*;
//
///**
// * @ClassName: UserApp
// * @project: ayundao
// * @author: 念
// * @Date: 2019/7/20 9:44
// * @Description: 用户授权信息
// * @Version: V1.0
// */
//@Entity
//@Table(name = "t_user_app")
//public class UserApp extends BaseEntity {
//
//    private final static long serialVersionUID = -183209481093280491L;
//
//    /**
//     * 第三方类型
//     */
//    @Enumerated(EnumType.ORDINAL)
//    @Column(name = "APPTYPE", nullable = false)
//    private APP_TYPE type;
//
//    /**
//     * 所属用户
//     */
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "USERID")
//    private User user;
//
//    /**
//     * 昵称
//     */
//    @Column(name = "NICKNAME", nullable = false, length = 50)
//    private String nickName;
//
//
//
//    public enum APP_TYPE{
//        /**
//         * 微信
//         */
//        WeChat(0, "微信"),
//
//        /**
//         * 小程序
//         */
//        WeApp(1, "微信小程序");
//
//        private int index;
//
//        private String name;
//
//        APP_TYPE(int index, String name) {
//            this.index = index;
//            this.name = name;
//        }
//
//        public int getIndex() {
//            return index;
//        }
//
//        public void setIndex(int index) {
//            this.index = index;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//    }
//}
