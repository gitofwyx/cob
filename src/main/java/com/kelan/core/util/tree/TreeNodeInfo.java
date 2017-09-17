package com.kelan.core.util.tree;

public class TreeNodeInfo {
	/** 节点Id */
	private String nodeId;
	/** 父节点Id */
	private String parentId;
	/** 文本内容 */
	private String text;

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
