import './Loading.scss'
export default function Loading() {
    return (
        <div className='loading-wrapper'>
            <div className="spinner">
                <svg className="circular" viewBox="25 25 50 50">
                    <circle className="path" cx="50" cy="50" r="20" fill="none" strokeWidth="3" strokeMiterlimit="10">
                    </circle>
                </svg>
            </div>
        </div>
    )
}