# SWSecurityProject

Phase 1 Java Swing car rental recommendation system with security requirements.

## Features
- Sign up
- Login for registered users only
- Password hashing with salt
- Text-file user storage
- Failed login attempt detection
- Input validation
- Car recommendation based on cost, then comfort
- Clear/re-enter after invalid input

## Project Structure
- `src/swsecurity/model` -> data classes
- `src/swsecurity/service` -> authentication, storage, business logic
- `src/swsecurity/ui` -> Swing user interfaces
- `src/swsecurity/util` -> validation helpers
- `data/users.txt` -> stored user accounts

## Run
Open the project in VS Code and run:

`src/swsecurity/Main.java`