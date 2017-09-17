package com.kelan.core.util.tree;

import com.kelan.core.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class ManyNodeTree {
	/** 树根 */
	private ManyTreeNode root;

	/**
	 * 构造函数
	 */
	public ManyNodeTree() {
		root = new ManyTreeNode(new TreeNodeInfo());
	}

	/**
	 * 生成一颗多叉树，根节点为root
	 * 
	 * @param treeNodes
	 *            生成多叉树的节点集合
	 * @return ManyNodeTree
	 */
	public ManyNodeTree createTree(List<TreeNodeInfo> treeNodes) {
		if (treeNodes == null || treeNodes.size() < 0)
			return null;

		ManyNodeTree manyNodeTree = new ManyNodeTree();

		// 将所有节点添加到多叉树中
		for (TreeNodeInfo treeNode : treeNodes) {
			if (StringUtil.isNullorEmpty(treeNode.getParentId()) || treeNode.getParentId().equals("root")) {
				// 向根添加一个节点
				manyNodeTree.getRoot().getChildList().add(new ManyTreeNode(treeNode));
			} else {
				addChild(manyNodeTree.getRoot(), treeNode);
			}
		}

		return manyNodeTree;
	}

	/**
	 * 向指定多叉树节点添加子节点
	 * 
	 * @param manyTreeNode
	 *            多叉树节点
	 * @param child
	 *            节点
	 */
	public void addChild(ManyTreeNode manyTreeNode, TreeNodeInfo child) {
		for (ManyTreeNode item : manyTreeNode.getChildList()) {
			if (item.getData().getNodeId().equals(child.getParentId())) {
				// 找到对应的父亲
				item.getChildList().add(new ManyTreeNode(child));
				break;
			} else {
				if (item.getChildList() != null && item.getChildList().size() > 0) {
					addChild(item, child);
				}
			}
		}
	}

	/**
	 * 遍历多叉树
	 * 
	 * @param manyTreeNode
	 *            多叉树节点
	 * @return
	 */
	public String iteratorTree(ManyTreeNode manyTreeNode) {
		StringBuilder buffer = new StringBuilder();
		buffer.append("\n");

		if (manyTreeNode != null) {
			for (ManyTreeNode index : manyTreeNode.getChildList()) {
				buffer.append(index.getData().getNodeId() + ",");

				if (index.getChildList() != null && index.getChildList().size() > 0) {
					buffer.append(iteratorTree(index));
				}
			}
		}

		buffer.append("\n");

		return buffer.toString();
	}
	/**
	 * 遍历多叉树
	 * 
	 * @param manyTreeNode
	 *            多叉树节点
	 * @return
	 */
	public void iteratorTreeIdsToList(ManyTreeNode manyTreeNode, List<String> ids) {
		if (manyTreeNode != null) {
			for (ManyTreeNode index : manyTreeNode.getChildList()) {
				String id = index.getData().getNodeId();
				ids.add(id);
				if (index.getChildList() != null && index.getChildList().size() > 0) {
					iteratorTreeIdsToList(index,ids);
				}
			}
		}
	}
	/**
	 * 遍历多叉树查找当前节点
	 * @param manyTreeNode 所有树数据
	 * @param nodeId 当前节点id
	 * @param ids 获得所有子节点
	 */
	public void getNodeById(ManyTreeNode manyTreeNode, String nodeId, List<String> ids) {
		if (manyTreeNode != null) {
			for (ManyTreeNode index : manyTreeNode.getChildList()) {
				String id = index.getData().getNodeId();
				if(id.equals(nodeId)){
					iteratorTreeIdsToList(index,ids);
					break;
				}
				if (index.getChildList() != null && index.getChildList().size() > 0) {
					getNodeById(index,nodeId,ids);
				}
			}
		}
	}
	/**
	 * 遍历多叉树获得当前节点
	 * @param manyTreeNode 所有树数据
	 * @param nodeId 当前节点id
	 */
	public ManyTreeNode getNodeById(ManyTreeNode manyTreeNode, String nodeId) {
		if (manyTreeNode != null) {
			for (ManyTreeNode index : manyTreeNode.getChildList()) {
				String id = index.getData().getNodeId();
				if(id.equals(nodeId)){
					return index;
				}
				if (index.getChildList() != null && index.getChildList().size() > 0) {
					getNodeById(index,nodeId);
				}
			}
		}
		return null;
	}
	
	public ManyTreeNode getRoot() {
		return root;
	}

	public void setRoot(ManyTreeNode root) {
		this.root = root;
	}

	public static void main(String[] args) {
		List<TreeNodeInfo> treeNodes = new ArrayList<TreeNodeInfo>();
//		treeNodes.add(new TreeNodeInfo("系统权限管理", ""));
//		treeNodes.add(new TreeNodeInfo("用户管理", "系统权限管理"));
//		treeNodes.add(new TreeNodeInfo("角色管理", "系统权限管理"));
//		treeNodes.add(new TreeNodeInfo("组管理", "系统权限管理"));
//		treeNodes.add(new TreeNodeInfo("用户菜单管理", "系统权限管理"));
//		treeNodes.add(new TreeNodeInfo("角色菜单管理", "系统权限管理"));
//		treeNodes.add(new TreeNodeInfo("用户权限管理", "系统权限管理"));
//		
//		treeNodes.add(new TreeNodeInfo("三级1", "组管理"));
//		treeNodes.add(new TreeNodeInfo("三级2", "组管理"));
//		treeNodes.add(new TreeNodeInfo("三级3", "组管理"));
//		
//		treeNodes.add(new TreeNodeInfo("四级1", "三级1","四级1名字"));
//		treeNodes.add(new TreeNodeInfo("四级2", "三级1","四级2名字"));
//		
//		treeNodes.add(new TreeNodeInfo("三级j1", "角色管理"));
//		
//		treeNodes.add(new TreeNodeInfo("站内信", "root"));
//		treeNodes.add(new TreeNodeInfo("写信", "站内信"));
//		treeNodes.add(new TreeNodeInfo("收信", "站内信"));
//		treeNodes.add(new TreeNodeInfo("草稿", "站内信"));
		
		ManyNodeTree tree = new ManyNodeTree();
		
		String idstr = "系统权限管理,站内信";
		
		ManyTreeNode node = tree.createTree(treeNodes).getRoot();
		
//		List<String> ids = new ArrayList<String>();
//		ManyTreeNode mtreeNode = tree.getNodeById(node, idstr, ids);
//		System.out.println(ids.toString());
		//拼装树
		String[] idarr = idstr.split(",");
		StringBuffer treehtml = new StringBuffer();
//		for (int i = 0; i < idarr.length; i++) {
//			treehtml.append(TreeUtil.creatNodeTree(idarr[i],treeNodes,"",""));
//		}
//		System.out.println(treehtml.toString());
	}
	
}
