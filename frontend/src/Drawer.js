import React, { useState, useEffect, useRef } from 'react';
import { Link, useLocation } from "react-router-dom";
import {Stage, Layer, Line } from 'react-konva';
import ColorPicker from './ColorPicker';
import axios from 'axios';
import StrokeWidthPicker from './StrokeWidthPicker';
import { Placeholder } from 'react-bootstrap';
import './drawer.css'


const DrawingBoard = () => {
    const location = useLocation();
    console.log(location)
    const stageRef = useRef();
  const [layers, setLayers] = useState([[]]);
  const [currentLayerIndex, setCurrentLayerIndex] = useState(0);
  const isDrawing = useRef(false);
  const [currentColor, setCurrentColor] = useState('#000000');
  const [isErasing, setErasing] = useState(false);
  const [strokeWidth, setStrokeWidth] = useState(5);
  const [Project, setProjectData] = useState(location.state.project_name);
  const [backgroundImage, setBackgroundImage] = useState(null);


  const handleSaveImage = async (e) => {
    e.preventDefault();
    // Send image data to the Spring Boot backend

        const stage = stageRef.current;
        const dataURL = stage.toDataURL({ mimeType: 'image/png' });
    
        const formData = new FormData();
        formData.append('image', dataURL);
        formData.append('username', location.state.username );
        formData.append('project_name', location.state.project_name != null ? location.state.project_name : "Placeholder");
        console.log(formData)
        axios.post('/api/draw/save-project', formData,{headers: {
            "Content-Type": "multipart/form-data",
          }})
        .then(response => {
          console.log(response.data);
          // Handle success, e.g., display a success message
        })
        .catch(error => {
          console.error('Error uploading image:', error);
          // Handle error, e.g., display an error message
        });

        e.preventDefault();
    
        try {
          const response = await fetch('/api/draw/save-projects-by-id?project_name='+location.state.project_name+'&id='+location.state.project_id+'&width='+window.innerWidth+'&height='+window.innerHeight, {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
          });
    
          if (!response.ok) {
            throw new Error('Failed to submit the post');
          }
        
          console.log('Post submitted successfully');
          // You can handle the response data here if needed
        } catch (error) {
          console.error('Error submitting post:', error.message);
        }


    }
  useEffect(() => {
    document.body.style.cursor = isDrawing.current ? 'crosshair' : isErasing ? 'cell' : 'default';

    const fetchImage = async () => {
      
      const response = await fetch('/api/draw/get-project?project_name='+location.state.project_name+'&username='+location.state.username);
      console.log(response)
      if (response.ok) {
          const blob = await response.blob();
          setBackgroundImage(URL.createObjectURL(blob));
          console.log(backgroundImage)
      }
    
  };

  fetchImage();

  }, [isDrawing.current, isErasing]);

  const handleMouseDown = () => {
    isDrawing.current = true;
    const lineColor = isErasing ? 'white' : currentColor;
    const updatedLayers = [...layers];
    const currentLayer = updatedLayers[currentLayerIndex];
    currentLayer.push({ color: lineColor, points: [], strokeWidth: strokeWidth });
    setLayers(updatedLayers);
  };

  const handleMouseMove = (e) => {
    if (!isDrawing.current) return;

    const pos = e.target.getStage().getPointerPosition();
    const updatedLayers = [...layers];
    const currentLayer = updatedLayers[currentLayerIndex];
    const lastLine = currentLayer[currentLayer.length - 1];
    lastLine.points = lastLine.points.concat([pos.x, pos.y]);
    setLayers(updatedLayers);
  };

  const handleMouseUp = () => {
    isDrawing.current = false;
  };

  const handleColorChange = (newColor) => {
    setCurrentColor(newColor);
    setErasing(false);
  };

  const toggleEraser = () => {
    setErasing(!isErasing);
  };

  const handleStrokeWidthChange = (newWidth) => {
    setStrokeWidth(newWidth);
  };

  const handleClearCanvas = () => {
    const updatedLayers = layers.map((layer) => []);
    setLayers(updatedLayers);
  };

  const handleAddLayer = () => {
    setLayers([...layers, []]);
    setCurrentLayerIndex(layers.length);
  };

  const handleRemoveLayer = () => {
    if (layers.length > 1) {
      const updatedLayers = layers.filter((_, index) => index !== currentLayerIndex);
      setLayers(updatedLayers);
      setCurrentLayerIndex(Math.min(currentLayerIndex, updatedLayers.length - 1));
    }
  };

  return (
    <div className="pageClass">
      <ColorPicker onColorChange={handleColorChange} />
      <StrokeWidthPicker strokeWidth={strokeWidth} onStrokeWidthChange={handleStrokeWidthChange} />
      <button onClick={toggleEraser}>{isErasing ? 'Exit Eraser' : 'Eraser'}</button>
      <button onClick={handleClearCanvas}>Clear Canvas</button>
      <button onClick={handleAddLayer}>Add Layer</button>
      <button onClick={handleSaveImage}>Save image</button>
      <button onClick={handleRemoveLayer} disabled={layers.length === 1}>
        Remove Layer
      </button>
      <div>
        Layers:
        {layers.map((layer, index) => (
          <button
            key={index}
            onClick={() => setCurrentLayerIndex(index)}
            style={{
              fontWeight: index === currentLayerIndex ? 'bold' : 'normal',
            }}
          >
            {index + 1}
          </button>
        ))}
      </div>
      <Stage
        width={window.innerWidth}
        height={window.innerHeight}
        onMouseDown={handleMouseDown}
        onMousemove={handleMouseMove}
        onMouseup={handleMouseUp}
        ref={stageRef}
      >
        <Layer>
          {layers.map((layer, index) => (
            <React.Fragment key={index}>
              {layer.map((line, i) => (
                <Line
                  key={i}
                  points={line.points}
                  stroke={line.color}
                  strokeWidth={line.strokeWidth}
                />
              ))}
            </React.Fragment>
          ))}
        </Layer>
      </Stage>
    </div>
  );
};

export default DrawingBoard;