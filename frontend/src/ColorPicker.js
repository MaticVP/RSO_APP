import React, { useState } from 'react';

const ColorPicker = ({ onColorChange }) => {
  const [selectedColor, setSelectedColor] = useState('#000000'); // Initial color: black

  const handleColorChange = (e) => {
    const newColor = e.target.value;
    setSelectedColor(newColor);
    onColorChange(newColor);
  };

  return (
    <span>
      <label htmlFor="colorPicker">Choose a color:</label>
      <input
        type="color"
        id="colorPicker"
        value={selectedColor}
        onChange={handleColorChange}
      />
    </span>
  );
};

export default ColorPicker;