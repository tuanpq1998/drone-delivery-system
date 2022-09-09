import axios from "axios";
import { authHeader, getCurrentInfo } from "./account";

function getCurrentRole() {
  const user = getCurrentInfo();
  return user == null ? null : user.role.toLowerCase();
}

export function getAllMissions() {
  return axios({
    method: "GET",
    url: process.env.REACT_APP_API_HOST + "/api/v1/" + getCurrentRole() + "/missions",
    headers : authHeader(),
  }).catch((err) => {
    console.log(err);
  });
}

export function getOneMission(missionId) {
  return axios({
    method: "GET",
    url: process.env.REACT_APP_API_HOST + "/api/v1/" + getCurrentRole() + "/missions/" + missionId,
    headers : authHeader(),
  }).catch((err) => {
    console.log(err);
  });
}

export function createMission(missionObj, sellerLocation) {
  return axios({
    method : "POST",
    headers : authHeader(),
    url: process.env.REACT_APP_API_HOST + "/api/v1/" + getCurrentRole() + "/missions",
    data: {
      ...missionObj,
      senderLocationLatitude : sellerLocation[0],
      senderLocationLongitude : sellerLocation[1]
    }
  }).catch(error => console.log(error))
}

export function updateMission(missionId, missionObj) {
  return axios({
    method : "PUT",
    url: process.env.REACT_APP_API_HOST + "/api/v1/" + getCurrentRole() + "/missions/" + missionId,
    headers : authHeader(),
    data: (missionObj)
  }).catch(error => console.log(error))
}

export function assignMission(missionObj, droneId) {
  return axios({
    method : "POST",
    headers : authHeader(),
    url: process.env.REACT_APP_API_HOST + "/api/v1/" + getCurrentRole() + "/drones/" + droneId + "/assignMission",
    data: missionObj
  }).catch(error => console.log(error))
}

export function startMission(droneId) {
  return axios({
    method: "GET",
    headers : authHeader(),
    url: process.env.REACT_APP_API_HOST + "/api/v1/" + getCurrentRole() + "/drones/" + droneId + "/startMission",
  }).catch((err) => {
    console.log(err);
  });
}