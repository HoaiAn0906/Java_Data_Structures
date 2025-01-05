package org.andev.tree;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Stack;

public class BinarySearchTree<T extends Comparable<T>> implements TreeADT<T> {
    private int nodeCount = 0;
    private Node root = null;
    
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        return nodeCount;
    }

    @Override
    public int height() {
        return height(root);
    }

    @Override
    public boolean contains(T element) {
        return contains(root, element);
    }

    @Override
    public boolean add(T element) {
        if (contains(element)) {
            return false;
        }
        root = add(root, element);
        nodeCount++;
        
        return true;
    }

    @Override
    public boolean remove(T element) {
        if (!contains(element)) {
            return false;
        }
        
        root = remove(root, element);
        nodeCount--;
        
        return true;
    }

    @Override
    public Iterator<T> traverse(TreeTraverseType traverseType) {
        return switch (traverseType) {
            case PRE_ORDER -> preOrderTraverse();
            case IN_ORDER -> inOrderTraverse();
            case POST_ORDER -> postOrderTraverse();
            case LEVEL_ORDER -> levelOrderTraverse();
            default -> null;
        };
    }

    // Pre-order: root, left, right
    private Iterator<T> preOrderTraverse() {
        final int expectedNodeCount = nodeCount;
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                if (expectedNodeCount != nodeCount) {
                    throw new ConcurrentModificationException();
                }

                return !stack.isEmpty();
            }

            @Override
            public T next() {
                if (expectedNodeCount != nodeCount) {
                    throw new ConcurrentModificationException();
                }
                
                Node node = stack.pop();
                if (node.getRight() != null) {
                    stack.push(node.getRight());
                }
                if (node.getLeft() != null) {
                    stack.push(node.getLeft());
                }
                return (T) node.getData();
            }
        };
    }
    
    // In-order: left, root, right
    private Iterator<T> inOrderTraverse() {
        final int expectedNodeCount = nodeCount;
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        
        return new Iterator<T>() {
            Node trav = root;
            
            @Override
            public boolean hasNext() {
                if (expectedNodeCount != nodeCount) {
                    throw new ConcurrentModificationException();
                }
                
                return !stack.isEmpty() && trav != null;
            }

            @Override
            public T next() {
                if (expectedNodeCount != nodeCount) {
                    throw new ConcurrentModificationException();
                }
                
                while (trav != null && trav.getLeft() != null) {
                    stack.push(trav.getLeft());
                    trav = trav.getLeft();
                }
                
                Node node = stack.pop();
                if (node.getRight() != null) {
                    stack.push(node.getRight());
                    trav = node.getRight();
                }
                
                return (T) node.getData();
            }
        };
    }
    
    // Post-order: left, right, root
    private Iterator<T> postOrderTraverse() {
        final int expectedNodeCount = nodeCount;
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        
        return new Iterator<T>() {
            Node trav = root;
            
            @Override
            public boolean hasNext() {
                if (expectedNodeCount != nodeCount) {
                    throw new ConcurrentModificationException();
                }
                
                return !stack.isEmpty() && trav != null;
            }

            @Override
            public T next() {
                if (expectedNodeCount != nodeCount) {
                    throw new ConcurrentModificationException();
                }
                
                while (trav != null && trav.getLeft() != null) {
                    stack.push(trav.getLeft());
                    trav = trav.getLeft();
                }
                
                Node node = stack.pop();
                if (node.getRight() != null) {
                    stack.push(node.getRight());
                    trav = node.getRight();
                }
                
                return (T) node.getData();
            }
        };
    }

    // Level-order: root, left, right
    private Iterator<T> levelOrderTraverse() {
        final int expectedNodeCount = nodeCount;
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                if (expectedNodeCount != nodeCount) {
                    throw new ConcurrentModificationException();
                }
                
                return !stack.isEmpty() && root != null;
            }

            @Override
            public T next() {
                if (expectedNodeCount != nodeCount) {
                    throw new ConcurrentModificationException();
                }
                
                Node node = stack.pop();
                if (node.getLeft() != null) {
                    stack.push(node.getLeft());
                }
                if (node.getRight() != null) {
                    stack.push(node.getRight());
                }
                return (T) node.getData();
            }
        };
    }


    private int height(Node node) {
        if (node == null) {
            return 0;
        }
        
        return 1 + Math.max(height(node.getLeft()), height(node.getRight()));
    }
    
    private boolean contains(Node node, T element) {
        if (node == null) {
            return false;
        }
        
        int result = element.compareTo((T) node.getData());
        
        if (result < 0) {
            return contains(node.getLeft(), element);
        } else if (result > 0) {
            return contains(node.getRight(), element);
        } else {
            return true;
        }
    }
    
    private Node add(Node node, T element) {
        if (node == null) {
            node = new Node(element, null, null);
        } else {
            int result = element.compareTo((T) node.getData());

            if (result < 0) {
                node.setLeft(add(node.getLeft(), element));
            } else if (result > 0) {
                node.setRight(add(node.getRight(), element));
            }
        }
        
        return node;
    }

    private Node remove(Node node, T element) {
        int result = element.compareTo((T) node.getData());
        
        if (result < 0) {
            node.setLeft(remove(node.getLeft(), element));
        } else if (result > 0) {
            node.setRight(remove(node.getRight(), element));
        } else {
            if (node.getLeft() == null) {
                Node rightNode = node.getRight();
                
                node.setData(null);
                node = null;
                
                return rightNode;
            } else if (node.getRight() == null) {
                Node leftNode = node.getLeft();

                node.setData(null);
                node = null;

                return leftNode;
            } else {
                T temp = minRight(node);
                
                node.setData(temp);
                node.setRight(remove(node.getRight(), temp));
            }
        }
        
        return node;
    }

    private T minRight(Node node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        
        return (T) node.getData();
    }

    private T minLeft(Node node) {
        while (node.getRight() != null) {
            node = node.getRight();
        }

        return (T) node.getData();
    }
}
