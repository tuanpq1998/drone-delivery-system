#Drone Delivery System

##folder
- database: MySQL dump file
- dronecontrol: Backend - Spring Java
- drone-manager-reactjs: Frontend - ReactJs

## required
- Ide: Eclipse, VSCode
- Java, NPM
- Ubuntu 18.04
- PX4 with jMAVSim
- MySQL

## build
- database: import sql file
- px4: [https://docs.px4.io/main/en/simulation/jmavsim.html](https://docs.px4.io/main/en/simulation/jmavsim.html)
- backend: 
	- edit database config `src/main/resources/application.properties`
	- add permission `chmod +x src/main/resources/sdk/mavsdk_server`
	- build and run [http://localhost:8080](http://localhost:8080)
- frontend:
	- `npm install`
	- create `.env`:
```
REACT_APP_BINGMAP_API_KEY = Ap4raFmKqev2eLT8Dj3pNN-73PtA4HTh0p6-5mRIJw8vpDS8ICL-Sg8080IvGYdg
REACT_APP_API_HOST = http://localhost:8080
```
	- `npm start` [http://localhost:3000](http://localhost:3000)
