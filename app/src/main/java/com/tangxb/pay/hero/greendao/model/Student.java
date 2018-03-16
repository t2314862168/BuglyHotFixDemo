package com.tangxb.pay.hero.greendao.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * 学生实体
 * Created by Tangxb on 2017/2/20.
 */
@Entity
public class Student {
    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "studentId")
    private Long studentId;
    @Property(nameInDb = "studentName")
    private String name;
    @Property(nameInDb = "age")
    private int age;
    @Property(nameInDb = "teacherId")
    private Long teacherId;
    @Property(nameInDb = "classGradeId")
    private Long classGradeId;
    @ToMany(joinProperties = {@JoinProperty(name = "teacherId", referencedName = "teacherId")})
    private List<Teacher> teachers;
    @ToOne(joinProperty = "classGradeId")
    private ClassGrade classGrade;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1943931642)
    private transient StudentDao myDao;
    @Generated(hash = 731479184)
    public Student(Long id, Long studentId, String name, int age, Long teacherId, Long classGradeId) {
        this.id = id;
        this.studentId = studentId;
        this.name = name;
        this.age = age;
        this.teacherId = teacherId;
        this.classGradeId = classGradeId;
    }
    @Generated(hash = 1556870573)
    public Student() {
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
    public int getAge() {
        return this.age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public Long getTeacherId() {
        return this.teacherId;
    }
    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }
    public Long getClassGradeId() {
        return this.classGradeId;
    }
    public void setClassGradeId(Long classGradeId) {
        this.classGradeId = classGradeId;
    }
    @Generated(hash = 606928493)
    private transient Long classGrade__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 749130637)
    public ClassGrade getClassGrade() {
        Long __key = this.classGradeId;
        if (classGrade__resolvedKey == null || !classGrade__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ClassGradeDao targetDao = daoSession.getClassGradeDao();
            ClassGrade classGradeNew = targetDao.load(__key);
            synchronized (this) {
                classGrade = classGradeNew;
                classGrade__resolvedKey = __key;
            }
        }
        return classGrade;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 989850003)
    public void setClassGrade(ClassGrade classGrade) {
        synchronized (this) {
            this.classGrade = classGrade;
            classGradeId = classGrade == null ? null : classGrade.getId();
            classGrade__resolvedKey = classGradeId;
        }
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 725275931)
    public List<Teacher> getTeachers() {
        if (teachers == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TeacherDao targetDao = daoSession.getTeacherDao();
            List<Teacher> teachersNew = targetDao._queryStudent_Teachers(teacherId);
            synchronized (this) {
                if (teachers == null) {
                    teachers = teachersNew;
                }
            }
        }
        return teachers;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 456331696)
    public synchronized void resetTeachers() {
        teachers = null;
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1701634981)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getStudentDao() : null;
    }
}
