package com.seckill.admin.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Data
public class Account {
    private String username;
    private String password;
    private String name;
    private String salt;
    private Boolean state;
    private String type;
    private Timestamp createDate;
    private Timestamp updateDate;
    
    private List<Role> roleList;// 一个用户具有多个角色


}
