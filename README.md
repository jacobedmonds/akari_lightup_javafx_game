# Akari Puzzle Game

A desktop implementation of the classic **Akari (Light Up)** logic puzzle built in **Java** using **JavaFX**. This project was developed as part of a programming patterns course and follows the **Model-View-Controller (MVC)** design pattern along with the **Observer Pattern** for updating the user interface.

## Features

- Interactive JavaFX graphical interface
- Place and remove lamps with mouse clicks
- Real-time illumination and puzzle validation
- Detection of illegal lamp placements
- Numbered clue enforcement
- Multiple built-in puzzles
- Previous, Next, Random, and Reset puzzle controls
- Automatic puzzle completion detection

## Technologies

- Java
- JavaFX
- MVC Architecture
- Observer Pattern
- Object-Oriented Programming

## How to Play

The objective of Akari is to illuminate every corridor cell on the board by placing lamps.

Rules:
- Lamps illuminate horizontally and vertically until blocked by a wall.
- Lamps may not illuminate one another.
- Numbered clue cells must have exactly that many adjacent lamps.
- The puzzle is solved when all corridor cells are illuminated and all clues are satisfied.

## Project Structure

- **Model** – Manages puzzle state, lamp placement, and rule validation.
- **View** – JavaFX user interface components and board rendering.
- **Controller** – Handles user actions and puzzle navigation.

## Screenshots

_Add screenshots here if desired._

## Author

Jacob Edmonds

University of North Carolina at Chapel Hill  
B.S. Statistics and Analytics
Minor Computer Science
