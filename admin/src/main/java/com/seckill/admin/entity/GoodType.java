package com.seckill.admin.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Data
@Entity
@Table(name = "good_type", schema = "db_seckill", catalog = "")
public class GoodType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String tag;
    private Timestamp createTime;
    private Timestamp updateDate;
    @Basic
    private Boolean isAvailable;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoodType that = (GoodType) o;
        return id == that.id &&
                Objects.equals(tag, that.tag) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(updateDate, that.updateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tag, createTime, updateDate);
    }
}
