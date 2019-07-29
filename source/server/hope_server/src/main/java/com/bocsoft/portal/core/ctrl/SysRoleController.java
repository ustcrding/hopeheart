/**
 * Copyright Bocsoft
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 

package com.bocsoft.portal.core.ctrl;

import java.util.ArrayList;
import java.util.Collection;

import com.jfinal.aop.Before;
import com.jfinal.aop.Inject;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.CacheName;
import com.jfinal.plugin.ehcache.EvictInterceptor;
import com.bocsoft.common.model.SysRole;
import com.bocsoft.common.routes.ControllerBind;
import com.bocsoft.common.vo.Grid;
import com.bocsoft.common.vo.TreeNode;
import com.bocsoft.common.base.BaseController;
import com.bocsoft.common.config.WebContant;
import com.bocsoft.portal.core.service.SysRoleFuncService;
import com.bocsoft.portal.core.service.SysRoleService;
import com.bocsoft.portal.core.service.SysUserRoleService;
import com.bocsoft.portal.core.service.SysUserService;

/**
 * 角色
 * 
 * @author bocsoft
 *
 */
@ControllerBind(path="/portal/core/sysRole")
public class SysRoleController extends BaseController {

	@Inject
	SysRoleService service;
	@Inject
	SysUserService sysUserService;
	@Inject
	SysRoleFuncService roleFuncService;
	@Inject
	SysUserRoleService sysUserRoleService;
	public void index() {
		render("index.html");
	}

	public void list() {
		renderJson(service.queryForList(getParaToInt("pageNumber", 1), getParaToInt("pageSize", 10)));
	}

	public void add() {
		SysRole entity = service.findByRoleCode(getPara("roleCode"));
		if (entity != null) {
			setAttr("parentId", entity.getRoleCode());
			setAttr("parentName", entity.getRoleName());
		} else {
			setAttr("parentId", "superadmin");
			setAttr("parentName", "超级管理员");
		}
		render("add.html");
	}

	public void save() {
		SysRole sysRole=getBean(SysRole.class);
		sysRole.setId(createUUID());
		sysRole.setUserCode(getVisitor().getCode());
		if(!service.saveEntity(sysRole,getVisitor().getCode())) {
			setException("角色编号已存在，请重新输入");
		}
		SysRole entity = service.findByRoleCode(sysRole.getParentId());
		if (entity != null) {
			setAttr("parentId", entity.getRoleCode());
			setAttr("parentName", entity.getRoleName());
		} else {
			setAttr("parentId", "superadmin");
			setAttr("parentName", "超级管理员");
		}
		setAttr("sysRole", sysRole);
		render("add.html");
	}

	public void edit() {
		SysRole entity = service.findByRoleCode(getPara("roleCode"));
		setAttr("sysRole", entity);
		setAttr("parentName", entity.getParentId().equals("sys") ? WebContant.projectName
				: service.findByRoleCode(entity.getParentId()).getRoleName());
		render("edit.html");
	}

	public void update() {
		SysRole sysRole=getBean(SysRole.class);
		sysRole.update();
		setAttr("sysRole", sysRole);
		setAttr("parentName", service.findByRoleCode(sysRole.getParentId()).getRoleName());
		render("edit.html");
	}

	public void delete() {
		service.deleteRoleByRoleCode(getPara("roleCode"));
		renderJson(suc());
	}

	/**
	 * 用户角色树
	 * 
	 * @author bocsoft
	 * @date 2018年10月9日
	 */
	public void userRoleTree() {
		Collection<TreeNode> nodeList = this.service.getUserRoleTree(getVisitor(), null);
		Collection<TreeNode> nodes = new ArrayList<TreeNode>();
		TreeNode node = new TreeNode();
		node.setId("sys");
		node.setText(WebContant.projectName);
		node.setChildren(nodeList);
		nodes.add(node);

		renderJson(nodes);
	}

	/**
	 * * 配置角色功能权限
	 */
	@Before(EvictInterceptor.class)
	@CacheName("userFunc")
	public void saveRoleFunction() {
		boolean b = service.saveRoleFunc(getPara("roleCode"), getPara("funcs").split(","));
		if(b)CacheKit.remove("funcManager", getPara("roleCode"));
		renderJson(suc(b?"保存成功":"保存失败"));
	}

	/**
	 * 查询上级角色功能权限
	 *
	 * @param g
	 * @return
	 * @author bocsoft
	 * @date 2018年1月26日上午10:17:13
	 */
	public void queryRoleFuncByParentId() {

		Record record = new Record();
		String roleCode = getPara("id");
		if (!"sys".equals(roleCode)) {
			SysRole entity = service.findByRoleCode(roleCode);
			entity.getParentId();
			record.set("role_code", entity.getParentId());
		}
		renderJson(roleFuncService.queryForListEq(1, 10000, record));

	}

	/**
	 * 查询角色功能权限
	 *
	 * @param g
	 * @return
	 * @author bocsoft
	 * @date 2018年1月24日上午10:47:10
	 */
	public void queryRoleFunction() {
		Record record = new Record();
		record.set("role_code", getPara("id"));
		renderJson(roleFuncService.queryForListEq(1, 10000, record));
	}
	
	/**
	 * 配置角色用户页面
	 */
	public void roleUser(){
		setAttr("roleCode",getPara("roleCode"));
		setAttr("roleName",getPara("roleName"));
		setAttr("orgList", sysUserService.queryOrgIdAndNameRecord());
		render("roleUser.html");
	}
	
	/**
	 * 保存角色用户
	 */
	public void saveRoleUser(){
		String[] users=getPara("userCode").split(",");
		boolean b=sysUserRoleService.saveRoleUser(getPara("roleCode"), users);
		renderJson(suc(b?"保存成功":"保存失败"));
	}
	
	/**
	 * 查询角色用户列表
	 */
	public void queryRoleUserList(){
		int pageNumber=getParaToInt("pageNumber",1);
		int pageSize=getParaToInt("pageSize",10);
		String roleCode=getPara("roleCode");
		
		Record record=new Record();
		record.set("userName", getPara("userName"));
		record.set("orgId", getPara("orgId"));
		
		Grid grid=sysUserRoleService.queryRoleUserList(pageNumber, pageSize,roleCode,record);
		renderJson(grid);
	}
	
	/**
	 * 查询角色未选的用户列表
	 */
	public void queryUserListNotInRoleCode(){
		int pageNumber=getParaToInt("pageNumber",1);
		int pageSize=getParaToInt("pageSize",10);
		String roleCode=getPara("roleCode");
		
		Record record=new Record();
		record.set("userName", getPara("userName"));
		record.set("orgId", getPara("orgId"));
			    
		Grid grid=sysUserRoleService.queryUserListNotInRoleCode(pageNumber, pageSize,roleCode,record);
		renderJson(grid);
	}
	
	/**
	 * 移除角色用户
	 */
	public void deleteRoleUser(){
		boolean b=sysUserRoleService.deleteRoleUser(getPara("userCode"), getPara("roleCode"));
		renderJson(suc(b?"移除成功":"移除失败"));
	}
}
