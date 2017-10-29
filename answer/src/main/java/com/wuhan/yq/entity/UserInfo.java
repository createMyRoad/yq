package com.wuhan.yq.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * Created by fraiday on 2017/10/27.
 */
@Setter
@Getter
@Table(name = "y_e_user_logininfo")
public class UserInfo extends BaseEntity
{
    @Column(name = "userid")
    private String userId;
}
