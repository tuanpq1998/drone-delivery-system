import axios from "axios";

export function getLocationByAddress(addressStr) {
    return axios({
        method: "GET",
        url: "http://dev.virtualearth.net/REST/v1/Locations?query=" + encodeURIComponent(addressStr) + "&key=" + process.env.REACT_APP_BINGMAP_API_KEY,
      }).catch((err) => {
        console.log(err);
      });
}