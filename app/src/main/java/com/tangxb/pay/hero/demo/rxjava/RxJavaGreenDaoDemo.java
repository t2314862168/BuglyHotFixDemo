package com.tangxb.pay.hero.demo.rxjava;

import android.content.Context;
import android.util.Log;

import com.tangxb.pay.hero.greendao.model.DaoMaster;
import com.tangxb.pay.hero.greendao.model.DaoSession;
import com.tangxb.pay.hero.greendao.model.StudentModel;
import com.tangxb.pay.hero.greendao.model.TeacherModel;

import org.greenrobot.greendao.database.Database;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * <a href="http://www.th7.cn/Program/Android/201702/1115565.shtml">greendao3.0 多表关联踩坑实践</a><br>
 * Created by Taxngb on 2017/4/24.
 */

public class RxJavaGreenDaoDemo {
    private static final String TAG = RxJavaGreenDaoDemo.class.getSimpleName();
    /**
     * https://github.com/greenrobot/greenDAO/blob/master/tests/DaoTestEntityAnnotation/src/androidTest/java/org/greenrobot/greendao/test/entityannotation/CustomerOrderTest.java
     */
    private DaoSession daoSession;

    public RxJavaGreenDaoDemo(Context appContext) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(appContext, "greendao-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public void testSomething() {
        test2();
    }

    public void test1() {
        final String methodName = "####test1()";
        Observable
                .create(new ObservableOnSubscribe<Long>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<Long> e) throws Exception {
                        e.onNext(10L);
                        Log.d(TAG + methodName, "call Current Thread===" + Thread.currentThread().getName());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long count) throws Exception {
                        Log.d(TAG + methodName, "subscribe Current Thread===" + Thread.currentThread().getName());
                        Log.d(TAG + methodName, "subscribe count===" + count);
                    }
                });
    }

    public void test2() {
        final String methodName = "####test2()";

    }

    private List<StudentModel> getStudents() {
        TeacherModel teacher = new TeacherModel();
        teacher.setTeacherId(new Long(1));
        teacher.setName("teacher1");
        getDaoSession().insert(teacher);

        StudentModel student = new StudentModel();
        student.setStudentId(new Long(1));
        student.setName("student1");
        student.setT_teacherId(teacher.getTeacherId());
        getDaoSession().insert(student);

        List<TeacherModel> teacherList = getDaoSession().getTeacherModelDao().loadAll();
        List<StudentModel> students = teacherList.get(0).getStudents();
        return students;
    }
}
