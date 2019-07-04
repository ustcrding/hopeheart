package com.boc.service;

import com.boc.model.Doctorinfo;
import com.boc.model.SysUser;
import com.boc.model.Victiminfo;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;

public class IndexService {
	public Ret login(String username,String password){
		SysUser sysUser = SysUser.dao.findFirst("select * from sys_user where username=? and password=?",username,password);
		if(sysUser!=null){
			//账户密码验证成功，查询下是否已经维护过信息
			if(sysUser.getRoleType().equals("0")){
			String roleid=sysUser.getRoleId()==null?sysUser.getId()+"":sysUser.getRoleId();
			Victiminfo victiminfo = Victiminfo.dao.findFirst("select * from victiminfo where victim_id=?",roleid);
			String preserve=victiminfo!=null?"0":"1";
			System.out.println(preserve);
			return Ret.by("code", "0").set("message", "登录成功").set("data", sysUser.put("preserve", preserve));
			}else{
			Doctorinfo doctorinf=Doctorinfo.dao.findFirst("select * from doctorinfo where doctor_id=?",sysUser.getRoleId());
			return Ret.by("code", "0").set("message", "登录成功").set("data", sysUser.put("preserve", doctorinf!=null?"0":"1"));
			}
			
		}
		return Ret.by("code", "1").set("message", "账户或密码错误");
	}
	
	public Ret verify(String victimId){
		Victiminfo victiminfo = Victiminfo.dao.findFirst("select * from victiminfo where victim_id=?",victimId);
		return Ret.by("code", "0").set("data", Kv.by("preserve", victiminfo!=null?"0":"1"));
	}
}
