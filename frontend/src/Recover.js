import { Link } from "react-router-dom";
import React, { Component, useState }  from 'react';
import { useNavigate } from 'react-router-dom';
import './Recover.css'
function showpass({User, isrecoverd})
{
    console.log(isrecoverd)
    if({isrecoverd}!=""){
        
        return <p>"User name for account:" + {User.username}</p>
    }

    return null
}

export default function Recover()
{
    const [isrecoverd, setIsRecoverd] = useState('');

    const [User, setPostData] = useState({
        username: '',
      });

      const navigate = useNavigate();
      
      const handleInputChangeUsername = (e) => {
        setPostData({
          ...User,
          username: e.target.value
        });
        setIsRecoverd("")
      };

      const handleRecover = async (e) => {
        e.preventDefault();
        console.log("Prep to send")
        try {
          const response = await fetch('/api/users/recover?username='+User.username);
    
          if (!response.ok) {
            throw new Error('GET for recover failed');
          }

          const data = await  response.text();
          setIsRecoverd(data)
          // You can handle the response data here if needed
        } catch (error) {
          console.error('Error submitting post:', error.message);
        }
      };

    return (
        <div className="forgot-container">
            <form action="#" className="form-container">
                <h1>
                    Password recovery
                </h1>
                <div className="form-group">
                    <input type="text" placeholder="Username" value={User.username} onChange={handleInputChangeUsername} required />
                    <box-icon type='solid' name='user'></box-icon>
                </div>
                <button type="submit" onClick={handleRecover} className="form-group">Recover</button>
                {(() => {
                    if (isrecoverd!="") {
                      return <p>User name for account: {User.username} password is {isrecoverd}</p>
                    }
                  })()}
                  
      
            </form>
        </div>
    );
}