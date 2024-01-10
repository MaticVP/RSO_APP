import { Link } from "react-router-dom";
import React, { Component, useState }  from 'react';
import { useNavigate } from 'react-router-dom';
import './Login.css'
export default function Login()
{
    const [isLoggedIn, setIsLoggedIn] = useState(null);

    const [User, setPostData] = useState({
        username: '',
        password: '',
      });

      const navigate = useNavigate();
      
      const handleInputChangeUsername = (e) => {
        setPostData({
          ...User,
          username: e.target.value
        });
      };

      const handleInputChangePassword = (e) => {
        setPostData({
          ...User,
          password: e.target.value
        });
      };

      const handleLogin = async (e) => {
        e.preventDefault();
    
        try {
          const response = await fetch('/api/users/login?username='+User.username+'&password='+User.password);
          console.log(response)
          if (!response.ok) {
            throw new Error('GET for login failed');
          }

          const data = await response.json()
          console.log(response)
          setIsLoggedIn(data)
          console.log(data);
          if(data==0)
          {
            console.log('Loggin ok');
            navigate("/Profile",{state:{username: User.username, password: User.password}});
          };
          console.log('Loggin not ok');
          // You can handle the response data here if needed
        } catch (error) {
          console.error('Error submitting post:', error.message);
        }
      };
      


    return (
        <div className="login-container">
            <form action="#" className="form-container">
                <h1>
                    Login
                </h1>
                <div class="form-group">
                    <input type="text" placeholder="Username"  onChange={handleInputChangeUsername} value={User.username} required />
                    <box-icon type='solid' name='user'></box-icon>
                </div>
                <div className="form-group">
                    <input type="password" value={User.password} onChange={handleInputChangePassword} placeholder="Password" required />
                </div>
                <div className="form-group">
                    
                    <label>
                        <label><input type="checkbox" />Remember me</label>
                        <Link to="/recover">Forgot password?</Link>
                    </label>
                </div>
                <button type="submit" class="btn" onClick={handleLogin}>Login</button>
                <div class="register-link">
                    <p>Don't have an account? 
                         <p> </p>
                        <Link to="/register">Register</Link>
                    </p>
                </div>
            </form>
        </div>
    );
}