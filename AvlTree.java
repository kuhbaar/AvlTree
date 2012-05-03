package ads1ss12.pa;


/**
 * AVL-Baum-Klasse die die fehlenden Methoden aus {@link AbstractAvlTree}
 * implementiert.
 * 
 * <p>
 * In dieser Klasse m&uuml;ssen Sie Ihren Code einf&uuml;gen und die Methoden
 * {@link #remove remove()} sowie {@link #rotateLeft rotateLeft()} und
 * {@link #rotateRight rotateRight()} implementieren.
 * </p>
 * 
 * <p>
 * Sie k&ouml;nnen beliebige neue Variablen und Methoden in dieser Klasse
 * hinzuf&uuml;gen. Wichtig ist nur, dass die oben genannten Methoden
 * implementiert werden.
 * </p>
 */
public class AvlTree extends AbstractAvlTree {

	/**
	 * Der Default-Konstruktor.
	 * 
	 * Ruft einfach nur den Konstruktor der Oberklasse auf.
	 */
	public AvlTree() {
		super();
		// TODO: Hier ist der richtige Platz fuer Initialisierungen
	}

	/**
	 * F&uuml;gt ein Element mit dem Schl&uuml;ssel <code>k</code> ein.
	 * 
	 * <p>
	 * Existiert im AVL-Baum ein Knoten mit Schl&uuml;ssel <code>k</code>, soll
	 * <code>insert()</code> einfach nichts machen.
	 * </p>
	 * 
	 * <p>
	 * Nach dem Einf&uuml;gen muss sichergestellt sein, dass es sich bei dem
	 * resultierenden Baum immer noch um einen AVL-Baum handelt, und dass
	 * {@link AbstractAvlTree#root root} auf die tats&auml;chliche Wurzel des
	 * Baums zeigt!
	 * </p>
	 * 
	 * @param k
	 *            Der Schl&uuml;ssel der eingef&uuml;gt werden soll.
	 * @see AbstractAvlTree#insert
	 */
	public void insert(int k) {
		root=this.insert(k,root);
	}

	public AvlNode insert(int k, AvlNode node){
		if(node==null) node=new AvlNode(k);
		else if(k<node.key){
			node.left=insert(k,node.left);
			node.left.parent=node;
			node.balance=getHeight(node.right)-getHeight(node.left);
			if(node.balance==-2){
				if(node.left.balance!=1)
					node=rotateRight(node);
				else
					node=doubleRotateLeft(node);
			}
		}
		else if(k>node.key){
			node.right=insert(k,node.right);
			node.right.parent=node;
			node.balance=getHeight(node.right)-getHeight(node.left);
			if(node.balance==2){
				if(node.right.balance!=-1)
					node=rotateLeft(node);
				else
					node=doubleRotateRight(node);
			}
		}
		return node;
	}

	public int max(int i, int k){
		return i>=k ? i:k;
	}

	public int getHeight(AvlNode node){
		int l=0,r=0;
		if (node==null)
			return 0;
		else if (node.left==null && node.right==null)
			return 1;
		if (node.left!=null)
			l=getHeight(node.left);
		if (node.right!=null)
			r=getHeight(node.right);
		return max(l,r)+1;
	}

	/**
	 * Entfernt den Knoten mit Schl&uuml;ssel <code>k</code> falls er existiert.
	 * 
	 * <p>
	 * Existiert im AVL-Baum kein Knoten mit Schl&uuml;ssel <code>k</code>, soll
	 * <code>remove()</code> einfach nichts machen.
	 * </p>
	 * 
	 * <p>
	 * Nach dem Entfernen muss sichergestellt sein, dass es sich bei dem
	 * resultierenden Baum immer noch um einen AVL-Baum handelt, und dass
	 * {@link AbstractAvlTree#root root} auf die tats&auml;chliche Wurzel des
	 * Baums zeigt!
	 * </p>
	 * 
	 * @param k
	 *            Der Schl&uuml;ssel dessen Knoten entfernt werden soll.
	 * 
	 * @see AbstractAvlTree#root
	 * @see #rotateLeft rotateLeft()
	 * @see #rotateRight rotateRight()
	 */
	public void remove(int k) {
		root=remove(k,root);
	}

	public AvlNode remove(int k,AvlNode node){
		if(node==null) ;//Key not found;
		else if(k<node.key){
			node.left=remove(k,node.left);
			node.balance=getHeight(node.right)-getHeight(node.left);
			if(node.balance==2){
				if(node.right.balance!=-1)
					node=rotateLeft(node);
				else
					node=doubleRotateRight(node);
			}
			if(node.balance==-2){
				if(node.left.balance!=1)
					node=rotateRight(node);
				else
					node=doubleRotateLeft(node);
			}

		}
		else if(k>node.key){
			node.right=remove(k,node.right);
			node.balance=getHeight(node.right)-getHeight(node.left);
			if(node.balance==-2){
				if(node.left.balance!=1)
					node=rotateRight(node);
				else
					node=doubleRotateLeft(node);
			}
			if(node.balance==2){
				if(node.right.balance!=-1)
					node=rotateLeft(node);
				else
					node=doubleRotateRight(node);
			}
		}
		else if(node.key==k){ //Key found
			if(node.left==null && node.right==null) //if no kids
				node=null;
			else if(node.left==null){//if no left kid
				node.right.parent=node.parent;
				node=node.right;
				node.balance=getHeight(node.right)-getHeight(node.left);
				if(node.balance==2){
					if(node.right.balance!=-1)
						node=rotateLeft(node);
					else
						node=doubleRotateRight(node);
				}
			}
			else if(node.right==null){//if no right kid
				node.left.parent=node.parent;
				node=node.left;
				node.balance=getHeight(node.right)-getHeight(node.left);
				if(node.balance==-2){
					if(node.left.balance!=1)
						node=rotateRight(node);
					else
						node=doubleRotateLeft(node);
				}
			}
			else{
				node.key=successor(node.right).key;
				node.right=remove(node.key,node.right);
				node.balance=getHeight(node.right)-getHeight(node.left);
				if(node.balance==-2){
					if(node.left.balance!=1)
						node=rotateRight(node);
					else
						node=doubleRotateLeft(node);
				}

				if(node.balance==2){
					if(node.right.balance!=-1)
						node=rotateLeft(node);
					else
						node=doubleRotateRight(node);
				}	
			}
		}
	return node;
	}

	public AvlNode successor(AvlNode node){
		if(node==null) return null;
		else if(node.left==null)
			return node;
		else
			return successor(node.left);
	}
	/**
	 * F&uuml;hrt eine Links-Rotation beim Knoten <code>n</code> durch.
	 * 
	 * 
	 * @param n
	 *            Der Knoten bei dem die Rotation durchgef&uuml;hrt werden soll.
	 * 
	 * @return Die <em>neue</em> Wurzel des rotierten Teilbaums.
	 */
	public AvlNode rotateLeft(AvlNode n) {
		AvlNode temp=n.right;
		n.right=temp.left;
		if(temp.left!=null)
			temp.left.parent=n;
		temp.left=n;
		temp.parent=n.parent;
		n.parent=temp;
		n.balance=getHeight(n.right)-getHeight(n.left);
		temp.balance=getHeight(temp.right)-getHeight(temp.left);
		return temp;
	}

	/**
	 * F&uuml;hrt eine Rechts-Rotation beim Knoten <code>n</code> durch.
	 * 
	 * 
	 * @param n
	 *            Der Knoten bei dem die Rotation durchgef&uuml;hrt werden soll.
	 * 
	 * @return Die <em>neue</em> Wurzel des rotierten Teilbaums.
	 */
	public AvlNode rotateRight(AvlNode n) {
		AvlNode temp=n.left;
		n.left=temp.right;
		if(temp.right!=null)
			temp.right.parent=n;
		temp.right=n;
		temp.parent=n.parent;
		n.parent=temp;
		n.balance=getHeight(n.right)-getHeight(n.left);
		temp.balance=getHeight(temp.right)-getHeight(temp.left);

		return temp;
	}
	public AvlNode doubleRotateLeft(AvlNode n) {
		n.left=rotateLeft(n.left);
		return rotateRight(n);
	}
	public AvlNode doubleRotateRight(AvlNode n) {
		n.right=rotateRight(n.right);
		return rotateLeft(n);
	}

}