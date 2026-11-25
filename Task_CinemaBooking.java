import java.util.*;

/*
 =======================================================================
  TASK: Cinema Seat Booking  -  a HashMap with your own class as the key
 =======================================================================

 A cinema hall has rows A to E, with seats 1 to 6 in each row.
 You will store the bookings in a HashMap:

     key   = Seat     (a class you finish below, for example B5)
     value = String   (the name of the customer who booked it)

 STEP 1  Complete Parts A to G in main(). Each part says what to print.
 STEP 2  Run the program BEFORE writing equals() and hashCode().
         Some results will be wrong. That is expected - write down which ones.
 STEP 3  Write equals() and hashCode() in the Seat class.
 STEP 4  Run it again and compare with the EXPECTED OUTPUT below.
 STEP 5  Answer the questions at the bottom of this file.

 RULES
   - Every time you use a seat, create a NEW object: new Seat('B', 5).
     A real program works the same way: when a customer types "B5",
     the program makes a new Seat object for it.
   - Do not change printChart().

 EXPECTED OUTPUT (after STEP 3)
 The order inside { } may be different in your output. HashMap has no order.

   Bookings:           {A1=Ali, C3=Mona, A2=Sara, B5=Omar}
   Who booked C3:      Mona
   Is A2 taken:        true
   Is E6 taken:        false
   Youssef -> B5:      refused, taken by Omar
   After Ali moved:    {C3=Mona, A2=Sara, B5=Omar, D2=Ali}
   Hana -> E1:         refused, seat is broken
   Seats booked:       4

        1  2  3  4  5  6
   A    .  X  .  .  .  .
   B    .  .  .  .  X  .
   C    .  .  X  .  .  .
   D    .  X  .  .  .  .
   E    #  #  .  .  .  .
*/

class Task_CinemaBooking {

    // A seat in the cinema hall, for example B5 = row B, seat number 5.
    // static so that main() can create Seat objects directly.
    static class Seat {
        final char row;     // 'A' to 'E'
        final int number;   // 1 to 6

        Seat(char row, int number) {
            this.row = row;
            this.number = number;
        }

        @Override
        public String toString() {
            return "" + row + number;
        }

        // TODO 1: override equals()
        //   Two Seat objects are the same seat when the row AND the number are the same.
        //   The parameter must be of type Object, not Seat. Write @Override above it.


        // TODO 2: override hashCode()
        //   Equal seats must return the same number.
        //   Use the same fields you used in equals().


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Seat)) return false;
            Seat other = (Seat) o;
            return row == other.row && number == other.number;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, number);
        }

    }

    public static void main(String[] args) {

        // Key = Seat, Value = name of the customer who booked it
        HashMap<Seat, String> bookings = new HashMap<>();
        HashSet<Seat> broken = new HashSet<>();

        // Part A: book these seats using put()
        //   A1 -> Ali     A2 -> Sara     B5 -> Omar     C3 -> Mona
        // Print:  Bookings:           <the map>
        bookings.put(new Seat('A', 1), "Ali");
        bookings.put(new Seat('A', 2), "Sara");
        bookings.put(new Seat('B', 5), "Omar");
        bookings.put(new Seat('C', 3), "Mona");
        System.out.println("Bookings:           " + bookings);


        // Part B: who booked seat C3?  Use get().
        // Print:  Who booked C3:      <name>
        System.out.println("Who booked C3:      " + bookings.get(new Seat('C', 3)));


        // Part C: are seats A2 and E6 taken?  Use containsKey().
        // Print:  Is A2 taken:        <true or false>
        //         Is E6 taken:        <true or false>
        System.out.println("Is A2 taken:        " + bookings.containsKey(new Seat('A', 2)));
        System.out.println("Is E6 taken:        " + bookings.containsKey(new Seat('E', 6)));


        // Part D: Youssef wants seat B5. Use putIfAbsent(), so a booked seat is not replaced.
        //   putIfAbsent() returns null if the seat was free,
        //   otherwise it returns the name that is already there.
        // Print:  Youssef -> B5:      booked
        //    or:  Youssef -> B5:      refused, taken by <name>
        String existing = bookings.putIfAbsent(new Seat('B', 5), "Youssef");
        if (existing == null) {
            System.out.println("Youssef -> B5:      booked");
        } else {
            System.out.println("Youssef -> B5:      refused, taken by " + existing);
        }


        // Part E: Ali moves from seat A1 to seat D2.  Use remove() and put().
        // Print:  After Ali moved:    <the map>
        bookings.remove(new Seat('A', 1));
        bookings.put(new Seat('D', 2), "Ali");
        System.out.println("After Ali moved:    " + bookings);


        // Part F: seats E1 and E2 are broken. Add them to the HashSet "broken".
        //   Hana wants seat E1. Book it only if it is not broken.  Use contains().
        // Print:  Hana -> E1:         booked
        //    or:  Hana -> E1:         refused, seat is broken
        broken.add(new Seat('E', 1));
        broken.add(new Seat('E', 2));
        Seat hanaSeat = new Seat('E', 1);
        if (broken.contains(hanaSeat)) {
            System.out.println("Hana -> E1:         refused, seat is broken");
        } else {
            bookings.put(hanaSeat, "Hana");
            System.out.println("Hana -> E1:         booked");
        }


        // Part G: how many seats are booked?  Use size().
        // Print:  Seats booked:       <number>
        System.out.println("Seats booked:       " + bookings.size());


        // GIVEN - prints the seating chart
        System.out.println();
        printChart(bookings, broken);
    }

    // GIVEN - do not change.  Prints the hall:  X = booked   # = broken   . = free
    static void printChart(HashMap<Seat, String> bookings, HashSet<Seat> broken) {
        System.out.println("     1  2  3  4  5  6");
        for (char row = 'A'; row <= 'E'; row++) {
            System.out.print(row + "  ");
            for (int number = 1; number <= 6; number++) {
                Seat seat = new Seat(row, number);
                if (bookings.containsKey(seat)) {
                    System.out.print("  X");
                } else if (broken.contains(seat)) {
                    System.out.print("  #");
                } else {
                    System.out.print("  .");
                }
            }
            System.out.println();
        }
    }
}

/*
 QUESTIONS - write your answers here

 STEP 2 NOTES - results BEFORE writing equals() and hashCode() (I ran it; they were wrong):
   Who booked C3:      null            (expected Mona)
   Is A2 taken:        false           (expected true)
   Youssef -> B5:      booked          (expected: refused, taken by Omar)
   After Ali moved:    A1=Ali was NOT removed, and B5 appears twice (Omar and Youssef)
   Hana -> E1:         booked          (expected: refused, seat is broken)
   Seats booked:       7              (expected 4)
   Chart: empty, no X and no #
   Reason: each new Seat(...) is a different object, so HashMap never finds the old key.

 Q1.Why did we have to override hashcode() and equals()?
     A1. HashMap uses hashCode() to choose the bucket, then equals() to check it is the same key.
         Without them, Object compares by memory address, so new Seat('B', 5) is never equal to the
         Seat('B', 5) stored before. Overriding them on row and number makes the same seat the same key.

 Q2. Delete your hashCode() but keep equals(), and run again.
     What happens? Why?
     A2. The results go wrong, the same as in the STEP 2 notes: get(C3) = null, containsKey(A2) = false,
         Youssef gets B5 (B5 appears twice), Seats booked = 7, and the chart is empty.
         Why: the default hashCode() is different for every object, so HashMap looks in the wrong
         bucket and equals() is never even called.

 Q3. Why are row and number final? What could go wrong if the number
     of a booked seat could be changed after it was put in the map?
     A3. final means a Seat cannot change after it is created. If the number changed after put(),
         its hashCode() would change but the entry would stay in the old bucket. Then get() and
         containsKey() could not find it, the seat could be booked twice, and the old entry would
         be lost inside the map.

 Q4. Change equals(Object o) to equals(Seat o). What happens?
     Then also remove @Override and run again. What happens now?
     A4. With @Override kept: compile error, "method does not override or implement a method from a
         supertype", because equals(Seat) is not the equals(Object) of Object.
         With @Override removed too: it compiles, but equals(Seat) is only an overload. HashMap still
         calls equals(Object), which compares addresses. So the results go wrong like Q2 (get(C3) = null,
         Youssef gets B5, Seats booked = 7). @Override helps because the compiler catches this mistake.

 BONUS. Replace the whole Seat class with this one line:
            record Seat(char row, int number) {}
        Run it. What happens, and why?
     A-BONUS. It works and gives the same logic as the expected output: a record automatically creates
         the constructor, equals(), hashCode() and toString() from its components, and its fields are final.
         Difference: the default toString() prints Seat[row=B, number=5] instead of B5, so the printed
         maps look different (the chart is the same). Records need Java 16 or newer.
         (Checked against Oracle docs, not run: I only had Java 11.)
*/
