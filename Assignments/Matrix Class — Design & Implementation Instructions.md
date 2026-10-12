## Matrix Class — Design & Implementation Instructions

### Objective

Design and build a `Matrix` class in Java that supports basic matrix operations: addition, subtraction, multiplication, and transpose. This reinforces Encapsulation, array handling within objects, and validation logic (checking dimensions before operating).

------

### Part 1: Understand What You're Modeling

A matrix is a 2D grid of numbers, with a fixed number of **rows** and **columns**. Before writing any code, recall the rules that govern each operation, since your code must enforce these:

- **Addition/Subtraction:** only valid if both matrices have the **exact same dimensions** (same rows, same columns)
- **Multiplication:** only valid if the **number of columns in the first matrix equals the number of rows in the second matrix**
- **Transpose:** always valid — flips rows into columns (a matrix of size `m x n` becomes `n x m`)

------

### Part 2: Class Design

#### Fields (private — Encapsulation)

- `data` — a 2D array (`double[][]` or `int[][]`) holding the actual numbers
- `rows` — number of rows
- `cols` — number of columns

#### Constructor

- Should accept `rows` and `cols`, and initialize `data` as a new 2D array of that size (all values default to 0)
- Consider a second constructor that accepts an already-filled 2D array directly, so you can quickly create a Matrix from known values without manually setting each cell

#### Getters

- `getRows()`
- `getCols()`
- `getValue(int row, int col)` — returns a single cell's value
- Consider a `setValue(int row, int col, double value)` — but think about whether this should validate that `row` and `col` are within bounds before setting, to avoid an `ArrayIndexOutOfBoundsException`

------

### Part 3: Core Methods to Implement

#### `add(Matrix other)`

- **First**, validate: does `this.rows == other.rows` AND `this.cols == other.cols`? If not, this operation is invalid — decide how you'll handle it (throw an exception, print an error and return `null`, etc. — this is a good place to practice validation logic ahead of Exception Handling).
- If valid: create a new `Matrix` of the same dimensions, and fill each cell as `this.data[i][j] + other.data[i][j]`.

#### `subtract(Matrix other)`

- Same dimension-check as `add()`.
- Fill each cell as `this.data[i][j] - other.data[i][j]`.

#### `multiply(Matrix other)`

- **Validate:** `this.cols` must equal `other.rows`. This is different from the add/subtract check — make sure students understand why (matrix multiplication combines rows of the first with columns of the second, so these specific dimensions must line up).
- The resulting matrix will have dimensions: `this.rows x other.cols`.
- For each cell `[i][j]` in the result, the value is the sum of `this.data[i][k] * other.data[k][j]` for every `k` from `0` to `this.cols - 1`. This requires a **triple nested loop** (row of result, column of result, and the summing index) — a good checkpoint to confirm students actually understand the multiplication rule, not just copy a pattern.

#### `transpose()`

- No validation needed — always works.
- Create a new `Matrix` with dimensions swapped (`cols x rows` instead of `rows x cols`).
- Fill it so that `result.data[j][i] = this.data[i][j]` for every cell — essentially flipping rows and columns.

#### `display()`

- Print the matrix in a readable grid format — each row on its own line, values separated by spaces or tabs, so the output actually looks like a matrix, not a flat list of numbers.

------

### Part 4: Optional Stretch Methods

- `determinant()` — for a 2x2 matrix, this is simple (`a*d - b*c`); for 3x3, it requires expansion by minors — a good challenge for stronger students, but not required for everyone
- `isSquare()` — returns whether `rows == cols`, useful as a pre-check before attempting `determinant()`
- `scalarMultiply(double scalar)` — multiplies every cell by a single number, separate from matrix-by-matrix multiplication
- `identity(int size)` — a static method that generates and returns an identity matrix of a given size (1s on the diagonal, 0s elsewhere)

------

### Part 5: How to Structure Your `main()` Demonstration

1. Create two matrices of the same dimensions (e.g. 2x2 or 3x3) with sample values. 
2. Your Program Should allow user to create  any NxM dimensional matrix. 
3. Display The Matrices.
4. Add them, display the result.
5. Subtract them, display the result.
6. Create a third matrix whose dimensions are compatible for multiplication with one of the first two (columns of one = rows of the other).
7. Multiply, display the result.
8. Transpose one of your matrices, display the result — confirm visually that rows and columns have actually swapped.
9. Deliberately try to add two matrices of mismatched dimensions, and show that your validation correctly catches and reports this instead of crashing or producing wrong output.

------

### Part 6: Concepts Checklist

-  **Encapsulation** — `data`, `rows`, `cols` all private, accessed only through getters or controlled methods
-  **Validation logic** — dimension checks before add/subtract/multiply, handled cleanly rather than letting the program crash
-  **Method design** — each operation returns a brand-new `Matrix` object rather than modifying `this` in place (same immutability-style pattern used in the Fraction and Complex Number classes)
-  **Nested loops working with 2D arrays inside an object** — a good bridge between earlier procedural array practice and OOP



## Matrix + LinearEquationSolver — Combined Design Instructions

### Objective

Extend the `Matrix` class into a small 2-class system by adding a `LinearEquationSolver` class that **uses** `Matrix` internally to solve a system of linear equations. This turns Matrix from a standalone utility into a mini-system with real Composition/Aggregation, closer in structure to your bigger assignments.

------

### Part 1: The Real-World Problem Being Solved

A system of linear equations looks like this:

```
2x + 3y = 8
4x -  y = 2
```

This can be represented using matrices:

```
Coefficient Matrix (A)     Variable Matrix (X)     Result Matrix (B)
[ 2   3 ]                  [ x ]                   [ 8 ]
[ 4  -1 ]                  [ y ]                   [ 2 ]
```

Written as: **A × X = B**

To solve for X (the values of x and y), you compute: **X = A⁻¹ × B** (A inverse, multiplied by B).

This connects directly to your earlier Line Intersection program — that was solving exactly this kind of system, just with only 2 variables and a hardcoded formula (Cramer's Rule). `LinearEquationSolver` generalizes that idea using Matrix operations instead.

------

### Part 2: What Needs to Be Added to `Matrix` First

Before building `LinearEquationSolver`, your `Matrix` class needs two more methods it likely doesn't have yet:

#### `determinant()`

- Required to check if a matrix is invertible (a matrix with determinant `0` has NO inverse — this is your validation checkpoint, same idea as checking for parallel lines earlier)
- For a 2x2 matrix: `determinant = (a*d) - (b*c)`, where the matrix is `[[a,b],[c,d]]`
- For a 3x3 matrix (optional, harder): expansion by minors — only require this if your students are ready for it

#### `inverse()`

- For a 2x2 matrix, the inverse formula is:

```
inverse = (1 / determinant) * [ d  -b ]
                                [ -c   a ]
```

- Should call `determinant()` internally first, and if it's `0`, this operation is invalid (same "cannot proceed" situation as mismatched dimensions in add/subtract)

------

### Part 3: `LinearEquationSolver` Class Design

#### Purpose

Takes a system of equations (as a coefficient matrix and a result matrix) and solves for the unknown variables.

#### Fields (private)

- `coefficientMatrix` — a `Matrix` object (this is the "A" from A×X=B)
- `resultMatrix` — a `Matrix` object (this is the "B")

**Design note — this is your Aggregation/Composition checkpoint:** should `LinearEquationSolver` create its own Matrix objects internally, or accept already-built Matrix objects from outside (passed into its constructor)? Think back to Doctor/Hospital vs House/Room — here, the coefficient and result matrices are meaningful on their own (a Matrix doesn't need a Solver to exist), so this should be **Aggregation** — the constructor should accept two existing `Matrix` objects as parameters, not build them itself.

#### Constructor

```
LinearEquationSolver(Matrix coefficientMatrix, Matrix resultMatrix)
```

- Should validate: does `coefficientMatrix` have the same number of rows as `resultMatrix`? (They must, since each equation needs exactly one result value.)
- Should also validate: is `coefficientMatrix` square (`isSquare()`)? Only square matrices can have an inverse, which this solving method depends on.

#### Methods

**`solve()`**

- Calls `coefficientMatrix.determinant()` first — if it's `0`, the system has no unique solution (print a message, return `null`)
- Otherwise: calls `coefficientMatrix.inverse()`, then multiplies that inverse by `resultMatrix` (using your existing `multiply()` method from `Matrix`)
- Returns the resulting `Matrix` — this holds the solved values for x, y, (and z, if 3 variables)

**`displaySolution()`**

- Nicely formats and prints the result — e.g., `"x = 2.0, y = 1.33"` — rather than just printing a raw matrix grid, since the output here specifically represents variable values, not a generic matrix

------

### Part 4: How the Pieces Should Work Together (Flow)

1. Build a `Matrix` representing the coefficients of a 2-variable system (2x2 matrix).
2. Build a `Matrix` representing the results (2x1 matrix — 2 rows, 1 column).
3. Create a `LinearEquationSolver`, passing in both matrices.
4. Call `solve()`.
5. Call `displaySolution()` to show the final x, y values in a readable form.
6. Verify by hand (or with a calculator) that the printed values actually satisfy both original equations — a good practice students should get in the habit of doing.
7. Deliberately test a case where `determinant() == 0` (e.g., two equations that are actually the same line, like `x + y = 2` and `2x + 2y = 4`) — confirm `solve()` correctly reports "no unique solution" instead of crashing or dividing by zero.

------

### Part 5: Concepts Checklist

-  **Aggregation** — `LinearEquationSolver` receives existing `Matrix` objects through its constructor, rather than creating them itself
-  **Encapsulation** — validation logic inside the constructor and `solve()`, protecting against invalid states (non-square matrix, mismatched dimensions, zero determinant)
-  **Code reuse across classes** — `solve()` relies entirely on methods already built in `Matrix` (`determinant()`, `inverse()`, `multiply()`) rather than reimplementing any math itself — a good demonstration of why building a solid `Matrix` class first pays off
-  **Single Responsibility** — `Matrix` only knows about matrix math; `LinearEquationSolver` only knows about the equation-solving workflow, using Matrix as a tool rather than mixing both concerns into one class

------

### Part 6: Optional Stretch Goal

Extend to **3-variable systems** (3x3 coefficient matrix) — this requires the harder 3x3 `determinant()` and `inverse()` in `Matrix`, making this a good "advanced students only" extension.