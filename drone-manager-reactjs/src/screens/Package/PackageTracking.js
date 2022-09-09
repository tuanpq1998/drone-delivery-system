import React, { Component } from "react";
import { ReactBingmaps } from "react-bingmaps";
import Heading from "../components/Heading";
import SockJsClient from "react-stomp";
import { getLatLngCenter } from "../../api/general";

export default class PackageTracking extends Component {
  constructor(props) {
    super(props);
    this.state = {
      packageObj: null,
      password: null,
    };

    this.handleWSOnMessage = this.handleWSOnMessage.bind(this);
  }

  componentDidMount() {
    const { packageObj, password } = this.props;
    this.setState({ packageObj, password });
  }

  generateTopic() {
    const { packageObj, password } = this.state;
    if (packageObj && password)
      return [
        "/appTracking/locationTracking/" +
          packageObj.missionIdentifier +
          "." +
          password,
      ];
    else return [];
  }

  handleWSOnMessage(msg) {
    this.setState({
      packageObj: msg,
    });
  }

  generatePushpins() {
    const {packageObj} = this.state;
    return [
      {
        location : [packageObj.senderLocationLatitude, packageObj.senderLocationLongitude],
        option: {
          title: "Sender",
          color: "blue",
        },
      }, {
        location : [packageObj.receiverLocationLatitude, packageObj.receiverLocationLongitude],
        option: {
          title: "Receiver",
          color: "blue",
        },
      }, {
        location : [packageObj.drone.locationLatitude, packageObj.drone.locationLongitude],
        option: {
          title: "Drone",
          color: "green",
        },
      }
    ]
  }

  generatePolylines() {
    const {packageObj} = this.state;
    return [{
      location: [
        [packageObj.senderLocationLatitude, packageObj.senderLocationLongitude],
        [packageObj.receiverLocationLatitude, packageObj.receiverLocationLongitude]
      ],
      option: { strokeColor: "red", strokeThickness: 2 },
    }]
  }

  generateCenter() {
    const {packageObj} = this.state;
    return getLatLngCenter([
      [packageObj.senderLocationLatitude, packageObj.senderLocationLongitude],
      [packageObj.receiverLocationLatitude, packageObj.receiverLocationLongitude]
    ])
  }


  render() {
    const { packageObj } = this.state;

    const timelineItems =
      packageObj && packageObj.logs && packageObj.logs.length > 0
        ? packageObj.logs
            .split("|")
            .filter((item) => item.trim() != "")
            .map((item) => {
              const time = item.split("::")[0];
              const content = item.split("::")[1];
              return (
                <div className="timeline-step" key={time}>
                  <div
                    className="timeline-content"
                    data-toggle="popover"
                    data-trigger="hover"
                    data-placement="top"
                    data-content=""
                    data-original-title={time}
                  >
                    <div className="inner-circle" />
                    <p className="h6 mt-3 mb-1">{time}</p>
                    <p className="h6 text-muted mb-0 mb-lg-0">{content}</p>
                  </div>
                </div>
              );
            })
        : [];

    if (packageObj && !packageObj.started) {
      timelineItems.unshift(
        <div className="timeline-step" key="Created!">
          <div
            className="timeline-content"
            data-toggle="popover"
            data-trigger="hover"
            data-placement="top"
            data-content="Created!"
            data-original-title={packageObj.createdAt}
          >
            <div className="inner-circle" />
            <p className="h6 mt-3 mb-1">{packageObj.createdAt}</p>
            <p className="h6 text-muted mb-0 mb-lg-0">Created!</p>
          </div>
        </div>,
        <div className="timeline-step" key="Waiting a drone ...">
          <div
            className="timeline-content"
            data-toggle="popover"
            data-trigger="hover"
            data-placement="top"
            data-content="Created!"
            data-original-title=""
          >
            <div className="inner-circle" />
            <p className="h6 mt-3 mb-1"></p>
            <p className="h6 text-muted mb-0 mb-lg-0">Waiting a drone ...</p>
          </div>
        </div>
      );
    }

    if (packageObj && !packageObj.finished) {
      timelineItems.push(
        <div className="timeline-step" key={Date.now()}>
          <div
            className="timeline-content"
            data-toggle="popover"
            data-trigger="hover"
            data-placement="top"
            data-original-title=""
          >
            <div className="inner-circle" style={{ display: "none" }} />
          </div>
        </div>
      );
    }

    const center = packageObj && packageObj.trackingStart ? this.generateCenter() : [21.002881461996303, 105.80972385178059];
    const pushpins = packageObj && packageObj.trackingStart ? this.generatePushpins() : null;
    const polylines = packageObj && packageObj.trackingStart ? this.generatePolylines() : null;

    const topics = this.generateTopic();
    return packageObj ? (
      <>
        <SockJsClient
          url="http://localhost:8080/api/v1/chat-websocket"
          topics={topics}
          onConnect={() => {
            this.setState({ clientConnected: true });
          }}
          onMessage={this.handleWSOnMessage}
          ref={(client) => {
            this.clientRef = client;
          }}
        />

        <Heading title="Tracking" />
        <div className="content">
          <div className="container-fluid">
            <div className="row">
              <div className="col-md-12">
                <div className="card">
                  <div className="card-header">
                    <h3 className="card-title">
                      Package #{packageObj.missionIdentifier}
                    </h3>
                  </div>
                  <div className="card-body row">
                    <div className="container col-md-12 mb-3">
                      <div className="row">
                        <div className="col">
                          <div
                            className="timeline-steps aos-init aos-animate"
                            data-aos="fade-up"
                          >
                            {timelineItems}
                          </div>
                        </div>
                      </div>
                    </div>

                    <dl className="row col-md-12">
                      <dt className="col-sm-2">Package name: </dt>
                      <dd className="col-sm-4">{packageObj.packageName}</dd>
                      <dt className="col-sm-2"> Size - Weight: </dt>
                      <dd className="col-sm-4">
                        {packageObj.size} - {packageObj.weight}
                      </dd>

                      <dt className="col-sm-2">Sender: </dt>
                      <dd className="col-sm-4">
                        {packageObj.seller.fullname} (
                        {packageObj.seller.username})
                      </dd>
                      <dt className="col-sm-2">Receiver: </dt>
                      <dd className="col-sm-4">{packageObj.receiverName}</dd>
                      <dd className="col-sm-4 offset-sm-2">
                        {packageObj.seller.telephone}
                      </dd>
                      <dd className="col-sm-4 offset-sm-2">
                        {packageObj.receiverTel}
                      </dd>
                      <dd className="col-sm-4 offset-sm-2">
                        {packageObj.senderAddress}
                      </dd>
                      <dd className="col-sm-4 offset-sm-2">
                        {packageObj.receiverAddress}
                      </dd>
                    </dl>
                    <ReactBingmaps
                      className="bing-map-tracking"
                      bingmapKey={process.env.REACT_APP_BINGMAP_API_KEY}
                      pushPins={pushpins}
                      polylines={polylines}
                      center={center}
                      zoom={15}
                    />
                    <div className="col-md-12">
                      <small className="form-text text-muted">
                        Reat-time tracking only available after we assign
                        package to drone!
                      </small>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </>
    ) : null;
  }
}
