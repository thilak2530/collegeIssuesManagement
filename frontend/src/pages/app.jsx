import React from "react";
import { BrowserRouter as Router, Routes, Route ,Navigate} from "react-router-dom";
import Login from "./login";
import Home from "./reportissuepage";
import Staff from "./staff";
import Admin from "./admin";


function App(){
  return (
    <Router>
       
      <Routes>
        <Route path="/" element={<Navigate to={"/login"}/>} />
        <Route path="/login" element={<Login/>}></Route>
        <Route path="/raise_issue" element={<Home/>}></Route>
        <Route path="/staffpage" element={<Staff/>}></Route>
        <Route path="/adminpage" element={<Admin/>}></Route>
     
      </Routes>
    </Router>
    
  );
}


export default App;