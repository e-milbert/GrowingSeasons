import sunnycloud from "../../images/sunnycloud.jpg"

export function CarouselWeatherBack() {
    return (
        <div className="image-container">
            <img src={sunnycloud} className="img-fluid rounded reduced-saturation" alt="Sunny Cloud"/>
        </div>
    );
}

