# 🔐 Password Analyzer

A Java command-line tool that analyzes password strength and checks if a password
has been exposed in real-world data breaches using the HaveIBeenPwned API.

## 📋 Features
- ✅ Password strength scoring based on length and character variety
- ✅ Detects 20 most commonly used passwords instantly
- ✅ Checks against 10 billion breached passwords via HaveIBeenPwned API
- ✅ Privacy-safe using k-anonymity — your password is never fully transmitted
- ✅ Returns exact number of times a password appeared in known breaches
- ✅ Instant feedback with strength rating (Weak, Fair, Strong, Very Strong)

## 🔬 How It Works

### Strength Scoring
| Category | Points |
|----------|--------|
| Password length (6-9 chars) | 1/3 |
| Password length (10-15 chars) | 2/3 |
| Password length (16+ chars) | 3/3 |
| Contains lowercase letters | +1 |
| Contains uppercase letters | +1 |
| Contains numbers | +1 |
| Contains special characters (!@#$%^&*) | +1 |

### Breach Checking (k-anonymity)
1. Password is converted to a SHA-1 hash
2. Only the first 5 characters of the hash are sent to the API
3. API returns all hashes starting with those 5 characters
4. Your app checks the result locally
5. Your full password never leaves your machine 🔒

## 🛠️ Technologies Used
- Java 17+
- Maven
- Apache HttpClient5
- HaveIBeenPwned API

## 🏗️ Project Structure
```
src/main/java/
├── Main.java              → Entry point, CLI loop
├── PasswordAnalyzer.java  → Password strength scoring
├── BreachChecker.java     → HaveIBeenPwned API integration
└── HashUtil.java          → SHA-1 hashing utility
```

## 🚀 How To Run

### Prerequisites

**Mac:**
- Install Homebrew: https://brew.sh
- Install Java: `brew install java`
- Install Maven: `brew install maven`

**Windows/Linux:**
- Install Java 17 or higher: https://adoptium.net
- Install Maven: https://maven.apache.org/install.html

### Steps
```bash
git clone https://github.com/bri-kesh/password-analyzer.git
cd password-analyzer
mvn package
java -jar target/password-analyzer.jar
```

## 📊 Example Output
```
╔═══════════════════════════════╗
║      🔐 Password Analyzer      ║
║  Type 'exit' to quit           ║
╚═══════════════════════════════╝

Enter a password to analyze: Amwrica@2026#

Password: Amwrica@2026#
Length Score: 2/3
Variety Score: 4/4
Total Score: 6/7
Strength: Very Strong 💪
Breach Check: ✅ Not found in any known data breaches
```

## 🧠 Concepts Learned
- SHA-1 cryptographic hashing
- k-anonymity privacy model
- REST API integration in Java
- Maven dependency management
- Object-Oriented Programming
- Single Responsibility Principle (SRP)

