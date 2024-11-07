# CodeStat

**CodeStat** is a Spring-based web application designed to track and visualize coding platform statistics for users across platforms like Codeforces, LeetCode, and CodeChef. Users can view upcoming contests, review their coding history, and identify areas for improvement.

## Features

- **Main Dashboard**:
  - Displays a list of upcoming contests across Codeforces, LeetCode, and CodeChef.
  - Shows user ratings and profile details for each platform (where applicable).
  - Quick access buttons for each platform, allowing users to navigate directly to their profile or other relevant pages.

- **Platform-Specific Pages**:
  - Each platform (Codeforces, LeetCode, and CodeChef) has its own dedicated page.
  - **Profile Overview**:
    - Displays user profile information, such as name, rank, and rating (for platforms that support these details).
  - **Performance Analytics**:
    - A pie chart visualizes the number of questions solved in each topic, helping users identify their strengths and weaknesses.
    - A "Weak Areas" table lists tags or topics where the user has lower success rates.
  - **Contest Participation**:
    - A table shows recent contests the user has participated in, including results and performance details.
  - **Quick Stats**:
    - Mini tables summarize the total number of questions solved, contests attended, and a list of unsolved questions from recent contests.
    - Another table suggests questions related to weak tags identified in the weak areas table.

- **API Integration**:
  - Uses APIs from Codeforces and LeetCode to fetch user data, contest details, and question statistics.
  - **Note**: CodeChef currently does not provide a public API, so its page has limited functionality.

## Technologies Used

- **Programming Languages**: Java
- **Framework**: Spring Boot
- **Frontend**: HTML, CSS, JavaScript
- **Data Visualization**: Chart.js (for pie charts and tables)
- **API Integration**: Codeforces and LeetCode APIs
- **Version Control**: Git, GitHub

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/username/codestat.git
