-------------------------- SysFunctionService --------------------------------------------
查询功能树
#sql("getFunctionTree")
	select * from sys_function where parent_code=? order by order_no asc
#end

查询用户权限菜单树
#sql("getUserFunctionTree")
	select  DISTINCT  a.order_no ,a.id,a.parent_code,a.func_name,a.link_page,a.icon,a.func_type,a.is_stop 
 	from sys_function a 
	left join sys_role_function b on a.id=b.function_id 
	left join sys_user_role c on b.role_code=c.role_code 
	where c.user_code=? and a.parent_code=? and a.is_stop=0 and a.func_type=0 
    order by a.order_no asc
#end

查询角色权限菜单树
#sql("getRoleFunctionTree")
	select a.id,a.parent_code,a.func_name,a.link_page,a.icon,a.func_type,a.is_stop 
	from sys_function a 
	left join sys_role_function b on a.id=b.function_id 
	where b.role_code=? and a.parent_code=? and a.is_stop=0 
	order by a.order_no asc
#end

添加角色权限
#sql("saveRoleFunction")
	insert into sys_role_function (id,function_id,role_code) values(?,?,?)
#end

-------------------------- SysRoleService ---------------------------------------------
查询用户角色树 
#sql("getUserRoleTree")
	select a.role_code,a.parent_id,a.role_name
	from sys_role a
	left join sys_user_role b on a.role_code = b.role_code 
	where b.user_code =? and a.parent_id=?
#end

查询登录用户拥有的角色树
#sql("getLoginUserRoleTree")
	select a.role_code,a.parent_id,a.role_name
	from sys_role a
	left join sys_user_role b on a.role_code = b.role_code 
	where b.user_code =? and a.parent_id not in(select c.role_code from sys_user_role c where c.user_code=?)
#end

-------------------------- SysUserRoleService ------------------------------------------
查询用户角色集合
#sql("queryUserRoleList")
	select a.role_code 
	from sys_role a 
	where a.role_code in (select b.role_code from sys_user_role b where b.user_code=?)
		and a.role_code not  in (select b.role_code from sys_user_role b where b.user_code=?)
#end

查询角色用户列表
#sql("queryRoleUserList")
	select a.id,a.user_code,a.user_name,a.org_id,a.sex,a.mobile,a.email,CASE WHEN a.org_id in (select id from sys_org) then b.org_name else '' end org_name
	from  sys_user a,sys_org b where (a.org_id = b.org_code or a.org_id is null) and  a.user_code in (
	select c.user_code from sys_user_role c where c.role_code='?')
#end

查询角色未选用户列表
#sql("queryUserListNotInRoleCode")
	select a.id,a.user_code,a.user_name,a.org_id,a.sex,a.mobile,a.email,case when a.org_id in (select id from sys_org) then b.org_name else '' end org_name
	from  sys_user a,sys_org b 
	where (a.org_id = b.org_code or a.org_id is null) and a.user_code not in (select c.user_code from sys_user_role c where c.role_code='?')
#end

-------------------------- SysUserService ------------------------------------------
查询用户列表
#sql("getUserList")
	select a.id,a.user_code,a.user_name,a.sex,a.allow_login,a.tel,a.mobile,a.email,address,case when a.org_id in (select id from sys_org) then b.org_name else '' end org_name
	from  sys_user a,sys_org b 
	where (a.org_id = b.org_code or a.org_id is null)
#end

#sql("getOrgUserList")
	select a.id,a.user_code,a.user_name,a.sex,a.allow_login,a.tel,a.mobile,a.email,address,case when a.org_id in (select id from sys_org) then b.org_name else '' end org_name
	from  sys_user a,sys_org b 
	where (a.org_id = b.org_code or a.org_id is null) and b.id in(?)
#end

查询用户功能map集合,页面权限控制
#sql("getUserFuncMap")
    select rf.function_id from sys_role_function rf where rf.role_code in (select ur.role_code from sys_user_role ur where ur.user_code=?)  
#end

------------------------ SysRoleFuncService ----------------------------------------
查询用户访问权限,请求权限控制
#sql("getUserPermissions")
	select a.link_page from sys_function a where a.id IN (select rf.function_id from sys_role_function rf where rf.role_code in (
			select ur.role_code from sys_user_role ur where ur.user_code = ? )) and a.link_page is not null
#end

查询系统权限,需要权限认证的请求路径
#sql("getAllPermissions")
	select link_page from sys_function where link_page is not null
#end


查询医师列表
#sql("getDoctorInfoList")
	select a.doctor_id,a.doctor_name,a.doctor_gender,
	a.doctor_certype as certype,a.doctor_certno,a.certificate,a.address_code,affiliate,
a.psyledge_type,a.psyledge_subtype,a.psytests_type,a.psytests_subtype,a.reserved1
	from  doctorinfo a where a.reserved3 is not null
#end

查询志愿者列表
#sql("getVolInfoList")
	select a.volunteer_id, a.volunteer_name, a.volun_certtype as certype, a.volun_certno,
	a.volun_address, a.platform, a.psyledge_type, a.psyledge_subtype,
	a.psytests_type, a.psytests_subtype,a.voluntary_type,a.reserved1
	from  volunteerinfo a
	 where a.reserved3 is not null
#end

查询受灾者列表
#sql("getVimInfoList")
	select a.victim_id, a.victim_name, a.victim_gender,
	a.victim_certype as certype, a.victim_certno, a.victim_phone, a.address_code,
	a.address_detail,a.register_date, a.added
	from  victiminfo a
	 where a.reserved3 is not null
#end

插入医师信息
#sql("insertDocInfo")
INSERT INTO `doctorinfo`(`reserved2`,`reserved3`,`doctor_id`, `doctor_name`, `doctor_gender`, `doctor_certype`, `doctor_certno`, `certificate`, `address_code`, `affiliate`, `psyledge_type`, `psyledge_subtype`, `psytests_type`, `psytests_subtype`,`reserved1`)
VALUES (?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?)
#end

插入志愿者信息
#sql("insertVolunterInfo")
INSERT INTO `volunteerinfo`(`reserved2`,`reserved3`,`volunteer_id`, `volunteer_name`, `volun_certtype`, `volun_certno`, `volun_address`, `platform`, `psyledge_type`, `psyledge_subtype`, `psytests_type`, `psytests_subtype`,`voluntary_type`,`reserved1`)
VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?,?,?)
#end

插入受害者信息
#sql("insertVictimInfo")
INSERT INTO `victiminfo`(`reserved2`,`reserved3`,`victim_id`, `victim_name`, `victim_gender`, `victim_certype`, `victim_certno`, `victim_phone`, `address_code`, `address_detail`,`register_date`, `added`)
VALUES (?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?,?)
#end

判断文件类型
#sql("getDocType")
select  case when doctor_id is not null  then 'D' else '' end  as doctype from doctorinfo where doctor_id =?
union
select  case when volunteer_id is not null  then 'T' else '' end  as doctype from volunteerinfo where volunteer_id = ?
union
select  case when victim_id is not null then 'V' else '' end  as doctype from victiminfo where victim_id = ?
#end


