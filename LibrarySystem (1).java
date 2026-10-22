import java.util.*;

/** Library Book Reservation and Waiting Queue System: arrays + FIFO queues. */
public class LibrarySystem {
    private static final int MAX = 100;
    private static final int[] ids = new int[MAX];
    private static final String[] titles = new String[MAX], authors = new String[MAX], issuedTo = new String[MAX];
    private static final boolean[] available = new boolean[MAX];
    private static final Queue<String>[] waiting = new Queue[MAX];
    private static int count = 0;
    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        for (int i = 0; i < MAX; i++) waiting[i] = new LinkedList<>();
        addRecord(101, "Java Programming", "Herbert Schildt");
        addRecord(102, "Data Structures", "Seymour Lipschutz");
        addRecord(103, "Computer Networks", "Andrew S. Tanenbaum");
        boolean run = true;
        while (run) {
            System.out.println("\n===== LIBRARY BOOK RESERVATION SYSTEM =====");
            System.out.println("1 Add book  2 Display books  3 Search book");
            System.out.println("4 Issue book  5 Return book  6 Reserve book");
            System.out.println("7 View waiting queue  8 Exit");
            switch (readInt("Choose: ")) {
                case 1 -> addBook();
                case 2 -> displayBooks();
                case 3 -> searchBook();
                case 4 -> issueBook();
                case 5 -> returnBook();
                case 6 -> reserveBook();
                case 7 -> viewQueue();
                case 8 -> run = false;
                default -> System.out.println("Choose a number from 1 to 8.");
            }
        }
        in.close();
    }
    private static void addRecord(int id, String title, String author) {
        ids[count] = id; titles[count] = title; authors[count] = author;
        available[count] = true; issuedTo[count] = ""; waiting[count] = new LinkedList<>(); count++;
    }
    private static void addBook() {
        if (count == MAX) { System.out.println("Book storage is full."); return; }
        int id = readInt("Book ID: ");
        if (find(id) != -1) { System.out.println("ID already exists."); return; }
        addRecord(id, readText("Title: "), readText("Author: "));
        System.out.println("Book added.");
    }
    private static void displayBooks() {
        if (count == 0) System.out.println("No books.");
        for (int i = 0; i < count; i++) printBook(i);
    }
    private static void printBook(int i) {
        System.out.printf("ID %d | %s | Author: %s | %s | Waiting: %d%n", ids[i], titles[i], authors[i],
                available[i] ? "Available" : "Issued to " + issuedTo[i], waiting[i].size());
    }
    private static void searchBook() {
        String q = readText("Enter ID or title: ").toLowerCase(Locale.ROOT); boolean found = false;
        for (int i = 0; i < count; i++)
            if (String.valueOf(ids[i]).equals(q) || titles[i].toLowerCase(Locale.ROOT).contains(q)) {
                printBook(i); found = true;
            }
        if (!found) System.out.println("Book not found.");
    }
    private static void issueBook() {
        int i = find(readInt("Book ID: "));
        if (i < 0) { System.out.println("Book not found."); return; }
        if (!available[i]) { System.out.println("Already issued. Reserve it instead."); return; }
        if (!waiting[i].isEmpty()) { System.out.println("Waiting students have priority."); return; }
        issuedTo[i] = readText("Student name: "); available[i] = false;
        System.out.println("Book issued to " + issuedTo[i] + ".");
    }
    private static void returnBook() {
        int i = find(readInt("Book ID: "));
        if (i < 0) { System.out.println("Book not found."); return; }
        if (available[i]) { System.out.println("This book is not issued."); return; }
        String oldStudent = issuedTo[i]; issuedTo[i] = "";
        if (!waiting[i].isEmpty()) {
            issuedTo[i] = waiting[i].poll(); available[i] = false;
            System.out.println("Returned by " + oldStudent + "; FIFO queue served: issued to " + issuedTo[i] + ".");
        } else { available[i] = true; System.out.println("Book returned and is now available."); }
    }
    private static void reserveBook() {
        int i = find(readInt("Book ID: "));
        if (i < 0) { System.out.println("Book not found."); return; }
        if (available[i] && waiting[i].isEmpty()) { System.out.println("Book is available; issue it directly."); return; }
        String name = readText("Student name: ");
        if (name.equalsIgnoreCase(issuedTo[i]) || waiting[i].stream().anyMatch(s -> s.equalsIgnoreCase(name))) {
            System.out.println("Student already has this book or is waiting."); return;
        }
        waiting[i].offer(name);
        System.out.println("Reservation added. Queue position: " + waiting[i].size());
    }
    private static void viewQueue() {
        int i = find(readInt("Book ID: "));
        if (i < 0) { System.out.println("Book not found."); return; }
        System.out.println("Waiting list for " + titles[i] + ":");
        if (waiting[i].isEmpty()) System.out.println("No students waiting.");
        int p = 1; for (String name : waiting[i]) System.out.println((p++) + ". " + name);
    }
    private static int find(int id) {
        for (int i = 0; i < count; i++) if (ids[i] == id) return i;
        return -1;
    }
    private static int readInt(String prompt) {
        while (true) { System.out.print(prompt); try { return Integer.parseInt(in.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("Enter a valid number."); } }
    }
    private static String readText(String prompt) {
        while (true) { System.out.print(prompt); String s = in.nextLine().trim();
            if (!s.isEmpty()) return s; System.out.println("Cannot be empty."); }
    }
}
