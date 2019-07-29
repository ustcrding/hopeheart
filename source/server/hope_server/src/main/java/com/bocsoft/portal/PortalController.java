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

package com.bocsoft.portal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bocsoft.common.model.base.BaseDataDictionary;
import com.bocsoft.portal.core.service.DataDictionaryService;
import com.bocsoft.portal.core.service.SysDocInfoService;
import com.jfinal.aop.Inject;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.upload.UploadFile;
import com.bocsoft.common.model.FileUploaded;
import com.bocsoft.common.routes.ControllerBind;
import com.bocsoft.common.base.BaseController;
import com.bocsoft.common.config.WebContant;

/**
 * 附件、公共方法类
 * 特别说明:该类的的viewPath="/"
 * @author bocsoft
 *
 */
@ControllerBind(path="/portal")
public class PortalController extends BaseController {
	@Inject
	SysDocInfoService sysDocInfoService;

	/**
	 * 
	 * go后面带视图目录参数 如：/portal/go/common/upload/upload，<br/>
	 * 将会访问/common/upload/upload.html文件
	 * 
	 * @author bocsoft
	 * @date 2018年8月2日
	 */
	public void go() {
		render(getAttr("view") + ".html");
	}

	/**
	 * 上传页面
	 * 
	 * @author bocsoft
	 * @date 2018年8月13日
	 */
	public void toUpload() {
		String objectId = getPara();
		if (objectId == null) {
			objectId = getVisitor().getCode();
		}
		setAttr("objectId", objectId);
		render("common/upload/upload.html");
	}

	/**
	 * 上传文件,可多附件上传
	 * 
	 * @author bocsoft
	 * @date 2018年8月1日
	 */
	public void upload() {
		List<UploadFile> uploadList = getFiles();
		String doctype=getPara("doctype");
		String sql="";
		String objectId =  getPara().split("=")[1];
		System.out.println("用户ID "+objectId);
		if(doctype.equals("D")){
			sql=Db.getSql("core.insertDocInfo");
		}else if(doctype.equals("T")){
			sql=Db.getSql("core.insertVolunterInfo");
		}else {
			sql=Db.getSql("core.insertVictimInfo");
		}
		String docid="";
		boolean result=false;
		for(UploadFile uploadFile :uploadList){
			FileUploaded fileUploaded=saveFile(uploadFile,objectId,doctype);
			docid=fileUploaded.getId()+"";
			System.out.println("uploadFile"+uploadFile);
			result=importExcel(uploadFile,sql,fileUploaded);
			System.out.println(String.valueOf(result));
		}
		if(result){
			renderJson(Ret.ok().set("docid", docid).set("doctype", doctype));
		}else{
			renderJson(Ret.fail());
		}
	}

	/**
	 * 下载文件
	 * 
	 * @params url格式：18080615/18080615025800002
	 * @author bocsoft
	 * @date 2018年8月1日
	 */
	public void download() {
		String url = getAttr("url", "");
		FileUploaded fu;
		if (url.contains("/")) {
			fu = getFileUploaded(getAttr("url"));
		} else {
			fu = getFileUploadedByObjectId(getAttr("url"));
		}
		
		//实现服务器资源共享
		String fileTypeName = fu.getFileName().substring(fu.getFileName().lastIndexOf("."));
		fu.setSavePath(PathKit.getWebRootPath() +  "/" +
				WebContant.baseUloadPath + "/" + fu.getUrl() + fileTypeName);
		
		renderFile(new File(fu.getSavePath()), fu.getFileName());
	}

	/**
	 * 删除文件
	 * 
	 * @params url格式：18080615/18080615025800002
	 * @author bocsoft
	 * @date 2018年8月1日
	 */
	public void delete() {
		String url = getAttr("url", "");
		if (url.contains("/")) {
			deleteFileByUrl(url);
		} else {
			deleteFileByObjectId(url);
		}
		renderJson(Ret.ok());
	}

	/**
	 * 显示上传文件列表
	 * 
	 * @author bocsoft
	 * @date 2018年8月13日
	 */
	public void getFileList() {
		String objectId = getPara(0,getVisitor().getCode());
		setAttr("fileList", getFileUploadListByObjectId(objectId));
		setAttr("objectId", getPara());
		render("common/upload/fileList.html");
	}

	/**
	 * 下载模板文件
	 * 
	 * @params
	 * @author bocsoft
	 * @date 2018年8月2日
	 */
	public void temp() {
		renderFile(new File(PathKit.getWebRootPath() + "/" + WebContant.baseDownloadPath + getAttr("url")));
	}

	private static Map<String, String> imageContentType = new HashMap<String, String>();
	static {
		imageContentType.put("jpg","image/jpeg");
		imageContentType.put("jpeg","image/jpeg");
		imageContentType.put("png","image/png");
		imageContentType.put("tif","image/tiff");
		imageContentType.put("tiff","image/tiff");
		imageContentType.put("ico","image/x-icon");
		imageContentType.put("bmp","image/bmp");
		imageContentType.put("gif","image/gif");
	}
	
	/**
	 * 图片预览
	 * 
	 * @params url格式：18080615/18080615025800002
	 * @author bocsoft
	 * @date 2019年6月16日
	 */
	public void view() {
		String url = getAttr("url", "");
		FileUploaded fu;
		if (url.contains("/")) {
			fu = getFileUploaded(getAttr("url"));
		} else {
			fu = getFileUploadedByObjectId(getAttr("url"));
		}
		
		if(fu==null){
			renderHtml("<img src=\"/static/img/error/error.png\">");
			return;
		}
		
		try {
			String fileTypeName = fu.getFileName().substring(fu.getFileName().lastIndexOf("."));
			fu.setSavePath(PathKit.getWebRootPath() +  "/" +
					WebContant.baseUloadPath + "/" + fu.getUrl() + fileTypeName);
			File image = new File(fu.getSavePath());
			@SuppressWarnings("resource")
			FileInputStream inputStream = new FileInputStream(image);
			int length = inputStream.available();
			byte data[] = new byte[length];
			getResponse().setContentLength(length);
			String fileName = image.getName();
			String fileType = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
			getResponse().setContentType(imageContentType.get(fileType));
			inputStream.read(data);
			OutputStream toClient = getResponse().getOutputStream();
			toClient.write(data);
			toClient.flush();
			toClient.close();
		} catch (IOException e) {
			handerException(e);
			renderHtml("<img src=\"/static/img/error/error.png\">");
			return;
		}
		renderNull();
	}
}
