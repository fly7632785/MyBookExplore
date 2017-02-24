package com.jafir.testgreendao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jafir.testgreendao.gen.HeadDao;
import com.jafir.testgreendao.gen.JoinStudentToPersonDao;
import com.jafir.testgreendao.gen.PersonDao;
import com.jafir.testgreendao.gen.StudentDao;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test();
    }

    private void test() {

        PersonDao personDao = GreendaoHelper.getDaoSession().getPersonDao();
        HeadDao headDao = GreendaoHelper.getDaoSession().getHeadDao();
        StudentDao studentDao = GreendaoHelper.getDaoSession().getStudentDao();
        JoinStudentToPersonDao spDao = GreendaoHelper.getDaoSession().getJoinStudentToPersonDao();

//        Head head = new Head();
//        head.setId(14l);
//        head.setName("head");

        List<Person> persons1 = personDao.queryBuilder().build().list();
        for (Person person : persons1) {
            Log.d("debug","before person:"+person.toString());
        }


        Person p1 = new Person();
        p1.setId(1l);
        p1.setName("jafir1");
        Person p2 = new Person();
        p2.setId(2l);
        p2.setName("jafir2");
        Person p3 = new Person();
        p3.setId(3l);
        p3.setName("jafir3");


        Student stu1 = new Student();
        stu1.setId(1l);
        stu1.setName("stu1");
        Student stu2 = new Student();
        stu2.setId(2l);
        stu2.setName("stu2");
        Student stu3 = new Student();
        stu3.setId(3l);
        stu3.setName("stu3");


        // 模拟 多对多关系
        // 假如 p1有3个：stu1\stu2\stu3
        //  stu1 stu2 stu3 都有2个 :p1\p2

        //p1有stu1 stu2 stu3     那么反过来stu123都有p1
        JoinStudentToPerson sp1 = new JoinStudentToPerson();
        sp1.setPid(1l);
        sp1.setSid(1l);
        JoinStudentToPerson sp2 = new JoinStudentToPerson();
        sp2.setPid(1l);
        sp2.setSid(2l);
        JoinStudentToPerson sp3 = new JoinStudentToPerson();
        sp3.setPid(1l);
        sp3.setSid(3l);

        //p2有stu1 stu2 stu3     那么反过来stu123都有p2
        JoinStudentToPerson sp4 = new JoinStudentToPerson();
        sp4.setPid(2l);
        sp4.setSid(1l);
        JoinStudentToPerson sp5 = new JoinStudentToPerson();
        sp5.setPid(2l);
        sp5.setSid(2l);
        JoinStudentToPerson sp6 = new JoinStudentToPerson();
        sp6.setPid(2l);
        sp6.setSid(3l);

        spDao.insert(sp1);
        spDao.insert(sp2);
        spDao.insert(sp3);
        spDao.insert(sp4);
        spDao.insert(sp5);
        spDao.insert(sp6);


        personDao.insert(p1);
        personDao.insert(p2);
        personDao.insert(p3);

        studentDao.insert(stu1);
        studentDao.insert(stu2);
        studentDao.insert(stu3);

//        headDao.insert(head);
//        personDao.insert(p1);

        List<Person> persons = personDao.queryBuilder().build().list();

        for (Person person : persons) {
            Log.d("debug","person:"+person.toString());
        }
    }
}
