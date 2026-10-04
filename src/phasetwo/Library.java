
package phasetwo;
import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;
import phasetwo.Book;


public class Library {
    private int membercount;
	private int bookCount;
	private BookLinkedList books = new BookLinkedList();
    private MemberLinkedList members = new MemberLinkedList();
    private Librarian librarian;

    public Library(String librarianName, int librarianId) {
        librarian = new Librarian(librarianName, librarianId);
    }

    public void addLibrarian(Librarian librarian) {
        this.librarian = librarian;
        System.out.println("✅ Librarian added: " + librarian.getName());
    }

    public void addBook(Book book) {
        books.add(book);
        System.out.println("✅ Book added: " + book.getTitle());
    }

    public boolean removeBook(String title) {
        boolean result = books.remove(title);
        if (result) {
            System.out.println("✅ Book removed: " + title);
        } else {
            System.out.println("❌ Book not found to remove: " + title);
        }
        return result;
    }

    public String listAvailableBooks() {
      /* if (books.isEmpty()) {
             return("No available books in the library.");
            
        }
        return("Available Books:"+books.display());
        */  
      if (books.isEmpty()) {
        return "No available books in the library.";
    }

    String result = "Available Books:\n";
    for (int i = 0; i < books.size(); i++) {
        if (!books.get(i).isBorrowed()) result += books.get(i).display() + "\n";
    }
    return result;


    }

    public boolean registerMember(Member member) {
        members.add(member);
        System.out.println("✅ Member added: " + member.getName());
        return true;
    }

    public Book searchBook(String title) {
        try {
            return books.search(title);
        } catch (Book.BookNotFoundException e) {
            System.out.println("❌ Book not found: " + title);
            return null;
        }
    }

    public Member searchMember(int id) {
        return members.searchById(id);
    }

    public boolean borrowBook(int memberID, String title) {
        Book book = searchBook(title);
        Member member = searchMember(memberID);

        if (book == null) {
            System.out.println("❌ Book not found.");
            return false;
        }

        if (member == null) {
            System.out.println("❌ Member not found.");
            return false;
        }

        if (book.isBorrowed()) {
            System.out.println("❌ Book is already borrowed.");
            return false;
        }

        if (!member.borrowBook(book)) return false;
        book.setBorrowed(true);
        System.out.println("✅ Book '" + book.getTitle() + "' borrowed by " + member.getName() + "!");
        return true;
    }

    public boolean returnBook(int memberID, String title) {
        Book book = searchBook(title);
        Member member = searchMember(memberID);

        if (book == null || member == null) {
            System.out.println("❌ Book or Member not found.");
            return false;
        }

        if (!member.returnBook(book)) return false;
        book.setBorrowed(false);
        System.out.println("✅ Book returned successfully.");
        return true;
    }

    public void saveBooksToFile(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            List<Book> bookList = books.toList();
            out.writeObject(bookList);
            System.out.println("✅ Books saved successfully!");
        } catch (IOException e) {
            System.out.println("❌ Error saving books: " + e.getMessage());
        }
    }

    public void loadBooksFromFile(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            List<Book> bookList = (List<Book>) in.readObject();
            books.loadFromList(bookList);
            System.out.println("✅ Books loaded successfully!");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("❌ Error loading books: " + e.getMessage());
        }
    }

    public void saveMembersToFile(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            List<Member> memberList = members.toList();
            out.writeObject(memberList);
            System.out.println("✅ Members saved successfully!");
        } catch (IOException e) {
            System.out.println("❌ Error saving members: " + e.getMessage());
        }
    }

    public void loadMembersFromFile(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            List<Member> memberList = (List<Member>) in.readObject();
            members.loadFromList(memberList);
            System.out.println("✅ Members loaded successfully!");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("❌ Error loading members: " + e.getMessage());
        }
        
        
        
    }
    
    public void saveInfo() {
    saveBooksToFile("books.dat");
    saveMembersToFile("members.dat");

    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("librarian.dat"))) {
        out.writeObject(librarian);
        System.out.println("✅ Librarian info saved!");
       
    } catch (IOException e) {
        System.out.println("❌ Failed to save librarian info: " + e.getMessage());
    }
}
    
    public void loadInfo() {
    loadBooksFromFile("books.dat");
    loadMembersFromFile("members.dat");

    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("librarian.dat"))) {
        librarian = (Librarian) in.readObject();
       System.out.println("Librarian info loaded.");
        
    } catch (IOException | ClassNotFoundException e) {
        System.out.println("❌ Failed to load librarian info: " + e.getMessage());
    }
}
    
    public Librarian getLibrarian(){
    return librarian;
    }
    public String displayAllBooks() {
    String result = "Books:\n";
    for (int i = 0; i < books.size(); i++) {
        result += ((Book) books.get(i)).display() + "\n";
    }
    return result;
}
public String displayAllMembers() {
    String result = "Members:\n";
    for (Member m : members.toList()) {
        result += m.getName() + " (ID: " + m.getmemberID() + ")\n";
    }
    return result;
}


} 

