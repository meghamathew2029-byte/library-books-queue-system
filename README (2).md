# Library Book Reservation and Waiting Queue System

Java mini project based on the supplied abstract. It uses arrays for book details and a FIFO queue for reservations.

## Files
- `LibrarySystem.java` — Java console application
- `index.html` — styled website and interactive browser demonstration
- `style.css` — website styling
- `README.md` — instructions

## Features
Add/display/search books, issue and return books, reserve unavailable books, and view each book's waiting queue. When a book is returned, the first waiting student receives it (FIFO).

## Run Java
Install JDK 17 or newer. In a terminal opened in this folder, run:

```bash
javac LibrarySystem.java
java LibrarySystem
```

## Open the website
Open `index.html` in a browser. The website demo supports search, filtering, adding books, issuing, returning, and reservations. Demo data resets on page reload.

**Important:** In this four-file version, the website's demonstration uses browser JavaScript and the Java application runs separately in a terminal. GitHub Pages cannot execute Java code. To connect the page to the Java backend, a Java web server/API and additional setup would be required.

## Publish with GitHub Pages
1. Upload all four files to the root of the GitHub repository, not a subfolder.
2. Open **Settings → Pages**.
3. Choose **Deploy from a branch**, branch `main`, folder `/(root)`, then save.
4. Wait for deployment and open the published URL. GitHub Pages should load `index.html`.

## Data structures
- **Array:** stores book ID, title, author, availability and issued student.
- **Queue:** stores students waiting for a book.
- **FIFO:** first student to join the queue is served first.
