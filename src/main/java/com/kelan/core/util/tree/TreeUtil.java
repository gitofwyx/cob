package com.kelan.core.util.tree;

import com.kelan.core.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class TreeUtil {

	/**
	 * 根据当前节点获得所有子节点id
	 * @param manyTreeNode 所有树数据
	 * @param nodeId 当前节点id
	 * @param ids 获得所有子节点
	 */
	public static List<String> getNodeIdsById(String orgIds,List<TreeNodeInfo> alllist){
		ManyNodeTree tree = new ManyNodeTree();//树结构
		ManyTreeNode node = tree.createTree(alllist).getRoot();//所有树结构数据
		List<String> ids = new ArrayList<String>();
		if(!StringUtil.isNullorEmpty(orgIds)){
			String[] idsarr = orgIds.split(",");
			for (int i = 0; i < idsarr.length; i++) {
				ids.add(idsarr[i]);
				tree.getNodeById(node, idsarr[i], ids);
			}
		}
		return ids;
	}
	/**
	 * 根据当前节点组装树，html页面
	 * @param orgId
	 * @return
	 */
	public static String creatNodeTree(String orgId,List<TreeNodeInfo> alllist, String towtree, String type){
		ManyNodeTree tree = new ManyNodeTree();//树结构
		ManyTreeNode node = tree.createTree(alllist).getRoot();//所有树结构数据
		StringBuffer treeHtml = new StringBuffer();
		if(!StringUtil.isNullorEmpty(orgId)){
			ManyTreeNode mtreenode = tree.getNodeById(node, orgId);
			if(null!=mtreenode){
				TreeNodeInfo tnode = mtreenode.getData();
				//treeHtml.append("<ul class='area-only'>");
				treeHtml.append("<li data-val='"+tnode.getNodeId()+"'><span class='home'>"+tnode.getText()+"</span>");
				getNodeForHtml(node, orgId, treeHtml, towtree, type);
				treeHtml.append("</li>");
			}
			//treeHtml.append("</ul>");
		}
		
		return treeHtml.toString();
	}
	/**
	 * 遍历多叉树查找当前节点
	 * @param manyTreeNode 所有树数据
	 * @param nodeId 当前节点id
	 * @param treeHtml 拼装页面树
	 */
	public static void getNodeForHtml(ManyTreeNode manyTreeNode, String nodeId, StringBuffer treeHtml, String towtree, String type) {
		if (manyTreeNode != null) {
			for (ManyTreeNode index : manyTreeNode.getChildList()) {
				String id = index.getData().getNodeId();
				if(id.equals(nodeId)){
					if ("1".equals(towtree) && "1".equals(type)) {
						createTreeHtml(index,treeHtml,"floor-w","floor-num", type);
					}else{
						createTreeHtml(index,treeHtml,"","floor-num", type);
					}
					break;
				}
				if (index.getChildList() != null && index.getChildList().size() > 0) {
					getNodeForHtml(index,nodeId,treeHtml,towtree, type);
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
	private static void createTreeHtml(ManyTreeNode manyTreeNode, StringBuffer treeHtml, String ulcls, String cls, String type) {
		if (manyTreeNode != null && null!=manyTreeNode.getChildList() && manyTreeNode.getChildList().size()>0) {
			treeHtml.append("<ul class='"+ulcls+"'>");
			for (ManyTreeNode index : manyTreeNode.getChildList()) {
				String id = index.getData().getNodeId();
				String name = index.getData().getText();
				String timg = "";
				if("".equals(cls) && "1".equals(type)){
					timg = "<img src='/img/room.png'/>";
				}
				treeHtml.append("<li data-val='"+id+"' class='treeNodeLi'><span class='"+cls+"'>"+timg+name+"</span>");
				if (index.getChildList() != null && index.getChildList().size() > 0) {
					createTreeHtml(index,treeHtml,"","", type);
				}
				treeHtml.append("</li>");
			}
			treeHtml.append("</ul>");
		}
	}
}
