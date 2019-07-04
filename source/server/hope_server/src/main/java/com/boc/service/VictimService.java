package com.boc.service;

import java.util.Date;

import com.boc.model.SysUser;
import com.boc.model.Victiminfo;
import com.jfinal.kit.Ret;

import cn.hutool.core.date.DateUtil;

public class VictimService {
	public Ret collect(String id, String name, String sex, String identityType, String certificateCode, String phone,
			String province, String city, String address, String demo) {
		try {
			Victiminfo victimInfo = Victiminfo.dao.findFirst("select * from victiminfo where victim_id=?", id);
			SysUser sysuser= SysUser.dao.findFirst("select * from sys_user where id=?", id);
			
			if (victimInfo != null) {
				System.out.println(sysuser.toJson());
				if(sysuser.getRoleId()==null){
					sysuser.setId(Integer.valueOf(id)).setRoleId(id).setIdentityCode(certificateCode).setIdentityType(identityType).setAddress(address)
					.setSex(sex).setProvince(province).setCity(city)
					.update();	
					System.out.println(sysuser.toString());
				}
				// 更新
				victimInfo.setVictimId(id).setVictimName(name).setVictimGender(sex).setVictimCertype(identityType)
						.setVictimCertno(certificateCode).setVictimPhone(phone).setAddressCode(province + "|" + city)
						.setAddressDetail(address).setRegisterDate(DateUtil.format(new Date(), "yyyy-MM-dd"))
						.setAdded(demo).update();
			} else {
				// 插入
				new Victiminfo().setVictimId(id).setVictimName(name).setVictimGender(sex).setVictimCertype(identityType)
						.setVictimCertno(certificateCode).setVictimPhone(phone).setAddressCode(province + "|" + city)
						.setAddressDetail(address).setRegisterDate(DateUtil.format(new Date(), "yyyy-MM-dd"))
						.setAdded(demo).save();
			}
			return Ret.by("code", "0");
		} catch (Exception e) {
			return Ret.by("code", "1").set("msg", "数据入库失败");
		}
	}
}
