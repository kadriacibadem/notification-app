import React from "react";
import {
    BrowserRouter as Router,
    Routes,
    Route,
    Link
  } from "react-router-dom";

function Navbar(props) {
    const Template = props.template;
    const Notification = props.notification;
    const Mainpage = props.mainpage;
  return (
    <Router>
    <nav className="navbar navbar-expand-lg bg-body-secondary">
      <div className="container-fluid">
        <Link className="navbar-brand" to="/">
          Noti
        </Link>
        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarSupportedContent">
          <ul className="navbar-nav me-auto mb-2 mb-lg-0">
            <li className="nav-item">
              <Link className="nav-link active" aria-current="page" to="/template">
              Template Oluştur
              </Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link" to="/notification">
                Bildirim Gönder
              </Link>
            </li>
          </ul>
        </div>
      </div>
    </nav>
        <Routes>
            <Route path="/" element={<Mainpage/>} />
            <Route path="/template" element={<Template/>} />
            <Route path="/notification" element={<Notification/>} />
        </Routes>
    </Router>
  );
}

export default Navbar;
