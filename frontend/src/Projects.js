import { Link, useLocation } from "react-router-dom";
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import './Project.css'
//import "./Profile.css"
function Projects() {
    const location = useLocation();
    console.log(location)
    const [dataArray, setDataArray] = useState([]);
    const [imageData, setImageData] = useState(null);
    const [Project, setProjectData] = useState({
        project_name: '',
      });

    const [responseData, setResponseData] = useState(null);

    const navigate = useNavigate();

    useEffect(() => {
      const fetchImage = async () => {
        
          const response = await fetch('/api/users/get-photo?username='+location.state.username);
          if (response.ok) {
              const blob = await response.blob();
              setImageData(URL.createObjectURL(blob));
          }
        
      };
  
      fetchImage();

      const fetchData = async () => {
        try {
          const response = await fetch('/api/users/get-id?username='+location.state.username);
          
          if (!response.ok) {
            throw new Error('Network response was not ok');
          }
  
          const data = await response.json();

          console.log('GET request successful:', data);
          fetch('/api/draw/get-projects-by-id?id='+data)
          .then(response => response.json())
          .then(data => setDataArray(data))
          .catch(error => console.error('Error fetching data:', error));
          setResponseData(data)
        } catch (error) {
          console.error('Error during GET request:', error.message);
        }


      };
  
      fetchData();

      
    }, []);

    const handleInputChangeUsername = (e) => {
        setProjectData({
          ...Project,
          project_name: e.target.value
        });
      };

      const handleRecover = async (e) => {
        e.preventDefault();

        navigate("/drawing",{state:{username: location.state.username, project_name: Project.project_name, project_id: responseData}});
    
      };

  return (
    <div className="pageClass">
        <div className="nav-bar">
            <nav className="nav">
                <img src={imageData} alt="Fetched" />
                <label>Welcome {location.state.username}</label>
                <Link to={"/profile"} state={{username:location.state.username}}>Home</Link>
                <Link to={"/projects"} state={{username:location.state.username}}>Projects</Link>
                <Link to={"/settings"} state={{username:location.state.username}}>Settings</Link>
                <Link to={"/"}>Sign out</Link>
            </nav>
        </div>
        <div className="input-box">
                    <input type="text" placeholder="Project name" value={Project.project_name} onChange={handleInputChangeUsername} required />
                    <box-icon type='solid' name='project'></box-icon>
                </div>
        <button type="submit" className="btn" onClick={handleRecover}>Start project</button>
        <h2>Projects:</h2>
        <ul>
            {dataArray.map((item, index) => (
            <li key={index}>{item}</li>
            ))}
        </ul>
    </div>
  );
}

export default Projects;