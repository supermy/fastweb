package org.supermy.core.web.resource;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.supermy.core.domain.Resource;
import org.supermy.core.service.Page;
import org.supermy.core.service.ResourceService;
import org.supermy.core.web.BaseActionSupport;
import org.supermy.core.web.Struts2Utils;
import org.supermy.workflow.service.WorkflowService;

/**
 * 资源管理Action.
 * 
 * 使用Struts2 convention-plugin annotation定义Action参数.
 * 
 */
@Results( { @Result(name = BaseActionSupport.RELOAD, location = "resource.action?page.pageRequest=${page.pageRequest}", type = "redirect") })
public class ResourceAction extends BaseActionSupport<Resource> {

	@Autowired
	private ResourceService resourceService;

	@Autowired
	private WorkflowService approveService;

	// 基本属性
	private Resource resource;
	private Long id;
	private Page<Resource> page = new Page<Resource>(5);

	private File upload;
	// 上传文件名
	private String uploadFileName;
	// 上传文件类型
	private String uploadContentType;

	// //多文件上传
	// private File[] uploads;
	// private String[] uploadFileNames;
	// private String[] uploadContentTypes;

	/**
	 * @return the upload
	 */
	public File getUpload() {
		return upload;
	}

	/**
	 * @param upload
	 *            the upload to set
	 */
	public void setUpload(File upload) {
		this.upload = upload;
	}

	/**
	 * @return the uploadFileName
	 */
	public String getUploadFileName() {
		return uploadFileName;
	}

	/**
	 * @param uploadFileName
	 *            the uploadFileName to set
	 */
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	/**
	 * @return the uploadContentType
	 */
	public String getUploadContentType() {
		return uploadContentType;
	}

	/**
	 * @param uploadContentType
	 *            the uploadContentType to set
	 */
	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	// 基本属性访问函数 //
	public Resource getModel() {
		return resource;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			resource = resourceService.getResourceUtil().get(id);
		} else {
			resource = new Resource();
		}
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Page<Resource> getPage() {
		return page;
	}

	// CRUD Action 函数 //

	@Override
	public String list() throws Exception {
		page = resourceService.getResourceUtil().get(page);
		log.debug("find :{}", page.getResult());
		log.debug("find user by page:" + page.getResult().size());

		return SUCCESS;
	}

	@Override
	public String input() throws Exception {
		return INPUT;
	}

	/*
	 * 注意，新上传文件的时候，删除原有的文件
	 * 
	 * @see org.supermy.core.web.BaseActionSupport#save()
	 */
	@Override
	public String save() throws Exception {
		if (resource.getId() == null && upload == null) {
			throw new RuntimeException("必须上传文件，才能保存信息");
		}
		// 修改或者上传文件时候修改下列信息
		if (upload != null) {
			if (resource.getPath() != null) {
				Struts2Utils.delete(resource.getPath());
			}

			String fileName = Struts2Utils
					.generateFileName(getUploadFileName());

			log.debug("上传的文件的路径：{}", fileName);
			log.debug("上传的文件的类型：{}", getUploadContentType());

			File dstFile = new File(Struts2Utils.getUploadPath() + "/"
					+ fileName);
			FileUtils.copyFile(this.upload, dstFile);
			resource.setPath(fileName);
			resource.setFileType(uploadContentType);
			if (org.apache.commons.lang.StringUtils.isBlank(resource.getName())) {
				resource.setName(getUploadFileName());
			}
		}

		resourceService.getResourceUtil().save(resource);
		addActionMessage("保存资源成功");
		return RELOAD;
	}

	/*
	 * 删除数据库记录的时候，删除原有的文件
	 * 
	 * @see org.supermy.core.web.BaseActionSupport#delete()
	 */
	@Override
	public String delete() throws Exception {
		try {

			// Resource res = resourceService.getResourceUtil().get(id);

			Struts2Utils.delete(resource.getPath());

			resourceService.getResourceUtil().delete(resource);

			addActionMessage("删除资源成功");
		} catch (RuntimeException e) {
			log.error(e.getMessage(), e);
			addActionMessage(e.getMessage());
		}
		return RELOAD;
	}

	// 其他属性访问函数与Action函数 //
	/**
	 * 发布工作流
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deployProcess() throws Exception {
		try {
			prepareModel();

			String processZipFileName = Struts2Utils.getFilePathName(resource
					.getPath());
			approveService.deployProcess(processZipFileName);
			resource.setDone(true);
			resourceService.getResourceUtil().save(resource);

			addActionMessage("发布流程成功");
		} catch (RuntimeException e) {
			log.error(e.getMessage(), e);
			addActionMessage(e.getMessage());
		}
		return RELOAD;
	}

}
