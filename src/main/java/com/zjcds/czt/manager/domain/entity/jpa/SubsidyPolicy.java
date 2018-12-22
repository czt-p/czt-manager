package com.zjcds.czt.manager.domain.entity.jpa;

import com.zjcds.common.jpa.domain.CreateModifyTime;

import javax.persistence.*;

/**
 * @author luokp on 2018/12/22.
 */
@Entity
@Table(name = "t_subsidy_policy")
public class SubsidyPolicy extends CreateModifyTime {

    /**
     * id
     **/
    private Integer id;

    /**
     * 政策名称
     **/
    private String name;

    /**
     * 所属地区
     **/
    private String region;

    /**
     * 登记机关
     **/
    private String registerDepartment;

    /**
     * 内容
     **/
    private String content;

    @Id
    @Column(name = "ID")
    @TableGenerator(name = "idGenerator", table = "t_id_generator", pkColumnName = "id_key", pkColumnValue = "subsidyPolicy", valueColumnName = "id_value")
    @GeneratedValue(generator = "idGenerator", strategy = GenerationType.TABLE)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "region")
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Basic
    @Column(name = "register_department")
    public String getRegisterDepartment() {
        return registerDepartment;
    }

    public void setRegisterDepartment(String registerDepartment) {
        this.registerDepartment = registerDepartment;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
