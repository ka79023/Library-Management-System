
package phasetwo;
import java.io.*;
import java.util.*;

public class BookLinkedList {

    Book get(int i) {
        if (i < 0) throw new IndexOutOfBoundsException("Negative index: " + i);
        Node current = head;
        for (int index = 0; index < i && current != null; index++) current = current.next;
        if (current == null) throw new IndexOutOfBoundsException("Index: " + i);
        return current.data;
    }

    int size() {
    int count = 0;
    Node curr = head;
    while (curr != null) {
        count++;
        curr = curr.next;
    }
    return count;
}

    private static class Node implements Serializable {
        Book data;
        Node next;
        Node(Book data) { this.data = data; }
    }
	
	   private transient Node head;

	    public void add(Book book) {
	        Node node = new Node(book);
	        node.next = head;
	        head = node;
	    }

	    public boolean remove(String title) {
	        Node curr = head, prev = null;
	        while (curr != null) {
	            if (curr.data.getTitle().equalsIgnoreCase(title)) {
	                if (prev == null) head = curr.next;
	                else prev.next = curr.next;
	                return true;
	            }
	            prev = curr;
	            curr = curr.next;
	        }
	        return false;
	    }

	    public Book search(String title) throws Book.BookNotFoundException {
	        Node curr = head;
	        while (curr != null) {
	            if (curr.data.getTitle().equalsIgnoreCase(title)) return curr.data;
	            curr = curr.next;
	        }
	        throw new Book.BookNotFoundException("Book not found: " + title);
	    }

	    public void display() {
	        Node curr = head;
	        while (curr != null) {
	            System.out.println(curr.data);
	            curr = curr.next;
	        }
	    }

	    public List<Book> toList() {
	        List<Book> list = new ArrayList<>();
	        Node curr = head;
	        while (curr != null) {
	            list.add(curr.data);
	            curr = curr.next;
	        }
	        return list;
	    }

	    public void loadFromList(List<Book> books) {
	        head = null;
	        for (int i = books.size() - 1; i >= 0; i--) {
	            add(books.get(i));
	        }}
	    public boolean isEmpty() {
	        return head == null;
	    }
}

