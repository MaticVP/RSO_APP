import React, { Component, useState }  from 'react';
import { useNavigate } from 'react-router-dom';
import './Register.css'
export default function Register()
{
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

      const handleSubmit = async (e) => {
        e.preventDefault();
    
        try {
          const response = await fetch('/api/users/add?username='+User.username+'&password='+User.password, {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify(User),
          });
    
          if (!response.ok) {
            throw new Error('Failed to submit the post');
          }
          
          navigate("/Profile",{state:{username: User.username, password: User.password}});
          console.log('Post submitted successfully');
          // You can handle the response data here if needed
        } catch (error) {
          console.error('Error submitting post:', error.message);
        }
      };
    
    

    return (
        <div className="register-container">
            <form onSubmit={handleSubmit} className='form-container'>
                <h2>
                    Registration
                </h2>
                <div class="input-box" className='form-group'>
                    <input type="text" placeholder="Username" value={User.username} onChange={handleInputChangeUsername} required />
                    <box-icon type='solid' name='user'></box-icon>
                </div>
                <div class="input-box" className='form-group'>
                    <input type="password" value={User.password} onChange={handleInputChangePassword} placeholder="Password" required />
                </div>
                <button type="submit" class="btn">Register</button>
            </form>
        </div>
    );
}