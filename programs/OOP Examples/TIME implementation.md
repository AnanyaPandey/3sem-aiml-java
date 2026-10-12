## OOP Example 

### Time Implementation

### Basic Implementation

```java

class Time {
    int hours;
    int minutes;
    int seconds;

    // Constructor
    Time(int h, int m, int s) {
        hours = h;
        minutes = m;
        seconds = s;
    }

    // Display time
    void display() {
        System.out.printf("%02d:%02d:%02d%n",
                          hours, minutes, seconds);
    }

    // Add two time objects
    Time add(Time t) {
        int h = hours + t.hours;
        int m = minutes + t.minutes;
        int s = seconds + t.seconds;

        // Convert excess seconds into minutes
        m = m + s / 60;
        s = s % 60;

        // Convert excess minutes into hours
        h = h + m / 60;
        m = m % 60;

        // Keep hours within a 24-hour day
        h = h % 24;

        return new Time(h, m, s);
    }

    // Main method
    public static void main(String[] args) {
        Time t1 = new Time(10, 45, 50);
        Time t2 = new Time(5, 30, 25);

        System.out.print("First time: ");
        t1.display();

        System.out.print("Second time: ");
        t2.display();

        Time result = t1.add(t2);

        System.out.print("Sum of times: ");
        result.display();
    }
}

```

### Correct Complete Implementation

```java
class Time {
    private int hours;
    private int minutes;
    private int seconds;

    // Constructor: any wrong value gets corrected
    Time(int h, int m, int s) {
        setTime(h, m, s);
    }

    // Corrects the values and stores them
    private void setTime(int h, int m, int s) {
        int total = h * 3600 + m * 60 + s;           // everything in seconds
        total = ((total % 86400) + 86400) % 86400;   // keep inside one day, also fixes negatives

        hours = total / 3600;
        minutes = (total % 3600) / 60;
        seconds = total % 60;
    }

    // 24 hour display
    void display() {
        System.out.printf("%02d:%02d:%02d%n", hours, minutes, seconds);
    }

    // 12 hour display
    void display12() {
        String period = (hours >= 12) ? "PM" : "AM";
        int h = hours % 12;
        if (h == 0) {
            h = 12;
        }
        System.out.printf("%02d:%02d:%02d %s%n", h, minutes, seconds, period);
    }

    // Add two time objects
    Time add(Time t) {
        return new Time(hours + t.hours, minutes + t.minutes, seconds + t.seconds);
    }

    // Move the clock forward by 1 second
    void tick() {
        setTime(hours, minutes, seconds + 1);
    }

    public static void main(String[] args) {
        Time t1 = new Time(10, 45, 50);
        Time t2 = new Time(5, 30, 25);

        System.out.print("First time: ");
        t1.display();

        System.out.print("Second time: ");
        t2.display();

        Time result = t1.add(t2);
        System.out.print("Sum of times: ");
        result.display();
        System.out.print("Sum (12 hour): ");
        result.display12();

        // Wrong values get corrected
        System.out.print("Time(5, 75, 90) becomes: ");
        new Time(5, 75, 90).display();

        System.out.print("Time(1, -30, 0) becomes: ");
        new Time(1, -30, 0).display();

        System.out.print("Time(25, 0, 0) becomes: ");
        new Time(25, 0, 0).display();

        // tick() with rollover
        Time clock = new Time(23, 59, 59);
        System.out.print("Clock before tick: ");
        clock.display();

        clock.tick();
        System.out.print("Clock after tick: ");
        clock.display();
        System.out.print("Clock after tick (12 hour): ");
        clock.display12();
    }
}
```

### To Override toString

```java
@Override
public String toString() {
    return String.format("%02d:%02d:%02d",
                         hours, minutes, seconds);
}
```

