//import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import React from 'react';
import Template from './Components/Template';
import Navbar from './Components/Navbar';
import Notification from './Components/Notification';
import Mainpage from './Components/Mainpage';
import Customer from './Components/Customer';
function App() {
  return (
    <div className="container mt-4">
        <div>
        <Navbar template={Template} notification={Notification} mainpage={Mainpage} customer={Customer}/>
        </div>
    </div>
  );
}

export default App;