package com.tangxb.pay.hero.greendao.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Taxngb on 2017/4/24.
 */
@Entity
public class StudentModel {
    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "studentId")
    private Long studentId;
    @Property(nameInDb = "studentName")
    private String name;
    @Property(nameInDb = "t_teacherId")
    private Long t_teacherId;
    @Generated(hash = 994784035)
    public StudentModel(Long id, Long studentId, String name, Long t_teacherId) {
        this.id = id;
        this.studentId = studentId;
        this.name = name;
        this.t_teacherId = t_teacherId;
    }
    @Generated(hash = 2060229341)
    public StudentModel() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getStudentId() {
        return this.studentId;
    }
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getT_teacherId() {
        return this.t_teacherId;
    }
    public void setT_teacherId(Long t_teacherId) {
        this.t_teacherId = t_teacherId;
    }
}
