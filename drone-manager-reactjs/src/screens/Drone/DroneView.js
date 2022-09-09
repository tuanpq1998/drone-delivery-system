import React, { Component } from "react";
import Heading from "../components/Heading";
import { ReactBingmaps } from "react-bingmaps";
import DroneList from "./DroneList";
import {getAllDrones} from "../../api/drone";
import DroneInfoGeneral from "./DroneInfoGeneral";
import SockJsClient from 'react-stomp';
import { startMission } from "../../api/mission";
import DroneInfo from "./DroneInfo";
import { getHomeLocation } from "../../api/general";
import { authHeader } from "../../api/account";

export default class DroneView extends Component {
  constructor(props) {
    super(props);
    this.state = {
      loading: false,
      drones: [],
      displayDroneIds: [],
      clientConnected : false,
      homeLocation : null,
      selectedId : null
    };

    this.subscribeOnMap = this.subscribeOnMap.bind(this);
    this.unSubscribeOnMap = this.unSubscribeOnMap.bind(this);
    this.sendMsgUpdateLocation = this.sendMsgUpdateLocation.bind(this);
    this.handleWSOnMessage = this.handleWSOnMessage.bind(this);
    this.handleStartMissionBtnClick = this.handleStartMissionBtnClick.bind(this);
  }
  
  
  componentDidMount() {
    getAllDrones().then((list) => {
      getHomeLocation().then((home) => {
        const that = this;
        // const intervalId = setInterval(that.sendMsgUpdateLocation, 5000);
        this.setState({
          drones: list.data,
          // intervalId, 
          homeLocation : home.data
        });
      })
    });
    
  }
  
  componentWillUnmount() {
    console.log("unmounted");
    const {intervalId} = this.state;
    clearInterval(intervalId);
  }
  
  sendMessage = () => {
    const { displayDroneIds } = this.state;
    console.log(JSON.stringify(displayDroneIds));
    this.clientRef.sendMessage('/app/getLocation', JSON.stringify(displayDroneIds));
  }
  
  sendMsgUpdateLocation() {
    const { displayDroneIds } = this.state;
    if (displayDroneIds.length > 0)
      this.clientRef.sendMessage('/app/getLocation', JSON.stringify(displayDroneIds));
  }

  subscribeOnMap(droneId) {
    const { displayDroneIds } = this.state;

    !displayDroneIds.includes(droneId) && this.setState({
      displayDroneIds: [...displayDroneIds, droneId],
      selectedId : droneId,
    });
  }

  unSubscribeOnMap(droneId) {
    const { displayDroneIds } = this.state;
    const newIds = displayDroneIds.filter(id => id !== droneId);
    this.clientRef.sendMessage('/app/unsubscribeLocation', droneId);
    this.setState({
      displayDroneIds : newIds,
      selectedId : newIds.length == 0 ? null : newIds[newIds.length - 1],
    })
  }

  handleWSOnMessage(msg) {
    const {drones} = this.state;
      const drone = drones.find(d => d.id === msg.id);

      drone.locationAltitude = msg.locationAltitude;
      drone.locationLatitude = msg.locationLatitude;
      drone.locationLongitude = msg.locationLongitude;
      drone.lastUpdatedAt = msg.lastUpdatedAt;
      drone.activeMission = msg.activeMission;

      this.setState({ drones })
  }

  handleStartMissionBtnClick(droneId) {
    startMission(droneId).then(data => {
      if (data.data && data.data.length > 0) {
        alert("Started!")
      }
    })
  }

  gerenateDronePushPin() {
    const {drones, displayDroneIds} = this.state;
    return drones.filter(d => d.locationAltitude != null && displayDroneIds.includes(d.id)).map((d) => ({
      location: [d.locationLatitude, d.locationLongitude],
      addHandler: "mouseover",
      infoboxOption: { 
        title: d.droneName,
        description: `Altitude : ${d.locationAltitude}
          <br/>Last update: ${new Date(d.lastUpdatedAt).toLocaleString()}` 
      },
      pushPinOption: {
        text: d.id,
        title: d.droneName,
        color: "blue",
      },
      infoboxAddHandler: { type: "click", callback: this.callBackMethod },
      pushPinAddHandler: { type: "click", callback: this.callBackMethod },
    }));
  }

  gerenateHomePushPin() {
    const {homeLocation} = this.state;
    return {
      location: [homeLocation.latitude, homeLocation.longitude],
      addHandler: "mouseover",
      infoboxOption: { 
        title: "Home",
        description: `Address: ${homeLocation.address}` 
      },
      pushPinOption: {
        title: "Home",
        color: "red",
      },
      infoboxAddHandler: { type: "click", callback: this.callBackMethod },
      pushPinAddHandler: { type: "click", callback: this.callBackMethod },
    }
  }

  generatePolylines() {
    const {drones, displayDroneIds, homeLocation, selectedId} = this.state;

    const droneObjs = drones.filter(d => d.activeMission != null && d.locationAltitude != null && displayDroneIds.includes(d.id));
    const res = [];
    droneObjs.forEach(drone => {
      res.push({
        location : [
          [homeLocation.latitude, homeLocation.longitude], 
          [drone.activeMission.senderLocationLatitude,drone.activeMission.senderLocationLongitude]
        ],
        option: { strokeColor: "red", strokeThickness: 2 },
        addHandler : {
          type : "mouseover",
          callback : this.handleMouserOverPolylines,
        }
      })
      res.push({
        location : [
          [drone.activeMission.senderLocationLatitude,drone.activeMission.senderLocationLongitude], 
          [drone.activeMission.receiverLocationLatitude,drone.activeMission.receiverLocationLongitude]
        ],
        option: { strokeColor: "red", strokeThickness: 2 },
      })
      res.push({
        location : [
          [drone.activeMission.receiverLocationLatitude,drone.activeMission.receiverLocationLongitude], 
          [homeLocation.latitude, homeLocation.longitude]
        ],
        option: { strokeColor: "green", strokeThickness: 2 },
      })
    })
    return res;
  }

  gerenateSenderAndReceiverPushPin() {
    const {drones, displayDroneIds} = this.state;
    const activeDrone = drones.filter(d => d.locationAltitude != null && displayDroneIds.includes(d.id) && d.activeMission!=null);
    const results = []
    activeDrone.forEach(drone => {
      results.push({
        location: [drone.activeMission.senderLocationLatitude,drone.activeMission.senderLocationLongitude],
        addHandler: "mouseover",
        infoboxOption: { 
          title: "Sender",
          description: `Address : ${drone.activeMission.senderAddress}` 
        },
        pushPinOption: {
          text: drone.id,
          title: "Sender",
          color: "blue",
        },
        infoboxAddHandler: { type: "click", callback: this.callBackMethod },
        pushPinAddHandler: { type: "click", callback: this.callBackMethod },
      })
      results.push({
        location: [drone.activeMission.receiverLocationLatitude,drone.activeMission.receiverLocationLongitude],
        addHandler: "mouseover",
        infoboxOption: { 
          title: "Receiver",
          description: `Address : ${drone.activeMission.receiverAddress}` 
        },
        pushPinOption: {
          text: drone.id,
          title: "Receiver",
          color: "blue",
        },
        infoboxAddHandler: { type: "click", callback: this.callBackMethod },
        pushPinAddHandler: { type: "click", callback: this.callBackMethod },
      })
    })
    return results;
  }

  // handleMouserOverPolylines() {
  //   console.log("handleMouserOverPolylines");
  // }

  render() {
    const { drones, clientConnected, homeLocation, displayDroneIds, selectedId } = this.state;
    
    const pushpins = this.gerenateDronePushPin();
    homeLocation && pushpins.push(this.gerenateHomePushPin());
    pushpins.push(...this.gerenateSenderAndReceiverPushPin());

    const polylines = this.generatePolylines();

    return (
      <>
      <SockJsClient url='http://localhost:8080/api/v1/chat-websocket' 
        topics={displayDroneIds.map(id => '/app/location/'+id)}
        headers={authHeader()}
        subscribeHeaders={authHeader()}
        onConnect={ () => { this.setState({ clientConnected: true }) } }
        onMessage={this.handleWSOnMessage}
        ref={ (client) => { this.clientRef = client }} />

      {/* <button onClick={this.sendMessage} value="Send" /> */}

        <Heading title="Drones" />
        <div className="content">
          <div className="container-fluid">
            <div className="row">
              <div className="col-md-4">
                <DroneList
                  drones={drones}
                  handleDisplayOnMap={this.subscribeOnMap}
                  handleHideOnMap={this.unSubscribeOnMap}
                  displayIds={displayDroneIds}
                  handleStartMission={this.handleStartMissionBtnClick}
                />
              </div>
              <div className="col-md-8">
                <ReactBingmaps
                  className="bing-map"
                  bingmapKey={process.env.REACT_APP_BINGMAP_API_KEY}
                  center={
                    homeLocation ? [homeLocation.latitude, homeLocation.longitude] :
                     [13.0827, 80.2707]
                  }
                  polylines={polylines ? polylines : null}
                  zoom={15}
                  infoboxesWithPushPins={pushpins}
                ></ReactBingmaps>

                
                {
                  selectedId == null ? 
                  <DroneInfoGeneral isWsConnect={clientConnected} drones={drones} />
                  : 
                  displayDroneIds.map(did => <DroneInfo key={did} drone={drones.find(d => d.id==did)} />)                  
                }
              </div>
            </div>
          </div>
        </div>
      </>
    );
  }
}
