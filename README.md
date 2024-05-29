# GrowingSeasons

! Application is refactored to an webapp. WIP

This application aimes at helping hobby gardeners like myself with crop rotation and planning sowing / planting of their plants. 
The current planting planner is a prototype and will be tested by me during this gardening season. Weather data is fetched from the Open-Meteo Api

Beyond its practical gardening applications, this project also serves as a personal journey into software development. As someone new to this field, I started the Plant Planner not only to organize my gardening but to learn about software development. Please be aware that, as a learning project, there may be unconventional pieces of code or potential issues that I have not yet identified. Feedback and contributions are welcome as they will help me grow and improve the application.

 If you have suggestions, bug reports, or feature requests, please open an issue on our GitHub page. For general discussions or questions, feel free to use the Discussions tab.


### Installation

Before you start, ensure you have Node.js and JDK 11 (or newer) installed on your machine. Also, make sure Maven is set up for managing Java dependencies.

1. Clone the repository:
 ```git clone https://github.com/e-milbert/plantplanner.git```

2. Install NPM dependencies:
```
cd plantplanner/frontend/frontend
npm install
```

3. Update NPM dependencies to the latest versions (optional): 
```npm update```

4. Install Maven dependencies:
```
cd ../../src
mvn install
```

5. Update Maven dependencies to the latest versions (optional):
```mvn versions:use-latest-versions```

6. To run the Spring Boot application:
   ```mvn spring-boot:run```
   
7. To start the front-end application:
```
cd ../frontend/frontend
npm start
```




