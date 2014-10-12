package sk.fiit.robocup.library.representations;


import java.util.HashMap;
import java.util.Map;




public class Tree<V> {
	Node<V> node;
	String key;
	Map<String, Tree<V>> childrens;
	public Tree(){
		this.childrens = new HashMap<String, Tree<V>>();
		node = new Node<V>();
	}
	Tree(String key){
		this.childrens = new HashMap<String, Tree<V>>();
		this.key = key;
		node = new Node<V>();
	}
	public void put(String key, V value){
		_put(new StringBuffer(key), new StringBuffer(), value);
	}
	void _put(StringBuffer remainder, StringBuffer prefix, V value) {
        if (remainder.length() > 0) {
        	String keyElement = remainder.toString();//remainder.charAt(0);
            Tree<V> t = null;
            try {
                t = childrens.get(keyElement);
            } catch (IndexOutOfBoundsException e) {
            }
            if (t == null) {
                t = new Tree<V>(keyElement);
                childrens.put(keyElement, t);
            }
            prefix.append(remainder.charAt(0));
            t._put(remainder.deleteCharAt(0), prefix, value);
        } else {
            this.node.value = value;
            //this.node.type = prefix.toString();
        }
 
    }
	public V get(String key) {
        return _get(new StringBuffer(key), 0);
    }
	public boolean hasPrefix(String key) {
        return ((this.get(key) != null) ? true : false);
    }
 
    V _get(StringBuffer key, int level) {
        if (key.length() > 0) {
            Tree<V> t = childrens.get(key.charAt(0));
            if (t != null) {
                return t._get(key.deleteCharAt(0), ++level);
            } else {
                return (level > 0) ? node.value : null;
            }
 
        } else {
            return node.value;
        }
    }
 
    @Override
    public String toString() {
        return "Trie [entry=" + node + ", key=" + key + ", childrens="
                + childrens + "]";
    }
	
	static public class Node<V> {
        TypeNode type;
        V value;
 
        public Node() {
        }
 
        public Node(TypeNode p, V v) {
            type = p;
            value = v;
        }
 
        public TypeNode getType() {
            return type;
        }
 
        public V getValue() {
            return value;
        }
 
        @Override
        public String toString() {
            return "Entry [prefix=" + type + ", value=" + value + "]";
        }
 
    }
}


