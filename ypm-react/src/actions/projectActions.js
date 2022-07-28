import axios from "axios";
import { GET_ERRORS, GET_PROJECTS } from "./types";


export const createProject = (project, history) => async dispatch => {
    try {
        const res = await axios.post("http://localhost:8090/api/project/", project)
        history.push("/dashboard")
    } catch (error) {
        dispatch({type:GET_ERRORS,
            payload:error.response.data
        })
    }
};

export const getProjects = () => async dispatch => {
        const res = await axios.get("http://localhost:8090/api/project/all")
        dispatch({type:GET_PROJECTS,
            payload:res.data
        })

}
