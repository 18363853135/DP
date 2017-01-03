package cn.ipathology.dp.entityClass;

/**
 * Created by wdb on 2016/11/15.
 * 登录信息 实体类对象
 */

public class Login {


    /**
     * group : 200  用户分组（枚举：enumAccountGroup）
     * account_id : 11
     * center_name : 华夏病理云诊断中心
     * center_id : 28
     * site_id : null
     * sex : null 性别（枚举：enumSex）
     * head_image_url : v2/1478827346.08_804.jpg  头像
     * token : dddd
     * account_phone : 18911111111
     * site_name : null
     * user_name : 就不告诉你
     * email : null 邮箱
     * head_image : 8885
     * signature : null
     * memo : null 简介
     * company : null 单位
     * expert_title : null 职称
     * type : null 类型（枚举：enumExpertType）
     * position : null 职务
     *
     */

    private int group;
    private int account_id;
    private String center_name;
    private int center_id;
    private Object site_id;
    private int sex;
    private String head_image_url;
    private String token;
    private String account_phone;
    private Object site_name;
    private String user_name;
    private Object email;
    private int head_image;

    private String signature;
    private String memo;
    private String company;
    private String expert_title;
    private String type;
    private String position;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getExpert_title() {
        return expert_title;
    }

    public void setExpert_title(String expert_title) {
        this.expert_title = expert_title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public String getCenter_name() {
        return center_name;
    }

    public void setCenter_name(String center_name) {
        this.center_name = center_name;
    }

    public int getCenter_id() {
        return center_id;
    }

    public void setCenter_id(int center_id) {
        this.center_id = center_id;
    }

    public Object getSite_id() {
        return site_id;
    }

    public void setSite_id(Object site_id) {
        this.site_id = site_id;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getHead_image_url() {
        return head_image_url;
    }

    public void setHead_image_url(String head_image_url) {
        this.head_image_url = head_image_url;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAccount_phone() {
        return account_phone;
    }

    public void setAccount_phone(String account_phone) {
        this.account_phone = account_phone;
    }

    public Object getSite_name() {
        return site_name;
    }

    public void setSite_name(Object site_name) {
        this.site_name = site_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public int getHead_image() {
        return head_image;
    }

    public void setHead_image(int head_image) {
        this.head_image = head_image;
    }
}
