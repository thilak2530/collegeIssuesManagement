import React from "react";
import { BrowserRouter as Router, Routes, Route ,Navigate} from "react-router-dom";
import Login from "./login";
import Home from "./reportissuepage";
import Staff from "./staff";
import Admin from "./admin";
import ProtectedRoute from "../utils/protectedroutes";


function App(){
  return (
    <Router>
       
      <Routes>
        <Route path="/" element={<Navigate to={"/login"}/>} />
        <Route path="/login" element={<Login/>}></Route>
        <Route path="/raise_issue" element={<ProtectedRoute allowedRoles={["user"]}><Home/></ProtectedRoute>}></Route>
        <Route path="/staffpage" element={<ProtectedRoute allowedRoles={["staff","admin" ]}><Staff/></ProtectedRoute>}></Route>
        <Route path="/adminpage" element={<ProtectedRoute allowedRoles={["admin"]}><Admin/></ProtectedRoute>}></Route>
     
      </Routes>
    </Router>
    
  );
}


export default App;