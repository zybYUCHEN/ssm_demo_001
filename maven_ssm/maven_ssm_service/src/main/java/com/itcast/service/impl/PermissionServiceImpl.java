package com.itcast.service.impl;

import com.github.pagehelper.PageHelper;
import com.itcast.dao.PermissionDao;
import com.itcast.domain.Permission;
import com.itcast.domain.UserInfo;
import com.itcast.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther : 32725
 * @Date: 2019/2/24 11:17
 * @Description: 权限控制的业务层接口实现类
 */
@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;


    /**
     * @Author: 32725
     * @Param: [permission]
     * @Return: void
     * @Description: 添加权限
     **/
    public void savePermission(Permission permission)throws Exception{
        permissionDao.savePermission(permission);
    }
    /**
     * @Author: 32725
     * @Param: [id]
     * @Return: void
     * @Description: 根据权限id删除权限
     **/
    @Override
    public void deletePermission(String id) throws Exception {
        //1.先删除关联表中的数据
        permissionDao.deleteRoleAndPermission(id);
        //2.删除权限表中的数据
        permissionDao.deletePermission(id);
    }

    /**
     * @Author: 32725
     * @Param: [id]
     * @Return: com.itcast.domain.Permission
     * @Description: 根据id查找指定权限
     **/
    @Override
    public Permission findOne(String id) throws Exception {
        return permissionDao.findOne(id);
    }

    /**
     * @Author: 32725
     * @Param: [id, model]
     * @Return: java.lang.String
     * @Description: 修改权限
     **/
    @Override
    public void update(Permission permission, String id) throws Exception{
        permissionDao.update(permission,id);
    }

    /**
     * @Author: 32725
     * @Param: [pageNum, pageSize, term]
     * @Return: java.util.List<com.itcast.domain.UserInfo>
     * @Description: 分页查询所有权限
     **/
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    @Override
    public List<UserInfo> findAll(Integer pageNum, Integer pageSize, String term) throws Exception {
        //1.使用PageHelper静态方法进行物理分页
        PageHelper.startPage(pageNum, pageSize);
        //2.判断条件是否存在,存在就加上模糊查询
        if (term!=null&&!term.equals("")){
            term="%"+term+"%";
        }
        return permissionDao.findAll(term);
    }
}
