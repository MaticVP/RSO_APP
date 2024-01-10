import React from 'react';

const StrokeWidthPicker = ({ strokeWidth, onStrokeWidthChange }) => {
  const handleStrokeWidthChange = (e) => {
    onStrokeWidthChange(Number(e.target.value));
  };

  return (
    <span>
      <label htmlFor="strokeWidthPicker">Choose stroke width:</label>
      <input
        type="range"
        id="strokeWidthPicker"
        min={1}
        max={20}
        step={1}
        value={strokeWidth}
        onChange={handleStrokeWidthChange}
      />
      <span>{strokeWidth}</span>
    </span>
  );
};

export default StrokeWidthPicker;