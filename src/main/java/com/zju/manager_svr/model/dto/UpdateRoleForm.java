package com.zju.manager_svr.model.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.zju.manager_svr.valid.UserDetailValidGroup;
import com.zju.manager_svr.valid.UserRoleValidGroup;

import lombok.Data;

@Data
public class UpdateRoleForm {

    @NotNull(groups = { UserRoleValidGroup.class, UserDetailValidGroup.class }, message = "userID不能为空")
    private Integer userID;

    @Min(value = 1, groups = { UserRoleValidGroup.class }, message = "role最小值为1")
    @Max(value = 3, groups = { UserRoleValidGroup.class }, message = "role最大值为3")
    @NotNull(groups = {UserRoleValidGroup.class},message = "role不能为空")
    private Integer role;
}
