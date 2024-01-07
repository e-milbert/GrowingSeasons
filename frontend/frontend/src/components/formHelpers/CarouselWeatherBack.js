import Image from 'react-bootstrap/Image';
import sunnycloud from "../../images/sunnycloud.jpg"

export function CarouselWeatherBack() {
    return (
        <div className="image-container">
            <Image rounded fluid src={sunnycloud} className=" reduced-saturation"/>
        </div>
    )
}

