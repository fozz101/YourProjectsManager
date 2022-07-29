import axios from "axios";
import { GET_ERRORS, GET_PROJECT, GET_PROJECTS, DELETE_PROJECT } from "./types";


export const createProject = (project, history) => async dispatch => {
    try {
        const res = await axios.post("/api/project/", project)
        history.push("/dashboard")
        dispatch({
            type:GET_PROJECT,
            payload: {}
        })
    } catch (error) {
        dispatch({type:GET_ERRORS,
            payload:error.response.data
        })
    }
};

export const getProjects = () => async dispatch => {
        const res = await axios.get("/api/project/all")
        dispatch({type:GET_PROJECTS,
            payload:res.data
        })

}

export const getProject = (id, history) => async dispatch => {
    try {
        const res = await axios.get(`/api/project/${id}`)
        dispatch({
            type:GET_PROJECT,
            payload: res.data
        })
    } catch (error) {
        history.push("/dashboard")
    }
        
    }

export const deleteProject = (id) => async dispatch => {
    if(window.confirm("This will be permenantely deleted! Continue ?")){
        const res = await axios.delete(`/api/project/${id}`)
        dispatch({
            type:DELETE_PROJECT,
            payload : id
        })
    }
    
    
}