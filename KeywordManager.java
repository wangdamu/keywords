package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeywordManager {
	private Node root;
	private Map<Character, Node> allNodes = new HashMap<Character, Node>();
	
	public KeywordManager(String[] keyWords){
		createKeywordTree(keyWords);
	}

	private void createKeywordTree(String[] keyWords) {
		root = new Node(null);
		for(String keyWord : keyWords){
			Node temp = root;
			for(int i = 0; i < keyWord.length(); i++){
				char ch = keyWord.charAt(i);
				temp = temp.put(ch, i == keyWord.length() - 1);
			}
		}
	}
	
	public List<int[]> search(String value){
		List<int[]> ret = new ArrayList<int[]>();
		int index = -1;
		Node node = root;
		for(int i = 0; i < value.length(); i++){
			char ch = value.charAt(i);
			Node chNode = node.getChild(ch);
			if(chNode == null){
				index = -1;
				continue;
			}else if(chNode.isEnd()){
				int[] idxes = new int[2];
				idxes[1] = i;
				if(index != -1){ //单个字符关键�?
					idxes[0] = index;
				}
				ret.add(idxes);
				index = i + 1;
				node = root;
				continue;
			}else if(index == -1){
				index = i;
			}
			node = chNode;
		}
		return ret;
	}
	
	private class Node{
		private Character ch;
		private Map<Character, Node> children = new HashMap<Character, Node>();
		private boolean end;
		
		public Node(Character ch){
			this.ch = ch;
		}
		
		public Node put(Character ch, boolean isEnd){
			Node node = null;
			if(!isEnd){
				node = allNodes.get(ch);
				if(node == null){
					node = new Node(ch);
					allNodes.put(ch, node);
				}
			}else{
				node = new Node(ch);
				node.setEnd(true);
			}
			children.put(ch, node);
			return node;
		}
		
		public Node getChild(Character ch){
			return children.get(ch);
		}

		public boolean isEnd() {
			return end;
		}

		public void setEnd(boolean end) {
			this.end = end;
		}
	}
	
	public static void main(String[] args) {
		String[] keyWords = new String[]{"习近平", "李克强", "法轮功", "近水", "习书记"};
		KeywordManager km = new KeywordManager(keyWords);
		List<int[]> ret = km.search("习近水啊法轮功操操操法轮功操操操箱习近平");
		for(int[] indexes : ret){
			System.out.println(Arrays.toString(indexes));
		}
	}
}
