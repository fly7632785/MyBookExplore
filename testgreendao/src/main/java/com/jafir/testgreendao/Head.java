package com.jafir.testgreendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by jafir on 17/2/21.
 */
@Entity
public class Head {

    @Override
    public String toString() {
        return "Head{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Id(autoincrement = true)
    private Long id;
    private String name;
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
    @Generated(hash = 213555330)
    public Head(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    @Generated(hash = 1745729831)
    public Head() {
    }

}
