import React, { useCallback, useEffect, useState } from "react";
import "../css/adminpage.css";
import Update from "./update";
import { XCircle } from "lucide-react";
import Box from "../components/box";
import axios from "axios";


const Admin = () => {
  const [refIds,setReIds]=useState([]);
  const [selectedStaff, setSelectedStaff] = useState("");
  const token = localStorage.getItem("token");
  const BASE_URL = process.env.REACT_APP_BACKEND_URL;
  const [formData, setFormData] = useState({
    id: "",
    refId:"",
    title: "",
    status: "",
    roomno: "",
    refIds:[]
   
  });
  const [numData, setNumData] = useState({
    
    totalIssues:0,
    totalPending:0,
    totalResolved:0,
    totalUsers: 0
   
  });
  const [issues,setIssues]=useState([])



    const handleAssign = () => {
      if (!selectedStaff) {
        alert("Please select staff");
        return;
      }
  
      axios.patch(
        `${BASE_URL}/issues/assign`,
         {
          issueId: formData.id,
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
         
      })
      .catch(err => {
        console.error(err);
        alert("Assignment failed");
      });
    };




  const fetchAdminData = useCallback(() => {
         axios.get(`${BASE_URL}/data`,{
      headers: {
        "Authorization": `Bearer ${token}`,
        "Content-Type": "application/json"
      }
    })
    .then((res) => {setNumData(res.data);})
    .catch(console.error);


    axios.get(`${BASE_URL}/totalIssues`,{
      headers: {
        "Authorization": `Bearer ${token}`,
        "Content-Type": "application/json"
      }
    })
    .then((res) => {setIssues(res.data);})
    .catch(console.error);

  },[BASE_URL,token])

  useEffect(()=>{
      fetchAdminData();
       axios.get(`${BASE_URL}/staffRefIds`,{
          headers:{
            "Authorization": `Bearer ${token}`,
            "Content-Type": "application/json"
          }
        })
        .then((res)=>{setReIds(res.data)})
        .catch(err=>{console.error("Login failed:", err);})
  },[fetchAdminData,BASE_URL,token]);

    const [updatetrue,setupdatetrue]=useState(false);
    

  return (
     <>
        <div className={updatetrue ? "page blur" :"dashboard-page"}>
        <div className="dashboard-card">

            {/* Header */}
            <div className="dashboard-header">
            <h2 className="dashboard-title">Welcome, Admin</h2>
            <div className="dashboard-icons">
                <div className="icon-box">☰</div>
                <div className="icon-box">✉</div>
                <div className="profile-circle">J</div>
            </div>
            </div>

            {/* Content */}
            <div className="dashboard-content">

            {/* Stats */}
            <div className="stats-container">

            
               <Box 
                    class="stat-card stat-total"
                    class1="stat-label"
                    class2="stat-value"
                    script="Total Issues"
                    number={numData.totalIssues}              
                />
                <Box 
                    class="stat-card stat-pending"
                    class1="stat-label"
                    class2="stat-value"
                    script="Pending Issues"
                    number={numData.totalPending}            
                />
                <Box 
                    class="stat-card stat-resolved"
                    class1="stat-label"
                    class2="stat-value"
                    script="Resolved Issues"
                    number={numData.totalResolved}          
                />
                <Box 
                    class="stat-card stat-total"
                    class1="stat-label"
                    class2="stat-value"
                    script="Users "
                    number={numData.totalUsers}          
                />
                
            </div>

            {/* Recent Issues */}
            <div className="issues-section">
                <h3 className="section-title">Recent Issues</h3>

                <table className="issues-table">
                <thead>
                    <tr>
                    <th>Issue Raised</th>
                    <th>Title</th>
                    <th>Assigning</th>
                    <th>Room No</th>
                    </tr>
                </thead>
                 <tbody>
                    {issues.map((issue) => (
                    <tr
                        key={issue.id}
                        onClick={()=>{setupdatetrue(true);
                         
                           setFormData({
                                id: issue.id,
                                refId:issue.user.refId,
                                title: issue.title,
                                status: issue.status,
                                roomno: issue.location,
                            });
                         }

                        }
                        
                    >
                        <td>{issue.user.refId}</td>
                        <td>{issue.title}</td>
                        <td>
                        <span className={`status ${issue.assigned}`}>
                            {issue.assigned}
                        </span>
                        </td>
                        <td>{issue.location}</td>
                    </tr>
                    ))}
                </tbody>
                </table>

                
                

            </div>
            </div>

        </div>
        
        </div>
     

      {updatetrue && (
        <div className="modal-overlay" >
          
            <Update 
                
              
                refId={formData.refId}
                refIds={refIds}
                title={formData.title}
                statuss={formData.status}
                roomno={formData.roomno}
                text="staff"
                onClose={() => setupdatetrue(false)}
                onsucess={fetchAdminData}
                onrequest={()=>handleAssign()}
                selectedStaff={selectedStaff}
                setSelectedStaff={setSelectedStaff}

                
            />
            <XCircle strokeWidth={1} color="black" className="crossicon" size={16} onClick={() => setupdatetrue(false)}/>
          
        </div>
      )}
    </>
  );
};

export default Admin;
