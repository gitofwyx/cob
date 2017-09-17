package com.kelan.core.util.tree;

import java.util.ArrayList;
import java.util.List;

public class ManyTreeNode {
	/** 树节点 */
	private TreeNodeInfo data;
	/** 子树集合 */
	private List<ManyTreeNode> childList;

	/**
	 * 构造函数
	 * 
	 * @param data
	 *            树节点
	 */
	public ManyTreeNode(TreeNodeInfo data) {
		this.data = data;
		this.childList = new ArrayList<ManyTreeNode>();
	}

	/**
	 * 构造函数
	 * 
	 * @param data
	 *            树节点
	 * @param childList
	 *            子树集合
	 */
	public ManyTreeNode(TreeNodeInfo data, List<ManyTreeNode> childList) {
		this.data = data;
		this.childList = childList;
	}

	public TreeNodeInfo getData() {
		return data;
	}

	public void setData(TreeNodeInfo data) {
		this.data = data;
	}

	public List<ManyTreeNode> getChildList() {
		return childList;
	}

	public void setChildList(List<ManyTreeNode> childList) {
		this.childList = childList;
	}
}
