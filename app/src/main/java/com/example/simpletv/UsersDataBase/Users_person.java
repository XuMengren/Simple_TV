package com.example.simpletv.UsersDataBase;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

/***
 *创建时间：2021/3/8 3:33 PM
 *作者：xyd
 *描述：Users_person实体类
 *参数：
 *返回值(Y/N):
 */
@Entity(tableName = "Users_person")
public class Users_person {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int u_id;
    @NonNull
    @ColumnInfo(name = "accountNumber")
    private String accountNumber;
    @NonNull
    @ColumnInfo(name = "password")
    private String password;
    @ColumnInfo(name = "sex")
    private String sex;
    @ColumnInfo(name = "nickname")
    private String nickname;
    @ColumnInfo(name = "dateOfBirth")
    private String dateOfBirth;
    @ColumnInfo(name = "address")
    private String address;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "headPortrait")
    private byte[] headPortrait;
    @ColumnInfo(name = "securityQuestion")
    private String securityQuestion;
    @ColumnInfo(name = "answer")
    private String answer;
    @ColumnInfo(name = "background_pic")
    private byte[] background_pic;
    @ColumnInfo(name = "signature")
    private String signature;



    public int getU_id() {
        return u_id;
    }

    public void setU_id(int u_id) {
        this.u_id = u_id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(byte[] headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public byte[] getBackground_pic() {
        return background_pic;
    }

    public void setBackground_pic(byte[] background_pic) {
        this.background_pic = background_pic;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
