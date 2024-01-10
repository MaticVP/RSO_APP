import { Link, useLocation } from "react-router-dom";
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import './Profile.css'
//import "./Profile.css"
function Profile() {
  //document.body.style.background = `rgb(37, 39, 66)`;
  const location = useLocation();
  console.log(location)
  const [imageData, setImageData] = useState(null);
  const [data,setData] = useState(null)

  useEffect(() => {
    const fetchImage = async () => {
      
        const response = await fetch('/api/users/get-photo?username='+location.state.username);
        if (response.ok) {
            const blob = await response.blob();
            setImageData(URL.createObjectURL(blob));
        }
      
    };

    const fetchData = async () => {
      try {
        const response = await fetch('/api/users/get-about?username='+location.state.username);
        
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }

        const data = await response.json();

        console.log('GET request successful:', data);
        setData(data)
      } catch (error) {
        console.error('Error during GET request:', error.message);
      }


    };

    fetchImage();
    fetchData();
  }, []);


//<Link to={"/drawing"} state={{username:location.state.username}}>Draw</Link>
  return (
    <div className="pageClass">
      <div className="nav-bar">
          <nav className="nav">
              <img src={imageData} alt="Fetched" />
              <label>Welcome {location.state.username}</label>
              <Link to={"/profile"} state={{username:location.state.username}}>Home</Link>
              <Link to={"/projects"} state={{username:location.state.username}}>Projects</Link>
              <Link to={"/settings"} state={{username:location.state.username}}>Settings</Link>
              <Link to={"/"} state={{username:location.state.username}}>Sign out</Link>
          </nav>
      </div>
      <div className="ProfileInfo">
        <label>Username</label>
        <p>{location.state.username}</p>
        <label>about</label>
        <p>
          {data}
       </p>
      </div>
    </div>
  );
}

export default Profile;