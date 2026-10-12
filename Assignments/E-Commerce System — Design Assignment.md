# E-Commerce System — Design Assignment

## Objective

Design and build a simplified E-Commerce ordering system in Java. This assignment is meant to bring together everything covered in Unit 2 — Encapsulation, Inheritance, Abstraction, Polymorphism, Composition, Aggregation, and Access Modifiers — into one cohesive, realistic program, rather than a single isolated concept.

You are being given the **design only**. Your task is to implement it yourselves in Java.

------

## Part 1: Identify the Real-World Entities

Before writing any code, list out the "things" (nouns) involved in an e-commerce system. Here are the core ones this assignment expects:

- **Product** — an item that can be bought
- **Customer** — the person placing an order
- **Cart** — holds items a customer intends to buy, before checkout
- **Order** — a finalized purchase, created from a cart
- **Payment** — how an order gets paid for
- **Inventory** — tracks how much stock exists for each product

------

## Part 2: Class-by-Class Design

### 2.1 `Product` (Concrete Class)

**Purpose:** Represents a single item available for sale.

**Fields (all `private` — encapsulation):**

- `productId` (String or int)
- `name` (String)
- `price` (double)
- `stockQuantity` (int)

**Methods:**

- Constructor to set all fields
- Getters for each field (no setters for `productId` — an ID should never change once assigned)
- `reduceStock(int quantity)` — decreases stock when an order is placed; should validate that enough stock exists first
- `display()` — prints product details in a readable format

**Design questions for students to think through:**

- Should `price` have a setter? What happens if someone sets a negative price? (Hint: validate in the setter, or don't allow direct setting at all — only allow price changes through a dedicated `updatePrice()` method with validation.)

------

### 2.2 `Product` Subclasses (Inheritance)

Not all products are the same — some categories need extra fields. Create at least **two** subclasses of `Product`:

**`Electronics extends Product`**

- Extra field: `warrantyPeriodMonths` (int)

**`Clothing extends Product`**

- Extra fields: `size` (String), `color` (String)

**Design requirement:** Each subclass must use `super(...)` in its constructor to pass the common fields (`productId`, `name`, `price`, `stockQuantity`) up to `Product`, and only handle its own extra fields itself.

------

### 2.3 `Customer` (Concrete Class)

**Purpose:** Represents a person using the platform.

**Fields (private):**

- `customerId`
- `name`
- `email`
- `address`

**Methods:**

- Constructor
- Getters
- A method to `placeOrder()` — described further under `Order` below

**Design question:** Should `Customer` have a direct reference to a `Cart`, or should `Cart` exist independently and just be linked to a customer? (This is your Composition vs Aggregation decision — think about which makes more sense: does a Cart make sense without a Customer?)

------

### 2.4 `Cart` (Composition Example)

**Purpose:** Temporarily holds products a customer wants to buy, before checkout.

**Fields (private):**

- `items` — a list (e.g. `ArrayList<Product>`) of products currently in the cart
- `owner` — the `Customer` this cart belongs to

**Methods:**

- `addItem(Product p)`
- `removeItem(Product p)`
- `calculateTotal()` — sums up the price of every item in the cart
- `clearCart()` — empties the cart after an order is placed

**Design requirement — this is your Composition checkpoint:** A `Cart` should be **created inside** the `Customer`'s own constructor (like `House` creating its own `Room` in our earlier lessons), since a cart without an owner doesn't make sense, and shouldn't be able to be handed off to a different customer.

------

### 2.5 `Order` (Composition Example)

**Purpose:** Represents a finalized, placed order — a snapshot of what was in the cart at checkout time.

**Fields (private):**

- `orderId`
- `orderedProducts` — list of products that were purchased (copied from the cart at the time of order)
- `totalAmount`
- `orderDate`
- `status` (e.g. "Placed", "Shipped", "Delivered" — consider using a `String` or, if covered, an `enum`)

**Methods:**

- Constructor — builds an `Order` from a given `Cart`
- `getOrderSummary()` — returns/prints order details
- `updateStatus(String newStatus)`

**Design question:** Should `Order` create its own copy of the list of products (Composition), or just reference the same `Cart` object (Aggregation)? Think about what happens if the customer's cart changes AFTER the order is placed — should the order change too? (It shouldn't — this should guide your answer.)

------

### 2.6 `Payment` (Abstraction — Abstract Class or Interface)

**Purpose:** Different payment methods work differently internally, but should all be usable the same way from the Order's perspective.

**Design requirement:** Create this as either an **abstract class** or an **interface** (you decide which fits better, and be ready to justify your choice) called `Payment`, with:

- An abstract/interface method: `processPayment(double amount)`

Then create at least **two** implementations:

**`CreditCardPayment`**

- Extra fields: `cardNumber` (store only last 4 digits for realism), `cardHolderName`
- Implements `processPayment()` with its own logic (can just be a printed simulation, e.g. "Charging $X to card ending in 1234")

**`UPIPayment`** (or `PayPalPayment`, your choice)

- Extra field: `upiId` or `email`
- Implements `processPayment()` differently

**Design question to answer in your submission:** Why did you choose abstract class vs interface here? (Hint: think about whether these payment types share any common, reusable code, or whether you're purely just enforcing "must have processPayment()".)

------

### 2.7 `Inventory` (Aggregation + Singleton candidate)

**Purpose:** Tracks stock across ALL products — a central place the whole system checks before allowing an order.

**Design requirement:** This class should be a **Singleton** — there should only ever be ONE Inventory system for the whole store, not a separate one per customer or per order. Apply what you learned about private constructors and a static `getInstance()` method here.

**Fields:**

- A collection (e.g. `HashMap<String, Product>`) mapping product IDs to Product objects

**Methods:**

- `getInstance()` (static)
- `addProduct(Product p)`
- `checkStock(String productId, int quantity)` — returns true/false
- `updateStock(String productId, int quantity)`

------

## Part 3: How the Pieces Should Work Together (Flow)

Walk through this sequence to understand how your classes should interact — this is the actual "program flow" your `main()` method should demonstrate:

1. A few `Product` objects (including at least one `Electronics` and one `Clothing`) are created and added to the `Inventory`.
2. A `Customer` object is created. Their `Cart` is created automatically as part of the Customer's own construction (Composition).
3. The customer adds a few products to their `Cart` using `addItem()`.
4. `Cart.calculateTotal()` is called and displayed before checkout.
5. The customer chooses a `Payment` method (either `CreditCardPayment` or `UPIPayment`) — this is where **polymorphism** comes in: your checkout code should accept a `Payment` reference and call `processPayment()`, without needing to know which specific payment type was used.
6. Before finalizing, the system checks `Inventory.checkStock()` for every item in the cart.
7. If stock is available: an `Order` is created from the Cart, `Inventory.updateStock()` reduces the stock, `Payment.processPayment()` is called, and the `Cart` is cleared.
8. If stock is NOT available: the order should not proceed — a message should be shown instead (this is a good place to practice Exception Handling once we cover it — for now, a simple validation check and printed message is enough).
9. Print the final `Order` summary.

------

## Part 4: Concepts Checklist — Make Sure Your Design Demonstrates Each of These

Before submitting, go through this list and be ready to point to exactly where in your code each concept appears:

- [ ] **Encapsulation** — private fields with getters (and validated setters where appropriate) in every class
- [ ] **Inheritance** — `Electronics` and `Clothing` extending `Product`
- [ ] **Abstraction** — the `Payment` abstract class/interface, with multiple implementations
- [ ] **Polymorphism** — checkout code that works with `Payment` generically, regardless of which specific payment type is passed in
- [ ] **Composition** — `Customer` creating its own `Cart`; `Order` holding its own snapshot of purchased items
- [ ] **Aggregation** — `Inventory` referencing `Product` objects that exist independently of any one order
- [ ] **Singleton** — the `Inventory` class
- [ ] **Access Modifiers** — deliberate, justified use of `private`, `public`, and (optionally) `protected`

------

## Part 5: Stretch Goals (Optional, for Students Who Finish Early)

- Add a `Discount` or `Coupon` class that can be applied to a `Cart` before checkout, reducing the total
- Add an `Address` class instead of a plain `String` for the customer's address (practice building small, focused classes rather than cramming everything into one)
- Add order history — let a `Customer` hold a list of their past `Order` objects
- Add a simple `Review` or `Rating` system tied to `Product`

------

## Submission Expectations

Students should submit:

1. All `.java` files, organized into a sensible package structure (e.g. `ecommerce.products`, `ecommerce.orders`, `ecommerce.payments`)
2. A short written note (a few sentences per item) answering the design questions posed above — especially the Composition vs Aggregation choice for `Order`, and the abstract class vs interface choice for `Payment`
3. A working `main()` method demonstrating the full flow described in Part 3, with printed output at each step