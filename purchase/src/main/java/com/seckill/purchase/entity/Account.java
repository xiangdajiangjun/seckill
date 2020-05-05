package com.seckill.purchase.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
@Data
@Entity
@Table(name = "account", schema = "db_seckill", catalog = "")
public class Account {
    @Id
    @Column(name = "username")
    private String username;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "salt")
    private String salt;
    @Basic
    @Column(name = "status")
    private Boolean state;
    @Basic
    @Column(name = "type")
    private String type;
    @Basic
    @CreationTimestamp
    private Timestamp createDate;
    @UpdateTimestamp
    private Timestamp updateDate;
    
    @ManyToMany(targetEntity = Role.class,fetch= FetchType.EAGER)//立即从数据库中进行加载数据;
    @JoinTable(name = "account_role", joinColumns = { @JoinColumn(name = "account") }, inverseJoinColumns ={@JoinColumn(name = "role_id") })
    private List<Role> roleList;// 一个用户具有多个角色


    public Account() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account that = (Account) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(type, that.type) &&
                Objects.equals(createDate, that.createDate) &&
                Objects.equals(updateDate, that.updateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, type, createDate, updateDate);
    }
}
