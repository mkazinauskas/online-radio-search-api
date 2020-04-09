export const resolveApiUrl = () => {
    if(window.apiUrl){
        return window.apiUrl;
    }
    if(process.env.REACT_APP_API_URL){
        return process.env.REACT_APP_API_URL;
    }
}