package com.niaobulashi.javademo.excel.model;

/**
 * 测试学生类
 * 
 * @author lisuo
 *
 */
public class revealReportModel {

	private String id;
	/** ID */
	private String projectName;
	/** 创建时间 */
	private String projectShortName;
	/** 姓名 */
	private String projectSource;
	/** 年龄 */
	private Integer revealType;
	/** 学号 */
	private String revealRate;
	/** 创建人 */
	private String revealDueTime;
	/** 创建人ID */
	private String noticeDueTime;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectShortName() {
		return projectShortName;
	}

	public void setProjectShortName(String projectShortName) {
		this.projectShortName = projectShortName;
	}

	public String getProjectSource() {
		return projectSource;
	}

	public void setProjectSource(String projectSource) {
		this.projectSource = projectSource;
	}

	public Integer getRevealType() {
		return revealType;
	}

	public void setRevealType(Integer revealType) {
		this.revealType = revealType;
	}

	public String getRevealRate() {
		return revealRate;
	}

	public void setRevealRate(String revealRate) {
		this.revealRate = revealRate;
	}

	public String getRevealDueTime() {
		return revealDueTime;
	}

	public void setRevealDueTime(String revealDueTime) {
		this.revealDueTime = revealDueTime;
	}

	public String getNoticeDueTime() {
		return noticeDueTime;
	}

	public void setNoticeDueTime(String noticeDueTime) {
		this.noticeDueTime = noticeDueTime;
	}

	@Override
	public String toString() {
		return "revealReportModel [id = " + id + ", projectName=" + projectName + ", projectShortName=" + projectShortName + ", projectSource=" + projectSource + ", revealType=" + revealType
				+ ", revealRate=" + revealRate + ", revealDueTime=" + revealDueTime + ", noticeDueTime=" + noticeDueTime + "]";
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
