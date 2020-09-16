package com.example.memo.Agenda;

import java.util.List;

/**
 * Created by MC on 16/3/18.
 */
public class FirstEntity {
    String userName;
    List<SecondEntity> secondList;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<SecondEntity> getSecondList() {
        return secondList;
    }

    public void setSecondList(List<SecondEntity> secondList) {
        this.secondList = secondList;
    }
}
