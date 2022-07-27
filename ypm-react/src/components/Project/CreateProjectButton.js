import React from 'react'
import { Link } from 'react-router-dom';

function CreateProjectButton() {
  return (
    <React.Fragment>
    <Link to="/addProject" className="btn btn-lg btn-info">
    Create a Project
    </Link>
    </React.Fragment>
  )
}

export default CreateProjectButton;