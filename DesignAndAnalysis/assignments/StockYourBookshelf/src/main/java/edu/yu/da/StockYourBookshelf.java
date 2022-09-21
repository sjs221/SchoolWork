package edu.yu.da;

/** Implements the StockYourBookshelfI API.
 *
 * Students MAY NOT change the provided constructor signature!
 *
 * @author Avraham Leff
 */

import java.util.*;

public class StockYourBookshelf implements StockYourBookshelfI {

    private class Shelf {

        int classIndex;
        int priceCap;
        int totalPrice;
        LinkedList<Book> books;

        public Shelf(int classIndex, int priceCap) {
            this.classIndex = classIndex;
            this.priceCap = priceCap;
            this.totalPrice = 0;
            books = new LinkedList<>();
        }

        private void addBook(Book b){
            books.add(b);
            totalPrice += b.cost;
        }

        public void addBooks(LinkedList<Book> books) {
            for (Book b : books) {
                this.books.add(b);
                totalPrice += b.cost;
            }
        }

        public LinkedList<Book> getBooks() {
            return books;
        }

        private int size(){
            return books.size();
        }

        public int getTotalPrice() {
            return totalPrice;
        }
    }

    private class Book {
        String name;
        int cost;

        public Book(String s, int i) {
            name = s;
            cost = i;
        }

    }

    private Map<Integer, ArrayList<Shelf>> n;
    private boolean permittedInvocation = false;
    private Shelf bestShelf;

    /** No-op constructor */
    public StockYourBookshelf() {
        // no-op, students may change the implementation
    }

    @Override
    public List<Integer> solution() {
        if (!permittedInvocation) {
            throw new IllegalStateException();
        }
        Collections.sort(bestShelf.getBooks(), new Comparator<Book>() {
            @Override
            public int compare(Book o1, Book o2) {
                return o1.name.compareTo(o2.name);
            }
        });
        ArrayList<Integer> returnList = new ArrayList<>();
        for (Book b : bestShelf.getBooks()) {
            returnList.add(b.cost);
        }
        return returnList;
    }

    @Override
    public int maxAmountThatCanBeSpent(final int budget, final Map<String, List<Integer>> seforimClassToTypePrices) {
        permittedInvocation = true;
        n = new HashMap<>();
        int classIndex = 1;
        for (String s : seforimClassToTypePrices.keySet()) {
            ArrayList<Shelf> shelves = new ArrayList<>();
            for (int j = 1; j <= budget; j++) {
                int price = maximizeType(seforimClassToTypePrices.get(s), classIndex, j);
                if (price != 0){
                    Shelf shelf = new Shelf(classIndex, j);
                    if (classIndex != 1){
                        shelf.addBooks(getShelf(classIndex - 1, j - price).getBooks());
                    }
                    shelf.addBook(new Book(s,price));
                    shelves.add(shelf);
                } else {
                    shelves.add(new Shelf(classIndex, j));
                }
            }
            n.put(classIndex, shelves);
            classIndex++;
        }

        this.bestShelf = getBestShelf(classIndex - 1);
        return bestShelf.getTotalPrice() == 0 ? Integer.MIN_VALUE : bestShelf.getTotalPrice();
    }

    private Shelf getBestShelf(int i) {
        ArrayList<Shelf> shelves = n.get(i);
        return shelves.get(shelves.size() - 1);
    }

    private int maximizeType(List<Integer> integers, int classIndex, int j) {
        int max = 0;
        for (Integer i : integers) {
            if (i > max && i <= j && allPriorClasses(classIndex, j, i)) {
                max = i;
            }
        }
        return max;
    }

    private boolean allPriorClasses(int classIndex, int j, int i) {
        if (classIndex == 1) return true;
        int lastCat = classIndex - 1;
        int lastBud = j - i;
        if (lastBud < 1) return false;
        Shelf s = getShelf(classIndex - 1, j - i);
        return (s.size() + 1 == classIndex);
    }

    private Shelf getShelf(int x, int y) {
        ArrayList<Shelf> shelves = n.get(x);
        return shelves.get(y - 1);
    }

} // StockYourBookshelf