import React, { useState } from "react";
import { useNavigate} from "react-router-dom";
import "../css/app.css";
import axios from "axios";

function App() {

    const navigate = useNavigate();

  const [name,setName]=useState("");
  const [password,setpassword]=useState("");

   const BASE_URL = process.env.REACT_APP_BACKEND_URL;
   
    const handleLogin = (e) => {
    e.preventDefault(); // stop page refresh

    axios.post(`${BASE_URL}/login`, {
      username: name,
      password: password
    })
    .then((response) => {
      localStorage.setItem("token",response.data);
      navigate("/raise_issue");
    })
    .catch(error => {
        alert("incorrect credentials");
      console.error("Login failed:", error);
    });
  };


  return (
    <div className="auth-container">
      <div className="auth-card">

        <h2 className="auth-title">Login</h2>
        <p className="auth-subtitle">
          Enter your credentials to continue
        </p>

        <form className="auth-form" onSubmit={handleLogin}>

          <div className="form-group">
            <label>Email</label>
            <input onChange={(e)=>{setName(e.target.value)}} type="text" placeholder="name@example.com" />
          </div>

          <div className="form-group">
            <label>Password</label>
            <input onChange={(e)=>{setpassword(e.target.value)}}  type="password" placeholder="Enter password" />
          </div>

          <button className="auth-button">
            Sign In
          </button>

        </form>

        <p className="auth-footer">
          Don’t have an account? <span>Sign up</span>
        </p>

      </div>
    </div>
  );
}

export default App;



