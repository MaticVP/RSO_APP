import React from 'react';
import { Routes , Route } from 'react-router-dom';

import LOGIN from './Login';
import REGISTER from './Register';
import REVCOVER from './Recover'
import PROFILE from './Profile'
import SETTINGS from './settings'
import PROJECTS from './Projects'
import DRAWINGBOARD from './Drawer'

const Main = () => {
  return (
    <Routes> {/* The Switch decides which component to show based on the current URL.*/}
      <Route exact path='/' element={<LOGIN />} />
      <Route exact path='/register' element={<REGISTER />} />
      <Route exact path='/recover' element={<REVCOVER />} />
      <Route exact path='/profile' element={<PROFILE />} />
      <Route exact path='/settings' element={<SETTINGS />} />
      <Route exact path='/projects' element={<PROJECTS />} />
      <Route exact path='/drawing' element={<DRAWINGBOARD />} />
    </Routes>
  );
}

export default Main;