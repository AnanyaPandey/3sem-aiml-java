## Hospital Management System — Design Assignment

### Objective

Design and build a simplified Hospital Management System in Java. Like the E-Commerce assignment, this should bring together Encapsulation, Inheritance, Abstraction, Polymorphism, Composition, Aggregation, and Access Modifiers into one cohesive program.

You are being given the design only — implement it yourselves.

------

### Part 1: Identify the Real-World Entities

- **Person** — a general base for anyone in the system
- **Patient** — someone receiving treatment
- **Doctor** — someone providing treatment
- **Appointment** — links a Patient and a Doctor at a specific time
- **Prescription** — medicines prescribed during an appointment
- **Hospital** — the overall system tying everything together
- **Department** — a specialization area (Cardiology, Neurology, etc.)

------

### Part 2: Class-by-Class Design

#### 2.1 `Person` (Abstract Class — Inheritance base)

**Purpose:** Both `Patient` and `Doctor` share common traits (name, age, contact info). Rather than duplicating these fields in both classes, pull them into a shared parent.

**Fields (protected, so children can access them directly):**

- `name` (String)
- `age` (int)
- `gender` (String)
- `phoneNumber` (String)

**Methods:**

- Constructor taking all common fields
- Getters for each
- An abstract method: `displayDetails()` — forces every subclass to define how it prints its own specific information

**Design requirement:** `Person` should be `abstract` — you should never be able to create a plain "Person" object; only a specific `Patient` or `Doctor`.

------

#### 2.2 `Patient extends Person`

**Purpose:** Represents someone receiving care.

**Extra fields (private):**

- `patientId`
- `bloodGroup`
- `assignedDoctor` — a reference to a `Doctor` object (this is your Aggregation checkpoint — a Doctor exists independently of any one patient)
- `medicalHistory` — a list of past diagnoses/notes (`ArrayList<String>`)

**Methods:**

- Constructor — calls `super(...)` for the shared fields, then sets its own
- `addMedicalRecord(String note)`
- `displayDetails()` — overriding the abstract method from `Person`

**Design question:** Should `medicalHistory` be fully open (`public`) or private with a controlled method to add entries? Think about why letting outside code directly do `patient.medicalHistory.add(...)` without any validation might be risky (e.g., empty/null entries, no timestamp).

------

#### 2.3 `Doctor extends Person`

**Purpose:** Represents a medical professional.

**Extra fields (private):**

- `doctorId`
- `specialization` (String)
- `department` — a reference to a `Department` object
- `consultationFee` (double)

**Methods:**

- Constructor — calls `super(...)`, then sets its own fields
- `displayDetails()` — overriding the abstract method from `Person`
- `checkAvailability()` — returns whether the doctor has open appointment slots (can be a simple simulated boolean for this assignment)

**Design requirement:** This is where **Polymorphism** shows up naturally — if you store a list like `Person[] hospitalStaffAndPatients`, calling `.displayDetails()` on each element should correctly run either `Patient`'s or `Doctor`'s version, depending on the actual object.

------

#### 2.4 `Department` (Concrete Class)

**Purpose:** Groups doctors by specialty.

**Fields (private):**

- `departmentId`
- `departmentName` (e.g. "Cardiology")
- `doctors` — a list of `Doctor` objects belonging to this department

**Methods:**

- `addDoctor(Doctor d)`
- `listDoctors()`

**Design question:** Is a `Department` composed of its `Doctors`, or does it aggregate them? Think about it this way — if a Department is shut down, should the Doctor objects be destroyed too, or should they be reassignable elsewhere? Let this answer guide whether Department creates its Doctors internally or just stores references handed to it.

------

#### 2.5 `Appointment` (Composition Example)

**Purpose:** Represents a scheduled meeting between a specific Patient and a specific Doctor.

**Fields (private):**

- `appointmentId`
- `patient` — reference to a `Patient`
- `doctor` — reference to a `Doctor`
- `dateTime`
- `status` (e.g. "Scheduled", "Completed", "Cancelled")
- `prescription` — a `Prescription` object (created only once the appointment is completed)

**Methods:**

- Constructor — takes a `Patient` and `Doctor` (these already exist independently — this is Aggregation, since an Appointment doesn't "own" the Patient or Doctor's existence)
- `completeAppointment(Prescription p)` — marks status as completed, attaches the prescription
- `cancelAppointment()`
- `getAppointmentDetails()`

**Design requirement — Composition checkpoint:** Unlike Patient/Doctor being aggregated (referenced), the `Prescription` tied to an appointment should be **created and owned by that specific Appointment** — it doesn't make sense for a Prescription to exist without the Appointment it came from, and it shouldn't be shared across multiple appointments.

------

#### 2.6 `Prescription` (Composition — owned by Appointment)

**Purpose:** Represents medicines prescribed during a specific appointment.

**Fields (private):**

- `medicines` — a list of medicine names (or a small `Medicine` class with `name`, `dosage`, `duration`, if you want to go further)
- `instructions` (String)

**Methods:**

- `addMedicine(String name, String dosage)`
- `displayPrescription()`

------

#### 2.7 `Hospital` (Aggregation Hub + Singleton candidate)

**Purpose:** The central system tying everything together — tracks all patients, doctors, departments, and appointments.

**Design requirement:** Like `Inventory` in the E-Commerce assignment, `Hospital` should be a **Singleton** — there should only be ONE hospital system instance managing everything, not multiple competing ones.

**Fields:**

- `patients` — list of all registered `Patient` objects
- `doctors` — list of all registered `Doctor` objects
- `departments` — list of all `Department` objects
- `appointments` — list of all `Appointment` objects

**Methods:**

- `getInstance()` (static)
- `registerPatient(Patient p)`
- `registerDoctor(Doctor d)`
- `scheduleAppointment(Patient p, Doctor d, dateTime)` — creates and stores a new `Appointment`
- `cancelAppointment(appointmentId)`
- `findDoctorsBySpecialization(String specialization)`

------

### Part 3: How the Pieces Should Work Together (Flow)

1. Create a few `Department` objects (e.g. Cardiology, Neurology).
2. Create several `Doctor` objects, each assigned to a `Department`, and register them with the `Hospital` Singleton.
3. Create a few `Patient` objects and register them with the `Hospital`.
4. A patient books an `Appointment` with a specific doctor — `Hospital.scheduleAppointment()` handles this, creating the `Appointment` object.
5. Demonstrate **polymorphism**: create an array/list of type `Person` containing a mix of `Patient` and `Doctor` objects, loop through it, and call `.displayDetails()` on each — showing that each one prints its own specific information correctly, even though the list is typed generically as `Person`.
6. The doctor completes the appointment: call `completeAppointment()`, passing in a newly created `Prescription` object (built with `addMedicine()` calls).
7. Print the final appointment details, including the attached prescription.
8. Try cancelling a different appointment and show the status update.

------

### Part 4: Concepts Checklist

Before submitting, be ready to point to exactly where each concept appears in your code:

-  **Abstraction** — `Person` as an abstract class with an abstract `displayDetails()` method
-  **Inheritance** — `Patient` and `Doctor` both extending `Person`
-  **Polymorphism** — a `Person[]` or `List<Person>` where `.displayDetails()` correctly runs the right version for each object
-  **Encapsulation** — private fields with getters/controlled methods throughout, especially `medicalHistory`
-  **Composition** — `Appointment` creating and owning its own `Prescription`
-  **Aggregation** — `Patient` referencing an existing `Doctor`; `Department` referencing `Doctor` objects that could exist independently
-  **Singleton** — the `Hospital` class
-  **Access Modifiers** — deliberate use of `private`, `protected` (for `Person`'s fields, since children need access), and `public`

------

### Part 5: Stretch Goals (Optional)

- Add a `Bill` class calculating charges based on `Doctor.consultationFee` plus any additional treatment costs
- Add a `Nurse` class, also extending `Person`, to show `Department` can aggregate more than one type of staff
- Add appointment time-slot conflict checking — prevent double-booking the same doctor at the same time
- Add an emergency/priority flag on `Appointment` that affects how it's displayed or sorted

------

### Submission Expectations

Students should submit:

1. All `.java` files, organized into a sensible package structure (e.g. `hospital.people`, `hospital.appointments`, `hospital.core`)
2. A short written note answering the design questions above — especially the Aggregation vs Composition reasoning for `Department`↔`Doctor` and `Appointment`↔`Prescription`
3. A working `main()` method demonstrating the full flow from Part 3, with printed output at each step