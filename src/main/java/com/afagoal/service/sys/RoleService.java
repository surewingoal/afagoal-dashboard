package com.afagoal.service.sys;

import com.afagoal.dao.system.SysRoleDao;
import com.afagoal.dto.sys.RoleRequestDto;
import com.afagoal.entity.system.SysRole;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by BaoCai on 18/2/24.
 * Description:
 */
@Service
public class RoleService {

    @Autowired
    private SysRoleDao sysRoleDao;
    @Autowired
    private RoleFunctionService roleFunctionService;

    @Transactional
    public void save(RoleRequestDto roleRequestDto) {
        SysRole role = new SysRole();
        role.setId(roleRequestDto.getId());
        role.setRoleName(roleRequestDto.getRoleName());
        role.setIntroduce(roleRequestDto.getIntroduce());
        if (null == role.getId()) {
            sysRoleDao.save(role);
        } else {
            sysRoleDao.merge(role);
        }

        roleFunctionService.delete(role.getId(),roleRequestDto.getFunctionIds());
        roleFunctionService.save(role.getId(),roleRequestDto.getFunctionIds());
    }

}
