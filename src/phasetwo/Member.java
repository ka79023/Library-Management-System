
package phasetwo;
import java.io.*;
import java.util.List;

public class Member implements Serializable {
    private static final long serialVersionUID = 1083352966239952113L;

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        int count = 0;
        for (Book book : borrowedBooks) if (book != null) borrowedBooks[count++] = book;
        for (int i = count; i < borrowedBooks.length; i++) borrowedBooks[i] = null;
        borrowedCount = count;
    }
    private String name;
    private int memberid;
    private Book[] borrowedBooks;
    private int borrowedCount;

    public Member(String name, int memberid, int maxBooks) {
        this.name = name;
        this.memberid = memberid;
       borrowedBooks = new Book[maxBooks];  
       borrowedCount = 0;
    }

    public String getName() {
        return name;
    }

    public int getmemberID() {
        return memberid;
    }

    public boolean borrowBook(Book book) {
        if (borrowedCount >= borrowedBooks.length) return false;
        borrowedBooks[borrowedCount++] = book;
        return true;
    }

    public boolean returnBook(Book book) {
        for (int i = 0; i < borrowedCount; i++) {
            if (borrowedBooks[i] != null && borrowedBooks[i].getTitle().equalsIgnoreCase(book.getTitle())) {
                for (int j = i; j < borrowedCount - 1; j++) borrowedBooks[j] = borrowedBooks[j + 1];
                borrowedBooks[--borrowedCount] = null;
                return true;
            }
        }
        return false;
    }

  public String showBorrowedBooks() {
	   /* return(name + "'s Borrowed Books:");

	    boolean hasBooks = false; 
	    for (int i = 0; i < borrowedCount; i++) {
	        if (borrowedBooks[i] != null) { 
	            return("- " + borrowedBooks[i].getTitle());
	            hasBooks = true;
	        }
	    }

	    if (!hasBooks) {
	        return(" No borrowed books found.");
	    }*/
    String result = name + "'s Borrowed Books:\n";
    boolean hasBooks = false;

    for (int i = 0; i < borrowedCount; i++) {
        if (borrowedBooks[i] != null) {
            result += "- " + borrowedBooks[i].getTitle() + "\n";
            hasBooks = true;
        }
    }

    if (!hasBooks) {
        return name + " has no borrowed books.";
    }

    return result;
}


	}



