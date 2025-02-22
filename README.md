# Banking System

A Java-based banking system that simulates basic banking operations including account management, transactions, and time-based operations.

## Features

- Multiple account types:
  - Checking Account
  - Savings Account
  - Certificate of Deposit (CD)
- Account operations:
  - Create accounts with unique 8-digit IDs
  - Deposit and withdraw funds
  - Transfer money between accounts
  - Calculate APR (Annual Percentage Rate)
  - Pass time simulation
- Command validation and processing
- Comprehensive error handling

## Technical Requirements

- Java 8 or higher
- Gradle build tool
- JUnit 5 for testing

## Building the Project

1. Clone the repository
2. Navigate to the project directory
3. Run the Gradle build:
```bash
./gradlew build
```

## Running Tests

The project includes comprehensive unit tests. To run them:

```bash
./gradlew test
```

For test coverage report:
```bash
./gradlew jacocoTestReport
```

For mutation testing:
```bash
./gradlew pitest
```

## Account Types and Rules

### Checking Account
- No minimum balance
- Maximum withdrawal: $400 per transaction
- Maximum deposit: $1000 per transaction

### Savings Account
- Maximum withdrawal: $1000 per transaction
- Limited to one withdrawal per month
- Maximum deposit: $2500 per transaction

### Certificate of Deposit (CD)
- Initial deposit between $1000 and $10000
- No additional deposits allowed
- 12-month minimum holding period before withdrawal
- Must withdraw entire balance

## Commands

The system accepts the following commands:

### Create Account
```
create <account_type> <account_id> <apr> [initial_amount]
```
- `account_type`: checking, savings, or cd
- `account_id`: 8-digit number
- `apr`: between 0 and 10
- `initial_amount`: required for CD accounts (1000-10000)

### Deposit
```
deposit <account_id> <amount>
```

### Withdraw
```
withdraw <account_id> <amount>
```

### Transfer
```
transfer <from_account_id> <to_account_id> <amount>
```

### Pass Time
```
pass <months>
```
- Simulates the passage of time (1-60 months)
- Applies APR calculations
- Handles account maintenance fees

## Error Handling

- Invalid commands are logged and reported
- Account balance cannot go below zero
- Transfer amounts are limited by account balance
- CD accounts have specific withdrawal restrictions

## Project Structure

- `banking/`: Main package containing all banking logic
  - Account classes (Account, CheckingAccount, SavingsAccount, CertificateDeposit)
  - Command processors and validators
  - Bank class for account management
  - Master control for command processing

## Testing

The project includes extensive unit tests covering:
- Account creation and management
- Transaction processing
- Command validation
- Edge cases and error conditions
- Time-based operations

## Code Quality

The project uses:
- JaCoCo for code coverage analysis
- PIT mutation testing
- SonarJava for code quality checks
- JUnit 5 for unit testing
