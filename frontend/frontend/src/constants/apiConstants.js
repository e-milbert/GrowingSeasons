const baseUrl = 'http://localhost:8080';

export const timelinePutUpdate = `${baseUrl}/timeline`;
export const timelinesGetByPlant = `${baseUrl}/actiontimes`
export const timelinesForSinglePlant = `${baseUrl}/actiontimes/{plantid}`

export const colorsPostColor = `${baseUrl}/color`;
export const colorsGetAllColors = `${baseUrl}/colors`;
export const colorsDeleteColor = `${baseUrl}/color/{id}`;

export const exposurePostExposure = `${baseUrl}/exposure`;
export const exposureGetAllExposures = `${baseUrl}/exposures`;
export const exposureDeleteExposure = `${baseUrl}/delete/{id}`;

export const germinationPostGermination = `${baseUrl}/germination`;
export const germinationGetAllGerminations = `${baseUrl}/germinations`;
export const germinationDeleteGermination = `${baseUrl}/germination/{id}`;

export const growthPostGrowth = `${baseUrl}/growth`;
export const growthGetAllGrowth = `${baseUrl}/growths`;
export const growthDeleteGrowth = `${baseUrl}/growth/{id}`;

export const plantPostPlant = `${baseUrl}/plant`;
export const plantPutPlant = `${baseUrl}/plant/update`;
export const plantGetSingleById = `${baseUrl}/plant/{id}`

export const plantGetAllGrowth = `${baseUrl}/growths`;
export const plantDeleteGrowth = `${baseUrl}/growth/{id}`;
export const plantPostAddTimelineToPlant = `${baseUrl}/plant/{id}`;
export const plantGetAllPlants = `${baseUrl}/plants`;
export const plantDeletePlant = `${baseUrl}/plant/{id}`;
export const plantPlainGetAll = `${baseUrl}/plainplants`

export const soilPostSoil = `${baseUrl}/soil`;
export const soilDeleteSoil = `${baseUrl}/soil/{id}`;
export const soilGetAllSoils = `${baseUrl}/soils`;

export const weatherCurrent = `${baseUrl}/weather/current`;
export const soilTemp = `${baseUrl}/weather/soil`;
export const forecastWeek = `${baseUrl}/weather/forecast`;
export const updatePostWeatherSettings = `${baseUrl}/weather/update`

export const shutdownInit = `${baseUrl}/shutdown`;

