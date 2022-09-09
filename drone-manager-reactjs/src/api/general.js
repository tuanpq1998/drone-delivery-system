import axios from "axios";
import { authHeader } from "./account";

export function calculateDistance(points) {
    var total = 0;
    for (var i = 0; i < points.length - 1; i++) {
        total += distance(points[i][0],points[i+1][0], points[i][1],points[i+1][1])
    }
    return total;
}

function distance(lat1, lat2, lon1, lon2) {
  // The math module contains a function
  // named toRadians which converts from
  // degrees to radians.
  lon1 = (lon1 * Math.PI) / 180;
  lon2 = (lon2 * Math.PI) / 180;
  lat1 = (lat1 * Math.PI) / 180;
  lat2 = (lat2 * Math.PI) / 180;

  // Haversine formula
  let dlon = lon2 - lon1;
  let dlat = lat2 - lat1;
  let a =
    Math.pow(Math.sin(dlat / 2), 2) +
    Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);

  let c = 2 * Math.asin(Math.sqrt(a));

  // Radius of earth in kilometers. Use 3956
  // for miles
  let r = 6371;

  // calculate the result
  return c * r;
}

function rad2degr(rad) { return rad * 180 / Math.PI; }
function degr2rad(degr) { return degr * Math.PI / 180; }

/**
 * @param latLngInDeg array of arrays with latitude and longtitude
 *   pairs in degrees. e.g. [[latitude1, longtitude1], [latitude2
 *   [longtitude2] ...]
 *
 * @return array with the center latitude longtitude pairs in 
 *   degrees.
 */
export function getLatLngCenter(latLngInDegr) {
    var LATIDX = 0;
    var LNGIDX = 1;
    var sumX = 0;
    var sumY = 0;
    var sumZ = 0;

    for (var i=0; i<latLngInDegr.length; i++) {
        var lat = degr2rad(latLngInDegr[i][LATIDX]);
        var lng = degr2rad(latLngInDegr[i][LNGIDX]);
        // sum of cartesian coordinates
        sumX += Math.cos(lat) * Math.cos(lng);
        sumY += Math.cos(lat) * Math.sin(lng);
        sumZ += Math.sin(lat);
    }

    var avgX = sumX / latLngInDegr.length;
    var avgY = sumY / latLngInDegr.length;
    var avgZ = sumZ / latLngInDegr.length;

    // convert average x, y, z coordinate to latitude and longtitude
    var lng = Math.atan2(avgY, avgX);
    var hyp = Math.sqrt(avgX * avgX + avgY * avgY);
    var lat = Math.atan2(avgZ, hyp);

    return ([rad2degr(lat), rad2degr(lng)]);
}


export function getHomeLocation(role) {
  return axios({
    method: "GET",
    url: process.env.REACT_APP_API_HOST + "/api/v1/homeLocation",
    headers : authHeader(),
  }).catch((err) => {
    console.log(err);
  });
}

export function getTrackingMission(password, identifier) {
  return axios({
    method: "POST",
    url: process.env.REACT_APP_API_HOST + "/api/v1/tracking/"+identifier,
    data : {
      username : "",
      password
    }
  }).catch((err) => {
    console.log(err);
  });
}