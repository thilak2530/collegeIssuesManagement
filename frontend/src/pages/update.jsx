import React, { useEffect, useState } from "react";
import "../css/update.css";
import axios from "axios";


const Update = (props) => {
  const [refIds,setReIds]=useState([]);
  const [selectedStaff, setSelectedStaff] = useState("");
  const token = localStorage.getItem("token");
  const BASE_URL = process.env.REACT_APP_BACKEND_URL;



  const handleAssign = () => {
    if (!selectedStaff) {
      alert("Please select staff");
      return;
    }

    axios.patch(
      `${BASE_URL}/issues/assign`,
       {
        issueId: props.id,
        assignedMem: selectedStaff
      },
      {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json"
        }
      }
    )
    .then(() => {
      alert("Staff assigned successfully");
       props.onClose();
       props.onsucess();
    })
    .catch(err => {
      console.error(err);
      alert("Assignment failed");
    });
  };

  useEffect(()=>{
        axios.get(`${BASE_URL}/staffRefIds`,{
          headers:{
            "Authorization": `Bearer ${token}`,
            "Content-Type": "application/json"
          }
        })
        .then((res)=>{setReIds(res.data)})
        .catch(err=>{console.error("Login failed:", err);})
    },[token,BASE_URL])
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
                <td>{props.id}</td>
                <td>{props.title}</td>
                <td><span className={`status ${props.statuss}`}>{props.statuss}</span></td>
                <td>{props.roomno}</td>
              </tr>

              
            </tbody>
          </table>
        </div>

        {/* Status Update Section */}
        <div className="status-section">
          <h3 className="section-title">Assign Staff</h3>

          <div className="status-row">
            <label>Select staff mem</label>
            <select
              className="status-select"
              value={selectedStaff}
              onChange={(e) => setSelectedStaff(e.target.value)}
              >
              <option value="">-- Select Staff --</option>
              {refIds.map((refid, index) => (
                <option key={index} value={refid}>
                  {refid}
                </option>
              ))} 
            </select>
          </div>

         <button className="btn update-btn" onClick={handleAssign}>
            Assign
          </button>
          
        </div>

      </div>
      
  );
};

export default Update;
