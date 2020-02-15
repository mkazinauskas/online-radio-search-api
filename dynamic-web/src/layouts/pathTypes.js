export const HOME = '/';
export const RADIO_STATIONS = '/radio-stations';
export const SONGS = '/songs';
export const RADIO_STATION_STREAMS = '/radio-stations/:radioStationId/streams';

export const createURLRadioStationStreams = (radioStationId) =>{
    return RADIO_STATION_STREAMS.replace(':radioStationId', radioStationId);
}