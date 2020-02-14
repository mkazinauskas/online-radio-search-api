import { combineReducers } from 'redux';
import authReducer from '../../auth/reducers/authReducer';
import radioStationsReducer from '../../pages/radioStations/store/reducers/radioStationsReducer';

export default combineReducers(
    {
        auth: authReducer,
        radioStations: radioStationsReducer
    }
)