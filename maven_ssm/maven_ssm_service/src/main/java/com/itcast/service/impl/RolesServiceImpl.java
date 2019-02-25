package com.itcast.service.impl;

import com.github.pagehelper.PageHelper;
import com.itcast.dao.RoleDao;
import com.itcast.dao.UserInfoDao;
import com.itcast.domain.Permission;
import com.itcast.domain.Role;
import com.itcast.domain.UserInfo;
import com.itcast.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther : 32725
 * @Date: 2019/2/22 21:44
 * @Description:
 */
@Service
@Transactional(propagation = Propagation.REQUIRED,readOnly = false)
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
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Role> findAll(Integer pageNum,Integer pageSize,String term) throws Exception {
        //1.使用PageHelper静态方法进行物理分页
        PageHelper.startPage(pageNum, pageSize);
        //2.判断条件是否存在,存在就加上模糊查询
        if (term!=null&&!term.equals("")){
            term="%"+term+"%";
        }
        return roleDao.findAll(term);
    }

    /**
    * @Author: 32725
    * @Param: []
    * @Return: java.util.List<com.itcast.domain.Role>
    * @Description: 查找所有角色
    **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
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

    /**
     * @Author: 32725
     * @Param: [role]
     * @Return: java.lang.String
     * @Description: 保存新角色
     **/
    @Override
    public void saveRole(Role role) throws Exception {
        roleDao.saveRole(role);
    }

    /**
     * @Author: 32725
     * @Param: [ids]
     * @Return: void
     * @Description: 删除指定的角色
     **/
    @Override
    public void deleteRole(String[] ids) throws Exception {
        //1.遍历id集合，循环删除
        for (String id : ids) {
            //2.先删除关联表中的记录
            roleDao.deleteUserAndRole(id);
            roleDao.deleteRoleAndPermission(id);
            //3.删除角色表中的数据
            roleDao.deleteRole(id);
        }

    }

    /**
    * @Author: 32725
    * @Param: [id]
    * @Return: void
    * @Description: 根据权限id删除用户的权限
    **/
    @Override
    public void deleteRoleAndPermission(String id) throws Exception {
        //删除关联表中的记录
        roleDao.deleteRoleAndPermission(id);
    }

    /**
     * @Author: 32725
     * @Param: [roleId]
     * @Return: java.util.List<com.itcast.domain.Permission>
     * @Description: 查询当前角色所有权限
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Permission> findAllPermissionById(String roleId) throws Exception{

        return roleDao.findAllPermissionById(roleId);
    }

    /**
     * @Author: 32725
     * @Param: [roleId]
     * @Return: com.itcast.domain.Role
     * @Description: 根据角色id查找角色
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Role findById(String roleId) throws Exception {
        return roleDao.findById(roleId);
    }
    /**
     * @Author: 32725
     * @Param: [s]
     * @Return: void
     * @Description: 给指定的用户添加权限
     **/
    @Override
    public void addPermission(String roleId, String permissionId) throws Exception {
        roleDao.addPermission(roleId,permissionId);
    }
}
