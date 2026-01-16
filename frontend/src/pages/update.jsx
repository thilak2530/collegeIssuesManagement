import React from "react";
import "../css/update.css";



const Update = (props) => {

  return (

    <div className="manage-users-card">
        {/* Users Table */}
        <div className={"table-container"}>
          <table className="users-table">
            <thead>
              <tr>
                <th>User ID</th>
                <th>Title</th>
                <th>Status</th>
                <th>Room No</th>
              </tr>
            </thead>

            <tbody>
              <tr>
                <td>{props.refId}</td>
                <td>{props.title}</td>
                <td><span className={`status ${props.statuss}`}>{props.statuss}</span></td>
                <td>{props.roomno}</td>
              </tr>

              
            </tbody>
          </table>
        </div>

        {/* Status Update Section */}
        <div className="status-section">
          <h3 className="section-title">{"select "+props.text}</h3>

          <div className="status-row">
            <label>{props.text}</label>
            <select
              className="status-select"
              value={props.selectedStaff}
              onChange={(e) => props.setSelectedStaff(e.target.value)}
              >
              <option value="">-- Select --</option>
              {props.refIds.map((refid, index) => (
                <option key={index} value={refid}>
                  {refid}
                </option>
              ))} 
            </select>
          </div>

         <button className="btn update-btn" onClick={props.onrequest}>
            Assign
          </button>
          
        </div>

      </div>
      
  );
};

export default Update;
