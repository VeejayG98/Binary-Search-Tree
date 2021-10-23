import java.util.LinkedList;
import java.util.Queue;

import org.w3c.dom.Node;

// BinarySearchTree class
//
// CONSTRUCTION: with no initializer
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x
// boolean contains( x )  --> Return true if x is present
// Comparable findMin( )  --> Return smallest item
// Comparable findMax( )  --> Return largest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// void printTree( )      --> Print tree in sorted order
// ******************ERRORS********************************
// Throws UnderflowException as appropriate

/**
 * Implements an unbalanced binary search tree.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 */
public class BinarySearchTree<AnyType extends Comparable<? super AnyType>>
{
    /**
     * Construct the tree.
     */
    public BinarySearchTree( )
    {
        root = null;
    }

    /**
     * Insert into the tree; duplicates are ignored.
     * @param x the item to insert.
     */
    public void insert( AnyType x )
    {
        root = insert( x, root );

    }

    /**
     * Remove from the tree. Nothing is done if x is not found.
     * @param x the item to remove.
     */
    public void remove( AnyType x )
    {
        root = remove( x, root );
    }

    /**
     * Find the smallest item in the tree.
     * @return smallest item or null if empty.
     */
    public AnyType findMin( )
    {
        if( isEmpty( ) )
            throw new UnderflowException(null);
        return findMin( root ).element;
    }

    /**
     * Find the largest item in the tree.
     * @return the largest item of null if empty.
     */
    public AnyType findMax( )
    {
        if( isEmpty( ) )
            throw new UnderflowException(null);
        return findMax( root ).element;
    }

    /**
     * Find an item in the tree.
     * @param x the item to search for.
     * @return true if not found.
     */
    public boolean contains( AnyType x )
    {
        return contains( x, root );
    }

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty( )
    {
        root = null;
    }

    /**
     * Test if the tree is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( )
    {
        return root == null;
    }

    /**
     * Print the tree contents in sorted order.
     */
    public void printTree( )
    {
        if( isEmpty( ) )
            System.out.println( "Empty tree" );
        else
            printTree( root );
    }

    /**
     * Internal method to insert into a subtree.
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode<AnyType> insert( AnyType x, BinaryNode<AnyType> t )
    {
        if( t == null )
            return new BinaryNode<AnyType>( x, null, null );
        
        int compareResult = x.compareTo( t.element );
            
        if( compareResult < 0 )
            t.left = insert( x, t.left );
        else if( compareResult > 0 )
            t.right = insert( x, t.right );
        else
            ;  // Duplicate; do nothing
        return t;
    }

    /**
     * Internal method to remove from a subtree.
     * @param x the item to remove.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode<AnyType> remove( AnyType x, BinaryNode<AnyType> t )
    {
        if( t == null )
            return t;   // Item not found; do nothing
            
        int compareResult = x.compareTo( t.element );
            
        if( compareResult < 0 )
            t.left = remove( x, t.left );
        else if( compareResult > 0 )
            t.right = remove( x, t.right );
        else if( t.left != null && t.right != null ) // Two children
        {
            t.element = findMin( t.right ).element;
            t.right = remove( t.element, t.right );
        }
        else
            t = ( t.left != null ) ? t.left : t.right;
        return t;
    }

    /**
     * Internal method to find the smallest item in a subtree.
     * @param t the node that roots the subtree.
     * @return node containing the smallest item.
     */
    private BinaryNode<AnyType> findMin( BinaryNode<AnyType> t )
    {
        if( t == null )
            return null;
        else if( t.left == null )
            return t;
        return findMin( t.left );
    }

    /**
     * Internal method to find the largest item in a subtree.
     * @param t the node that roots the subtree.
     * @return node containing the largest item.
     */
    private BinaryNode<AnyType> findMax( BinaryNode<AnyType> t )
    {
        if( t != null )
            while( t.right != null )
                t = t.right;

        return t;
    }

    /**
     * Internal method to find an item in a subtree.
     * @param x is item to search for.
     * @param t the node that roots the subtree.
     * @return node containing the matched item.
     */
    private boolean contains( AnyType x, BinaryNode<AnyType> t )
    {
        if( t == null )
            return false;
            
        int compareResult = x.compareTo( t.element );
            
        if( compareResult < 0 )
            return contains( x, t.left );
        else if( compareResult > 0 )
            return contains( x, t.right );
        else
            return true;    // Match
    }

    /**
     * Internal method to print a subtree in sorted order.
     * @param t the node that roots the subtree.
     */
    private void printTree( BinaryNode<AnyType> t )
    {
        if( t != null )
        {
            printTree( t.left );
            System.out.println( t.element );
            printTree( t.right );
        }
    }

    public int nodeCount(){
        return 1 + nodeCount(root);
    }

    private int nodeCount(BinaryNode<AnyType> t)
    {
        if(t == null)
            return -1;

        if(t.left == null && t.right == null)
            return 0;
        
        int sum_left = 1 + nodeCount(t.left);
        int sum_right = 1 + nodeCount(t.right);
        
        return sum_left + sum_right;
    }

    public boolean isFull(){
        return isFull(root);
    }

    private boolean isFull(BinaryNode<AnyType> t){
        if(t == null)
            return true;
        
        boolean twoChilds = (t.left != null) && (t.right != null);
        boolean noChilds = (t.left == null) && (t.right == null);
        if(!(twoChilds || noChilds))
            return false;

        boolean leftFull = isFull(t.left);
        if(!leftFull)
            return false;

        boolean rightFull = isFull(t.right);
        if(!rightFull)
            return false;
        
        // return (twoChilds || noChilds) && (leftFull && rightFull);
        return true;
    }

    public BinaryNode<AnyType> getRoot(){
        return root;
    }

    public boolean compareStructure(BinarySearchTree<AnyType> tree){
        return compareStructure(root, tree.getRoot());
    }

    private boolean compareStructure(BinaryNode<AnyType> t1, BinaryNode<AnyType> t2){

        if(t1 == null && t2 == null)
            return true;
        
        else if(t1 == null || t2 == null)
            return false;
        
        return compareStructure(t1.left, t2.left) && compareStructure(t1.right, t2.right);
    }

    public boolean equals(BinarySearchTree<AnyType> tree){
        return equals(root, tree.getRoot());
    }
    
    private boolean equals(BinaryNode<AnyType> t1, BinaryNode<AnyType> t2){
        if(t1 == null && t2 == null)
            return true;
        
        else if((t1 != null && t2 != null) && (t1.element == t2.element))
            return equals(t1.left, t2.left) && equals(t1.right, t2.right);
        
        return false;
    }

    private BinarySearchTree<AnyType> copy(){
        BinarySearchTree<AnyType> new_tree = new BinarySearchTree<>();
        new_tree.root = copy(root);
        return new_tree;
    }

    public BinaryNode<AnyType> copy(BinaryNode<AnyType> node){
        
        if(node == null)
            return null;

        return new BinaryNode<AnyType>(node.element, copy(node.left), copy(node.right));
    }

    private BinarySearchTree<AnyType> mirror(){
        BinarySearchTree<AnyType> mirror_tree = new BinarySearchTree<>();
        mirror_tree.root = mirror(root);
        return mirror_tree;
    }

    public BinaryNode<AnyType> mirror(BinaryNode<AnyType> node){
        if(node == null)
            return null;
        
        return new BinaryNode<AnyType>(node.element, mirror(node.right), mirror(node.left));
    }
        // new_node = new BinaryNode<AnyType>(node.element,copy(node.left, new_node.left), copy(node.right, new_node.right);       
        // new_node.left = copy(node.left, new_node.left);
        // new_node.right = copy(node.right, new_node.right);

    private boolean isMirror(BinarySearchTree<AnyType> mirror){
        return isMirror(root, mirror.root);
    }

    public boolean isMirror(BinaryNode<AnyType> node, BinaryNode<AnyType> mirror_node){

        if(node == null && mirror_node == null)
            return true;
        
            
        else if((node != null && mirror_node != null) && (node.element == mirror_node.element))
            return isMirror(node.left, mirror_node.right) && isMirror(node.right, mirror_node.left);
        
        return false;
    }

    public void rotateLeft(AnyType key){
        root = rotateLeft(key, root);
    }

    private BinaryNode<AnyType> rotateLeft(AnyType key, BinaryNode<AnyType> node){
        if(node == null)
            return node;

        int compareResult = key.compareTo(node.element);
        if(compareResult < 0)
            node.left = rotateLeft(key, node.left);
        else if(compareResult > 0)
            node.right = rotateLeft(key, node.right);
        
        else{
            BinaryNode<AnyType> right = node.right;
            BinaryNode<AnyType> right_l = right.left;

            right.left = node;
            node.right = right_l;

            return right;
        }

        return node;
    }

    public void rotateRight(AnyType key){
        root = rotateRight(key, root);
    }

    private BinaryNode<AnyType> rotateRight(AnyType key, BinaryNode<AnyType> node){
        if(node == null)
            return node;
        
        int compareResult = key.compareTo(node.element);
        if(compareResult < 0)
            node.left = rotateRight(key, node.left);
        else if(compareResult > 0)
            node.right = rotateRight(key, node.right);
        
        else{
            BinaryNode<AnyType> left = node.left;
            BinaryNode<AnyType> left_r = left.right;

            left.right = node;
            node.left = left_r;

            return left;
        }

        return node;
    }

    public void printLevels(){
        this.printLevels(root);
    }

    private void printLevels(BinaryNode<AnyType> root){

        Queue<BinaryNode> q = new LinkedList<>();
        q.add(root);

        while(true){

            int count = q.size();
            if(count == 0)
                break;

            while(count > 0){
                BinaryNode<AnyType> temp = q.peek();
                System.out.print(temp.element + " ");
                q.remove();

                if(temp.left != null)
                    q.add(temp.left);
                
                if(temp.right != null)
                    q.add(temp.right);

                count--;
            }
            System.out.println();

            
        }
    }
            // return new_node;
    /**
     * Internal method to compute height of a subtree.
     * @param t the node that roots the subtree.
     */
    private int height( BinaryNode<AnyType> t )
    {
        if( t == null )
            return -1;
        else
            return 1 + Math.max( height( t.left ), height( t.right ) );    
    }
    
    // Basic node stored in unbalanced binary search trees
    private static class BinaryNode<AnyType>
    {
            // Constructors
        BinaryNode( AnyType theElement )
        {
            this( theElement, null, null );
        }

        BinaryNode( AnyType theElement, BinaryNode<AnyType> lt, BinaryNode<AnyType> rt )
        {
            element  = theElement;
            left     = lt;
            right    = rt;
        }

        AnyType element;            // The data in the node
        BinaryNode<AnyType> left;   // Left child
        BinaryNode<AnyType> right;  // Right child
    }


      /** The tree root. */
    private BinaryNode<AnyType> root;


        // Test program
    public static void main( String [ ] args )
    {
        BinarySearchTree<Integer> t = new BinarySearchTree<Integer>( );
        final int NUMS = 4000;
        final int GAP  =   37;

        System.out.println( "Checking... (no more output means success)" );

        t.insert(10);
        t.insert(5);
        t.insert(15);
        t.insert(4);
        t.insert(6);
        t.insert(20);
        t.insert(7);

        t.insert(14);

        // t.printTree();
        int x = t.nodeCount();

        System.out.println("The number of nodes in the tree is: " + x);

        if (t.isFull())
            System.out.println("Tree is Full!");
        else
            System.out.println("Tree is not full!");

        BinarySearchTree<Integer> t2 = new BinarySearchTree<>();
        
        t2.insert(10);
        t2.insert(5);
        t2.insert(15);
        t2.insert(4);
        t2.insert(6);
        t2.insert(20);
        t2.insert(7);
        t2.insert(14);
        t2.printTree();

        
        if(t.compareStructure(t2))
            System.out.println("The trees have the same structure");
        else   
            System.out.println("The trees have different structures");
        
        t2.remove(14);
        t2.insert(13);

        if(t.equals(t2))
            System.out.println("The trees are equal");
        else   
            System.out.println("The trees are not equal");

        
        BinarySearchTree<Integer> t3 = t.copy();

        System.out.println("Copied tree:");
        t3.printTree();

        System.out.println("Mirrored tree:");
        BinarySearchTree<Integer> t4 = t.mirror();
        t4.printTree();

        if(t.equals(t3))
            System.out.println("The trees are equal");
        else   
            System.out.println("The trees are not equal");

        if(t.isMirror(t4))
            System.out.println("The trees are mirrors");
        else   
            System.out.println("The trees are not mirrors");

        System.out.println("Rotate Left:");
        t3.rotateLeft(10);
        t3.printTree();

        BinaryNode<Integer> temp = t3.getRoot();
        System.out.println(temp.element + " " + temp.left.element + " " + temp.right.element);

        System.out.println("Rotate Right:");
        t3.rotateRight(15);
        t3.printTree();

        temp = t3.getRoot();
        System.out.println(temp.element + " " + temp.left.element + " " + temp.right.element);

        System.out.println("Level Printing: ");
        t.printLevels();
        



        

        // for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS )
        //     t.insert( i );

        // for( int i = 1; i < NUMS; i+= 2 )
        //     t.remove( i );

        // // if( NUMS < 40 )
        // //     t.printTree( );
        // // if( t.findMin( ) != 2 || t.findMax( ) != NUMS - 2 )
        // //     System.out.println( "FindMin or FindMax error!" );

        // for( int i = 2; i < NUMS; i+=2 )
        //      if( !t.contains( i ) )
        //          System.out.println( "Find error1!" );

        // for( int i = 1; i < NUMS; i+=2 )
        // {
        //     if( t.contains( i ) )
        //         System.out.println( "Find error2!" );
        // }
    }
}