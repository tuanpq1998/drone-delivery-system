import axios from "axios";
import { authHeader, getCurrentInfo } from "./account";

function getCurrentRole() {
  const user = getCurrentInfo();
  return user == null ? null : user.role.toLowerCase();
}

export function getAllDrones() {
  return axios({
    method: "GET",
    url: process.env.REACT_APP_API_HOST + "/api/v1/"+ getCurrentRole() +"/drones",
    headers : authHeader(),
  }).catch((err) => {
    console.log(err);
  });
}

export function getAssignable() {
  return axios({
    method : "GET",
    url : process.env.REACT_APP_API_HOST + "/api/v1/"+ getCurrentRole() +"/drones/getAssignable",
    headers : authHeader(),
  }).catch((err) => {
    console.log(err);
  });
}

export function createDrone(drone) {
  return axios({
    method : "POST",
    url : process.env.REACT_APP_API_HOST + "/api/v1/"+ getCurrentRole() +"/drones",
    headers : authHeader(),
    data : drone
  }).catch((err) => {
    console.log(err);
  });
}
