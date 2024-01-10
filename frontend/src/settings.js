import { Link, useLocation } from "react-router-dom";
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import './settings.css'

export default function Settings()
{
    const location = useLocation();
    const [imageData, setImageData] = useState(null);
    const [selectedFile, setSelectedFile] = useState(null);
    const [userName, setUserName] = useState('');
    const [password, setPasswword] = useState('');
    const [about, setAbout] = useState('');

    useEffect(() => {
      const fetchImage = async () => {
        
          const response = await fetch('/api/users/get-photo?username='+location.state.username);
          if (response.ok) {
              const blob = await response.blob();
              setImageData(URL.createObjectURL(blob));
          }
        
      };
  
      fetchImage();
    }, []);
  
    const handleFileChange = (event) => {
      setSelectedFile(event.target.files[0]);
    };
  
    const handleUserNameChange = (event) => {
      setUserName(event.target.value);
    };
    
    const handleAboutChange = (event) => {
      setAbout(event.target.value);
    };

    const handlePassChange = (event) => {
      setPasswword(event.target.value);
    };
    const handleUpload = () => {
      const formData = new FormData();
      formData.append('image', selectedFile);
      formData.append('username', userName);
  
      axios.post('/api/users/upload-photo', formData)
        .then(response => {
          console.log(response.data);
          // Handle success, e.g., display a success message
        })
        .catch(error => {
          console.error('Error uploading image:', error);
          // Handle error, e.g., display an error message
        });

        const ProfileData = new FormData();
        ProfileData.append('username', userName);
        ProfileData.append('password', password);
        ProfileData.append('description', userName);


        axios.post('/api/users/update-profile', ProfileData)
        .then(response => {
          console.log(response.data);
          // Handle success, e.g., display a success message
        })
        .catch(error => {
          console.error('Error uploading image:', error);
          // Handle error, e.g., display an error message
        });

    };
    
    

    return (
      <div className="pageClass">
          <div className="nav-bar">
              <nav className="nav">
              <img src={imageData} alt="Fetched" />
              <Link to={"/profile"} state={{username:location.state.username}}>Home</Link>
              <Link to={"/projects"} state={{username:location.state.username}}>Projects</Link>
              <Link to={"/settings"} state={{username:location.state.username}}>Settings</Link>
              <Link to={"/"} state={{username:location.state.username}}>Sign out</Link>
              <Link to={"/"}>Sign out</Link>
              </nav>
          </div>
        <p>Profile image</p>
        <input type="file" onChange={handleFileChange} />
        <p>Username</p>
        <input type="text" placeholder="User Name" value={userName} onChange={handleUserNameChange} />
        <p>Password</p>
        <input type="password" placeholder="Password" value={password} onChange={handlePassChange} />
        <p>Profile description</p>
        <textarea placeholder="Write some thing about you" value={about} onChange={handleAboutChange} rows={4} cols={40} />
        <button onClick={handleUpload}>Update</button>
      </div>
    );
}