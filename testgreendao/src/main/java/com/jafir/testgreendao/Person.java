package com.jafir.testgreendao;

import com.jafir.testgreendao.gen.DaoSession;
import com.jafir.testgreendao.gen.HeadDao;
import com.jafir.testgreendao.gen.PersonDao;
import com.jafir.testgreendao.gen.StudentDao;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.List;

/**
 * Created by jafir on 17/2/21.
 */
@Entity
public class Person {

    @Override
    public String toString() {
        return "Person{" +
                "students=" + getStudents() +
                ", head=" + head +
                ", hid=" + hid +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
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

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2105847700)
    public void setHead(Head head) {
        synchronized (this) {
            this.head = head;
            hid = head == null ? null : head.getId();
            head__resolvedKey = hid;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 257687018)
    public Head getHead() {
        Long __key = this.hid;
        if (head__resolvedKey == null || !head__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            HeadDao targetDao = daoSession.getHeadDao();
            Head headNew = targetDao.load(__key);
            synchronized (this) {
                head = headNew;
                head__resolvedKey = __key;
            }
        }
        return head;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2056799268)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPersonDao() : null;
    }

    public Long getHid() {
        return this.hid;
    }

    public void setHid(Long hid) {
        this.hid = hid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 238993120)
    public synchronized void resetStudents() {
        students = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 189379596)
    public List<Student> getStudents() {
        if (students == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            StudentDao targetDao = daoSession.getStudentDao();
            List<Student> studentsNew = targetDao._queryPerson_Students(id);
            synchronized (this) {
                if(students == null) {
                    students = studentsNew;
                }
            }
        }
        return students;
    }

    @Id(autoincrement = true)
    private Long id;
    private String name;

    private Long hid;
    @ToOne(joinProperty = "hid")
    private Head head;

    // 对多，@JoinEntity注解：entity 中间表；sourceProperty 实体属性；targetProperty 外链实体属性
    @ToMany
    @JoinEntity(
            entity = JoinStudentToPerson.class,
            sourceProperty = "pid",
            targetProperty = "sid"
    )
    private List<Student> students;


    @Generated(hash = 80859862)
    private transient Long head__resolvedKey;
    /** Used for active entity operations. */
    @Generated(hash = 778611619)
    private transient PersonDao myDao;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    @Generated(hash = 2010165863)
    public Person(Long id, String name, Long hid) {
        this.id = id;
        this.name = name;
        this.hid = hid;
    }

    @Generated(hash = 1024547259)
    public Person() {
    }




}
