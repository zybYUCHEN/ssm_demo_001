package com.itcast.service.impl;

import com.github.pagehelper.PageHelper;
import com.itcast.dao.RoleDao;
import com.itcast.dao.UserInfoDao;
import com.itcast.domain.Role;
import com.itcast.domain.UserInfo;
import com.itcast.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther : 32725
 * @Date: 2019/2/22 21:44
 * @Description:
 */
@Service
@Transactional
public class RolesServiceImpl implements RolesService {
    @Autowired
    private RoleDao roleDao;
    /**
     * @Author: 32725
     * @Param: []
     * @Return: java.lang.String
     * @Description: 查找所有角色，以及角色所有的权限
     **/
    @Override
    public List<Role> findAll(Integer pageNum,Integer pageSize,String term) throws Exception {
        //1.使用PageHelper静态方法进行物理分页
        PageHelper.startPage(pageNum, pageSize);
        //2.判断条件是否存在,存在就加上模糊查询
        if (term!=null&&!term.equals("")){
            term="%"+term+"%";
        }
        return roleDao.findAll(term);
    }

    @Override
    public List<Role> findAll() throws Exception {
        List<Role> list = roleDao.findAll("");
        return list;
    }

    /**
     * @Author: 32725
     * @Param: [id]
     * @Return: void
     * @Description: 添加指定id的权限，添加数据到users_role中
     **/
    @Override
    public void addRole(String userId,String roleId) throws Exception{
        roleDao.addRole(userId,roleId);
    }
}
