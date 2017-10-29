package com.wuhan.yq.controller;

import com.wuhan.yq.dao.UserInfoMapper;
import com.wuhan.yq.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by fraiday on 2017/10/27.
 */
@RestController
@RequestMapping("user")
public class UserInfoController
{
    @Autowired
    private UserInfoMapper userInfoMapper;
    
    @RequestMapping("list")
    public List<UserInfo> list()
    {
       return  userInfoMapper.selectAll();
    }
}
