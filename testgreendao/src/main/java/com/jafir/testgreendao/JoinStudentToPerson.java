package com.jafir.testgreendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by jafir on 17/2/21.
 */

@Entity
public class JoinStudentToPerson {
    @Id(autoincrement = true)
    private Long id;
    //和person关联的id
    private Long pid;
    //和student关联的id
    private Long sid;
    public Long getSid() {
        return this.sid;
    }
    public void setSid(Long sid) {
        this.sid = sid;
    }
    public Long getPid() {
        return this.pid;
    }
    public void setPid(Long pid) {
        this.pid = pid;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1975047655)
    public JoinStudentToPerson(Long id, Long pid, Long sid) {
        this.id = id;
        this.pid = pid;
        this.sid = sid;
    }
    @Generated(hash = 1596216153)
    public JoinStudentToPerson() {
    }
}
