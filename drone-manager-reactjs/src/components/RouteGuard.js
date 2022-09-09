import React from "react";
import { Navigate } from "react-router-dom";
import { getCurrentInfo } from "../api/account";

const RouteGuard = ({ component: Component, ...rest }) => {
  function hasJWT() {
    if (localStorage.getItem("token") == null) return null;
    return getCurrentInfo();
  }
  console.log(rest);
  
  if (hasJWT() != null) {
    const user = hasJWT();
    if (rest.hasAnyRole && !rest.hasAnyRole.includes(user.role.toUpperCase())) {
        return <Navigate to={{ pathname: "/" }} />
    }
    return <Component {...rest} user={hasJWT()} />
  }
  else return <Navigate to={{ pathname: "/login" }} />
  
};

export default RouteGuard;
